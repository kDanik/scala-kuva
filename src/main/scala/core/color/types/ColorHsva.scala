package com.example
package core.color.types

import core.support.{FloatWithAlmostEquals, Precision}

import spire.math.UByte

final case class ColorHsva(hue: Float, saturation: Float, value: Float, alpha: UByte = UByte(255))
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

  /**
   * Check if is this ColorHsva almost equals to another ColorHsva. This function should be used
   * instead of equals because of floating point precision problems
   */
  def almostEquals(obj: ColorHsva): Boolean = {
    (hue ~= obj.hue) && (saturation ~= obj.saturation) && (value ~= obj.value) && (alpha == obj.alpha)
  }
}
