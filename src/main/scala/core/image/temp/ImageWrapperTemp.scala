package com.example
package core.image.temp

import core.color.operations.grayscale.{GrayscaleColorConversion, GrayscaleConversionAlgorithm}
import core.color.operations.inversion.ColorInversion
import core.color.types.{Color, ColorRgba}

import java.awt.image.BufferedImage
import java.io.{File, IOException}
import javax.imageio.ImageIO

/**
 * This is just temporary (and dirty) image wrapper (for ColorAWT), that is used for testing / debugging of color blending, grayscale and etc.
 * Later this will be replaced by better image wrapper (probably immutable).
 */
object ImageWrapperTemp {
  def main(args: Array[String]): Unit = {
    generateCocktailImageExamples()
    generateCocktailImageExamples()
  }

  def generateStrawberryImageExamples(): Unit = {
    applyOperationToEachPixelOfImage("src/main/resources/source/strawberry.png",
      "src/main/resources/result/grayscale_averaging_strawberry.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.AVERAGING))

    applyOperationToEachPixelOfImage("src/main/resources/source/strawberry.png",
      "src/main/resources/result/grayscale_luma_bt601_strawberry.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.LUMA_BT601))

    applyOperationToEachPixelOfImage("src/main/resources/source/strawberry.png",
      "src/main/resources/result/grayscale_luma_bt709_strawberry.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.LUMA_BT709))

    applyOperationToEachPixelOfImage("src/main/resources/source/strawberry.png",
      "src/main/resources/result/grayscale_decomposition_max_strawberry.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, algorithm = GrayscaleConversionAlgorithm.DECOMPOSITION_MAX))

    applyOperationToEachPixelOfImage("src/main/resources/source/strawberry.png",
      "src/main/resources/result/grayscale_decomposition_min_strawberry.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.DECOMPOSITION_MIN))

    applyOperationToEachPixelOfImage("src/main/resources/source/strawberry.png",
      "src/main/resources/result/grayscale_desaturation_strawberry.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.DESATURATION))

    applyOperationToEachPixelOfImage("src/main/resources/source/strawberry.png",
      "src/main/resources/result/grayscale_single_color_channel_red_strawberry.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_RED))

    applyOperationToEachPixelOfImage("src/main/resources/source/strawberry.png",
      "src/main/resources/result/grayscale_single_color_channel_green_strawberry.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_GREEN))

    applyOperationToEachPixelOfImage("src/main/resources/source/strawberry.png",
      "src/main/resources/result/grayscale_single_color_channel_blue_strawberry.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_BLUE))

    applyOperationToEachPixelOfImage("src/main/resources/source/strawberry.png",
      "src/main/resources/result/grayscale_lightness_strawberry.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.LIGHTNESS))

    applyOperationToEachPixelOfImage("src/main/resources/source/strawberry.png",
      "src/main/resources/result/grayscale_lightness_hsl_strawberry.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.LIGHTNESS_HSL))

    applyOperationToEachPixelOfImage("src/main/resources/source/strawberry.png",
      "src/main/resources/result/color_inversion_strawberry.png",
      (color: Color) => ColorInversion.invertColor(color, false))
  }

  def generateCocktailImageExamples(): Unit = {
    applyOperationToEachPixelOfImage("src/main/resources/source/cocktail.png",
      "src/main/resources/result/grayscale_averaging_cocktail.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.AVERAGING))

    applyOperationToEachPixelOfImage("src/main/resources/source/cocktail.png",
      "src/main/resources/result/grayscale_luma_bt601_cocktail.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.LUMA_BT601))

    applyOperationToEachPixelOfImage("src/main/resources/source/cocktail.png",
      "src/main/resources/result/grayscale_luma_bt709_cocktail.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.LUMA_BT709))

    applyOperationToEachPixelOfImage("src/main/resources/source/cocktail.png",
      "src/main/resources/result/grayscale_decomposition_max_cocktail.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, algorithm = GrayscaleConversionAlgorithm.DECOMPOSITION_MAX))

    applyOperationToEachPixelOfImage("src/main/resources/source/cocktail.png",
      "src/main/resources/result/grayscale_decomposition_min_cocktail.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.DECOMPOSITION_MIN))

    applyOperationToEachPixelOfImage("src/main/resources/source/cocktail.png",
      "src/main/resources/result/grayscale_desaturation_cocktail.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.DESATURATION))

    applyOperationToEachPixelOfImage("src/main/resources/source/cocktail.png",
      "src/main/resources/result/grayscale_single_color_channel_red_cocktail.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_RED))

    applyOperationToEachPixelOfImage("src/main/resources/source/cocktail.png",
      "src/main/resources/result/grayscale_single_color_channel_green_cocktail.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_GREEN))

    applyOperationToEachPixelOfImage("src/main/resources/source/cocktail.png",
      "src/main/resources/result/grayscale_single_color_channel_blue_cocktail.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.SINGLE_COLOR_CHANNEL_BLUE))

    applyOperationToEachPixelOfImage("src/main/resources/source/cocktail.png",
      "src/main/resources/result/grayscale_lightness_cocktail.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.LIGHTNESS))

    applyOperationToEachPixelOfImage("src/main/resources/source/cocktail.png",
      "src/main/resources/result/grayscale_lightness_hsl_cocktail.png",
      (color: Color) => GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.LIGHTNESS_HSL))

    applyOperationToEachPixelOfImage("src/main/resources/source/cocktail.png",
      "src/main/resources/result/color_inversion_cocktail.png",
      (color: Color) => ColorInversion.invertColor(color, false))
  }

  def applyOperationToEachPixelOfImage(sourceImagePath: String, resultedImagePath: String, colorOperation: Color => ColorRgba): Unit = {
    val bufferedImage = loadImage(sourceImagePath)
    val width = bufferedImage.getWidth
    val height = bufferedImage.getHeight

    val resultedImage = BufferedImage(width, height, bufferedImage.getType);

    for (x <- 0 until width; y <- 0 until height) {
      val awtColor = java.awt.Color(bufferedImage.getRGB(x, y), true)

      resultedImage.setRGB(x, y, colorOperation(ColorRgba.apply(awtColor)).asAwtColor.getRGB)
    }

    writeImage(resultedImagePath, resultedImage)
  }

  def combineTwoImageInOne(sourceBackgroundImagePath: String, sourceForegroundImagePath: String, resultedImagePath: String, colorOperation: (ColorRgba, ColorRgba) => ColorRgba): Unit = {
    val bufferedImageBackground = loadImage(sourceBackgroundImagePath)
    val bufferedImageForeground = loadImage(sourceForegroundImagePath)

    // this test method assumes that 2 images have same size. For real life blending also coordinates of images should be used
    // (to combine images with different size
    val width = bufferedImageBackground.getWidth
    val height = bufferedImageBackground.getHeight

    // it also assumes images have same type
    val resultedImage = BufferedImage(width, height, bufferedImageBackground.getType);


    for (x <- 0 until width; y <- 0 until height) {
      val awtColorBackground = java.awt.Color(bufferedImageBackground.getRGB(x, y), true)
      val awtColorForeground = java.awt.Color(bufferedImageForeground.getRGB(x, y), true)

      resultedImage.setRGB(x, y, colorOperation(ColorRgba.apply(awtColorBackground), ColorRgba.apply(awtColorForeground)).asAwtColor.getRGB)
    }

    writeImage(resultedImagePath, resultedImage)
  }

  private def loadImage(path: String): BufferedImage = {
    ImageIO.read(File(path))
  }

  private def writeImage(path: String, bufferedImage: BufferedImage) = {
    val outputFile = new File(path)
    ImageIO.write(bufferedImage, "png", outputFile)
  }
}
