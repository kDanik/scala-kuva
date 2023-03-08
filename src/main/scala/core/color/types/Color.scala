package com.example
package core.color.types

/**
 * Base trait for color format / type
 */
trait Color {
  /**
   * Converts this color to java.awt.Color
   * @return new java.awt.Color with values equivalent to this color value
   */
  def asAWTColor: java.awt.Color

  /**
   * Converts this color to ColorRGBA
   * @return new ColorRGBA with values equivalent to this color value
   */
  def asColorRGBA: ColorRGBA

  /**
   * Converts this color to ColorHSLA (Hue, saturation, lightness, alpha)
   * @return new ColorHSLA with values equivalent to this color value
   */
  def asColorHSLA: ColorHSLA

  /**
   * Converts this color to ColorHSVA (Hue, saturation, value, alpha)
   * @return new ColorHSVA with values equivalent to this color value
   */
  def asColorHSVA: ColorHSVA
}
