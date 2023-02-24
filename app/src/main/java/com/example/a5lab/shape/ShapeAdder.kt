package com.example.a5lab.shape

class ShapeAdder {
    companion object {
        fun addShape(shape: Shape, shapes: MutableList<Shape>) {
            shapes.add(shape)
        }
    }
}