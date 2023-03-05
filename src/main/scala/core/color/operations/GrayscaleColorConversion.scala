package com.example
package core.color.operations

import core.color.types.{Color, ColorRGBA}

import spire.math.UByte

/**
 * Grayscale color is a color that only represents an amount of light
 */
object GrayscaleColorConversion {
  /**
   * Applies grayscale to color, using specified grayscale algorithm
   *
   * @param color     initial color
   * @param algorithm see GrayscaleConversionAlgorithm
   * @return grayscale color as ColorRGBA
   */
  def applyGrayscale(color: Color, algorithm: GrayscaleConversionAlgorithm): ColorRGBA = {
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

  def applyDesaturationGrayscale(color: Color): ColorRGBA = {
    color.asColorHSLA.copy(saturation = 0f).asColorRGBA
  }

  def applyLumaBT709Grayscale(color: Color): ColorRGBA = {
    val colorRGBA = color.asColorRGBA
    val grey = UByte((colorRGBA.red.intValue * 0.2126f + colorRGBA.red.intValue * 0.7152f + colorRGBA.blue.intValue * 0.0722f).round)

    ColorRGBA(grey, grey, grey, colorRGBA.alpha)
  }

  def applyLumaBT601Grayscale(color: Color): ColorRGBA = {
    val colorRGBA = color.asColorRGBA
    val grey = UByte((colorRGBA.red.intValue * 0.299f + colorRGBA.red.intValue * 0.587f + colorRGBA.blue.intValue * 0.114f).round)

    ColorRGBA(grey, grey, grey, colorRGBA.alpha)
  }

  def applyAveragingGrayscale(color: Color): ColorRGBA = {
    val colorRGBA = color.asColorRGBA
    val averageColorValue = UByte(((colorRGBA.red.intValue + colorRGBA.blue.intValue + colorRGBA.green.intValue) / 3f).round)

    ColorRGBA(averageColorValue, averageColorValue, averageColorValue, colorRGBA.alpha)
  }

  def applySingleChannelRedGrayscale(color: Color): ColorRGBA = {
    val colorRGBA = color.asColorRGBA

    ColorRGBA(colorRGBA.red, colorRGBA.red, colorRGBA.red, colorRGBA.alpha)
  }

  def applySingleChannelGreenGrayscale(color: Color): ColorRGBA = {
    val colorRGBA = color.asColorRGBA

    ColorRGBA(colorRGBA.green, colorRGBA.green, colorRGBA.green, colorRGBA.alpha)
  }

  def applySingleChannelBlueGrayscale(color: Color): ColorRGBA = {
    val colorRGBA = color.asColorRGBA

    ColorRGBA(colorRGBA.blue, colorRGBA.blue, colorRGBA.blue, colorRGBA.alpha)
  }
  def applyDecompositionMaxGrayscale(color: Color): ColorRGBA = {
    val colorRGBA = color.asColorRGBA
    val maxChannelValue = UByte(colorRGBA.red.intValue.max(colorRGBA.green.intValue).max(colorRGBA.blue.intValue))

    ColorRGBA(maxChannelValue, maxChannelValue, maxChannelValue, colorRGBA.alpha)
  }

  def applyDecompositionMinGrayscale(color: Color): ColorRGBA = {
    val colorRGBA = color.asColorRGBA
    val minChannelValue = UByte(colorRGBA.red.intValue.min(colorRGBA.green.intValue).min(colorRGBA.blue.intValue))

    ColorRGBA(minChannelValue, minChannelValue, minChannelValue, colorRGBA.alpha)
  }
}
