package com.example
package core.color.operations.grayscale

import core.color.operations.grayscale.{GrayscaleColorConversion, GrayscaleConversionAlgorithm}
import core.color.types.ColorRgba

import org.scalatest.flatspec.AnyFlatSpec

class GrayscaleColorConversionSpec extends AnyFlatSpec {
  "GrayscaleColorConversion" can "apply grayscale conversion with desaturation algorithm" in {
    val initialColor = ColorRgba(183, 159, 10, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(
      initialColor,
      GrayscaleConversionAlgorithm.DESATURATION)
    val expectedGrayscaleColor = ColorRgba(97, 97, 97, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }

  "GrayscaleColorConversion" can "apply grayscale conversion with averaging algorithm" in {
    val initialColor = ColorRgba(180, 160, 10, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(
      initialColor,
      GrayscaleConversionAlgorithm.AVERAGING)
    val expectedGrayscaleColor = ColorRgba(117, 117, 117, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }

  "GrayscaleColorConversion" can "apply grayscale conversion with Luma BT601 algorithm" in {
    val initialColor = ColorRgba(180, 160, 10, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(
      initialColor,
      GrayscaleConversionAlgorithm.LUMA_BT601)
    val expectedGrayscaleColor = ColorRgba(161, 161, 161, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }

  "GrayscaleColorConversion" can "apply grayscale conversion with Luma BT709 algorithm" in {
    val initialColor = ColorRgba(180, 160, 10, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(
      initialColor,
      GrayscaleConversionAlgorithm.LUMA_BT709)
    val expectedGrayscaleColor = ColorRgba(168, 168, 168, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }

  "Luma algorithm overflow" should "never happen (input with max values)" in {
    val initialColor = ColorRgba(255, 255, 255, 125)

    val resultedGrayscaleColorBT709 = GrayscaleColorConversion.applyGrayscale(
      initialColor,
      GrayscaleConversionAlgorithm.LUMA_BT709)
    val resultedGrayscaleColorBT601 = GrayscaleColorConversion.applyGrayscale(
      initialColor,
      GrayscaleConversionAlgorithm.LUMA_BT601)
    val expectedGrayscaleColor = ColorRgba(255, 255, 255, 125)

    assert(resultedGrayscaleColorBT709 == expectedGrayscaleColor)
    assert(resultedGrayscaleColorBT601 == expectedGrayscaleColor)
  }

  "GrayscaleColorConversion" can "apply grayscale using single channel algorithm" in {
    val initialColor = ColorRgba(255, 200, 100, 125)

    val resultedColorUsingRedChannel = GrayscaleColorConversion.applyGrayscale(
      initialColor,
      GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_RED)
    val resultedColorUsingGreenChannel =
      GrayscaleColorConversion.applyGrayscale(
        initialColor,
        GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_GREEN)
    val resultedColorUsingBlueChannel = GrayscaleColorConversion.applyGrayscale(
      initialColor,
      GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_BLUE)

    assert(resultedColorUsingRedChannel == ColorRgba(255, 255, 255, 125))
    assert(resultedColorUsingBlueChannel == ColorRgba(100, 100, 100, 125))
    assert(resultedColorUsingGreenChannel == ColorRgba(200, 200, 200, 125))
  }

  "GrayscaleColorConversion" can "apply grayscale using decomposition (min) algorithm" in {
    val initialColor = ColorRgba(255, 200, 100, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(
      initialColor,
      GrayscaleConversionAlgorithm.DECOMPOSITION_MIN)
    val expectedGrayscaleColor = ColorRgba(100, 100, 100, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }

  "GrayscaleColorConversion" can "apply grayscale using decomposition (max) algorithm" in {
    val initialColor = ColorRgba(255, 200, 100, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(
      initialColor,
      GrayscaleConversionAlgorithm.DECOMPOSITION_MAX)
    val expectedGrayscaleColor = ColorRgba(255, 255, 255, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }

  "GrayscaleColorConversion" can "apply grayscale using lightness algorithm" in {
    val initialColor = ColorRgba(255, 200, 100, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(
      initialColor,
      GrayscaleConversionAlgorithm.LIGHTNESS)
    val expectedGrayscaleColor = ColorRgba(234, 234, 234, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }

  "GrayscaleColorConversion" can "apply grayscale using lightness (hsl) algorithm" in {
    val initialColor = ColorRgba(255, 200, 100, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(
      initialColor,
      GrayscaleConversionAlgorithm.LIGHTNESS_HSL)
    val expectedGrayscaleColor = ColorRgba(177, 177, 177, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }
}
