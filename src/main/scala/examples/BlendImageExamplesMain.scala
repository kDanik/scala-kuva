package com.example
package examples

import core.color.operations.blend.{BlendMode, ColorBlending}
import core.color.types.Color
import examples.util.{ImageExampleFileUtil, ImageExampleOperations}

object BlendImageExamplesMain {
  def main(args: Array[String]): Unit = {
    blendExamplesAbstractGraphics()
    blendExamplesGradients()
  }

  private def blendExamplesAbstractGraphics(): Unit = {
    val backgroundImagePath = "src/main/resources/source/blend/abstract_background.png"
    val foregroundImagePath = "src/main/resources/source/blend/abstract_foreground.png"

    blendTwoImages(
      BlendMode.SIMPLE_ALPHA_COMPOSITING,
      "src/main/resources/result/blend/blend_simple_alpha_compositing.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.MULTIPLY,
      "src/main/resources/result/blend/blend_multiply.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.SCREEN,
      "src/main/resources/result/blend/blend_screen.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.OVERLAY,
      "src/main/resources/result/blend/blend_overlay.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.HARD_LIGHT,
      "src/main/resources/result/blend/blend_hard_light.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.SOFT_LIGHT,
      "src/main/resources/result/blend/blend_soft_light.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.COLOR_DODGE,
      "src/main/resources/result/blend/blend_color_dodge.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.LINEAR_DODGE,
      "src/main/resources/result/blend/blend_linear_dodge.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.COLOR_BURN,
      "src/main/resources/result/blend/blend_color_burn.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.LINEAR_BURN,
      "src/main/resources/result/blend/blend_linear_burn.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.VIVID_LIGHT,
      "src/main/resources/result/blend/blend_vivid_light.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.LINEAR_LIGHT,
      "src/main/resources/result/blend/blend_linear_light.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.DIFFERENCE,
      "src/main/resources/result/blend/blend_difference.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.SUBTRACT,
      "src/main/resources/result/blend/blend_subtract.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.LIGHTEN_ONLY,
      "src/main/resources/result/blend/blend_lighten_only.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.DARKEN_ONLY,
      "src/main/resources/result/blend/blend_darken_only.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.DIVIDE,
      "src/main/resources/result/blend/blend_divide.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.HARD_MIX,
      "src/main/resources/result/blend/blend_hard_mix.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.PIN_LIGHT,
      "src/main/resources/result/blend/blend_pin_light.png",
      backgroundImagePath,
      foregroundImagePath)
  }

  private def blendExamplesGradients(): Unit = {
    val backgroundImagePath = "src/main/resources/source/blend/gradient_base.png"
    val foregroundImagePath = "src/main/resources/source/blend/gradient_blend.png"

    blendTwoImages(
      BlendMode.SIMPLE_ALPHA_COMPOSITING,
      "src/main/resources/result/blend/gradient/blend_simple_alpha_compositing.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.MULTIPLY,
      "src/main/resources/result/blend/gradient/blend_multiply.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.SCREEN,
      "src/main/resources/result/blend/gradient/blend_screen.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.OVERLAY,
      "src/main/resources/result/blend/gradient/blend_overlay.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.HARD_LIGHT,
      "src/main/resources/result/blend/gradient/blend_hard_light.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.SOFT_LIGHT,
      "src/main/resources/result/blend/gradient/blend_soft_light.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.COLOR_DODGE,
      "src/main/resources/result/blend/gradient/blend_color_dodge.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.LINEAR_DODGE,
      "src/main/resources/result/blend/gradient/blend_linear_dodge.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.COLOR_BURN,
      "src/main/resources/result/blend/gradient/blend_color_burn.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.LINEAR_BURN,
      "src/main/resources/result/blend/gradient/blend_linear_burn.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.VIVID_LIGHT,
      "src/main/resources/result/blend/gradient/blend_vivid_light.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.LINEAR_LIGHT,
      "src/main/resources/result/blend/gradient/blend_linear_light.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.DIFFERENCE,
      "src/main/resources/result/blend/gradient/blend_difference.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.SUBTRACT,
      "src/main/resources/result/blend/gradient/blend_subtract.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.LIGHTEN_ONLY,
      "src/main/resources/result/blend/gradient/blend_lighten_only.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.DARKEN_ONLY,
      "src/main/resources/result/blend/gradient/blend_darken_only.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.DIVIDE,
      "src/main/resources/result/blend/gradient/blend_divide.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.HARD_MIX,
      "src/main/resources/result/blend/gradient/blend_hard_mix.png",
      backgroundImagePath,
      foregroundImagePath)
    blendTwoImages(
      BlendMode.PIN_LIGHT,
      "src/main/resources/result/blend/gradient/blend_pin_light.png",
      backgroundImagePath,
      foregroundImagePath)
  }

  private def blendTwoImages(
      blendMode: BlendMode,
      resultedImagePath: String,
      backgroundImagePath: String,
      foregroundImagePath: String): Unit = {
    val resultedImage = ImageExampleOperations.combineTwoImageInOne(
      ImageExampleFileUtil.loadImage(backgroundImagePath),
      ImageExampleFileUtil.loadImage(foregroundImagePath),
      (background: Color, foreground: Color) =>
        ColorBlending.blend(background, foreground, blendMode))

    ImageExampleFileUtil.writeImage(resultedImagePath, resultedImage)
  }
}
