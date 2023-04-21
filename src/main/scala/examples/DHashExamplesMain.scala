package com.example
package examples

import core.image.ImmutableBufferedImage
import examples.util.ImageExampleFileUtil
import core.image.hash.ImageHashing

object DHashExamplesMain {
  def main(args: Array[String]): Unit = {
    val cocktailImage: ImmutableBufferedImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail.png")
    val cocktailImageLowRes: ImmutableBufferedImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail_low_res.png")
    val sheep: ImmutableBufferedImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/sheep.png")

    println(
      "ImageHashing for the picture of cocktail(high res): " + ImageHashing.dHash(cocktailImage))
    println(
      "ImageHashing for the picture of cocktail(low res, downscaled): " + ImageHashing.dHash(
        cocktailImageLowRes))
    println("ImageHashing for the picture of sheep: " + ImageHashing.dHash(sheep))
  }
}
