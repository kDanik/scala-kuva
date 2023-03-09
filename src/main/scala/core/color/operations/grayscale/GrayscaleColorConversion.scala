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
    }
  }

  private def applyDesaturationGrayscale(color: Color): ColorRgba = {
    color.asColorHsla.copy(saturation = 0f).asColorRgba
  }

  private def applyLumaBT709Grayscale(color: Color): ColorRgba = {
    val colorRGBA = color.asColorRgba
    val grey = UByte((colorRGBA.red.intValue * 0.2126f + colorRGBA.red.intValue * 0.7152f + colorRGBA.blue.intValue * 0.0722f).round)

    ColorRgba(grey, grey, grey, colorRGBA.alpha)
  }

  private def applyLumaBT601Grayscale(color: Color): ColorRgba = {
    val colorRGBA = color.asColorRgba
    val grey = UByte((colorRGBA.red.intValue * 0.299f + colorRGBA.red.intValue * 0.587f + colorRGBA.blue.intValue * 0.114f).round)

    ColorRgba(grey, grey, grey, colorRGBA.alpha)
  }

  private def applyAveragingGrayscale(color: Color): ColorRgba = {
    val colorRGBA = color.asColorRgba
    val averageColorValue = UByte(((colorRGBA.red.intValue + colorRGBA.blue.intValue + colorRGBA.green.intValue) / 3f).round)

    ColorRgba(averageColorValue, averageColorValue, averageColorValue, colorRGBA.alpha)
  }

  private def applySingleChannelRedGrayscale(color: Color): ColorRgba = {
    val colorRGBA = color.asColorRgba

    ColorRgba(colorRGBA.red, colorRGBA.red, colorRGBA.red, colorRGBA.alpha)
  }

  private def applySingleChannelGreenGrayscale(color: Color): ColorRgba = {
    val colorRGBA = color.asColorRgba

    ColorRgba(colorRGBA.green, colorRGBA.green, colorRGBA.green, colorRGBA.alpha)
  }

  private def applySingleChannelBlueGrayscale(color: Color): ColorRgba = {
    val colorRGBA = color.asColorRgba

    ColorRgba(colorRGBA.blue, colorRGBA.blue, colorRGBA.blue, colorRGBA.alpha)
  }

  private def applyDecompositionMaxGrayscale(color: Color): ColorRgba = {
    val colorRGBA = color.asColorRgba
    val maxChannelValue = UByte(colorRGBA.red.intValue.max(colorRGBA.green.intValue).max(colorRGBA.blue.intValue))

    ColorRgba(maxChannelValue, maxChannelValue, maxChannelValue, colorRGBA.alpha)
  }

  private def applyDecompositionMinGrayscale(color: Color): ColorRgba = {
    val colorRGBA = color.asColorRgba
    val minChannelValue = UByte(colorRGBA.red.intValue.min(colorRGBA.green.intValue).min(colorRGBA.blue.intValue))

    ColorRgba(minChannelValue, minChannelValue, minChannelValue, colorRGBA.alpha)
  }
}
