package com.example
package examples

import core.image.ImmutableBufferedImage
import core.image.operations.blur.{BoxBlur, GaussianBlur, MedianBlur}
import examples.util.ImageExampleFileUtil

import javax.print.attribute.standard.Media

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

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/blur/median_blur_cocktail.png",
      MedianBlur.blur(cocktailImage, 7))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/blur/gaussian_blur_cocktail.png",
      GaussianBlur.blur(cocktailImage, 101, 101))
  }
}
