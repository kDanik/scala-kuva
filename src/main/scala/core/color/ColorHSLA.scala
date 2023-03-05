package com.example
package core.color

import spire.math.UByte

case class ColorHSLA(hue: Float, saturation: Float, lightness: Float, alpha: Float) extends Color {
  override def asAWTColor: java.awt.Color = asColorRGBA.asAWTColor

  override def asColorRGBA: ColorRGBA = {
    // refer to formula for HSL to RGB conversion
    val h = hue / 60
    val c = (1 - math.abs(2 * lightness - 1)) * saturation
    val x = c * (1 - math.abs(h % 2 - 1))
    val m = lightness - c / 2

    def hslToRGB(h: Float, c: Float, x: Float): (Float, Float, Float) = {
      if (h < 1f) (c, x, 0)
      else if (h >= 1f && h < 2f) (x, c, 0)
      else if (h >= 2f && h < 3f) (0, c, x)
      else if (h >= 3f && h < 4f) (0, x, c)
      else if (h >= 4f && h < 5f) (x, 0, c)
      else (c, 0, x)
    }

    val (r1, g1, b1) = hslToRGB(h, c, x)

    ColorRGBA.apply(((r1 + m) * 255).ceil.toInt, ((g1 + m) * 255).ceil.toInt, ((b1 + m) * 255).ceil.toInt, (255 * alpha).ceil.toInt)
  }

  override def asColorHSLA: ColorHSLA = this
}
