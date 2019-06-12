package io.kinoplan.demo.components.demos.Cards

import io.kinoplan.demo.components.ComponentContainer
import io.kinoplan.demo.styles.demos.Cards.{DefaultMediaCardStyle, MediaCardStyle}
import io.kinoplan.demo.utils.Constants.HOST
import io.kinoplan.scalajs.react.material.ui.core.{MuiButton, MuiCard, MuiCardActionArea, MuiCardActions, MuiCardContent, MuiCardMedia, MuiTypography}
import japgolly.scalajs.react.vdom.all.{VdomElement, _}
import japgolly.scalajs.react.{BackendScope, ScalaComponent}
import scalacss.ScalaCssReact._

object MediaCardFit {
  case class Props(style: MediaCardStyle)

  class Backend(t: BackendScope[Props, Unit]) {
    def render(props: Props): VdomElement = {
      val css = props.style

      div(
        ComponentContainer("Media fit")(
          MuiCard()(css.card,
            MuiCardActionArea()(
              MuiCardMedia(
                component = "img",
                image = Some(s"$HOST/static/images/cards/contemplative-reptile.jpg")
              )(css.mediaFit,
                alt := "Contemplative Reptile",
                height := "140",
                title := "Contemplative Reptile"
              ),
              MuiCardContent()(
                MuiTypography(component = "h2", variant = MuiTypography.Variant.h5, gutterBottom = true)(
                  "Lizard"
                ),
                MuiTypography(component = "p")(
                  "Lizards are a widespread group of squamate reptiles, with over 6,000 species, ranging " +
                    "across all continents except Antarctica"
                )
              )
            ),
            MuiCardActions()(
              MuiButton(size = MuiButton.Size.small, color = MuiButton.Color.primary)(
                "Share"
              ),
              MuiButton(size = MuiButton.Size.small, color = MuiButton.Color.primary)(
                "Learn More"
              )
            )
          )
        )
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("MediaCardFit")
    .renderBackend[Backend]
    .build

  def apply(style: MediaCardStyle = DefaultMediaCardStyle) = component(Props(style))
}