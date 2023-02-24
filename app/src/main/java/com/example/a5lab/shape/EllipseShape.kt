package com.example.a5lab.shape

import android.graphics.Canvas
import com.example.a5lab.PaintOptions

class EllipseShape (val paintOptions: PaintOptions): Shape(paintOptions) {

    override fun show(canvas: Canvas) {
        canvas.drawOval(x1, y1, x2, y2, paintOptions.inProgress)
    }

    override fun showFinal(canvas: Canvas) {
        canvas.drawOval(x1, y1, x2, y2, paintOptions.final)
    }

    override fun fill (canvas: Canvas) {
        canvas.drawOval(x1, y1, x2, y2, paintOptions.fillEllipse)
    }

    //for 4 lab
    override fun createNew(newPaintOptions: PaintOptions?): EllipseShape {
        if (newPaintOptions != null)
            return EllipseShape(newPaintOptions)
        return EllipseShape(paintOptions)
    }
}