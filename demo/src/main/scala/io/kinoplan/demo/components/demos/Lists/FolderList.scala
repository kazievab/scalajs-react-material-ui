package io.kinoplan.demo.components.demos.Lists

import io.kinoplan.demo.components.{ComponentContainer, Layout}
import io.kinoplan.demo.styles.demos.Lists.{DefaultListStyle, ListStyle}
import io.kinoplan.scalajs.react.material.ui.core.{MuiAvatar, MuiList, MuiListItem, MuiListItemText}
import io.kinoplan.scalajs.react.material.ui.icons.{MuiBeachAccessIcon, MuiImageIcon, MuiWorkIcon}
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react.{BackendScope, ScalaComponent}
import scalacss.ScalaCssReactImplicits

object FolderList extends ScalaCssReactImplicits {
  case class Props(style: ListStyle)

  class Backend(t: BackendScope[Props, Unit]) {
    def render(props: Props): VdomElement = {
      val css = props.style

      div(
        ComponentContainer("Folder List")(
          div(css.root, css.rootPaper(Layout.isPaletteLight),
            MuiList()(
              MuiListItem()(
                MuiAvatar()(
                  MuiImageIcon()
                ),
                MuiListItemText(primary = VdomNode("Photos"), secondary = VdomNode("Jan 9, 2014"))
              ),
              MuiListItem()(
                MuiAvatar()(
                  MuiWorkIcon()
                ),
                MuiListItemText(primary = VdomNode("Work"), secondary = VdomNode("Jan 7, 2014"))
              ),
              MuiListItem()(
                MuiAvatar()(
                  MuiBeachAccessIcon()
                ),
                MuiListItemText(primary = VdomNode("Vacation"), secondary = VdomNode("July 20, 2014"))
              )
            )
          )
        )
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("FolderList")
    .renderBackend[Backend]
    .build

  def apply(style: ListStyle = DefaultListStyle) = component(Props(style))
}
