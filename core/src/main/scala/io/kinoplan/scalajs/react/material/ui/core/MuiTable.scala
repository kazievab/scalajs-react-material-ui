package io.kinoplan.scalajs.react.material.ui.core

import com.payalabs.scalajs.react.bridge.{ReactBridgeComponent, WithProps}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object MuiTable extends ReactBridgeComponent with MuiTableExtensions {
  override protected lazy val componentValue: js.Function = RawComponent

  @JSImport("@material-ui/core", "Table")
  @js.native
  object RawComponent extends js.Function

  def apply(
    classes: js.UndefOr[Map[ClassKey.Value, String]] = js.undefined,
    component: OptComponentPropType = js.undefined,
    padding: js.UndefOr[Padding.Value] = js.undefined
  ): WithProps = auto
}

trait MuiTableExtensions {
  object Padding extends Enumeration {
    type Value = String

    val default = "default"
    val checkbox = "checkbox"
    val dense = "dense"
    val none = "none"
  }

  object ClassKey extends Enumeration {
    type Value = String

    val root = "root"
  }
}
