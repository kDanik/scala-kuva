package com.example
package core.color.types

import core.color.types.{Color, ColorHSLA, ColorRGBA}
import core.support.{FloatWithAlmostEquals, Precision}

import spire.math.{UByte, max}

final case class ColorRGBA(red: UByte, green: UByte, blue: UByte, alpha: UByte = UByte(255)) extends Color {
  lazy val RGBInt: Int = (red.intValue << 16) | (green.intValue << 8) | blue.intValue
  lazy val RGBAInt: Int = (alpha.intValue << 24) | (red.intValue << 16) | (green.intValue << 8) | blue.intValue

  // is having this as lazy val instead more effective?
  override def asAWTColor: java.awt.Color = java.awt.Color(red.intValue, green.intValue, blue.intValue, alpha.intValue)

  override def asColorRGBA: ColorRGBA = this

  override def asColorHSLA: ColorHSLA = {
    val (r, g, b) = convertRGBValuesToFloat

    val value: Float = r.max(g).max(b)
    val xMin: Float = r.min(g).min(b)
    val chroma = value - xMin
    val lightness = (value + xMin) / 2

    ColorHSLA(
      calculateHue(chroma, value, r, g, b),
      calculateHSLSaturation(value, lightness),
      lightness,
      alpha
    )
  }

  override def asColorHSVA: ColorHSVA = {
    val (r, g, b) = convertRGBValuesToFloat

    val value: Float = r.max(g).max(b)
    val xMin: Float = r.min(g).min(b)
    val chroma = value - xMin

    ColorHSVA(
      calculateHue(chroma, value, r, g, b),
      calculateHSVSaturation(value, chroma),
      value,
      alpha
    )
  }

  private def convertRGBValuesToFloat: (Float, Float, Float) = {
    val r: Float = red.toFloat / 255
    val g: Float = green.toFloat / 255
    val b: Float = blue.toFloat / 255
    (r, g, b)
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

  private def calculateHSLSaturation(value: Float, lightness: Float): Float = {
    implicit val precision: Precision = Precision(0.0001f)

    if ((lightness ~= 0) || (lightness ~= 1)) {
      0
    }
    else {
      (value - lightness) / lightness.min(1 - lightness)
    }
  }

  private def calculateHSVSaturation(value: Float, chroma: Float): Float = {
    implicit val precision: Precision = Precision(0.0001f)

    if (value ~= 0) {
      0
    }
    else {
      chroma / value
    }
  }
}

object ColorRGBA {
  def apply(colorAWT: java.awt.Color): ColorRGBA = {
    apply(colorAWT.getRed, colorAWT.getGreen, colorAWT.getBlue, colorAWT.getAlpha)
  }

  def apply(red: Int, green: Int, blue: Int, alpha: Int): ColorRGBA = {
    ColorRGBA(UByte(red), UByte(green), UByte(blue), UByte(alpha))
  }

  def apply(red: Int, green: Int, blue: Int, alpha: UByte): ColorRGBA = {
    ColorRGBA(UByte(red), UByte(green), UByte(blue), alpha)
  }
}