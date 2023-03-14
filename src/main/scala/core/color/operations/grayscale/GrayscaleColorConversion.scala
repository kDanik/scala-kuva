package com.example
package core.color.operations.grayscale

import core.color.types.{Color, ColorRgba}

import spire.math.UByte

/**
 * Grayscale color is a color that only represents an amount of light
 */
object GrayscaleColorConversion {
  /**
   * Using initial color and selected grayscale algorithms, creates new grayscale color.
   *
   * @param color     initial color
   * @param algorithm see GrayscaleConversionAlgorithm
   * @return grayscale color as ColorRgba
   */
  def applyGrayscale(color: Color, algorithm: GrayscaleConversionAlgorithm): ColorRgba = {
    algorithm match {
      case GrayscaleConversionAlgorithm.DESATURATION => applyDesaturationGrayscale(color)
      case GrayscaleConversionAlgorithm.LUMA_BT601 => applyLumaBT601Grayscale(color)
      case GrayscaleConversionAlgorithm.LUMA_BT709 => applyLumaBT709Grayscale(color)
      case GrayscaleConversionAlgorithm.AVERAGING => applyAveragingGrayscale(color)
      case GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_RED => applySingleChannelRedGrayscale(color)
      case GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_GREEN => applySingleChannelGreenGrayscale(color)
      case GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_BLUE => applySingleChannelBlueGrayscale(color)
      case GrayscaleConversionAlgorithm.DECOMPOSITION_MIN => applyDecompositionMinGrayscale(color)
      case GrayscaleConversionAlgorithm.DECOMPOSITION_MAX => applyDecompositionMaxGrayscale(color)
      case GrayscaleConversionAlgorithm.LIGHTNESS => applyLightnessGrayscale(color)
      case GrayscaleConversionAlgorithm.LIGHTNESS_HSL => applyLightnessHslGrayscale(color)
    }
  }

  private def applyDesaturationGrayscale(color: Color): ColorRgba = {
    color.asColorHsla.copy(saturation = 0f).asColorRgba
  }

  private def applyLumaBT709Grayscale(color: Color): ColorRgba = {
    val colorRgba = color.asColorRgba
    val grey = ColorRgba.normalizeColorChannelValue((colorRgba.red.intValue * 0.2126f + colorRgba.red.intValue * 0.7152f + colorRgba.blue.intValue * 0.0722f).round)

    ColorRgba(grey, grey, grey, colorRgba.alpha)
  }

  private def applyLumaBT601Grayscale(color: Color): ColorRgba = {
    val colorRgba = color.asColorRgba
    val grey = ColorRgba.normalizeColorChannelValue((colorRgba.red.intValue * 0.299f + colorRgba.red.intValue * 0.587f + colorRgba.blue.intValue * 0.114f).round)

    ColorRgba(grey, grey, grey, colorRgba.alpha)
  }

  private def applyAveragingGrayscale(color: Color): ColorRgba = {
    val colorRgba = color.asColorRgba
    val averageColorValue = ColorRgba.normalizeColorChannelValue(((colorRgba.red.intValue + colorRgba.blue.intValue + colorRgba.green.intValue) / 3f).round)

    ColorRgba(averageColorValue, averageColorValue, averageColorValue, colorRgba.alpha)
  }

  private def applySingleChannelRedGrayscale(color: Color): ColorRgba = {
    val colorRgba = color.asColorRgba

    ColorRgba(colorRgba.red, colorRgba.red, colorRgba.red, colorRgba.alpha)
  }

  private def applySingleChannelGreenGrayscale(color: Color): ColorRgba = {
    val colorRgba = color.asColorRgba

    ColorRgba(colorRgba.green, colorRgba.green, colorRgba.green, colorRgba.alpha)
  }

  private def applySingleChannelBlueGrayscale(color: Color): ColorRgba = {
    val colorRgba = color.asColorRgba

    ColorRgba(colorRgba.blue, colorRgba.blue, colorRgba.blue, colorRgba.alpha)
  }

  private def applyDecompositionMaxGrayscale(color: Color): ColorRgba = {
    val colorRgba = color.asColorRgba
    val maxChannelValue = UByte(calculateMaximumColoChannelValue(colorRgba))

    ColorRgba(maxChannelValue, maxChannelValue, maxChannelValue, colorRgba.alpha)
  }

  private def applyDecompositionMinGrayscale(color: Color): ColorRgba = {
    val colorRgba = color.asColorRgba
    val minChannelValue = UByte(calculateMinimumColoChannelValue(colorRgba))

    ColorRgba(minChannelValue, minChannelValue, minChannelValue, colorRgba.alpha)
  }

  private def applyLightnessGrayscale(color: Color): ColorRgba = {
    val colorRgba = color.asColorRgba

    val y = 0.2126f * colorRgba.redAsFloat + 0.7152f * colorRgba.greenAsFloat + 0.0722f * colorRgba.blueAsFloat
    val lightness = 0.01f * (116 * y - 16)

    ColorRgba(lightness, lightness, lightness, colorRgba.alpha)
  }

  private def applyLightnessHslGrayscale(color: Color): ColorRgba = {
    val colorRgba = color.asColorRgba

    // this is a bit faster, than converting color to HSL directly, because here we only need lightness from HSL
    val lightnessHsl = (calculateMinimumColoChannelValue(colorRgba) + calculateMaximumColoChannelValue(colorRgba)) / 2

    ColorRgba(lightnessHsl, lightnessHsl, lightnessHsl, colorRgba.alpha)
  }


  private def calculateMaximumColoChannelValue(colorRgba: ColorRgba): Int = {
    colorRgba.red.intValue.max(colorRgba.green.intValue).max(colorRgba.blue.intValue)
  }

  private def calculateMinimumColoChannelValue(colorRgba: ColorRgba): Int = {
    colorRgba.red.intValue.min(colorRgba.green.intValue).min(colorRgba.blue.intValue)
  }
}
