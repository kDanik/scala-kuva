package com.example
package core.color.types

import core.support.{FloatWithAlmostEquals, Precision}

import spire.math.UByte

/**
 * HSL (for hue, saturation, lightness) is alternative representations of the RGB color model.
 * A in HSLA stands for alpha / transparency.
 *
 * @param hue        in range from 0f to 360f
 * @param saturation in range from 0f to 1f
 * @param lightness  in range from 0f to 1f
 * @param alpha      in range from 0 to 255
 */
final case class ColorHsla(hue: Float, saturation: Float, lightness: Float, alpha: UByte = UByte(255)) extends Color, HsvaHsla {
  override def asAwtColor: java.awt.Color = asColorRgba.asAwtColor

  override def asColorRgba: ColorRgba = {
    // refer to formula for HSL to RGB conversion
    val h = hue / 60
    val c = (1 - (2 * lightness - 1).abs) * saturation
    val x = c * (1 - (h % 2 - 1).abs)
    val m = lightness - c / 2

    hslHsvToRGB(h, c, x, m, alpha)
  }

  override def asColorHsla: ColorHsla = this

  override def asColorHsva: ColorHsva = {
    implicit val precision: Precision = Precision(0.001f)

    val value = lightness + saturation * lightness.min(1 - lightness)
    val saturationHSV = if (lightness ~= 0) 0 else 2 * (1 - lightness / value)

    ColorHsva(hue, saturationHSV, value, alpha)
  }

  /**
   * Check if is this ColorHsla almost equals to another ColorHsla.
   * This function should be used instead of equals because of floating point precision problems
   */
  def almostEquals(obj: ColorHsla): Boolean = {
    implicit val precision: Precision = Precision(0.001f)

    (hue ~= obj.hue) && (saturation ~= obj.saturation) && (lightness ~= obj.lightness) && (alpha == obj.alpha)
  }
}
