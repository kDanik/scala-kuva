package com.example
package examples

import core.color.operations.grayscale.{GrayscaleColorConversion, GrayscaleConversionAlgorithm}
import core.color.types.Color
import core.image.ImmutableBufferedImage
import examples.util.ImageExampleFileUtil

object GrayscaleImageExamplesMain {
  def main(args: Array[String]): Unit = {
    generateCocktailImageExamples()
    generateStrawberryImageExamples()
  }

  private def generateCocktailImageExamples(): Unit = {
    val cocktailImage: ImmutableBufferedImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail.png")

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_averaging_cocktail.png",
      cocktailImage.mapPixelColors(
        GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.AVERAGING)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_luma_bt601_cocktail.png",
      cocktailImage.mapPixelColors(
        GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.LUMA_BT601)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_luma_bt709_cocktail.png",
      cocktailImage.mapPixelColors(
        GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.LUMA_BT709)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_decomposition_max_cocktail.png",
      cocktailImage.mapPixelColors(
        GrayscaleColorConversion
          .applyGrayscale(_, GrayscaleConversionAlgorithm.DECOMPOSITION_MAX)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_decomposition_min_cocktail.png",
      cocktailImage.mapPixelColors(
        GrayscaleColorConversion
          .applyGrayscale(_, GrayscaleConversionAlgorithm.DECOMPOSITION_MIN)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_desaturation_cocktail.png",
      cocktailImage.mapPixelColors(
        GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.DESATURATION)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_single_color_channel_red_cocktail.png",
      cocktailImage.mapPixelColors(
        GrayscaleColorConversion
          .applyGrayscale(_, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_RED)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_single_color_channel_green_cocktail.png",
      cocktailImage.mapPixelColors(
        GrayscaleColorConversion
          .applyGrayscale(_, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_GREEN)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_single_color_channel_blue_cocktail.png",
      cocktailImage.mapPixelColors(
        GrayscaleColorConversion
          .applyGrayscale(_, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_BLUE)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_lightness_cocktail.png",
      cocktailImage.mapPixelColors(
        GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.LIGHTNESS)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_lightness_hsl_cocktail.png",
      cocktailImage.mapPixelColors(
        GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.LIGHTNESS_HSL)))
  }

  private def generateStrawberryImageExamples(): Unit = {
    val strawberryImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/strawberry.png")

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_averaging_strawberry.png",
      strawberryImage.mapPixelColors(
        GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.AVERAGING)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_luma_bt601_strawberry.png",
      strawberryImage.mapPixelColors(
        GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.LUMA_BT601)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_luma_bt709_strawberry.png",
      strawberryImage.mapPixelColors(
        GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.LUMA_BT709)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_decomposition_max_strawberry.png",
      strawberryImage.mapPixelColors(
        GrayscaleColorConversion
          .applyGrayscale(_, GrayscaleConversionAlgorithm.DECOMPOSITION_MAX)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_decomposition_min_strawberry.png",
      strawberryImage.mapPixelColors(
        GrayscaleColorConversion
          .applyGrayscale(_, GrayscaleConversionAlgorithm.DECOMPOSITION_MIN)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_desaturation_strawberry.png",
      strawberryImage.mapPixelColors(
        GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.DESATURATION)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_single_color_channel_red_strawberry.png",
      strawberryImage.mapPixelColors(
        GrayscaleColorConversion
          .applyGrayscale(_, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_RED)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_single_color_channel_green_strawberry.png",
      strawberryImage.mapPixelColors(
        GrayscaleColorConversion
          .applyGrayscale(_, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_GREEN)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_single_color_channel_blue_strawberry.png",
      strawberryImage.mapPixelColors(
        GrayscaleColorConversion
          .applyGrayscale(_, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_BLUE)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_lightness_strawberry.png",
      strawberryImage.mapPixelColors(
        GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.LIGHTNESS)))

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/grayscale_lightness_hsl_strawberry.png",
      strawberryImage.mapPixelColors(
        GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.LIGHTNESS_HSL)))
  }
}
