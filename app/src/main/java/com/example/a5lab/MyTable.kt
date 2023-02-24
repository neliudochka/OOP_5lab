package com.example.a5lab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.a5lab.databinding.FragmentMyTableBinding

class MyTable : Fragment() {
    private lateinit var binding: FragmentMyTableBinding
    var selectListener: (index: Int) -> Unit = {}
    var unSelectListener: () -> Unit = {}
    private val nameViews = mutableListOf<TextView>()

    var deleteListener: (index: Int) -> Unit = {}
    private val delViews = mutableListOf<TextView>()


    private val rowViews = mutableListOf<TableRow>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyTableBinding.inflate(inflater)
        return binding.root
    }

    fun Add(name: String, x1: Float, y1: Float, x2: Float, y2: Float) {
        println("add $name $x1 $y2 ")

        val tableRow = TableRow(context)

        //nameColumn
        val nameColumn = TextView(context)
        nameColumn.text = name
        //id
        nameColumn.id = View.generateViewId()
        nameViews.add(nameColumn)
        nameColumn.setOnClickListener { nameListener(nameColumn) }
        tableRow.addView(nameColumn)

        //coordColumns
        val coordColomns = arrayOf(x1.toString(), y1.toString(), x2.toString(), y2.toString())
        coordColomns.forEach {
            val newColumn = TextView(context)
            newColumn.text = it
            tableRow.addView(newColumn)
        }

        //delColumn
        val delColumn = TextView(context)
        delColumn.text = "X"
        val normButtonColor = ContextCompat.getColor(requireContext(), R.color.red)
        delColumn.setTextColor(normButtonColor)
        //id
        delColumn.id = View.generateViewId()
        delViews.add(delColumn)
        delColumn.setOnClickListener { delListener(delColumn) }
        tableRow.addView(delColumn)


        binding.tableLayout.addView(tableRow)

        rowViews.add(tableRow)
    }

    //selectShape
    fun initSelectListener(callbackSet: (index: Int) -> Unit, callbackUnSet: () -> Unit) {
        selectListener = callbackSet
        unSelectListener = callbackUnSet
    }

    private fun nameListener (view: TextView){
        val index = nameViews.indexOf(view)
        val pressed = "pressed"
        if (view.tag != pressed) {
            selectListener(index)
            pressedButton(view, nameViews)
            view.tag = pressed
        } else {
            view.tag = null
            unSelectListener()
            view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }


    private fun pressedButton (button: TextView, objects: MutableList<TextView>) {
        val pressedButtonColor = ContextCompat.getColor(requireContext(), R.color.pressedButton)
        val normButtonColor = ContextCompat.getColor(requireContext(), R.color.white)
        objects.forEach {
            it.setBackgroundColor(normButtonColor)
        }
        button.setBackgroundColor(pressedButtonColor)
    }

    //deleteShape
    fun initDeleteListener(callback: (index: Int) -> Unit) {
        deleteListener = callback
    }

    private fun delListener(view: TextView) {
        val index = delViews.indexOf(view)
        deleteListener(index)
        rowViews[index].removeAllViews()
        rowViews.removeAt(index)
        delViews.removeAt(index)
        nameViews.removeAt(index)
    }
}