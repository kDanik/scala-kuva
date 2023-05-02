package com.example
package examples

import core.image.ImmutableBufferedImage
import core.image.operations.blur.BoxBlur
import examples.util.ImageExampleFileUtil

object BlurExamplesMain {
  def main(args: Array[String]): Unit = {
    generateCocktailImageExamples()
  }
  private def generateCocktailImageExamples(): Unit = {
    val cocktailImage: ImmutableBufferedImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail.png")

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/blur/box_blur_cocktail.png",
      BoxBlur.blur(cocktailImage, 7))
  }
}
