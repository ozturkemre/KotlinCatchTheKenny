package com.ozturkemre.kotlincatchthekenny

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var score: Int = 0

    var imgs = ArrayList<ImageView>()

    var runnable: Runnable = Runnable { }
    var handler: Handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgs.add(imageView)
        imgs.add(imageView2)
        imgs.add(imageView3)
        imgs.add(imageView4)
        imgs.add(imageView5)
        imgs.add(imageView6)
        imgs.add(imageView7)
        imgs.add(imageView8)
        imgs.add(imageView9)

        score = 0
        disableClick()


    }

    fun start(view: View) {
        hideImages()
        enableClick()
        startButton.isEnabled = false
        object : CountDownTimer(10000, 1000) {
            override fun onFinish() {
                timeText.text = "Time is over"
                handler.removeCallbacks(runnable)
                for (image in imgs) {
                    image.visibility = View.INVISIBLE
                }

                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Play again")
                alert.setMessage("Wanna play again?")

                if (score > getHigh()) {
                    savePreferences("high", score.toString())
                }

                alert.setPositiveButton("Yes") { _, which ->
                    val intent: Intent = getIntent()
                    finish()
                    startActivity(intent)
                }

                alert.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(this@MainActivity, "Game over", Toast.LENGTH_LONG).show()
                }
                alert.show()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeText.text = "Remaining time : ${millisUntilFinished / 1000}"
            }

        }.start()

    }

    fun increaseScore(view: View) {
        score += 1
        scoreText.text = "Score: " + score
    }

    private fun hideImages() {
        handler = Handler()
        runnable = object : Runnable {
            override fun run() {
                for (image in imgs) {
                    image.visibility = View.INVISIBLE
                }

                val random = Random()
                val i = random.nextInt(9)
                imgs[i].visibility = View.VISIBLE
                handler.postDelayed(this, 500)
            }

        }
        handler.post(runnable)
    }

    fun savePreferences(key: String, value: String) {
        val sharedPreferences = this.getSharedPreferences("com.ozturkemre.kotlincatchthekenny", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key, value).apply()

    }

    fun changeToScores(view: View) {
        val intent = Intent(applicationContext, TopScores::class.java)
        startActivity(intent)
    }

    fun getHigh(): Int {
        val sharedPreferences = this.getSharedPreferences("com.ozturkemre.kotlincatchthekenny", Context.MODE_PRIVATE)
        val data: String? = sharedPreferences.getString("high", "0")
        return Integer.parseInt(data.toString())
    }

    private fun disableClick() {
        for (img in imgs) {
            img.isEnabled = false
        }
    }

    private fun enableClick() {
        for (img in imgs) {
            img.isEnabled = true
        }
    }

}
