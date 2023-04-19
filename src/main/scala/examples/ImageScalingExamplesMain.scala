package com.example
package examples

import core.image.operations.scale.ImageScaling
import examples.util.ImageExampleFileUtil

object ImageScalingExamplesMain {
  def main(args: Array[String]): Unit = {
    val cocktailImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail.png")
    val cocktailLowRes =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail_low_res.png")

    ImageScaling.scaleWithNearestNeighborAlgorithm(cocktailImage, 128, 160) match {
      case Right(resultedImage) =>
        ImageExampleFileUtil.writeImage(
          "src/main/resources/result/scale/downscale_nearest_neighbor_cocktail.png",
          resultedImage)
    }

    ImageScaling.scaleWithNearestNeighborAlgorithm(cocktailLowRes, 1024, 1280) match {
      case Right(resultedImage) =>
        ImageExampleFileUtil.writeImage(
          "src/main/resources/result/scale/upscale_nearest_neighbor_cocktail.png",
          resultedImage)
    }

    ImageScaling.scaleWithBilinearInterpolation(cocktailLowRes, 1024, 1280) match {
      case Right(resultedImage) =>
        ImageExampleFileUtil.writeImage(
          "src/main/resources/result/scale/upscale_bilinear_interpolation_cocktail.png",
          resultedImage)
    }

    ImageScaling.scaleWithBicubicInterpolation(cocktailLowRes, 1024, 1280) match {
      case Right(resultedImage) =>
        ImageExampleFileUtil.writeImage(
          "src/main/resources/result/scale/upscale_bicubic_interpolation_cocktail.png",
          resultedImage)
    }
  }
}
