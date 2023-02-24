package com.example.a5lab

import android.content.Context
import android.graphics.*
import android.os.Build
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import com.example.a5lab.shape.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.reflect.full.primaryConstructor

class CanvasView(context: Context) :  View(context) {
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in editor.getShapes()) {
            i.show(canvas)
            if(i.finished) {
                i.showFinal(canvas)
            }
            if(i.filled) {
                i.fill(canvas)
            }
        }
        selected?.showFinal(canvas)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    private fun touchStart() {
        invalidate()
        editor.onLBDown(motionTouchEventX, motionTouchEventY)
    }

    private fun touchMove() {
        invalidate()
        editor.onMouseMove(motionTouchEventX, motionTouchEventY )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun touchUp() {
        editor.onLBUp()
        invalidate()
        addShapeToTable()
        addShapeToRecord()
    }


//4
    private var editor: MyEditor = MyEditor.start(PointShape(regPaintOptions))

    fun startMyEditor(shapeType: Shape) {
        editor = editor.start(shapeType)
    }

    //5
    //table
    private lateinit var table: MyTable

    fun startMyTable(myTable: MyTable) {
        table = myTable
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addShapeToTable() {
        val shape = editor.getShapes().last()
        val(x1, y1, x2, y2) = shape.getCoordinates()
       table.Add(shape.javaClass.kotlin.simpleName.toString(), x1, y1, x2, y2)
    }

    //makeRecords
    private lateinit var file: File
    @RequiresApi(Build.VERSION_CODES.O)
    fun startRecord (fileName: String){
        val path = context.getExternalFilesDir(null)
        val oldFilePath = Paths.get("$path/$fileName")
        Files.deleteIfExists(oldFilePath)
        file = File(path, fileName)
    }

    private fun makeRecord (name: String, x1: Float, y1: Float, x2: Float, y2: Float) {
        file.appendText(name+"\t"+x1+"\t"+y1+"\t"+x2+"\t"+y2+"\n")
    }

    private fun addShapeToRecord() {
        val shape = editor.getShapes().last()
        val(x1, y1, x2, y2) = shape.getCoordinates()
        makeRecord(shape.javaClass.kotlin.simpleName.toString(), x1, y1, x2, y2)
    }

    //selectShape
    private var selected: Shape? = null
    private fun selectShape(index: Int) {
        invalidate()
        val shape = editor.getShapes()[index]
        val selectedShape = shape.createNew(selectedPaintOptions)
        val(x1, y1, x2, y2) = shape.getCoordinates()
        selectedShape.setStartCoords(x1, y1)
        selectedShape.setEndCoords(x2,y2)
        selected = selectedShape
        invalidate()
    }

    private fun unSelectShape() {
        selected = null
        invalidate()
    }

    fun startSelect () {
        table.initSelectListener(::selectShape, ::unSelectShape)
    }

    //deleteShape
    fun startDelete() {
        table.initDeleteListener(::deleteShape)
    }

    private fun deleteShape(index: Int) {
        selected = null
        editor.deleteShape(index)
        invalidate()
    }

    //openRecord
    @RequiresApi(Build.VERSION_CODES.O)
    fun openRecord(fileName: String){
        val path = context.getExternalFilesDir(null)
        val record = File(path, fileName)
        val listOfStrings = record.bufferedReader().readLines()
        listOfStrings.forEach { readLine(it) }
        invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun readLine(line: String) {
        val list = line.split("\t")
        val className = list[0]
        val x1 = list[1].toFloat()
        val y1 = list[2].toFloat()
        val x2 = list[3].toFloat()
        val y2 = list[4].toFloat()

        val myPackage = "com.example.a5lab.shape"
        val kClass = Class.forName("$myPackage.$className").kotlin
        val primaryConstructor = kClass.primaryConstructor
        val instance = primaryConstructor!!.call(regPaintOptions)
        println("wow"+instance.javaClass.kotlin.simpleName.toString())

        editor.start(instance as Shape)
        editor.onLBDown(x1, y1)
        editor.onMouseMove(x2,y2)
        editor.onLBUp()
        addShapeToTable()
        addShapeToRecord()
    }

    fun clear() {
        editor.clear()
    }
}

