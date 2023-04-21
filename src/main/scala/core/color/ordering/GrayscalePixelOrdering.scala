package com.example
package core.color.ordering

import core.image.Pixel
import core.color.types.Color

object GrayscalePixelOrdering extends Ordering[Pixel] {

  /**
   * Compare 2 pixel (grayscale) by their color value. If not grayscale pixels used, compare will
   * produce false results
   */
  def compare(a: Pixel, b: Pixel): Int = GrayscaleColorOrdering.compare(a.color, b.color)
}
