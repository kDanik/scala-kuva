# scala-kuva

**scala-kuva** (kuva means "picture" in Finnish) is supposed to be Scala library for color (and later image) processing, manipulation, and filters.

## Functionality
### Color types

Currently, there are implementations for 3 color formats: **RGB(A)**, **HSL(A)**, **HSV(A)**.

They can be converted between each other and converted to java.awt.Color.


### Color manipulation

#### Grayscale

There are different grayscale algorithms that can be applied to colors: Averaging, Luma (BT-709, BT-601), Desaturation, Single color channel, and Decomposition (min, max).

#### Inversion

Inversion can be applied to colors to create the opposite color (with inversion of alpha channel or without)

#### Blending colors
...
#### Darken
...
#### Lighten
...
#### Saturation
...
