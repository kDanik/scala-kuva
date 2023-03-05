package com.example
package core.color

import core.color.operations.{GrayscaleColorConversion, GrayscaleConversionAlgorithm}
import core.color.types.ColorRGBA

import org.scalatest.flatspec.AnyFlatSpec


class GrayscaleColorConversionSuite extends AnyFlatSpec {
  "GrayscaleColorConversion" can "apply grayscale conversion with desaturation algorithm" in {
    val initialColor = ColorRGBA.apply(183, 159, 10, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(initialColor, GrayscaleConversionAlgorithm.DESATURATION)
    val expectedGrayscaleColor = ColorRGBA.apply(97, 97, 97, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }

  "GrayscaleColorConversion" can "apply grayscale conversion with averaging algorithm" in {
    val initialColor = ColorRGBA.apply(180, 160, 10, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(initialColor, GrayscaleConversionAlgorithm.AVERAGING)
    val expectedGrayscaleColor = ColorRGBA.apply(117, 117, 117, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }

  "GrayscaleColorConversion" can "apply grayscale conversion with Luma BT601 algorithm" in {
    val initialColor = ColorRGBA.apply(180, 160, 10, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(initialColor, GrayscaleConversionAlgorithm.LUMA_BT601)
    val expectedGrayscaleColor = ColorRGBA.apply(161, 161, 161, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }

  "GrayscaleColorConversion" can "apply grayscale conversion with Luma BT709 algorithm" in {
    val initialColor = ColorRGBA.apply(180, 160, 10, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(initialColor, GrayscaleConversionAlgorithm.LUMA_BT709)
    val expectedGrayscaleColor = ColorRGBA.apply(168, 168, 168, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }

  "Luma algorithm overflow" should "never happen (input with max values)" in {
    val initialColor = ColorRGBA.apply(255, 255, 255, 125)

    val resultedGrayscaleColorBT709 = GrayscaleColorConversion.applyGrayscale(initialColor, GrayscaleConversionAlgorithm.LUMA_BT709)
    val resultedGrayscaleColorBT601 = GrayscaleColorConversion.applyGrayscale(initialColor, GrayscaleConversionAlgorithm.LUMA_BT601)
    val expectedGrayscaleColor = ColorRGBA.apply(255, 255, 255, 125)

    assert(resultedGrayscaleColorBT709 == expectedGrayscaleColor)
    assert(resultedGrayscaleColorBT601 == expectedGrayscaleColor)
  }

  "GrayscaleColorConversion" can "apply grayscale using single channel algorithm" in {
    val initialColor = ColorRGBA.apply(255, 200, 100, 125)

    val resultedColorUsingRedChannel = GrayscaleColorConversion.applyGrayscale(initialColor, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_RED)
    val resultedColorUsingGreenChannel = GrayscaleColorConversion.applyGrayscale(initialColor, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_GREEN)
    val resultedColorUsingBlueChannel = GrayscaleColorConversion.applyGrayscale(initialColor, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_BLUE)

    assert(resultedColorUsingRedChannel == ColorRGBA.apply(255, 255, 255, 125))
    assert(resultedColorUsingBlueChannel == ColorRGBA.apply(100, 100, 100, 125))
    assert(resultedColorUsingGreenChannel == ColorRGBA.apply(200, 200, 200, 125))
  }

  "GrayscaleColorConversion" can "apply grayscale using decomposition (min) algorithm" in {
    val initialColor = ColorRGBA.apply(255, 200, 100, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(initialColor, GrayscaleConversionAlgorithm.DECOMPOSITION_MIN)
    val expectedGrayscaleColor = ColorRGBA.apply(100, 100, 100, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }

  "GrayscaleColorConversion" can "apply grayscale using decomposition (max) algorithm" in {
    val initialColor = ColorRGBA.apply(255, 200, 100, 125)

    val resultedGrayscaleColor = GrayscaleColorConversion.applyGrayscale(initialColor, GrayscaleConversionAlgorithm.DECOMPOSITION_MAX)
    val expectedGrayscaleColor = ColorRGBA.apply(255, 255, 255, 125)

    assert(resultedGrayscaleColor == expectedGrayscaleColor)
  }
}
