package com.example.trafficlightsgame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.trafficlightsgame.game.CellColor
import com.example.trafficlightsgame.game.Game
import com.example.trafficlightsgame.game.Position
import com.example.trafficlightsgame.ui.DrawView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity(), View.OnTouchListener {

    lateinit var drawView : DrawView
    var scale by Delegates.notNull<Float>()
    var OFFSET by Delegates.notNull<Float>()
    var game : Game? = null
    var score : Int = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configScreen()
        drawView = DrawView(this, scale, OFFSET)
        drawView.setOnTouchListener(this)
        setContentView(drawView)
        start()

    }

    private fun configScreen(){
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y
        scale = ((if (width < height) width else height) / 8).toFloat()
        OFFSET = ((height - 9 * scale) / 2)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        //drawView.postInvalidate();
        val x = event.x - OFFSET
        val y = event.y - OFFSET
        if (event.action == MotionEvent.ACTION_DOWN) { // нажатие
            val a = (x / scale).toInt()
            val b = (y / scale).toInt()
            val cell = game?.cells?.let { Game.findCell(Position(a, b), cells = it) }
            when(cell?.color){
                CellColor.YEALLOW -> score += 10
                CellColor.BLUE -> score += 5
                CellColor.RED -> score -= 10
            }
            if(score >= 100)
                drawView.end = 1
            if(score <= -0)
                drawView.end = -1
            drawView.scrore = score
            drawView.postInvalidate()
        }
        return false
    }

    fun start(){
        game = Game()
        game?.generateRandomCells()
        drawView.cells = game?.cells
        drawView.postInvalidate()
        GlobalScope.launch(Dispatchers.IO) {
            Thread.sleep(5000)
            withContext(Dispatchers.Main){
                if(drawView.end == 0)
                    start()
            }
        }
    }

}