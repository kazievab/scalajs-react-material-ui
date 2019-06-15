package io.kinoplan.demo.components.demos.Dialogs

import io.kinoplan.scalajs.react.material.ui.core.{MuiButton, MuiDialog, MuiDialogActions, MuiDialogContent, MuiDialogTitle, MuiFormControlLabel, MuiRadio, MuiRadioGroup}
import japgolly.scalajs.react.vdom.Attr
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react.{BackendScope, Callback, ReactEventFromInput, ScalaComponent}

import scala.scalajs.js

object ConfirmationDialogRaw {
  case class Props(open: Boolean, onClose: js.Function1[String, Callback], value: String, dialogClassess: Map[String, String])

  case class State(value: String) {
    def setValue(value: String) = copy(value = value)
  }

  val options = List(
    "None", "Atria", "Callisto", "Dione", "Ganymede", "Hangouts Call", "Luna",
    "Oberon", "Phobos", "Pyxis", "Sedna", "Titania", "Triton", "Umbriel"
  )

  class Backend(t: BackendScope[Props, State]) {
    def componentWillReceiveProps(currentProps: Props, nextProps: Props): Callback = {
      t.modState(_.setValue(nextProps.value)).when_(nextProps.value != currentProps.value)
    }

    def handleCancel: Callback = {
      val props = t.props.runNow()

      props.onClose(props.value)
    }

    def handleOk: Callback = {
      val props = t.props.runNow()
      val state = t.state.runNow()

      props.onClose(state.value)
    }

    def handleChange: ReactEventFromInput => Callback = e => {
      val value = e.target.value

      t.modState(_.setValue(value))
    }

    def render(props: Props, state: State): VdomElement = {
      div(
        MuiDialog(
          open = props.open,
          disableBackdropClick = true,
          disableEscapeKeyDown = true,
          maxWidth = Some(MuiDialog.MaxWidth.xs),
          classes = props.dialogClassess
        )(
          aria.labelledBy := "confirmation-dialog-title",
          MuiDialogTitle()(id := "confirmation-dialog-title", "Phone Ringtone"),
          MuiDialogContent()(
            MuiRadioGroup()(
              aria.label := "Ringtone",
              name := "ringtone",
              value := state.value,
              onChange ==> handleChange
            )(
              options.toVdomArray(option =>
                MuiFormControlLabel(
                  control = Some(MuiRadio()().rawElement),
                  label = Some(option)
                )(
                  Attr("key") := option,
                  value := option
                )
              )
            )
          ),
          MuiDialogActions()(
            MuiButton(color = MuiButton.Color.primary)(
              onClick --> handleCancel,
              "Cancel"
            ),
            MuiButton(color = MuiButton.Color.primary)(
              onClick --> handleOk,
              "Ok"
            )
          )
        ).when(props.open)
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("ConfirmationDialogRaw")
    .initialStateFromProps(props => State(props.value))
    .renderBackend[Backend]
    .componentWillReceiveProps(f => f.backend.componentWillReceiveProps(f.currentProps, f.nextProps))
    .build

  def apply(
    open: Boolean,
    onClose: js.Function1[String, Callback],
    value: String,
    dialogClassess: Map[String, String]
  ) = component(Props(open, onClose, value, dialogClassess))
}

