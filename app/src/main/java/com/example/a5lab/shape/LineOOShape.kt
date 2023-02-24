package com.example.a5lab.shape

import android.graphics.Canvas
import android.graphics.Paint
import com.example.a5lab.PaintOptions
import kotlin.math.abs

class LineOOShape (var paintOptions: PaintOptions) : Shape(paintOptions) {
    private var radius: Float = 0F

    override fun show(canvas: Canvas) {
        drawLineOOShape(x1, y1, x2, y2, paintOptions.inProgress, canvas)
    }

    override fun showFinal(canvas: Canvas) {
        drawLineOOShape(x1, y1, x2, y2, paintOptions.final, canvas)
    }

    override fun fill(canvas: Canvas) {
        canvas.drawCircle(x1, y1, radius, paintOptions.fillLineOO)
        canvas.drawCircle(x2, y2, radius, paintOptions.fillLineOO)
    }
    override fun createNew(newPaintOptions: PaintOptions?): LineOOShape {
        if (newPaintOptions != null)
            return LineOOShape(newPaintOptions)
        return LineOOShape(paintOptions)
    }

    fun drawLineOOShape (x1: Float, y1: Float, x2: Float, y2:Float, paint: Paint, canvas: Canvas){
        radius = 10F + abs(x2-x1)/10 + abs(y2-y1)/10
        canvas.drawCircle(x1, y1, radius, paint)
        canvas.drawLine(x1, y1, x2, y2, paint)
        canvas.drawCircle(x2, y2, radius, paint)
    }
}