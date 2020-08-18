package com.example.trafficlightsgame.game

import android.util.Log
import kotlin.random.Random

class Game(){


    val cells = mutableListOf<Cell>()
    val positions = mutableListOf<Position>()

    init {
        for (i in 0..2){
            for (j in 0..2){
                positions.add(Position(i, j))
            }
        }
    }


    fun generateRandomCells(){
        val countOfYellow = Random.nextInt(0, 8)
        val countOfBlue = Random.nextInt(0, 8 - countOfYellow)
        val countOfRed = 9 - countOfBlue - countOfYellow
        generateCell(CellColor.YEALLOW, countOfYellow)
        generateCell(CellColor.BLUE, countOfBlue)
        generateCell(CellColor.RED, countOfRed)
        for(cell in cells){
            Log.e("Cells", cell.toString())
        }
    }

    private fun generateCell(color : Int, count : Int){
        for(i in 0 until count){
            val pos = Random.nextInt(0, positions.size)
            cells.add(Cell(color = color, position = positions.removeAt(pos)))
        }
    }

    companion object{
        fun findCell(position: Position, cells : List<Cell>) : Cell?{
            for (cell in cells){
                if(cell.position.x == position.x && cell.position.y == position.y){
                    return cell
                }
            }
            return null
        }
    }

}