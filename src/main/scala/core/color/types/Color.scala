package com.example
package core.color.types

import core.support.Precision

/**
 * Base trait for different color spaces.
 */
trait Color {
  implicit val floatCompartmentPrecision: Precision = Precision(0.001f)

  /**
   * Converts this color to java.awt.Color (RGB(A) java color).
   *
   * @return
   *   new java.awt.Color with values equivalent to this color value
   */
  def asAwtColor: java.awt.Color

  /**
   * Converts this color to ColorRgba color space.
   *
   * @return
   *   new ColorRgba with values equivalent to this color value
   */
  def asColorRgba: ColorRgba

  /**
   * Converts this color to ColorHsla (Hue, saturation, lightness, alpha) color space.
   *
   * @return
   *   new ColorHsla with values equivalent to this color value
   */
  def asColorHsla: ColorHsla

  /**
   * Converts this color to ColorHsva (Hue, saturation, value, alpha) color space
   *
   * @return
   *   new ColorHsva with values equivalent to this color value
   */
  def asColorHsva: ColorHsva
}
