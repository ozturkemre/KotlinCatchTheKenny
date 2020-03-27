package com.ozturkemre.kotlincatchthekenny

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_top_scores.*

class TopScores : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_scores)
        loadScores()

    }
    private fun loadScores(){
        val sharedPreferences = this.getSharedPreferences("com.ozturkemre.kotlincatchthekenny",Context.MODE_PRIVATE)
        scoreView.text = sharedPreferences.getString("high", "0")
    }

    fun changeToGame(view: View) {
        val intent= Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
    }
}
