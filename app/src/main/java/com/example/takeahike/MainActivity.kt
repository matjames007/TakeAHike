package com.example.takeahike

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.takeahike.model.Hike
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val newHikeActivityRequestCode = 1

    private val hikeViewModel: HikeViewModel by viewModels {
        HikeViewModelFactory((application as HikeApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val hikeList:RecyclerView = findViewById<RecyclerView>(R.id.hikeList)
        hikeList.adapter = HikeListAdapter()
        hikeList.layoutManager = LinearLayoutManager(this)


        hikeViewModel.allHikes.observe( this) { hikes ->
            hikes.let { (hikeList.adapter as HikeListAdapter).submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewHike::class.java)
            startActivityForResult(intent, newHikeActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newHikeActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val bundle = data?.extras

            try {
                val hike = Hike(
                    name = bundle?.getString("hikeName").toString(),
                    distance = bundle?.getDouble("hikeDistance")!!,
                    difficulty = bundle.getString("hikeDifficulty").toString(),
                    description = bundle?.getString("hikeDescription").toString(),
                    location = bundle?.getString("hikeLocation").toString(),
                    category = bundle?.getString("hikeCategory").toString()
                )
                hikeViewModel.insert(hike)
            } catch(e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "Unable to save hike!",
                    Toast.LENGTH_LONG
                ).show()
            }

        } else {
            Toast.makeText(
                applicationContext,
                "No information to save!",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}