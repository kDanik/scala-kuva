package com.example
package examples

import core.color.operations.grayscale.{GrayscaleColorConversion, GrayscaleConversionAlgorithm}
import core.color.operations.inversion.ColorInversion
import core.image.operations.grayscale.NShadesOfGray
import examples.util.ImageExampleFileUtil

object NShadesOfGrayExampleMain {
  def main(args: Array[String]): Unit = {
    generateCocktailImageExamples()
  }

  private def generateCocktailImageExamples(): Unit = {
    val cocktailImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail.png")

    val cocktailImageGrayscale = cocktailImage.mapPixelColors(
      GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.LUMA_BT601))
    val resultedImage = NShadesOfGray.applyNShadesOfGray(cocktailImageGrayscale, 8, true)

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/n_shade_of_gray_cocktail.png",
      resultedImage)
  }
}
