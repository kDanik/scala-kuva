package com.example
package examples

import core.image.operations.scale.ImageScaling
import examples.util.ImageExampleFileUtil

object ImageScalingExamplesMain {
  def main(args: Array[String]): Unit = {
    val cocktailImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail.png")

    ImageScaling.scaleWithNearestNeighborAlgorithm(cocktailImage, 400, 500) match {
      case Right(resultedImage) =>
        ImageExampleFileUtil.writeImage(
          "src/main/resources/result/scale/downscale_nearest_neighbor_cocktail.png",
          resultedImage)
    }

    ImageScaling.scaleWithNearestNeighborAlgorithm(cocktailImage, 2000, 1500) match {
      case Right(resultedImage) =>
        ImageExampleFileUtil.writeImage(
          "src/main/resources/result/scale/upscale_nearest_neighbor_cocktail.png",
          resultedImage)
    }
  }
}
