package io.kinoplan.scalajs.react.material.ui.core

import com.payalabs.scalajs.react.bridge.{ReactBridgeComponent, WithPropsNoChildren}
import japgolly.scalajs.react.vdom.html_<^._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

object MuiRadio extends ReactBridgeComponent with MuiRadioExtensions with JsWriterImplicits {
  override protected lazy val componentValue: js.Function = RawComponent

  @JSImport("@material-ui/core", "Radio")
  @js.native
  object RawComponent extends js.Function

  def apply(
    checked: js.UndefOr[Boolean | String] = js.undefined,
    checkedIcon: js.UndefOr[VdomNode] = js.undefined,
    classes: js.UndefOr[Map[ClassKey.Value, String]] = js.undefined,
    color: js.UndefOr[Color.Value] = js.undefined,
    disableRipple: js.UndefOr[Boolean] = js.undefined,
    icon: js.UndefOr[VdomNode] = js.undefined,
    inputProps: js.UndefOr[js.Object] = js.undefined,
    inputRef: OptComponentRefType = js.undefined
  ): WithPropsNoChildren = autoNoChildren
}

trait MuiRadioExtensions {
  object Color extends Enumeration {
    type Value = String

    val primary = "primary"
    val secondary = "secondary"
    val default = "default"
  }

  object ClassKey extends Enumeration {
    type Value = String

    val root = "root"
    val checked = "checked"
    val disabled = "disabled"
    val colorPrimary = "colorPrimary"
    val colorSecondary = "colorSecondary"
  }
}
