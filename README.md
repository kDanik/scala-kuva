# scala-kuva

**scala-kuva** (kuva means "picture" in Finnish) is supposed to be a Scala library for color (and later image) processing, manipulation, and filters.

## Functionality
### Color types

Currently, there are implementations for 3 color formats: **RGB(A)**, **HSL(A)**, and **HSV(A)**.

They can be converted between each other and converted to java.awt.Color.

### Color manipulation

This is the original picture used to demonstrate different color operations/filters below:

![original picture of strawberry](src/main/resources/source/strawberry.png)


#### Grayscale

Currently, there are implementations of the following grayscale algorithms:

##### Averaging 
![Alt text](src/main/resources/result/grayscale_averaging_strawberry.png)

##### Decomposition (using max channel value)
![Alt text](src/main/resources/result/grayscale_decomposition_max_strawberry.png)

##### Decomposition (using min channel value)
![Alt text](src/main/resources/result/grayscale_decomposition_min_strawberry.png)

##### Desaturation 
![Alt text](src/main/resources/result/grayscale_desaturation_strawberry.png)

##### Luma (ITU-R BT 601)
![Alt text](src/main/resources/result/grayscale_luma_bt601_strawberry.png)

##### Luma (ITU-R BT 709)
![Alt text](src/main/resources/result/grayscale_luma_bt709_strawberry.png)

##### Single channel (red)
![Alt text](src/main/resources/result/grayscale_single_color_channel_red_strawberry.png)

##### Single channel (green)
![Alt text](src/main/resources/result/grayscale_single_color_channel_green_strawberry.png)

##### Single channel (blue)
![Alt text](src/main/resources/result/grayscale_single_color_channel_blue_strawberry.png)

##### Lightness
![Alt text](src/main/resources/result/grayscale_lightness_strawberry.png)

##### Lightness (HSL)
![Alt text](src/main/resources/result/grayscale_lightness_hsl_strawberry.png)

#### Inversion

Inversion can be applied to colors to create the opposite color (with inversion of alpha channel or without)


![Alt text](src/main/resources/result/color_inversion_strawberry.png)

#### Blending colors
...
#### Darken
...
#### Lighten
...
#### Saturation
...
