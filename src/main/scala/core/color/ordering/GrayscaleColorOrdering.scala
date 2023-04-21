package com.example
package core.color.ordering

import core.color.types.Color

object GrayscaleColorOrdering extends Ordering[Color] {

  /**
   * Compare 2 colors (grayscale) by their color value. If not grayscale pixels used, compare will
   * produce false results
   */
  def compare(a: Color, b: Color): Int =
    a.asColorRgba.red.intValue.compare(b.asColorRgba.red.intValue)
}
