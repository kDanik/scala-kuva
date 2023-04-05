package com.example
package core.color.operations.blend

import core.color.types.ColorRgba

import org.scalatest.flatspec.AnyFlatSpec
import spire.math.UByte

/**
 * This tests can only check expected color output. The exact validity of blending can be better
 * checked with real images.
 *
 * TODO add tests for all blend types after they are tested with example images
 */
class ColorBlendingSpec extends AnyFlatSpec {
  "Color blending" should "work using simple alpha compositing algorithm" in {
    val backgroundColor = ColorRgba.apply(100, 30, 230, 100)
    val foregroundColor = ColorRgba.apply(30, 30, 80, 200)

    val expectedBlendedColor = ColorRgba.apply(37, 30, 95, 222)

    ColorBlending
      .blend(backgroundColor, foregroundColor, blendMode = BlendMode.SIMPLE_ALPHA_COMPOSITING)
      .equals(expectedBlendedColor)
  }
}
