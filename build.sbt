addCommandAlias("restartWDS", "; demo/fastOptJS::stopWebpackDevServer; ~demo/fastOptJS::startWebpackDevServer")

val root = project.in(file(".")).settings(commonSettings).aggregate(facade, demo).settings(
  name                 := "scalajs-react-material-ui",
  // No, SBT, we don't want any artifacts for root.
  // No, not even an empty jar.
  publish              := {},
  publishLocal         := {},
  publishArtifact      := false,
  Keys.`package`       := file(""),
).settings(
  aggregate in doc := false
)

lazy val muiIconsGenerator = taskKey[Seq[File]]("mui-icons-generator")

def generateIcons(src: File, npm: File): Seq[File] = {
  val iconSources = (npm / "node_modules" / "@material-ui" / "icons" ) * ("*.js" -- "index.js" -- "index.es.js")

  val files: Seq[File] = iconSources.get.map(f => {
    val name = f.getName.stripSuffix(".js")
    val file = src / s"Mui$name.scala"

    IO.write(
      file,
      s"""package io.kinoplan.scalajs.react.material.ui.icons
         |
         |import com.payalabs.scalajs.react.bridge.{ReactBridgeComponent, WithProps}
         |
         |import scala.scalajs.js
         |import scala.scalajs.js.annotation.JSImport
         |import scala.scalajs.js.|
         |
         |object Mui$name extends ReactBridgeComponent with SvgIconExtensions {
         |
         |  override protected lazy val componentValue: js.Object = RawComponent
         |
         |  @JSImport("@material-ui/icons/$name", JSImport.Default)
         |  @js.native
         |  object RawComponent extends js.Object
         |
         |  def apply(
         |    classes: Map[ClassKey.ClassKey, String] = Map.empty,
         |    color: Color.Value = Color.inherit,
         |    component: Option[String | js.Function] = Some("svg"),
         |    fontSize: FontSize.Value = FontSize.default,
         |    nativeColor: Option[String] = None,
         |    shapeRendering: Option[String] = None,
         |    titleAccess: Option[String] = None,
         |    viewBox: String = "0 0 24 24"
         |  ): WithProps = auto
         |}
          """.stripMargin.trim
    )

    file
  })

  files
}

lazy val facade = (project in file("facade")).enablePlugins(ScalaJSBundlerPlugin).settings(commonSettings).settings(
  name := "scalajs-react-material-ui",
  scalaJSUseMainModuleInitializer  := false,
  npmDependencies in Compile ++= Seq(
    "react"              -> "16.7.0",
    "react-dom"          -> "16.7.0",
    "@material-ui/core"  -> "3.9.3",
    "@material-ui/icons" -> "3.0.2"
  ),
  libraryDependencies ++= Seq(
    "com.github.japgolly.scalajs-react" %%% "core"                 % "1.4.2",
    "com.github.japgolly.scalajs-react" %%% "extra"                % "1.4.2",
    "com.github.japgolly.scalacss"      %%% "core"                 % "0.5.6",
    "com.github.japgolly.scalacss"      %%% "ext-react"            % "0.5.6",
    "com.github.japgolly.scalacss"      %% "ext-scalatags"         % "0.5.6",
    "org.scala-js"                      %%% "scalajs-dom"          % "0.9.7",
    "org.typelevel"                     %%  "cats-core"            % "1.2.0",
    "com.payalabs"                      %%% "scalajs-react-bridge" % "0.8.0",
    "com.beachape"                      %%% "enumeratum"           % "1.5.13"
  ),
  muiIconsGenerator := generateIcons(
    (sourceManaged in Compile).value / "io" / "kinoplan" / "scalajs" / "react" / "material" / "ui" / "icons",
    (npmInstallDependencies in Compile).value
  ),
  sourceGenerators in Compile += muiIconsGenerator.taskValue
)

lazy val demo = (project in file("demo")).dependsOn(facade)
  .enablePlugins(ScalaJSBundlerPlugin)
  .settings(commonSettings).settings(
  scalaJSUseMainModuleInitializer  := true,
  webpackBundlingMode              := BundlingMode.LibraryOnly(),
  webpackDevServerExtraArgs        := Seq("--inline"),
  yarnExtraArgs                    := Seq("--silent"),
  webpackConfigFile in fastOptJS   := Some(baseDirectory.value / "dev.webpack.config.js"),
  // don't publish the demo
  publish                          := {},
  publishLocal                     := {},
  publishArtifact                  := false,
  Keys.`package`                   := file("")
)

lazy val commonSettings = Seq(
  version := "0.0.1",
  scalaVersion := "2.12.8",
  organization := "io.kinoplan",
  description := "scalajs-react facade for material-ui",
  webpackBundlingMode := BundlingMode.LibraryOnly(),
  useYarn := true,
  version in webpack := "4.29.3",
  version in startWebpackDevServer := "3.1.14",
  webpackCliVersion := "3.2.3",
  emitSourceMaps := false,
  scalacOptions := Seq(
    "-target:jvm-1.8",
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",                // Specify character encoding used by source files.
    "-explaintypes",                     // Explain type errors in more detail.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
    "-language:higherKinds",             // Allow higher-kinded types
    "-language:implicitConversions",     // Allow definition of implicit functions called views
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
    "-Xfuture",                          // Turn on future language features.
    "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
    "-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
    "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
    "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
    "-Xlint:option-implicit",            // Option.apply used implicit view.
    "-Xlint:package-object-classes",     // Class or object defined in package object.
    "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
    "-Xlint:unsound-match",              // Pattern match may not be typesafe.
    "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    "-Ypartial-unification",             // Enable partial unification in type constructor inference
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
    "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
    "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-nullary-unit",               // Warn when nullary methods return Unit.
    "-Ywarn-numeric-widen",              // Warn when numerics are widened.
    "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals",              // Warn if a local definition is unused.
    // "-Ywarn-unused:params",              // Warn if a value parameter is unused.
    // "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates",            // Warn if a private member is unused.
    "-Ywarn-value-discard",              // Warn when non-Unit expression results are unused.
    "-P:scalajs:sjsDefinedByDefault",
    "-Yrangepos"
  )
)
