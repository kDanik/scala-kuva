package com.example
package core.color.types

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

  override def asColorHSLA: ColorHSLA = this.asColorRGBA.asColorHSLA
}
