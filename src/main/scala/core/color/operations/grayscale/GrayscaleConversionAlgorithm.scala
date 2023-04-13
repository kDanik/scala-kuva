package com.example
package core.color.operations.grayscale

/**
 * Different algorithms for grayscale color conversion used in GrayscaleColorConversion object.
 */
enum GrayscaleConversionAlgorithm:
  case
    /**
     * Calculates the average of red, green and blue values.
     */
    AVERAGING,
    /**
     * Conversion will weight each color based on how the human eye perceives it. BT709 and BT601
     * (Luminance) are different LUMA formulas based on recommendation of ITU-R.
     */
    LUMA_BT709,
    /**
     * Conversion will weight each color based on how the human eye perceives it. BT709 and BT601
     * (Luminance) are different LUMA formulas based on recommendation of ITU-R.
     */
    LUMA_BT601,
    /**
     * Desaturating works by converting color to an HSL, then changing the saturation to zero.
     * Results in a flatter, softer grayscale image.
     */
    DESATURATION,
    /**
     * Uses value of red channel as grayscale value.
     */
    SINGLE_COLOR_CHANNEL_RED,
    /**
     * Uses value of green channel as grayscale value.
     */
    SINGLE_COLOR_CHANNEL_GREEN,
    /**
     * Uses value of blue channel as grayscale value.
     */
    SINGLE_COLOR_CHANNEL_BLUE,
    /**
     * Uses minimum value of one of RGB channels as grayscale value.
     */
    DECOMPOSITION_MIN,
    /**
     * Uses maximum value of one of RGB channels as grayscale value.
     */
    DECOMPOSITION_MAX,
    /**
     * LIGHTNESS is a perceptually uniform grayscale representation used in the CIELAB and CIELUV
     * color spaces.
     */
    LIGHTNESS,
    /**
     * LIGHTNESS_HSL uses Lightness value from the HSL (Hue, Saturation and Lightness) color
     * space.
     */
    LIGHTNESS_HSL,
    /**
     * Middle value grayscale uses middle value of RGB channel as grayscale value. So for example
     * for RGB(100, 200, 120) it will use 120.
     */
    MIDDLE_VALUE
