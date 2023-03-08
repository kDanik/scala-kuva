package com.example
package core.color.operations.inversion

import core.color.operations.inversion.ColorInversion.invertSingleColorChannelValue
import core.color.types.{Color, ColorRGBA}

import spire.math.UByte

object ColorInversion {

  /**
   * Color inversion is a photo effect that flips all colors to their opposite hue on the color wheel.
   * For example, white becomes black, green becomes magenta, and blue becomes orange.
   *
   * @param initialColor initial color in any color format
   * @param invertAlpha  should alpha channel also be inverted (by default false)
   * @return inverted color in ColorRGBA format
   */
  def invertColor(initialColor: Color, invertAlpha: Boolean = false): ColorRGBA = {
    val initialColorAsRGBA: ColorRGBA = initialColor.asColorRGBA

    ColorRGBA(
      invertSingleColorChannelValue(initialColorAsRGBA.red),
      invertSingleColorChannelValue(initialColorAsRGBA.green),
      invertSingleColorChannelValue(initialColorAsRGBA.blue),
      if (invertAlpha) invertSingleColorChannelValue(initialColorAsRGBA.alpha) else initialColorAsRGBA.alpha
    )
  }

  private def invertSingleColorChannelValue(initialColorChannelValue: UByte): UByte = {
    UByte(255) - initialColorChannelValue
  }
}
