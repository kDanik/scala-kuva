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

  "Color blending" should "work using geometric mean blend mode" in {
    val expectedBlendedColor = ColorRgba(35, 19, 87, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.GEOMETRIC_MEAN)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using exclusion blend mode" in {
    val expectedBlendedColor = ColorRgba(64, 38, 125, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.EXCLUSION)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using reflect blend mode" in {
    val expectedBlendedColor = ColorRgba(3, 3, 27, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.REFLECT)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using pin light blend mode" in {
    val expectedBlendedColor = ColorRgba(27, 27, 72, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.PIN_LIGHT)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using hard mix blend mode" in {
    val expectedBlendedColor = ColorRgba(0, 0, 255, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColorLighter, blendMode = BlendMode.HARD_MIX)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using divide blend mode" in {
    val expectedBlendedColor = ColorRgba(176, 255, 204, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.DIVIDE)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using difference blend mode" in {
    val expectedBlendedColor = ColorRgba(18, 14, 32, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.DIFFERENCE)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using darken only blend mode" in {
    val expectedBlendedColor = ColorRgba(27, 14, 72, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.DARKEN_ONLY)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using lighten only blend mode" in {
    val expectedBlendedColor = ColorRgba(45, 27, 104, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.LIGHTEN_ONLY)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using linear light blend mode" in {
    val expectedBlendedColor = ColorRgba(0, 0, 144, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColorLighter, blendMode = BlendMode.LINEAR_LIGHT)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using linear burn blend mode" in {
    val expectedBlendedColor = ColorRgba(0, 0, 41, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColorLighter, blendMode = BlendMode.LINEAR_BURN)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using linear dodge blend mode" in {
    val expectedBlendedColor = ColorRgba(72, 41, 176, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.LINEAR_DODGE)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using vivid light blend mode" in {
    val expectedBlendedColor = ColorRgba(0, 0, 204, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColorLighter, blendMode = BlendMode.VIVID_LIGHT)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using soft light blend mode" in {
    val expectedBlendedColor = ColorRgba(10, 5, 56, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.SOFT_LIGHT)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using hard light blend mode" in {
    val expectedBlendedColor = ColorRgba(8, 2, 51, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.HARD_LIGHT)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using screen blend mode" in {
    val expectedBlendedColor = ColorRgba(68, 39, 150, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.SCREEN)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using overlay blend mode" in {
    val expectedBlendedColor = ColorRgba(8, 2, 51, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.OVERLAY)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using multiply blend mode" in {
    val expectedBlendedColor = ColorRgba(4, 1, 26, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.MULTIPLY)

    assert(resultedColor == expectedBlendedColor)
  }

  "Color blending" should "work using subtract blend mode" in {
    val expectedBlendedColor = ColorRgba(0, 14, 0, 222)
    val resultedColor = ColorBlending
      .blend(BackgroundColor, ForegroundColor, blendMode = BlendMode.SUBTRACT)

    assert(resultedColor == expectedBlendedColor)
  }
}
