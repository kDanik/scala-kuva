package com.example
package core.color.operations

/**
 * Algorithms for grayscale color conversion:
 *
 * AVERAGING (quick and dirty) - Calculates average of red, green and blue
 *
 * LUMA - Correcting for the human eye, conversion will weight each color based on how the human eye perceives it.
 * BT709 and BT601 are different LUMA formulas based on recommendation of ITU-R
 *
 * DESATURATION - Desaturating works by converting color to an HSL, then changing the saturation to zero. Results in a flatter, softer grayscale image
 *
 * SINGLE_COLOR_CHANNEL_* - uses value of one selected channel for values of all other channels
 */
enum GrayscaleConversionAlgorithm:
  case AVERAGING,
  LUMA_BT709,
  LUMA_BT601,
  DESATURATION,
  SINGLE_COLOR_CHANNEL_RED,
  SINGLE_COLOR_CHANNEL_GREEN,
  SINGLE_COLOR_CHANNEL_BLUE
