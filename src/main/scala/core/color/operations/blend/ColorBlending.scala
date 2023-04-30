package com.example
package core.color.operations.blend

import core.color.operations.blend.BlendMode.*
import core.color.operations.blend.ColorBlending.{blendSingleChannelUsingAlphaCompositing, calculateStandardBlendedAlpha, preMultiplyRgbValues, unMultiplyFinalColorChannel}
import core.color.operations.grayscale.GrayscaleColorConversion.*
import core.color.operations.grayscale.GrayscaleConversionAlgorithm
import core.color.types.{Color, ColorHsla, ColorRgba}
import core.support.{FloatWithAlmostEquals, Precision}

import spire.math.UByte

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
   * @param alphaBlending
   *   toggles blending of the foreground and background alpha channels. If false, will only use
   *   background alpha for final alpha. This will also affect the final color values (for most
   *   blend modes) because of alpha pre-multiplication and un-multiplication by final alpha.
   * @return
   *   resulting color in ColorRgba format
   */
  def blend(
      backgroundColor: Color,
      foregroundColor: Color,
      blendMode: BlendMode,
      alphaBlending: Boolean = true): ColorRgba = {
    blendMode match {
      case BlendMode.SIMPLE_ALPHA_COMPOSITING =>
        blendUsingAlphaCompositing(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case DISSOLVE =>
        blendUsingDissolveAlgorithm(backgroundColor.asColorRgba, foregroundColor.asColorRgba)
      case MULTIPLY =>
        blendUsingMultiplyAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case SCREEN =>
        blendUsingScreenAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case OVERLAY =>
        blendUsingOverlayAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case HARD_LIGHT =>
        blendUsingHardLightAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case SOFT_LIGHT =>
        blendUsingSoftLightAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case COLOR_DODGE =>
        blendUsingColorDodgeAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case LINEAR_DODGE =>
        blendUsingLinearDodgeAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case COLOR_BURN =>
        blendUsingColorBurnAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case LINEAR_BURN =>
        blendUsingLinearBurnAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case VIVID_LIGHT =>
        blendUsingVividLightAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case LINEAR_LIGHT =>
        blendUsingLinearLightAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case DIFFERENCE =>
        blendUsingDifferenceAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case SUBTRACT =>
        blendUsingSubtractAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case LIGHTEN_ONLY =>
        blendUsingLightenOnlyAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case DARKEN_ONLY =>
        blendUsingDarkenOnlyAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case DIVIDE =>
        blendUsingDivideAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case HARD_MIX =>
        blendUsingHardMixAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case PIN_LIGHT =>
        blendUsingPinLightAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case REFLECT =>
        blendUsingReflectAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case EXCLUSION =>
        blendUsingExclusionAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case GEOMETRIC_MEAN =>
        blendUsingGeometricMeanAlgorithm(
          backgroundColor.asColorRgba,
          foregroundColor.asColorRgba,
          alphaBlending)
      case LUMINOSITY =>
        blendUsingLuminosityAlgorithm(backgroundColor.asColorHsla, foregroundColor.asColorHsla)
      case COLOR =>
        blendUsingColorAlgorithm(backgroundColor.asColorHsla, foregroundColor.asColorHsla)
    }
  }

  private def blendUsingColorAlgorithm(
      backgroundColor: ColorHsla,
      foregroundColor: ColorHsla): ColorRgba = {
    if ((backgroundColor.alpha == UByte(0)) || foregroundColor.alpha == UByte(0))
      backgroundColor.asColorRgba
    else {
      ColorHsla(
        foregroundColor.hue,
        foregroundColor.saturation,
        backgroundColor.lightness,
        backgroundColor.alpha).asColorRgba
    }
  }

  private def blendUsingLuminosityAlgorithm(
      backgroundColor: ColorHsla,
      foregroundColor: ColorHsla): ColorRgba = {
    if ((backgroundColor.alpha == UByte(0)) || foregroundColor.alpha == UByte(0))
      backgroundColor.asColorRgba
    else {
      ColorHsla(
        backgroundColor.hue,
        backgroundColor.saturation,
        foregroundColor.lightness,
        backgroundColor.alpha).asColorRgba
    }
  }

  private def blendUsingGeometricMeanAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingGeometricMeanAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingExclusionAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingExclusionAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingReflectAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingReflectAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingPinLightAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingPinLightAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingHardMixAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingHardMixAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingDivideAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingDivideAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingLightenOnlyAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingLightenOnlyAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingDarkenOnlyAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingDarkenOnlyAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingSubtractAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingSubtractAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingDifferenceAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingDifferenceAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingLinearLightAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingLinearLightAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingVividLightAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingVividLightAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingLinearBurnAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingLinearBurnAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingColorBurnAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingColorBurnAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingLinearDodgeAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingLinearDodgeAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingColorDodgeAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingColorDodgeAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingSoftLightAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingSoftLightAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingHardLightAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingHardLightAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingOverlayAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (backgroundColor: Float, foregroundColor: Float, _: Float) =>
        blendSingleChannelUsingOverlayAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingMultiplyAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (foregroundColor: Float, backgroundColor: Float, _: Float) =>
        blendSingleChannelUsingMultiplyAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
  }

  private def blendUsingScreenAlgorithm(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = (backgroundColor: Float, foregroundColor: Float, _: Float) =>
        blendSingleChannelUsingScreenAlgorithm(backgroundColor, foregroundColor),
      false,
      alphaBlending)
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
      foregroundColor: ColorRgba,
      alphaBlending: Boolean): ColorRgba = {
    blend(
      backgroundColor,
      foregroundColor,
      blendAlgorithmForOneChannel = blendSingleChannelUsingAlphaCompositing,
      false,
      alphaBlending)
  }

  /**
   * Blends two colors using specified blend algorithm. If colors are not fully opaque, values
   * will be premultiplied and final alpha calculated.
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
   * @return
   *   resulting color after applying specified blend algorithm
   */
  private def blend(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      blendAlgorithmForOneChannel: (
          background: Float,
          foreground: Float,
          alphaForeground: Float) => Float,
      blendFullyTransparentColors: Boolean,
      alphaBlending: Boolean) = {
    val alphaBg = backgroundColor.alphaAsFloat
    val alphaFg = foregroundColor.alphaAsFloat

    if (!blendFullyTransparentColors && ((alphaFg ~= 0f) || (alphaBg ~= 0f))) {
      // fully transparent colors can produce invalid blend result for most blend algorithms
      // (especially if used with premultiplied alpha)
      backgroundColor
    } else {
      if ((alphaFg ~= alphaBg) && (alphaBg ~= 1)) {
        blendOpaqueColors(backgroundColor, foregroundColor, blendAlgorithmForOneChannel)
      } else {
        blendTransparentColors(
          backgroundColor,
          alphaBg,
          foregroundColor,
          alphaFg,
          blendAlgorithmForOneChannel,
          alphaBlending)
      }
    }
  }

  /**
   * Blends opaque colors by applying specified blendAlgorithmForOneChannel (for each channel).
   * This function does the same as blendTransparentColors(), but for opaque colors, as for them
   * calculation of alpha and pre-multiplication of values has no effect.
   */
  private def blendOpaqueColors(
      backgroundColor: ColorRgba,
      foregroundColor: ColorRgba,
      blendAlgorithmForOneChannel: (
          background: Float,
          foreground: Float,
          alphaForeground: Float) => Float): ColorRgba = {
    val (redBg, greenBg, blueBg) = backgroundColor.rgbValuesAsFloats
    val (redFg, greenFg, blueFg) = foregroundColor.rgbValuesAsFloats

    ColorRgba(
      blendAlgorithmForOneChannel(redBg, redFg, 1f),
      blendAlgorithmForOneChannel(greenBg, greenFg, 1f),
      blendAlgorithmForOneChannel(blueBg, blueFg, 1f),
      255)
  }

  /**
   * Blends transparent colors by applying specified blendAlgorithmForOneChannel (for each
   * channel). Final alpha will be calculated and values will be premultiplied before
   * calculations.
   */
  private def blendTransparentColors(
      backgroundColor: ColorRgba,
      alphaBg: Float,
      foregroundColor: ColorRgba,
      alphaFg: Float,
      blendAlgorithmForOneChannel: (
          background: Float,
          foreground: Float,
          alphaForeground: Float) => Float,
      alphaBlending: Boolean): ColorRgba = {
    val (redBg, greenBg, blueBg) = preMultiplyRgbValues(backgroundColor, alphaBg)
    val (redFg, greenFg, blueFg) = preMultiplyRgbValues(foregroundColor, alphaFg)

    val finalAlpha =
      if (alphaBlending) calculateStandardBlendedAlpha(alphaBg, alphaFg) else alphaBg

    ColorRgba(
      unMultiplyFinalColorChannel(blendAlgorithmForOneChannel(redBg, redFg, alphaFg), finalAlpha),
      unMultiplyFinalColorChannel(
        blendAlgorithmForOneChannel(greenBg, greenFg, alphaFg),
        finalAlpha),
      unMultiplyFinalColorChannel(
        blendAlgorithmForOneChannel(blueBg, blueFg, alphaFg),
        finalAlpha),
      finalAlpha)
  }

  private def blendSingleChannelUsingGeometricMeanAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    math
      .sqrt(premultipliedBackgroundChannelValue * premultipliedForegroundChannelValue)
      .floatValue
  }

  private def blendSingleChannelUsingExclusionAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    premultipliedBackgroundChannelValue + premultipliedForegroundChannelValue - (2 * premultipliedForegroundChannelValue * premultipliedBackgroundChannelValue)
  }

  private def blendSingleChannelUsingReflectAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    if (premultipliedForegroundChannelValue != 1f) {
      (premultipliedBackgroundChannelValue * premultipliedBackgroundChannelValue / (1f - premultipliedForegroundChannelValue))
        .min(1f)
    } else premultipliedForegroundChannelValue
  }

  private def blendSingleChannelUsingPinLightAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    if (premultipliedForegroundChannelValue < 0.5f) {
      blendSingleChannelUsingDarkenOnlyAlgorithm(
        premultipliedBackgroundChannelValue,
        2 * premultipliedForegroundChannelValue)
    } else {
      blendSingleChannelUsingLightenOnlyAlgorithm(
        premultipliedBackgroundChannelValue,
        2 * (premultipliedForegroundChannelValue - 0.5f))
    }
  }

  private def blendSingleChannelUsingHardMixAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    if (premultipliedBackgroundChannelValue + premultipliedForegroundChannelValue >= 1f) 1f
    else 0f
  }

  private def blendSingleChannelUsingDivideAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    if (premultipliedForegroundChannelValue > 0f)
      premultipliedBackgroundChannelValue / premultipliedForegroundChannelValue
    else 1
  }

  private def blendSingleChannelUsingMultiplyAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    premultipliedBackgroundChannelValue * premultipliedForegroundChannelValue
  }

  private def blendSingleChannelUsingLightenOnlyAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    premultipliedBackgroundChannelValue.max(premultipliedForegroundChannelValue)
  }

  private def blendSingleChannelUsingDarkenOnlyAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    premultipliedBackgroundChannelValue.min(premultipliedForegroundChannelValue)
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
      blendSingleChannelUsingColorBurnAlgorithm(
        premultipliedBackgroundChannelValue,
        2 * premultipliedForegroundChannelValue)
    } else {
      blendSingleChannelUsingColorDodgeAlgorithm(
        premultipliedBackgroundChannelValue,
        2 * (premultipliedForegroundChannelValue - 0.5f))
    }
  }

  private def blendSingleChannelUsingLinearLightAlgorithm(
      premultipliedBackgroundChannelValue: Float,
      premultipliedForegroundChannelValue: Float): Float = {
    if (premultipliedForegroundChannelValue < 0.5f) {
      blendSingleChannelUsingLinearBurnAlgorithm(
        premultipliedBackgroundChannelValue,
        2 * premultipliedForegroundChannelValue)
    } else {
      blendSingleChannelUsingLinearDodgeAlgorithm(
        premultipliedBackgroundChannelValue,
        2 * (premultipliedForegroundChannelValue - 0.5f))
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
    blendSingleChannelUsingOverlayAlgorithm(
      premultipliedForegroundChannelValue,
      premultipliedBackgroundChannelValue)
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
