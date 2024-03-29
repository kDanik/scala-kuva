package com.example
package core.color.types

import spire.math.UByte

trait HsvaHsla {

  /**
   * Refer to formula for conversion of HSL or HSV to RGB. HSL and HSV have almost same formula,
   * except some initial calculations are slightly different
   */
  def hslHsvToRGB(h: Float, c: Float, x: Float, m: Float, alpha: UByte): ColorRgba = {
    val (r1, g1, b1) = hslToRGBHelper(h, c, x)

    ColorRgba(r1 + m, g1 + m, b1 + m, alpha)
  }

  private def hslToRGBHelper(h: Float, c: Float, x: Float): (Float, Float, Float) = {
    if (h < 1f) (c, x, 0)
    else if (h >= 1f && h < 2f) (x, c, 0)
    else if (h >= 2f && h < 3f) (0, c, x)
    else if (h >= 3f && h < 4f) (0, x, c)
    else if (h >= 4f && h < 5f) (x, 0, c)
    else (c, 0, x)
  }
}
