package com.example
package examples

import core.color.operations.blend.{BlendMode, ColorBlending}
import core.color.operations.grayscale.{GrayscaleColorConversion, GrayscaleConversionAlgorithm}
import core.color.types.Color
import core.image.operations.binarization.OtsuBinarization
import examples.util.ImageExampleFileUtil

object BinarizationImageExamplesMain {
  def main(args: Array[String]): Unit = {
    val sourceImage = ImageExampleFileUtil.loadImage("src/main/resources/source/sheep.png")

    val sourceGrayscaleImage = sourceImage.mapPixelColors(
      GrayscaleColorConversion.applyGrayscale(_, GrayscaleConversionAlgorithm.LUMA_BT601))
    val binarizedImage = OtsuBinarization.binarizeImage(sourceGrayscaleImage)

    ImageExampleFileUtil.writeImage(
      "src/main/resources/result/otsu_binarization_sheep.png",
      binarizedImage)
  }

}
