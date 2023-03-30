package com.example
package core.image

import core.color.types.Color

/**
 * This class represents on pixel on image
 *
 * @param x
 *   x coordinate on image
 * @param y
 *   y coordinate on image
 * @param color
 *   color of this pixel
 */
final case class Pixel(x: Int, y: Int, color: Color)
