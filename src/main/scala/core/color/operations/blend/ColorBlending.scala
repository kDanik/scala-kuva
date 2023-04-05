package com.example
package core.color.operations.blend

import core.color.operations.blend.BlendMode.{DISSOLVE, MULTIPLY, SCREEN}
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
    }
  }

  private def blendUsingMultiplyAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    val alphaBg = backgroundColor.alphaAsFloat
    val alphaFg = foregroundColor.alphaAsFloat

    if (alphaFg == 0f || alphaBg == 0f) {
      // for multiply blend fully transparent colors must be ignored
      if (alphaFg == 0f) backgroundColor else foregroundColor
    } else {

      val (redBg, greenBg, blueBg) = preMultiplyRgbValues(backgroundColor, alphaBg)
      val (redFg, greenFg, blueFg) = preMultiplyRgbValues(foregroundColor, alphaFg)

      val finalAlpha = calculateStandardBlendedAlpha(alphaBg, alphaFg)

      ColorRgba.apply(
        unMultiplyFinalColorChannel(redFg * redBg, finalAlpha),
        unMultiplyFinalColorChannel(greenFg * greenBg, finalAlpha),
        unMultiplyFinalColorChannel(blueFg * blueBg, finalAlpha),
        finalAlpha)
    }
  }

  private def blendUsingScreenAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    val alphaBg = backgroundColor.alphaAsFloat
    val alphaFg = foregroundColor.alphaAsFloat

    if (alphaFg == 0f || alphaBg == 0f) {
      // for screen blend fully transparent colors must be ignored
      if (alphaFg == 0f) backgroundColor else foregroundColor
    } else {

      val (redBg, greenBg, blueBg) = preMultiplyRgbValues(backgroundColor, alphaBg)
      val (redFg, greenFg, blueFg) = preMultiplyRgbValues(foregroundColor, alphaFg)

      val finalAlpha = calculateStandardBlendedAlpha(alphaBg, alphaFg)

      ColorRgba.apply(
        /*
        TODO
          this and multiply (and maybe alpha compositing) blend modes can be combined with high order function,
          as main difference is formula / function, that is applied in the end to each color channel.
          It would be best to do that after most of blend modes are added, tested with examples and covered with unit tests.
         */
        unMultiplyFinalColorChannel(1f - (1f - redFg) * (1 - redBg), finalAlpha),
        unMultiplyFinalColorChannel(1f - (1f - greenFg) * (1 - greenBg), finalAlpha),
        unMultiplyFinalColorChannel(1f - (1f - blueFg) * (1 - blueBg), finalAlpha),
        finalAlpha)
    }
  }

  private def blendUsingDissolveAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    // TODO if one of colors is transparent calculation here is redundant and can be removed
    val totalOpacity = backgroundColor.alpha.intValue + foregroundColor.alpha.intValue

    if (Random.nextFloat() * totalOpacity < backgroundColor.alpha.intValue) backgroundColor
    else foregroundColor
  }

  private def blendUsingAlphaCompositing(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    val alphaBg = backgroundColor.alphaAsFloat
    val alphaFg = foregroundColor.alphaAsFloat

    val (redBg, greenBg, blueBg) = preMultiplyRgbValues(backgroundColor, alphaBg)
    val (redFg, greenFg, blueFg) = preMultiplyRgbValues(foregroundColor, alphaFg)

    val finalAlpha = calculateStandardBlendedAlpha(alphaBg, alphaFg)

    ColorRgba.apply(
      unMultiplyFinalColorChannel(
        blendSingleChannelUsingAlphaCompositing(redBg, redFg, alphaFg),
        finalAlpha),
      unMultiplyFinalColorChannel(
        blendSingleChannelUsingAlphaCompositing(greenBg, greenFg, alphaFg),
        finalAlpha),
      unMultiplyFinalColorChannel(
        blendSingleChannelUsingAlphaCompositing(blueBg, blueFg, alphaFg),
        finalAlpha),
      finalAlpha)
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
