package com.example
package core.color.types

import spire.math.UByte

trait ColorCompanion {

  /**
   * Converts color values from float (in standard range from 0 to 1f) to UByte, used for color
   * channel values in ColorRgba and for alpha in other channels. If value is outside of range
   * (0-1f), closest valid value will be used (so 0 or 1f)
   */
  def normalizeColorChannelValue(colorChannelValue: Float): UByte = {
    if (colorChannelValue < 0f) {
      UByte(0)
    } else if (colorChannelValue > 1f) {
      UByte(255)
    } else UByte((colorChannelValue * 255).round)
  }

  /**
   * Converts color values from integer (in standard range from 0 to 255) to UByte, used for color
   * channel values in ColorRgba and for alpha in other channels. If value is outside of range
   * (0-255), closest valid value will be used (so 0 or 255)
   */
  def normalizeColorChannelValue(colorChannelValue: Int): UByte = {
    if (colorChannelValue < 0) {
      UByte(0)
    } else if (colorChannelValue > 255) {
      UByte(255)
    } else UByte(colorChannelValue)
  }
}
