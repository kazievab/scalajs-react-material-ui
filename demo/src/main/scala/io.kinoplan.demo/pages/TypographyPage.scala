package io.kinoplan.demo.pages

import io.kinoplan.demo.CssSettings._
import io.kinoplan.demo.router.AppRouter.Page
import io.kinoplan.scalajs.react.material.ui.core.{AppBar, Avatar, Backdrop, Typography}
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.all._

object TypographyPage extends StyleSheet.Inline {
  case class Props(router: RouterCtl[Page])

  class Backend(t: BackendScope[Props, Unit]) {
    def render(props: Props) = {
      div(
        div(
          AppBar()(
            div(
              Avatar(src = Some("http://www.luljettas.com/images/avatar/img-6.jpg"))
            )
          )
        ),
        div(
          Typography(color = Typography.Color.primary)("Header Default")
        ),
        div(
          Backdrop(open = true)
        )
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("TypographyPage")
    .renderBackend[Backend]
    .build

  def apply(router: RouterCtl[Page]) = component(Props(router))
}