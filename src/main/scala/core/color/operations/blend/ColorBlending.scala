package com.example
package core.color.operations.blend

import core.color.operations.blend.BlendMode.*
import core.color.operations.blend.ColorBlending.{blendSingleChannelUsingAlphaCompositing, calculateStandardBlendedAlpha, preMultiplyRgbValues, unMultiplyFinalColorChannel}
import core.color.operations.grayscale.GrayscaleColorConversion.*
import core.color.operations.grayscale.GrayscaleConversionAlgorithm
import core.color.types.{Color, ColorRgba}

import scala.util.Random

/**
 * Most formulas for color blending are from SVG Spec
 * ([[https://dev.w3.org/SVG/modules/compositing/master/]])
 */
object ColorBlending {

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
    }
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

    if (!blendFullyTransparentColors && (alphaFg == 0f || alphaBg == 0f)) {
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

  private def blendSingleChannelUsingScreenAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    1f - (1f - premultipliedForegroundChannelValue) * (1f - premultipliedBackgroundChannelValue)
  }

  private def blendSingleChannelUsingOverlayAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    if (premultipliedBackgroundChannelValue < 0.5f) {
      premultipliedBackgroundChannelValue * premultipliedForegroundChannelValue * 2
    } else {
      1f - 2 * (1f - premultipliedForegroundChannelValue) * (1f - premultipliedBackgroundChannelValue)
    }
  }

  private def blendSingleChannelUsingHardLightAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    if (premultipliedBackgroundChannelValue < 0.5f) {
      1f - 2 * (1f - premultipliedForegroundChannelValue) * (1f - premultipliedBackgroundChannelValue)
    } else {
      premultipliedBackgroundChannelValue * premultipliedForegroundChannelValue * 2
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
