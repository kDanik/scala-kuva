package com.example
package core.color.operations.blend

/**
 * Blend modes that are used in ColorBlending object
 */
enum BlendMode:
  case
    /**
     * Normal blend mode will simply replace all corresponding pixels of background image with
     * pixels from foreground. Normal blend mode shouldn't be used for images with transparent
     * colors, instead SIMPLE_ALPHA_COMPOSITING should be used.
     *
     * TODO NOT IMPLEMENTED YET
     */
    NORMAL,
    /**
     * Alpha compositing work in the same way as normal blend mode, except it also takes in
     * account transparency of colors to create a more complex and flexible blend.
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
    SOFT_LIGHT_PHOTOSHOP,
    SOFT_LIGHT_W3C,
    SCREEN_DODGE,
    COLOR_DODGE,
    LINEAR_DODGE,
    DIVIDE_DODGE,
    MULTIPLY_BURN,
    COLOR_BURN,
    LINEAR_BURN,
    VIVID_LIGHT,
    LINEAR_LIGHT,
    SUBTRACT,
    INVERSE_SUBTRACT,
    DIFFERENCE,
    DARKEN_ONLY,
    LIGHTEN_ONLY
