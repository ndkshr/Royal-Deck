package me.ndkshr.royaldeck

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import me.ndkshr.royaldeck.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        binding.goto21CardTrick.setOnClickListener {
            startActivity(Intent(this, TwentyOneCardTrick::class.java))
        }

        binding.gotoMisingManTrick.setOnClickListener {
            startActivity(Intent(this, MissingManActivity::class.java))
        }
    }
}