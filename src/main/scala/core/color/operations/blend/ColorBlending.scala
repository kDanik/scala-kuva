package com.example
package core.color.operations.blend

import core.color.operations.blend.BlendMode.*
import core.color.operations.blend.ColorBlending.{blendSingleChannelUsingAlphaCompositing, calculateStandardBlendedAlpha, preMultiplyRgbValues, unMultiplyFinalColorChannel}
import core.color.operations.grayscale.GrayscaleColorConversion.*
import core.color.operations.grayscale.GrayscaleConversionAlgorithm
import core.color.types.{Color, ColorRgba}

import core.support.{FloatWithAlmostEquals, Precision}

import scala.util.Random

/**
 * Most formulas for color blending are from SVG Spec
 * ([[https://dev.w3.org/SVG/modules/compositing/master/]])
 */
object ColorBlending {
  private implicit val AlmostEqualPrecision: Precision = Precision(0.0001f)

  /**
   * @param backgroundColor
   *   color "on top" of which foreground color will be blended / applied / overlay
   * @param foregroundColor
   *   color which will go "on top" of background color (overlay)
   * @param blendMode
   *   blend mode (algorithm) that should be used for blending. More details in BlendMode enum.
   * @return
   *   resulting color in ColorRgba format
   */
  def blend(backgroundColor: Color, foregroundColor: Color, blendMode: BlendMode): ColorRgba = {
    blendMode match {
      case BlendMode.SIMPLE_ALPHA_COMPOSITING =>
        blendUsingAlphaCompositing(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case DISSOLVE =>
        blendUsingDissolveAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case MULTIPLY =>
        blendUsingMultiplyAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case SCREEN =>
        blendUsingScreenAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case OVERLAY =>
        blendUsingOverlayAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case HARD_LIGHT =>
        blendUsingHardLightAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case SOFT_LIGHT =>
        blendUsingSoftLightAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case COLOR_DODGE =>
        blendUsingColorDodgeAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case LINEAR_DODGE =>
        blendUsingLinearDodgeAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case COLOR_BURN =>
        blendUsingColorBurnAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case LINEAR_BURN =>
        blendUsingLinearBurnAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case VIVID_LIGHT =>
        blendUsingVividLightAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case LINEAR_LIGHT =>
        blendUsingLinearLightAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case DIFFERENCE =>
        blendUsingDifferenceAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case SUBTRACT =>
        blendUsingSubtractAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
    }
  }

  private def blendUsingSubtractAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingSubtractAlgorithm(backgroundColor, foregroundColor))
  }

  private def blendUsingDifferenceAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingDifferenceAlgorithm(backgroundColor, foregroundColor))
  }

  private def blendUsingLinearLightAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingLinearLightAlgorithm(backgroundColor, foregroundColor))
  }

  private def blendUsingVividLightAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingVividLightAlgorithm(backgroundColor, foregroundColor))
  }

  private def blendUsingLinearBurnAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingLinearBurnAlgorithm(backgroundColor, foregroundColor))
  }

  private def blendUsingColorBurnAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingColorBurnAlgorithm(backgroundColor, foregroundColor))
  }

  private def blendUsingLinearDodgeAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingLinearDodgeAlgorithm(backgroundColor, foregroundColor))
  }

  private def blendUsingColorDodgeAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingColorDodgeAlgorithm(backgroundColor, foregroundColor))
  }

  private def blendUsingSoftLightAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingSoftLightAlgorithm(backgroundColor, foregroundColor))
  }

  private def blendUsingHardLightAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingHardLightAlgorithm(backgroundColor, foregroundColor))
  }

  private def blendUsingOverlayAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (backgroundColor: Float, foregroundColor: Float, _: Float) =>
        blendSingleChannelUsingOverlayAlgorithm(backgroundColor, foregroundColor))
  }

  private def blendUsingMultiplyAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        foregroundColor * backgroundColor)
  }

  private def blendUsingScreenAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (backgroundColor: Float, foregroundColor: Float, _: Float) =>
        blendSingleChannelUsingScreenAlgorithm(backgroundColor, foregroundColor))
  }

  private def blendUsingDissolveAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    val backgroundAlpha = backgroundColor.alpha.intValue
    val foregroundAlpha = foregroundColor.alpha.intValue

    if (foregroundAlpha != 0 && backgroundAlpha != 0) {

      val totalOpacity = backgroundAlpha + foregroundAlpha

      if (Random.nextFloat() * totalOpacity < backgroundAlpha) backgroundColor
      else foregroundColor

    } else backgroundColor
  }

  private def blendUsingAlphaCompositing(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    blendWithPremultipliedAlpha(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = blendSingleChannelUsingAlphaCompositing)
  }

  /**
   * Blends two colors using specified blend algorithm. This function also premultiplies color
   * values, before applying blend algorithm.
   * @param backgroundColor
   *   Background (or base) color
   * @param foregroundColor
   *   Foreground (or overlay) color
   * @param blendAlgorithmForOneChannel
   *   blend function used to convert 2 color channels (background and foreground) int 1. Values
   *   of color channels are premultiplied by default
   * @param blendFullyTransparentColors
   *   Should fully transparent colors (background or foreground) be blended or handled
   *   differently? Default value is false
   * @param handleTransparentColor
   *   Function that will be used to handle transparent colors if blendFullyTransparentColors is
   *   false. Default value "nonTransparentColor" function, that will return either background or
   *   foreground, depending which one is non (fully) transparent.
   * @return
   *   resulting color after applying specified blend algorithm
   */
  private def blendWithPremultipliedAlpha(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      blendAlgorithmForOneChannel: (
          background: Float,
          foreground: Float,
          alphaForeground: Float) => Float,
      blendFullyTransparentColors: Boolean = false) = {
    val alphaBg = backgroundColor.alphaAsFloat
    val alphaFg = foregroundColor.alphaAsFloat

    if (!blendFullyTransparentColors && ((alphaFg ~= 0f) || (alphaBg ~= 0f))) {
      // fully transparent color can produce invalid blend result for some algorithms
      backgroundColor
    } else {

      val (redBg, greenBg, blueBg) = preMultiplyRgbValues(backgroundColor, alphaBg)
      val (redFg, greenFg, blueFg) = preMultiplyRgbValues(foregroundColor, alphaFg)

      val finalAlpha = calculateStandardBlendedAlpha(alphaBg, alphaFg)

      ColorRgba(
        unMultiplyFinalColorChannel(
          blendAlgorithmForOneChannel(redBg, redFg, alphaFg),
          finalAlpha),
        unMultiplyFinalColorChannel(
          blendAlgorithmForOneChannel(greenBg, greenFg, alphaFg),
          finalAlpha),
        unMultiplyFinalColorChannel(
          blendAlgorithmForOneChannel(blueBg, blueFg, alphaFg),
          finalAlpha),
        finalAlpha)
    }
  }

  private def blendSingleChannelUsingSubtractAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    if (premultipliedBackgroundChannelValue > premultipliedForegroundChannelValue) {
      premultipliedBackgroundChannelValue - premultipliedForegroundChannelValue
    } else 0
  }

  private def blendSingleChannelUsingDifferenceAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    (premultipliedBackgroundChannelValue - premultipliedForegroundChannelValue).abs
  }

  private def blendSingleChannelUsingVividLightAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {

    if (premultipliedForegroundChannelValue < 0.5f) {
      1 - (1 - premultipliedBackgroundChannelValue) / (2 * premultipliedForegroundChannelValue)
    } else {
      premultipliedBackgroundChannelValue / (2 * (1 - premultipliedForegroundChannelValue))
    }
  }

  private def blendSingleChannelUsingLinearLightAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    if (premultipliedForegroundChannelValue < 0.5f) {
      premultipliedBackgroundChannelValue + 2 * premultipliedForegroundChannelValue - 1
    } else {
      premultipliedBackgroundChannelValue + 2 * (premultipliedForegroundChannelValue - 0.5f)
    }
  }

  private def blendSingleChannelUsingLinearBurnAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    premultipliedBackgroundChannelValue + premultipliedForegroundChannelValue - 1
  }

  private def blendSingleChannelUsingColorBurnAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    1 - ((1 - premultipliedBackgroundChannelValue) / premultipliedForegroundChannelValue)
  }

  private def blendSingleChannelUsingLinearDodgeAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    premultipliedBackgroundChannelValue + premultipliedForegroundChannelValue
  }

  private def blendSingleChannelUsingColorDodgeAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    premultipliedBackgroundChannelValue / (1 - premultipliedForegroundChannelValue)
  }

  private def blendSingleChannelUsingScreenAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    1 - (1 - premultipliedForegroundChannelValue) * (1 - premultipliedBackgroundChannelValue)
  }

  private def blendSingleChannelUsingOverlayAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    if (premultipliedBackgroundChannelValue < 0.5f) {
      premultipliedBackgroundChannelValue * premultipliedForegroundChannelValue * 2
    } else {
      1 - 2 * (1 - premultipliedForegroundChannelValue) * (1 - premultipliedBackgroundChannelValue)
    }
  }

  private def blendSingleChannelUsingHardLightAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    if (premultipliedBackgroundChannelValue < 0.5f) {
      1 - 2 * (1 - premultipliedForegroundChannelValue) * (1 - premultipliedBackgroundChannelValue)
    } else {
      premultipliedBackgroundChannelValue * premultipliedForegroundChannelValue * 2
    }
  }

  private def blendSingleChannelUsingSoftLightAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    if (premultipliedForegroundChannelValue <= 0.5f) {
      premultipliedBackgroundChannelValue - (1 - 2 * premultipliedForegroundChannelValue) * premultipliedBackgroundChannelValue * (1 - premultipliedBackgroundChannelValue)
    } else {
      val gW3CBackground: Float =
        if (premultipliedBackgroundChannelValue <= 0.25f)
          ((premultipliedBackgroundChannelValue * 16 - 12) * premultipliedBackgroundChannelValue + 4) * premultipliedBackgroundChannelValue
        else math.sqrt(premultipliedBackgroundChannelValue).floatValue

      premultipliedBackgroundChannelValue + (premultipliedForegroundChannelValue * 2 - 1) * (gW3CBackground - premultipliedBackgroundChannelValue)
    }
  }

  private def blendSingleChannelUsingAlphaCompositing(
      colorChannelBackground: Float,
      colorChannelForeground: Float,
      alphaForeground: Float): Float = {
    colorChannelForeground + colorChannelBackground * (1 - alphaForeground)
  }

  /**
   * "Un-multiply" final color value by final alpha. This must be done if pre multiplication was
   * used before.
   */
  private def unMultiplyFinalColorChannel(finalColorChannel: Float, finalAlpha: Float): Float =
    finalColorChannel / finalAlpha

  /**
   * Formula for calculation of final alpha, used for most blending modes
   */
  private def calculateStandardBlendedAlpha(
      alphaBackground: Float,
      alphaForeground: Float): Float = {
    alphaBackground + alphaForeground - alphaBackground * alphaForeground
  }

  /**
   * Pre multiplies rgb values of input color by their alpha and returns it as tuple of floats.
   * This helps to improve correctness of blending.
   */
  private def preMultiplyRgbValues(colorRgba: ColorRgba, alpha: Float): (Float, Float, Float) = {
    (colorRgba.redAsFloat * alpha, colorRgba.greenAsFloat * alpha, colorRgba.blueAsFloat * alpha)
  }
}
