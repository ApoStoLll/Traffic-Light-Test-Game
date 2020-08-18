package com.example.trafficlightsgame.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.example.trafficlightsgame.ChooseCellListener
import com.example.trafficlightsgame.game.Cell
import com.example.trafficlightsgame.game.CellColor
import com.example.trafficlightsgame.game.Position


@SuppressLint("ViewConstructor")
class DrawView(context: Context?, private val scale : Float, private val OFFSET : Float) : View(context) {

    private val paint = Paint()
    private val p = Paint()
    var end = 0

    var cells : List<Cell>? = null
    var scrore : Int = 0
    lateinit var cellListener : ChooseCellListener


    init {
        paint.strokeWidth = 10F
        paint.style = Paint.Style.FILL
        p.textSize = 80F


    }


    override fun onDraw(canvas: Canvas) {
        drawCells(canvas)
    }

    private fun drawCells(canvas: Canvas) {
        if (end == 0){
            if (cells != null){
                for (cell in cells!!){
                    configurePaint(cell.color)
                    canvas.drawRect(
                        cell.position.x * scale + OFFSET,
                        cell.position.y * scale + OFFSET,
                        (cell.position.x + 1) * scale + OFFSET,
                        (cell.position.y + 1) * scale + OFFSET,
                        paint
                    )
                }
            }
            drawScore(canvas)
        }
        if (end == 1){
            p.textSize = 90F
            canvas.drawText("WIN", scale * 5, scale * 5, p)
        }
        if (end == -1){
            p.textSize = 90F
            canvas.drawText("WASTED", scale * 5, scale * 5, p)
        }
    }

    private fun drawScore(canvas: Canvas){
        canvas.drawText(scrore.toString(), scale * 7, scale * 7, p)
    }

    private fun configurePaint(color : Int){
        when(color){
            CellColor.BLUE -> paint.color = Color.BLUE
            CellColor.RED -> paint.color = Color.RED
            CellColor.YEALLOW -> paint.color = Color.YELLOW
        }
    }
}