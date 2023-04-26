package com.example
package examples

import core.color.operations.blend.{BlendMode, ColorBlending}
import core.color.types.Color
import examples.util.ImageExampleFileUtil

import com.example.core.image.Position
import com.example.core.image.operations.blending.ImageBlending

/**
 * This image examples are using randomly generated numbers to calculate how colors are blended.
 * Regenerating images will always modify them, as result is randomised.
 */
object RandomisedBlendImageExamplesMain {
  def main(args: Array[String]): Unit = {
    val resultedImageAbstractGraphics = ImageBlending.blend(
      ImageExampleFileUtil.loadImage("src/main/resources/source/blend/abstract_background.png"),
      ImageExampleFileUtil.loadImage("src/main/resources/source/blend/abstract_foreground.png"),
      Position(0, 0),
      BlendMode.DISSOLVE)

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/blend/blend_dissolve.png",
      resultedImageAbstractGraphics)

    val resultedImageGradient = ImageBlending.blend(
      ImageExampleFileUtil.loadImage("src/main/resources/source/blend/gradient_base.png"),
      ImageExampleFileUtil.loadImage("src/main/resources/source/blend/gradient_blend.png"),
      Position(0, 0),
      BlendMode.DISSOLVE)

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/blend/gradient/blend_dissolve.png",
      resultedImageGradient)
  }

}
