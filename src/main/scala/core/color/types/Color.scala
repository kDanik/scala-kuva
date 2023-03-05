package com.example
package core.color.types

trait Color {
  def asAWTColor: java.awt.Color

  def asColorRGBA: ColorRGBA

  def asColorHSLA: ColorHSLA

  def asColorHSVA: ColorHSVA
}
