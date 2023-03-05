package com.example
package core.color

import org.scalatest.flatspec.AnyFlatSpec

class ColorHSLASuite extends AnyFlatSpec {
  "ColorHSLA" can "be converted to ColorRGBA" in {
    val colorHSLA: ColorHSLA = ColorHSLA(190f, 0.75f, 0.6f, 0.2f)

    println(colorHSLA.asColorRGBA)
  }
}
