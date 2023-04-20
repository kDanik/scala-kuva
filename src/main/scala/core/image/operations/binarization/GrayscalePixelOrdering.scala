package com.example
package core.image.operations.binarization

import core.image.Pixel

object GrayscalePixelOrdering extends Ordering[Pixel] {

  /**
   * Compare 2 pixel (grayscale) by their color value. If not grayscale pixels used, compare will
   * produce false results
   */
  def compare(a: Pixel, b: Pixel): Int =
    a.color.asColorRgba.red.intValue.compare(b.color.asColorRgba.red.intValue)
}
