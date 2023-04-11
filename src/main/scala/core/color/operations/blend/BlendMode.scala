package com.example
package core.color.operations.blend

/**
 * Blend modes that are used in ColorBlending object
 */
enum BlendMode:
  case
    /**
     * Results in the top layer's shape, as defined by its alpha channel, appearing over the
     * bottom layer. Alpha compositing work in the same way as normal blend mode, except it also
     * takes in account transparency of colors to create a more complex and flexible blend.
     */
    SIMPLE_ALPHA_COMPOSITING,
    /**
     * Dissolve blend mode will simply randomly choose which color will be taken - foreground or
     * background. It can be implemented with pseudorandom noise, but here it is done with simple
     * random value. For images with transparency, the chances of getting foreground or background
     * will depend on their alpha values.
     */
    DISSOLVE,
    /**
     * Multiply blend mode takes RGB values (from 0f to 1f) of each channel and multiplies them.
     * Resulting color will always be darker than initial color.
     */
    MULTIPLY,
    /**
     * Screen blend mode is similar to Multiply, but it inverts value of color channel before
     * multiplication and inverts resulted value after multiplication. The result is the opposite
     * of Multiply, resulted color will always be brighter than initial color.
     */
    SCREEN,
    /**
     * Overlay blend mode is combination of Multiply and Screen blend modes. The light (> 0.5f)
     * initial value will become lighter (by applying Screen blend mode) and dark (< 0.5f) value
     * will become darker (by applying Multiply blend mode)
     */
    OVERLAY,
    /**
     * Hard light is the also combination of Multiply and Screen blend modes, opposite to Overlay
     * blend mode. Hard light doesn't have connection to Soft Light blend, except similar name.
     */
    HARD_LIGHT,
    /**
     * Soft light blend mode adjusts the brightness and contrast of the underlying layer based on
     * the luminance value of the top layer. If the blend color is brighter than 50% gray, it
     * creates a dodging effect that lightens the image, while a blend color darker than 50% gray
     * creates a burning effect that darkens the image.
     */
    SOFT_LIGHT,
    COLOR_DODGE,
    LINEAR_DODGE,
    COLOR_BURN,
    LINEAR_BURN,
    VIVID_LIGHT,
    LINEAR_LIGHT,
    SUBTRACT,
    DIVIDE,
    DIFFERENCE,
    DARKEN_ONLY,
    LIGHTEN_ONLY,
    PIN_LIGHT,
    HARD_MIX,
    REFLECT,
    EXCLUSION,
    GEOMETRIC_MEAN
