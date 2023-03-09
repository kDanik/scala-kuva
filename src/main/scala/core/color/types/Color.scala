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
  def asAwtColor: java.awt.Color

  /**
   * Converts this color to ColorRgba
   * @return new ColorRgba with values equivalent to this color value
   */
  def asColorRgba: ColorRgba

  /**
   * Converts this color to ColorHsla (Hue, saturation, lightness, alpha)
   * @return new ColorHsla with values equivalent to this color value
   */
  def asColorHsla: ColorHsla

  /**
   * Converts this color to ColorHsva (Hue, saturation, value, alpha)
   * @return new ColorHsva with values equivalent to this color value
   */
  def asColorHsva: ColorHsva
}
