package com.example
package core.color.operations.blend

enum BlendMode:
  case NORMAL, // 2 different options for Alpha compositing possible.
    SIMPLE_ALPHA_COMPOSITING,
    DISSOLVE,
    MULTIPLY,
    SCREEN,
    OVERLAY,
    HARD_LIGHT,
    SOFT_LIGHT,
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
