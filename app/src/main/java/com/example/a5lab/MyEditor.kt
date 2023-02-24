package com.example.a5lab

import com.example.a5lab.shape.Shape
import com.example.a5lab.shape.ShapeAdder

object MyEditor {
    private val shapes = mutableListOf<Shape>()
    lateinit var currentShape: Shape

     fun start (shape: Shape): MyEditor {
         currentShape = shape
         return this
     }

    fun onLBDown(x: Float, y: Float) {
        currentShape = currentShape.createNew(null)
        currentShape.setStartCoords(x, y)
    }

    fun onLBUp() {
        currentShape.setFillFlag()
        currentShape.setFinishedFlag()
    }

    fun onMouseMove(x: Float, y: Float) {
        if (shapes.contains(currentShape))
            shapes.removeAt(shapes.lastIndex)

        currentShape.setEndCoords(x, y)
        ShapeAdder.addShape(currentShape, shapes)
    }

    //5lab
    fun getShapes(): MutableList<Shape> {
        return shapes
    }

    fun deleteShape(index: Int) {
        shapes.removeAt(index)
    }

    fun clear() {
        shapes.clear()
    }
}