package com.example
package examples

import examples.util.ImageExampleFileUtil

object ImageCroppingExampleMain {
  def main(args: Array[String]): Unit = {
    val cocktailImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail.png")

    cocktailImage.crop(425, 550, 1000, 1250) match {
      case Right(resultedImage) =>
        ImageExampleFileUtil.writeImage(
          "src/main/resources/result/crop/cropped_cocktail.png",
          resultedImage)
    }
  }
}
