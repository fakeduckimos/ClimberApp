package com.douglasluong.climberapp

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val score = findViewById<TextView>(R.id.score)
        val hold = findViewById<TextView>(R.id.hold)
        val climbButton = findViewById<Button>(R.id.ClimbButton)
        val fallButton = findViewById<Button>(R.id.FallButton)
        val resetButton = findViewById<Button>(R.id.ResetButton)

        if (savedInstanceState != null) {
            score.text = savedInstanceState.getString("score")
            hold.text = savedInstanceState.getString("hold")
            score.setTextColor(savedInstanceState.getInt("scoreColor"))
        }

        fun createDialog(gameOver: String, scoreString: String) : AlertDialog {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(gameOver)
            builder.setMessage("Your score is: $scoreString")
            builder.setNeutralButton("Reset", object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    score.text = "0"
                    hold.text = "0"
                    score.setTextColor(Color.BLACK)
                    Log.d("ANNOUNCEMENT", "The Reset button has been clicked.")
                }
            })
            return builder.create()
        }

        climbButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                score.text = incrementScore(score.text.toString().toInt()).toString()
                hold.text = incrementHold(hold.text.toString().toInt()).toString()
                if (hold.text.toString().toInt() in 1..3) {
                    score.setTextColor(Color.BLUE)
                } else if (hold.text.toString().toInt() in 4..6) {
                    score.setTextColor(Color.GREEN)
                } else if (hold.text.toString().toInt() in 7..9) {
                    score.setTextColor(Color.RED)
                }

                if (score.text.toString().toInt() == 18) {
                    val dialog = createDialog("You Win!", score.text.toString())
                    dialog.show()
                    Log.d("DIALOG BOX", "The dialog box has been created.")
                    Log.d("SCORE", String.format("SCORE: %s", score.text.toString()))
                    Log.d("HOLD", String.format("HOLD: %s", hold.text.toString()))
                }

                Log.d("SCORE", String.format("SCORE: %s", score.text.toString()))
                Log.d("HOLD", String.format("HOLD: %s", hold.text.toString()))
                Log.d("ANNOUNCEMENT", "Climb button has been clicked.")
            }
        })

        fallButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if (score.text.toString().toInt() != 0) {
                    score.text = fallScore(score.text.toString().toInt()).toString()
                    val dialog = createDialog("Game Over.", score.text.toString())
                    dialog.show()
                    Log.d("DIALOG BOX", "The dialog box has been created.")
                    Log.d("SCORE", String.format("SCORE: %s", score.text.toString()))
                    Log.d("HOLD", String.format("HOLD: %s", hold.text.toString()))
                }

                Log.d("SCORE", String.format("SCORE: %s", score.text.toString()))
                Log.d("HOLD", String.format("HOLD: %s", hold.text.toString()))
                Log.d("ANNOUNCEMENT", "Fall button has been clicked.")
            }
        })

        resetButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                score.text = "0"
                hold.text = "0"
                score.setTextColor(Color.BLACK)
                Log.d("SCORE", String.format("SCORE: %s", score.text.toString()))
                Log.d("HOLD", String.format("HOLD: %s", hold.text.toString()))
                Log.d("ANNOUNCEMENT", "Reset button has been clicked.")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return true
    }

    fun incrementScore(integer: Int) : Int {
        when (integer) {
            in 0..2 -> return integer + 1
            in 3..8 -> return integer + 2
            in 9..17 -> return integer + 3
            else -> return integer
        }
    }

    fun incrementHold(integer: Int) : Int {
        when (integer) {
            in 0..8 -> return integer + 1
            else -> return integer
        }
    }

    fun fallScore(integer: Int) : Int {
        when (integer) {
            in 0..2 -> return 0
            in 3..17 -> return integer - 3
            else -> return integer
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val score = findViewById<TextView>(R.id.score)
        val hold = findViewById<TextView>(R.id.hold)

        score.text = savedInstanceState.getString("score")
        hold.text = savedInstanceState.getString("hold")
        score.setTextColor(savedInstanceState.getInt("scoreColor"))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val score = findViewById<TextView>(R.id.score)
        val hold = findViewById<TextView>(R.id.hold)

        outState.putString("score", score.text.toString())
        outState.putString("hold", hold.text.toString())
        outState.putInt("scoreColor", score.currentTextColor)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId

        if (itemId == R.id.EnglishLanguage) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            return true
        } else {
            val intent = Intent(this, NorwegianActivity::class.java)
            startActivity(intent)
            return true
        }
    }

}