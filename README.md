# FractaLens - A Fractal Generator Library Developed in Lens.

This project provides a *Java* library for generating fractal images.
It allows users to choose from a variety of complex sequences, including the
Julia set, Mandelbrot set, and other generalized sequences, to create
static or animated fractal images using different color palettes and
transformations.

## Overview

This library is composed of the following classes:

```plantuml
hide empty members

' --------------- '
' Complex Numbers '
' --------------- '

interface IComplex {
    + {abstract} getRealPart() : double
    + {abstract} getImaginaryPart() : double
    + {abstract} abs() : double
    + {abstract} negate() : IComplex
    + {abstract} conjugate() : IComplex
    + {abstract} add(other : IComplex) : IComplex
    + {abstract} subtract(other : IComplex) : IComplex
    + {abstract} multiply(value : double) : IComplex
    + {abstract} multiply(other : IComplex) : IComplex
    + {abstract} divide(other : IComplex) : IComplex
}

interface IComplexPlane {
    + {abstract} asComplex(row : int, column : int) : IComplex
}

IComplexPlane --> IComplex : << creates >>

' ----------------- '
' Complex Sequences '
' ----------------- '

interface IComplexSequence extends Iterable<IComplex> {
    + {abstract} computeNext(previous : IComplex) : IComplex
    + {abstract} iterator() : Iterator<IComplex>
}

IComplexSequence --> IComplex : << generates >>

' -------------- '
' Image Handling '
' -------------- '

interface IFractalImage {
    + {abstract} getHeight() : int
    + {abstract} getWidth() : int
    + {abstract} setColor(row : int, column : int, color : Color) : void
    + {abstract} saveAs(path : String) : void
}

' ------------------ '
' Fractal Generation '
' ------------------ '

interface IFractalGenerator {
    + {abstract} generateImage() : IFractalImage
    + {abstract} computePixelColor(row : int, column : int) : Color
    + {abstract} saveImage() : void
}

IFractalGenerator --> IFractalImage : << produces >>
IFractalGenerator --> IComplexSequence : << uses >>
```

## Feature list

| Features                                               | Design Pattern(s) (?)      | Author(s) |
|--------------------------------------------------------|----------------------------|-----------|
| Complex number                                         |                            |           |
| Default complex plane                                  | Decorator                  |           |
| Scaled complex plane                                   | Decorator                  |           |
| Translated complex plane                               | Decorator                  |           |
| Sequence of complex numbers                            | Template method            |           |
| Iterator over a sequence of complex numbers            | Iterator                   |           |
| Julia sequence                                         | Strategy                   |           |
| Mandelbrot sequence                                    | Strategy                           |           |
| Multijulia sequence                                    |   Strategy                         |           |
| Multibrot sequence                                     |     Strategy                       |           |
| Generic sequences                                      |     Strategy                       |           |
| Color palette                                          | Strategy                   |           |
| Grey scale palette                                     | Strategy                   |           |
| Red/Green/Blue/... scale palette                       | Strategy                   |           |
| Inverted color palette                                 | Decorator                  |           |
| Gradient-based color palette                           | Strategy                   |           |
| Predefined color palette                               | Strategy                   |           |
| `BufferedImage` used as an `IFractalImage`             | Adapter                    |           |
| Static image generation                                | Strategy                   |           |
| Scale-based animated image generation                  | Strategy + Builder         |           |
| Rotation-based animated image generation               | Strategy + Builder         |           |
| Configuration of the fractal generator                 | Strategy + Builder         |           |
| Command-line interface                                 | Abstract factory + Builder |           |

## Team

This project has been developed by:

- Brureau Théo
- Pesson Joan
- Breguet Thibault
- Tiers Marie
