package com.example
package core.color.types

import core.support.{FloatWithAlmostEquals, Precision}

case class ColorHSVA(hue: Float, saturation: Float, value: Float, alpha: Float = 1f) extends Color, HSVAndHSL {
  override def asAWTColor: java.awt.Color = asColorRGBA.asAWTColor

  override def asColorRGBA: ColorRGBA = {
    // refer to formula for HSV to RGB conversion
    val h = hue / 60
    val c = value * saturation
    val x = c * (1 - (h % 2 - 1).abs)
    val m = value - c

    hslHsvToRGB(h, c, x, m, alpha)
  }

  override def asColorHSLA: ColorHSLA = {
    implicit val precision: Precision = Precision(0.0001f)

    val lightness = value * (1 - saturation / 2)
    val saturationHSL = if ((lightness ~= 0) || (lightness ~= 1)) 0 else (value - lightness) / lightness.min(1 - lightness)

    ColorHSLA(hue, saturationHSL, lightness, alpha)
  }

  override def asColorHSVA: ColorHSVA = this

  /**
   * Check if is this ColorHSVA almost equals to another ColorHSVA.
   * This function should be used instead of equals because of floating point precision problems
   */
  def almostEquals(obj: ColorHSVA): Boolean = {
    implicit val precision: Precision = Precision(0.001f)

    (hue ~= obj.hue) && (saturation ~= obj.saturation) && (value ~= obj.value) && (alpha ~= obj.alpha)
  }
}
