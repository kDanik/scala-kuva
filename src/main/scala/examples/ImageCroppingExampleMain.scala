package com.example
package examples

import core.image.Position
import examples.util.ImageExampleFileUtil

object ImageCroppingExampleMain {
  def main(args: Array[String]): Unit = {
    val cocktailImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail.png")

    cocktailImage.crop(Position(425, 550), Position(1000, 1250)) match {
      case Right(resultedImage) =>
        ImageExampleFileUtil.writeImage(
          "src/main/resources/result/crop/cropped_cocktail.png",
          resultedImage)
    }
  }
}
