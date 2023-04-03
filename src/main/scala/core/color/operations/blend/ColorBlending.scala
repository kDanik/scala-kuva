package com.example
package core.color.operations.blend

import core.color.operations.grayscale.GrayscaleColorConversion.*
import core.color.operations.grayscale.GrayscaleConversionAlgorithm
import core.color.types.{Color, ColorRgba}

import com.example.core.color.operations.blend.BlendMode.DISSOLVE

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
    }
  }

  private def blendUsingDissolveAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    val totalOpacity = backgroundColor.alpha.intValue + foregroundColor.alpha.intValue

    if (Random.nextFloat() * totalOpacity < backgroundColor.alpha.intValue) backgroundColor
    else foregroundColor
  }

  private def blendUsingAlphaCompositing(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba): ColorRgba = {
    val alphaBg = backgroundColor.alphaAsFloat
    val alphaFg = foregroundColor.alphaAsFloat

    val (redBg: Float, greenBg: Float, blueBg: Float) =
      preMultiplyRgbValues(backgroundColor.rgbValuesAsFloats, alphaBg)
    val (redFg: Float, greenFg: Float, blueFg: Float) =
      preMultiplyRgbValues(foregroundColor.rgbValuesAsFloats, alphaFg)

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
   * Pre multiplies rgb values (tuple) by their alpha. This helps to improve correctness of
   * blending
   */
  private def preMultiplyRgbValues(
      rgbValues: (Float, Float, Float),
      alpha: Float): (Float, Float, Float) = {
    (rgbValues._1 * alpha, rgbValues._2 * alpha, rgbValues._3 * alpha)
  }
}
