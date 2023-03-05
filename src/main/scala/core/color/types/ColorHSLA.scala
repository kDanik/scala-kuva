package com.example
package core.color.types

import core.support.Precision
import core.support.FloatWithAlmostEquals

/**
 * HSL (for hue, saturation, lightness) is alternative representations of the RGB color model.
 * A in HSLA stands for alpha / transparency.
 *
 * @param hue        in range from 0f to 360f
 * @param saturation in range from 0f to 1f
 * @param lightness  in range from 0f to 1f
 * @param alpha      in range from 0f to 1f
 */
case class ColorHSLA(hue: Float, saturation: Float, lightness: Float, alpha: Float = 1f) extends Color, HSVAndHSL {
  override def asAWTColor: java.awt.Color = asColorRGBA.asAWTColor

  override def asColorRGBA: ColorRGBA = {
    // refer to formula for HSL to RGB conversion
    val h = hue / 60
    val c = (1 - (2 * lightness - 1).abs) * saturation
    val x = c * (1 - (h % 2 - 1).abs)
    val m = lightness - c / 2

    hslHsvToRGB(h, c, x, m, alpha)
  }

  override def asColorHSLA: ColorHSLA = this

  /**
   * Check if is this ColorHSLA almost equals to another ColorHSLA.
   * This function should be used instead of equals because of floating point precision problems
   */
  def almostEquals(obj: ColorHSLA): Boolean = {
    implicit val precision: Precision = Precision(0.001f)

    (hue ~= obj.hue) && (saturation ~= obj.saturation) && (lightness ~= obj.lightness) && (alpha ~= obj.alpha)
  }
}
