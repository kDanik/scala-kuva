package com.example
package core.color.types

import core.color.types.{Color, ColorHsla, ColorRgba}
import core.support.{FloatWithAlmostEquals, Precision}

import spire.math.{UByte, max}

final case class ColorRgba(red: UByte, green: UByte, blue: UByte, alpha: UByte = UByte(255)) extends Color {
  lazy val RgbInt: Int = (red.intValue << 16) | (green.intValue << 8) | blue.intValue
  lazy val RgbaInt: Int = (alpha.intValue << 24) | (red.intValue << 16) | (green.intValue << 8) | blue.intValue

  override def asAwtColor: java.awt.Color = java.awt.Color(red.intValue, green.intValue, blue.intValue, alpha.intValue)

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
      alpha
    )
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
      alpha
    )
  }

  def rgbValuesAsFloats: (Float, Float, Float) = {
    val r: Float = red.toFloat / 255
    val g: Float = green.toFloat / 255
    val b: Float = blue.toFloat / 255
    (r, g, b)
  }

  def rbgaValuesAsFloats: (Float, Float, Float, Float) = {
    val (r, g, b) = rgbValuesAsFloats
    val a: Float = alphaAsFloat

    (r, g, b, a)
  }

  def alphaAsFloat: Float = {
    alpha.toFloat / 255
  }

  private def calculateHue(chroma: Float, value: Float, r: Float, g: Float, b: Float): Float = {
    implicit val precision: Precision = Precision(0.0001f)
    if (chroma ~= 0f) { // could be Precision constant
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
    implicit val precision: Precision = Precision(0.0001f)

    if ((lightness ~= 0) || (lightness ~= 1)) {
      0
    }
    else {
      (value - lightness) / lightness.min(1 - lightness)
    }
  }

  private def calculateHsvSaturation(value: Float, chroma: Float): Float = {
    implicit val precision: Precision = Precision(0.0001f)

    if (value ~= 0) {
      0
    }
    else {
      chroma / value
    }
  }
}

object ColorRgba {
  def apply(colorAwt: java.awt.Color): ColorRgba = {
    apply(colorAwt.getRed, colorAwt.getGreen, colorAwt.getBlue, colorAwt.getAlpha)
  }

  def apply(red: Int, green: Int, blue: Int, alpha: Int): ColorRgba = {
    ColorRgba(normalizeColorChannelValue(red), normalizeColorChannelValue(green), normalizeColorChannelValue(blue), normalizeColorChannelValue(alpha))
  }

  def apply(red: Int, green: Int, blue: Int, alpha: UByte): ColorRgba = {
    ColorRgba(normalizeColorChannelValue(red), normalizeColorChannelValue(green), normalizeColorChannelValue(blue), alpha)
  }

  def apply(red: Float, green: Float, blue: Float, alpha: Float): ColorRgba = {
    ColorRgba(normalizeColorChannelValue(red), normalizeColorChannelValue(green), normalizeColorChannelValue(blue), normalizeColorChannelValue(alpha))
  }

  def apply(red: Float, green: Float, blue: Float, alpha: UByte): ColorRgba = {
    ColorRgba(normalizeColorChannelValue(red), normalizeColorChannelValue(green), normalizeColorChannelValue(blue), alpha)
  }

  /**
   * Converts color values from float (in standard range from 0 to 1f)
   * to UByte, used for color channel values in ColorRgba.
   * If value is outside of range (0-1f), closest valid value will be used (so 0 or 1f)
   */
  def normalizeColorChannelValue(colorChannelValue: Float): UByte = {
    UByte((colorChannelValue.max(0f).min(1f) * 255).round)
  }

  /**
   * Converts color values from integer (in standard range from 0 to 255)
   * to UByte, used for color channel values in ColorRgba.
   * If value is outside of range (0-255), closest valid value will be used (so 0 or 255)
   */
  def normalizeColorChannelValue(colorChannelValue: Int): UByte = {
    UByte(colorChannelValue.max(0).min(255))
  }
}