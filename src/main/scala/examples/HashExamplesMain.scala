package com.example
package examples

import core.image.ImmutableBufferedImage
import core.image.hash.ImageHashing
import examples.util.ImageExampleFileUtil

object HashExamplesMain {
  def main(args: Array[String]): Unit = {
    val cocktailImage: ImmutableBufferedImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail.png")
    val cocktailImageLowRes: ImmutableBufferedImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/cocktail_low_res.png")
    val sheep: ImmutableBufferedImage =
      ImageExampleFileUtil.loadImage("src/main/resources/source/sheep.png")

    println(
      "Difference hash for the picture of cocktail(high res): " + ImageHashing
        .dHash(cocktailImage)
        .fold(l => l, r => r.toHexString))
    println(
      "Difference hash for the picture of cocktail(low res, downscaled): " + ImageHashing
        .dHash(cocktailImageLowRes)
        .fold(l => l, r => r.toHexString))
    println(
      "Difference hash for the picture of sheep: " + ImageHashing
        .dHash(sheep)
        .fold(l => l, r => r.toHexString))

    println(
      "Average hash for the picture of cocktail(high res): " + ImageHashing
        .aHash(cocktailImage)
        .toHexString)
    println(
      "Average hash for the picture of cocktail(low res, downscaled): " + ImageHashing
        .aHash(cocktailImageLowRes)
        .toHexString)
    println(
      "Average hash for the picture of sheep: " + ImageHashing
        .aHash(sheep)
        .toHexString)

    println(
      "Median hash for the picture of cocktail(high res): " + ImageHashing
        .mHash(cocktailImage)
        .toHexString)
    println(
      "Median hash for the picture of cocktail(low res, downscaled): " + ImageHashing
        .mHash(cocktailImageLowRes)
        .toHexString)
    println(
      "Median hash for the picture of sheep: " + ImageHashing
        .mHash(sheep)
        .toHexString)
  }
}
