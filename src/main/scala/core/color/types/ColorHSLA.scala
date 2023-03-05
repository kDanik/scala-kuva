package com.example
package core.color.types

/**
 * HSL (for hue, saturation, lightness) is alternative representations of the RGB color model.
 * A in HSLA stands for alpha / transparency.
 *
 * @param hue        in range from 0f to 360f
 * @param saturation in range from 0f to 1f
 * @param lightness  in range from 0f to 1f
 * @param alpha      in range from 0f to 1f
 */
case class ColorHSLA(hue: Float, saturation: Float, lightness: Float, alpha: Float = 1f) extends Color {
  override def asAWTColor: java.awt.Color = asColorRGBA.asAWTColor

  override def asColorRGBA: ColorRGBA = {
    // refer to formula for HSL to RGB conversion
    val h = hue / 60
    val c = (1 - (2 * lightness - 1).abs) * saturation
    val x = c * (1 - (h % 2 - 1).abs)
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

    ColorRGBA.apply(((r1 + m) * 255).round,
      ((g1 + m) * 255).round,
      ((b1 + m) * 255).round,
      (255 * alpha).round)
  }

  override def asColorHSLA: ColorHSLA = this
}
