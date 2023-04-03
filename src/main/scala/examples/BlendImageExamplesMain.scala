package com.example
package examples

import core.color.operations.blend.{BlendMode, ColorBlending}
import core.color.types.Color
import examples.util.{ImageExampleFileUtil, ImageExampleOperations}

object BlendImageExamplesMain {
  def main(args: Array[String]): Unit = {
    val resultedImage = ImageExampleOperations.combineTwoImageInOne(
      ImageExampleFileUtil.loadImage("src/main/resources/source/blend/abstract_background.png"),
      ImageExampleFileUtil.loadImage("src/main/resources/source/blend/abstract_foreground.png"),
      (background: Color, foreground: Color) =>
        ColorBlending.blend(background, foreground, BlendMode.SIMPLE_ALPHA_COMPOSITING))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/blend/blend_simple_alpha_compositing.png",
      resultedImage)
  }
}
