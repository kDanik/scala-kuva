package com.example
package core.color.types

import core.color.types.{Color, ColorHsla, ColorRgba}
import core.support.{FloatWithAlmostEquals, Precision}

import spire.math.UByte

final case class ColorRgba(red: UByte, green: UByte, blue: UByte, alpha: UByte = UByte(255))
    extends Color {

  /**
   * Representation of this color (excluding alpha) as single int value
   */
  def rgbInt: Int = (red.intValue << 16) | (green.intValue << 8) | blue.intValue

  /**
   * Representation of this color as single int value
   */
  def rgbaInt: Int =
    (alpha.intValue << 24) | (red.intValue << 16) | (green.intValue << 8) | blue.intValue

  override def asAwtColor: java.awt.Color =
    java.awt.Color(red.intValue, green.intValue, blue.intValue, alpha.intValue)

  override def asColorRgba: ColorRgba = this

  override def asColorHsla: ColorHsla = {
    val (r, g, b) = rgbValuesAsFloats

    val value: Float = r.max(g).max(b)
    val xMin: Float = r.min(g).min(b)
    val chroma = value - xMin
    val lightness = (value + xMin) / 2

    ColorHsla(
      calculateHue(chroma, value, r, g, b),
      calculateHslSaturation(value, lightness),
      lightness,
      alpha)
  }

  override def asColorHsva: ColorHsva = {
    val (r, g, b) = rgbValuesAsFloats

    val value: Float = r.max(g).max(b)
    val xMin: Float = r.min(g).min(b)
    val chroma = value - xMin

    ColorHsva(
      calculateHue(chroma, value, r, g, b),
      calculateHsvSaturation(value, chroma),
      value,
      alpha)
  }

  /**
   * @return
   *   Tuple with RGB (excluding alpha) channel values converted to float (in range from 0f to 1f)
   */
  def rgbValuesAsFloats: (Float, Float, Float) = {
    (redAsFloat, greenAsFloat, blueAsFloat)
  }

  /**
   * @return
   *   Tuple with RGBA channel values converted to float (in range from 0f to 1f)
   */
  def rgbaValuesAsFloats: (Float, Float, Float, Float) = {
    val (r, g, b) = rgbValuesAsFloats
    val a: Float = alphaAsFloat

    (r, g, b, a)
  }

  /**
   * @return
   *   Alpha channel value as float (in range from 0f to 1f)
   */
  def alphaAsFloat: Float = {
    alpha.toFloat / 255
  }

  /**
   * @return
   *   Red channel value as float (in range from 0f to 1f)
   */
  def redAsFloat: Float = {
    red.toFloat / 255
  }

  /**
   * @return
   *   Green channel value as float (in range from 0f to 1f)
   */
  def greenAsFloat: Float = {
    green.toFloat / 255
  }

  /**
   * @return
   *   Blue channel value as float (in range from 0f to 1f)
   */
  def blueAsFloat: Float = {
    blue.toFloat / 255
  }

  private def calculateHue(chroma: Float, value: Float, r: Float, g: Float, b: Float): Float = {
    if (chroma ~= 0f) {
      0
    } else if (value ~= r) {
      60 * (((g - b) / chroma) % 2)
    } else if (value ~= g) {
      60 * (((b - r) / chroma) + 2)
    } else {
      60 * (((r - g) / chroma) + 4)
    }

  }

  private def calculateHslSaturation(value: Float, lightness: Float): Float = {
    if ((lightness ~= 0) || (lightness ~= 1)) {
      0
    } else {
      (value - lightness) / lightness.min(1 - lightness)
    }
  }

  private def calculateHsvSaturation(value: Float, chroma: Float): Float = {
    if (value ~= 0) {
      0
    } else {
      chroma / value
    }
  }
}

object ColorRgba extends ColorCompanion {
  def apply(colorAwt: java.awt.Color): ColorRgba = {
    apply(colorAwt.getRed, colorAwt.getGreen, colorAwt.getBlue, colorAwt.getAlpha)
  }

  def apply(red: Int, green: Int, blue: Int, alpha: Int): ColorRgba = {
    ColorRgba(
      normalizeColorChannelValue(red),
      normalizeColorChannelValue(green),
      normalizeColorChannelValue(blue),
      normalizeColorChannelValue(alpha))
  }

  def apply(red: Int, green: Int, blue: Int, alpha: UByte): ColorRgba = {
    ColorRgba(
      normalizeColorChannelValue(red),
      normalizeColorChannelValue(green),
      normalizeColorChannelValue(blue),
      alpha)
  }

  def apply(red: Float, green: Float, blue: Float, alpha: Float): ColorRgba = {
    ColorRgba(
      normalizeColorChannelValue(red),
      normalizeColorChannelValue(green),
      normalizeColorChannelValue(blue),
      normalizeColorChannelValue(alpha))
  }

  def apply(red: Float, green: Float, blue: Float, alpha: UByte): ColorRgba = {
    ColorRgba(
      normalizeColorChannelValue(red),
      normalizeColorChannelValue(green),
      normalizeColorChannelValue(blue),
      alpha)
  }

  def fromRgbInt(rgbInt: Int): ColorRgba = {
    val (red: UByte, green: UByte, blue: UByte) = rgbTupleFromRgbInt(rgbInt)

    ColorRgba(red, green, blue, UByte(255))
  }

  def fromRgbaInt(rgbaInt: Int): ColorRgba = {
    val (red: UByte, green: UByte, blue: UByte) = rgbTupleFromRgbInt(rgbaInt)
    val alpha = UByte((rgbaInt >> 24) & 0xff)

    ColorRgba(red, green, blue, alpha)
  }

  private def rgbTupleFromRgbInt(rgbInt: Int) = {
    val red = UByte((rgbInt >> 16) & 0xff)
    val green = UByte((rgbInt >> 8) & 0xff)
    val blue = UByte(rgbInt & 0xff)
    (red, green, blue)
  }
}
