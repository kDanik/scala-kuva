package com.example
package core.color

trait Color {
  def asAWTColor: java.awt.Color

  def asColorRGBA: ColorRGBA

  def asColorHSLA: ColorHSLA
}
