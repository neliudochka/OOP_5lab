package com.example.a5lab.shape

import android.graphics.Canvas
import com.example.a5lab.PaintOptions
import kotlin.math.abs
import android.graphics.Paint

class CubeShape (var paintOptions: PaintOptions) : Shape(paintOptions) {
    override fun show(canvas: Canvas) {
        drawCube(x1, y1, x2, y2, paintOptions.inProgress, canvas)
    }

    override fun showFinal(canvas: Canvas) {
        drawCube(x1, y1, x2, y2, paintOptions.final, canvas)
    }

    override fun createNew(newPaintOptions: PaintOptions?): CubeShape {
        if (newPaintOptions != null)
            return CubeShape(newPaintOptions)
        return CubeShape(paintOptions)
    }

    fun drawCube (x1: Float, y1: Float, x2: Float, y2: Float, paint: Paint, canvas: Canvas){
        val delta = abs( x2 -x1)/4
        val width = x2 - x1
        canvas.drawRect(x1, y1, x2, y2, paint)
        canvas.drawRect(x1+delta, y1+delta, x2+delta, y2+delta, paint)
        canvas.drawLine(x1, y1, x1+delta, y1+delta, paint)
        canvas.drawLine(x2, y2, x2+delta, y2+delta, paint)
        canvas.drawLine(x1 + width, y1, x1+delta+width, y1+delta, paint)
        canvas.drawLine(x2 - width, y2, x2+delta-width, y2+delta , paint)
    }
}