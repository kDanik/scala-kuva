package com.example
package core.color.operations.grayscale

/**
 * Algorithms for grayscale color conversion:
 *
 * AVERAGING (quick and dirty) - Calculates average of red, green and blue
 *
 * LUMA - Correcting for the human eye, conversion will weight each color based on how the human
 * eye perceives it. BT709 and BT601 (Luminance) are different LUMA formulas based on
 * recommendation of ITU-R
 *
 * DESATURATION - Desaturating works by converting color to an HSL, then changing the saturation
 * to zero. Results in a flatter, softer grayscale image.
 *
 * SINGLE_COLOR_CHANNEL_* - uses value of one selected RGB channel for values of all other
 * channels
 *
 * DECOMPOSITION_MIN - uses minimum value of one of RGB channel for values of all other channels
 *
 * DECOMPOSITION_MAX (also known as Value) - uses maximum value of one of RGB channel for values
 * of all other channels
 *
 * LIGHTNESS - is a perceptually uniform grayscale representation used in the CIELAB and CIELUV
 * color spaces
 *
 * LIGHTNESS_HSL - is the L channel in the HLS (Hue, Lightness, and Saturation) color space.
 */
enum GrayscaleConversionAlgorithm:
  case AVERAGING,
    LUMA_BT709,
    LUMA_BT601,
    DESATURATION,
    SINGLE_COLOR_CHANNEL_RED,
    SINGLE_COLOR_CHANNEL_GREEN,
    SINGLE_COLOR_CHANNEL_BLUE,
    DECOMPOSITION_MIN,
    DECOMPOSITION_MAX,
    LIGHTNESS,
    LIGHTNESS_HSL
