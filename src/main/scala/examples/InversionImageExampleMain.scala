package com.example
package examples

import core.color.operations.inversion.ColorInversion
import core.color.types.Color
import examples.util.{ImageExampleFileUtil, ImageExampleOperations}

import com.example.core.image.ImmutableBufferedImage

object InversionImageExampleMain {
  def main(args: Array[String]): Unit = {
    generateCocktailImageExamples()
    generateStrawberryImageExamples()
  }

  private def generateCocktailImageExamples(): Unit = {
    val cocktailImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail.png")

    val resultedInvertedImage = cocktailImage.mapPixelColors(ColorInversion.invertColor(_))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/color_inversion_cocktail.png",
      resultedInvertedImage)
  }

  private def generateStrawberryImageExamples(): Unit = {
    val strawberryImage: ImmutableBufferedImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/strawberry.png")

    val resultedInvertedImage = strawberryImage.mapPixelColors(ColorInversion.invertColor(_))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/color_inversion_strawberry.png",
      resultedInvertedImage)
  }
}
