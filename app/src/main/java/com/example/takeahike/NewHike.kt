package com.example.takeahike

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.takeahike.model.Hike

class NewHike : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_hike)

        val button = findViewById<Button>(R.id.saveHike)
        button.setOnClickListener {
            val replyIntent = Intent()
            val hike = createHike()
            if(hike.get("hikeName") == null) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtras(hike)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    fun createHike():Bundle {
        val hikeBundle: Bundle = Bundle()
        Log.d("Intent Bundle", "Starting")

        hikeBundle.putString("hikeName",findViewById<EditText>(R.id.hikeNameEntry).text.toString())
        hikeBundle.putString("hikeCategory",findViewById<EditText>(R.id.hikeCategoryEntry).text.toString())
        hikeBundle.putString("hikeDifficulty",findViewById<EditText>(R.id.hikeDifficultyEntry).text.toString())
        hikeBundle.putString("hikeDescription",findViewById<EditText>(R.id.hikeDescriptionEntry).text.toString())
        hikeBundle.putString("hikeLocation",findViewById<EditText>(R.id.hikeLocationEntry).text.toString())
        hikeBundle.putDouble("hikeDistance",findViewById<EditText>(R.id.hikeDistanceEntry).text.toString().toDouble())

        Log.d("Intent Bundle", hikeBundle.toString())
        return hikeBundle
    }
}