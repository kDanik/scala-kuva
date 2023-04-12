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
    /**
     * Color dodge blend mode divides the base layer by the inverted blend (top) layer.
     */
    COLOR_DODGE,
    /**
     * Linear dodge blend mode simply sums up the values of base and blend layers.
     */
    LINEAR_DODGE,
    /**
     * Color burn blend mode divides inverted base layer by the blend (top) layer and then inverts
     * the result.
     */
    COLOR_BURN,
    /**
     * Linear burn blend mode sums the values of the two layers and subtracts 1.
     */
    LINEAR_BURN,
    /**
     * Vivid Light blend mode combines Color Dodge and Color Burn (rescaled). Color Dodge is
     * applied when the value on the top layer is lighter than middle value, and Color Burn
     * applies when the top layer value is darker.
     */
    VIVID_LIGHT,
    /**
     * Linear Light blend mode combines Linear Dodge and Linear Burn (rescaled). Linear Dodge is
     * applied when the value on the top layer is lighter than middle value, and Linear Burn
     * applies when the top layer value is darker.
     */
    LINEAR_LIGHT,
    /**
     * SUBTRACT blend mode will simply subtract the top layer from bottom layer. In case of
     * negative values, black is displayed.
     */
    SUBTRACT,
    /**
     * Divide blend mode will simply divide bottom layer by top layer. It is useful for removing
     * color tint from image or brightening the image.
     */
    DIVIDE,
    /**
     * Difference blend mode calculates difference between values of bottom and top layers and
     * uses it as new color.
     */
    DIFFERENCE,
    /**
     * Darken only (or just darken) blend mode work by taking the smallest component of the two
     * layers for each color channel. Resulting color will always be either darker or same as it
     * was.
     */
    DARKEN_ONLY,
    /**
     * Lighten only (or just lighten) blend mode work by taking the maximum component of the two
     * layers for each color channel. Resulting color will always be either lighter or same as it
     * was.
     */
    LIGHTEN_ONLY,
    /**
     * Pin Light blending mode is a combination of the Darken only and Lighten only blending
     * modes. If a color channel's value is below 0.5, the Darken blend mode is applied, and the
     * Lighten mode is applied if the value is greater than 0.5.
     */
    PIN_LIGHT,
    /**
     * Hard mix blend mode converts each color channel to 0 or 1. If sum of base and blend is >= 1
     * color channel will have 1f value, otherwise 0
     */
    HARD_MIX,
    /**
     * The Reflect blend mode takes the value of the base layer raised to the power of 2 and
     * divides it by the inverted value of the blend layer.
     */
    REFLECT,
    /**
     * Exclusion blend mode multiplies the two layers, adds the base layer, and then subtracts the
     * multiple of two layers twice.
     */
    EXCLUSION,
    /**
     * Geometric mean blend mode work by multiplying the two layers and outputting the square root
     * of that.
     */
    GEOMETRIC_MEAN,
    /**
     * Color blend mode works by using hue and saturation of base (bottom) layer and lightness of
     * blend (top) layer. It is not clearly defined, how this blend mode should interact with
     * transparency. For this implementation it will be ignored for calculations and the final
     * color will have transparency of base layer.
     */
    LUMINOSITY,
    /**
     * Color blend mode works by using hue and saturation of blend (top) layer and lightness of
     * base (bottom) layer. It is not clearly defined, how this blend mode should interact with
     * transparency. For this implementation it will be ignored for calculations and the final
     * color will have transparency of base layer.
     */
    COLOR
