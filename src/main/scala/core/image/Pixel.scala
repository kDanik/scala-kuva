package com.example
package core.image

import core.color.types.Color
import core.image.Position

/**
 * This class represents one pixel on image
 *
 * @param position
 *   position of this pixel on image
 * @param color
 *   color of this pixel
 */
final case class Pixel(position: Position, color: Color)
