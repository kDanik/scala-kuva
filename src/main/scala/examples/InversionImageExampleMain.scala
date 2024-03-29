package com.example
package examples

import core.color.operations.inversion.ColorInversion
import core.color.types.Color
import core.image.ImmutableBufferedImage
import examples.util.ImageExampleFileUtil

object InversionImageExampleMain {
  def main(args: Array[String]): Unit = {
    generateCocktailImageExamples()
  }

  private def generateCocktailImageExamples(): Unit = {
    val cocktailImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail.png")

    val resultedInvertedImage = cocktailImage.mapPixelColors(ColorInversion.invertColor(_))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/color_inversion_cocktail.png",
      resultedInvertedImage)
  }
}
