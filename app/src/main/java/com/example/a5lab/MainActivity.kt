package com.example.a5lab

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.a5lab.databinding.ActivityMainBinding
import com.example.a5lab.shape.*


class MainActivity : AppCompatActivity() {

    lateinit var myCanvasView: CanvasView
    private lateinit var binding: ActivityMainBinding
        var objects = mutableListOf<ImageButton>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMyCanvasView()
        initSecondMenu()
        //5lab
        initMyTable()
        initRecord()
        initAdittPos()
    }

    private fun initMyCanvasView () {
        myCanvasView = CanvasView(this)
        myCanvasView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        binding.canvasLayout.addView(myCanvasView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initSecondMenu() {
        objects = mutableListOf<ImageButton>(
            binding.toolbarPoint,
            binding.toolbarLine,
            binding.toolbarRect,
            binding.toolbarEllipse,
            binding.toolbarLineOo,
            binding.toolbarCube
        )

        objects.forEach {
            it.setOnClickListener {
                setListener(it.tag.toString())
            }
        }

    }

    private fun pressedButton (button: ImageButton, objects: MutableList<ImageButton>) {
        val pressedButtonColor = ContextCompat.getColor(this, R.color.pressedButton)
        val normButtonColor = ContextCompat.getColor(this, R.color.unpressedButton)

        objects.forEach {
            it.setBackgroundColor(normButtonColor)
        }

        button.setBackgroundColor(pressedButtonColor)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val newTitle = item.title
        return setListener(newTitle)
     }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setListener (newTitle: CharSequence): Boolean {
        when(newTitle) {
            getString(R.string.point) -> {
                setTitle(newTitle)
                myCanvasView.startMyEditor(PointShape(regPaintOptions))
                pressedButton(objects[0], objects)
            }
            getString(R.string.line) -> {
                setTitle(newTitle)
                myCanvasView.startMyEditor(LineShape(regPaintOptions))
                pressedButton(objects[1], objects)
            }
            getString(R.string.rect) -> {
                setTitle(newTitle)
                myCanvasView.startMyEditor(RectShape(regPaintOptions))
                pressedButton(objects[2], objects)
            }
            getString(R.string.ellipse) -> {
                setTitle(newTitle)
                myCanvasView.startMyEditor(EllipseShape(regPaintOptions))
                pressedButton(objects[3], objects)
            }
            getString(R.string.lineOO) -> {
                setTitle(newTitle)
                myCanvasView.startMyEditor(LineOOShape(regPaintOptions))
                pressedButton(objects[4], objects)
            }
            getString(R.string.cube) -> {
                title = newTitle
                myCanvasView.startMyEditor(CubeShape(regPaintOptions))
                pressedButton(objects[5], objects)
            }
            "Таблиця" -> {
                setTableListener()
            }
            "Відкрити" -> {
                val openFile = "RecordsEx.txt"
                myCanvasView.clear()
                myCanvasView.openRecord(openFile)
            }
        }
        return true
    }

    //5lab
    private fun initMyTable() {
        val table = MyTable()
        binding.tableFrame.layoutParams.height = 300
        binding.tableFrame.isVisible = false
        supportFragmentManager.beginTransaction().replace(R.id.table_frame, table).commit()
        myCanvasView.startMyTable(table)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initRecord() {
        val fileName = "Records.txt"
        myCanvasView.startRecord(fileName)
    }


    private fun initAdittPos() {
        myCanvasView.startSelect()
        myCanvasView.startDelete()
    }

    private fun setTableListener() {
        val tableVisibility = binding.tableFrame.isVisible
        if (tableVisibility) {
            binding.tableFrame.isVisible = false
            setTitle("tClose")
        } else {
            binding.tableFrame.isVisible = true
            setTitle("tOpen")
        }
    }
}