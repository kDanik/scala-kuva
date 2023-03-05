package com.example
package core.color.types

import core.support.Precision
import core.support.FloatWithAlmostEquals

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
}
