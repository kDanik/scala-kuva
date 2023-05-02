package com.example
package core.color.types

import core.support.{FloatWithAlmostEquals, Precision}

import spire.math.UByte

final case class ColorHsva private (
    hue: Float,
    saturation: Float,
    value: Float,
    alpha: UByte = UByte(255))
    extends Color,
      HsvaHsla {
  override def asAwtColor: java.awt.Color = asColorRgba.asAwtColor

  override def asColorRgba: ColorRgba = {
    // refer to formula for HSV to RGB conversion
    val h = hue / 60
    val c = value * saturation
    val x = c * (1 - (h % 2 - 1).abs)
    val m = value - c

    hslHsvToRGB(h, c, x, m, alpha)
  }

  override def asColorHsla: ColorHsla = {
    val lightness = value * (1 - saturation / 2)
    val saturationHSL =
      if ((lightness ~= 0) || (lightness ~= 1)) 0
      else (value - lightness) / lightness.min(1 - lightness)

    ColorHsla(hue, saturationHSL, lightness, alpha)
  }

  override def asColorHsva: ColorHsva = this

  def copy(
      hue: Float = hue,
      saturation: Float = saturation,
      value: Float = value,
      alpha: UByte = alpha): ColorHsva =
    ColorHsva.apply(hue, saturation, value, alpha)

  /**
   * Checks if is this ColorHsva almost equals to another ColorHsva. This function should be used
   * instead of equals because of floating point precision problems
   */
  def almostEquals(obj: ColorHsva): Boolean = {
    (hue ~= obj.hue) && (saturation ~= obj.saturation) && (value ~= obj.value) && (alpha == obj.alpha)
  }
}

object ColorHsva extends ColorCompanion {
  def apply(hue: Float, saturation: Float, value: Float, alpha: UByte = UByte(255)): ColorHsva = {
    new ColorHsva(hue.max(0f).min(360f), saturation.max(0f).min(1f), value.max(0).min(1f), alpha)
  }
}
