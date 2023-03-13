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
  def main(args: Array[String]) = {
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
      "src/main/resources/result/color_inversion_strawberry.png",
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

  private def loadImage(path: String): BufferedImage = {
    ImageIO.read(File(path))
  }

  private def writeImage(path: String, bufferedImage: BufferedImage) = {
    val outputFile = new File(path)
    ImageIO.write(bufferedImage, "png", outputFile)
  }
}