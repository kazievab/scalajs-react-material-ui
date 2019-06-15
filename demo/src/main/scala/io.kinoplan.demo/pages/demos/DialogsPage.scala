package io.kinoplan.demo.pages.demos

import io.kinoplan.demo.components.demos.Dialogs.{AlertDialog, ConfirmationDialog, CustomizedDialog, FormDialog, FullScreenDialog, MaxWidthDialog, ScrollDialog, SimpleDialog}
import io.kinoplan.demo.router.AppRouter.Page
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.all._

object DialogsPage {
  case class Props(router: RouterCtl[Page])

  class Backend(t: BackendScope[Props, Unit]) {
    def render(props: Props): VdomElement = {
      div(
        SimpleDialog(),
        AlertDialog(),
        FormDialog(),
        CustomizedDialog(),
        FullScreenDialog(),
        MaxWidthDialog(),
        ConfirmationDialog(),
        ScrollDialog()
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("DialogsPage")
    .renderBackend[Backend]
    .build

  def apply(router: RouterCtl[Page]) = component(Props(router))
}
