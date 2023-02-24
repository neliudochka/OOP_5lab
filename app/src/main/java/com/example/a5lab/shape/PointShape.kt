package com.example.a5lab.shape

import android.graphics.Canvas
import com.example.a5lab.PaintOptions

class PointShape (val paintOptions: PaintOptions): Shape(paintOptions) {

    override fun show(canvas: Canvas) {
        canvas.drawCircle(x1, y1, 10f, paintOptions.inProgress)
    }

    override fun showFinal(canvas: Canvas) {
        canvas.drawCircle(x1, y1, 10f, paintOptions.point)
    }

    //for 4 lab
    override fun createNew(newPaintOptions: PaintOptions?): PointShape {
        if (newPaintOptions != null)
            return PointShape(newPaintOptions)
        return PointShape(paintOptions)
    }
}