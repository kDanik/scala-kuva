package com.example
package examples

import core.color.operations.blend.{BlendMode, ColorBlending}
import core.color.types.Color
import examples.util.{ImageExampleFileUtil, ImageExampleOperations}

/**
 * This image examples are using randomly generated numbers to calculate how colors are blended.
 * Regenerating images will always modify them, as result is randomised.
 */
object RandomisedBlendImageExamplesMain {
  def main(args: Array[String]): Unit = {
    val resultedImageAbstractGraphics = ImageExampleOperations.combineTwoImageInOne(
      ImageExampleFileUtil.loadImage("src/main/resources/source/blend/abstract_background.png"),
      ImageExampleFileUtil.loadImage("src/main/resources/source/blend/abstract_foreground.png"),
      (background: Color, foreground: Color) =>
        ColorBlending.blend(background, foreground, BlendMode.DISSOLVE))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/blend/blend_dissolve.png",
      resultedImageAbstractGraphics)

    val resultedImageGradient = ImageExampleOperations.combineTwoImageInOne(
      ImageExampleFileUtil.loadImage("src/main/resources/source/blend/gradient_base.png"),
      ImageExampleFileUtil.loadImage("src/main/resources/source/blend/gradient_blend.png"),
      (background: Color, foreground: Color) =>
        ColorBlending.blend(background, foreground, BlendMode.DISSOLVE))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/blend/gradient/blend_dissolve.png",
      resultedImageGradient)
  }

}
