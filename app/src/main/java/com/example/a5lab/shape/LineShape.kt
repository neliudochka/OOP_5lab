package com.example.a5lab.shape

import android.graphics.Canvas
import com.example.a5lab.PaintOptions

open class LineShape (open var paintOptions: PaintOptions): Shape(paintOptions) {

    override fun show(canvas: Canvas) {
        canvas.drawLine(x1, y1, x2, y2, paintOptions.inProgress)
    }

    override fun showFinal(canvas: Canvas) {
        canvas.drawLine(x1, y1, x2, y2, paintOptions.final)
    }

    //for 4 lab
    override fun createNew(newPaintOptions: PaintOptions?): LineShape {
        if (newPaintOptions != null)
            return LineShape(newPaintOptions)
        return LineShape(paintOptions)
    }
}