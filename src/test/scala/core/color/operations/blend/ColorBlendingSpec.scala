package com.example
package core.color.operations.blend

import core.color.types.ColorRgba

import org.scalatest.flatspec.AnyFlatSpec
import spire.math.UByte

/**
 * This tests can only check expected color output. The exact validity of blending can be better
 * checked with real images.
 */
class ColorBlendingSpec extends AnyFlatSpec {
  val BackgroundColor = ColorRgba(100, 30, 230, 100)
  val ForegroundColor = ColorRgba(30, 30, 80, 200)
  val ForegroundColorLighter = ColorRgba(200, 200, 255, 200)

  "Color blending" should "work using simple alpha compositing algorithm" in {
    val expectedBlendedColor = ColorRgba(37, 30, 95, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.SIMPLE_ALPHA_COMPOSITING)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work (color dodge as an example) with alphaBlending=false (alpha should be unchanged)" in {
    val expectedBlendedColor = ColorRgba(71, 63, 248, 100)
    val resultedColor = ColorBlending
      .blend(
        BackgroundColor,
        ForegroundColor,
        blendMode = BlendMode.COLOR_DODGE,
        alphaBlending = false)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using color blend mode" in {
    val expectedBlendedColor = ColorRgba(73, 73, 187, 100)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.COLOR)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using luminosity blend mode" in {
    val expectedBlendedColor = ColorRgba(42, 11, 99, 100)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.LUMINOSITY)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using color burn blend mode" in {
    val expectedBlendedColor = ColorRgba(0, 0, 115, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColorLighter, blendMode = BlendMode.COLOR_BURN)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using color dodge blend mode" in {
    val expectedBlendedColor = ColorRgba(32, 28, 112, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.COLOR_DODGE)

    assert(resultedColor == expectedBlendedColor)
  }
}
