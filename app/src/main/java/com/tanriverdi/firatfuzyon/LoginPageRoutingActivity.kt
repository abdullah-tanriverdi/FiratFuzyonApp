package com.tanriverdi.firatfuzyon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tanriverdi.firatfuzyon.databinding.ActivityLoginPageRoutingBinding

class LoginPageRoutingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageRoutingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageRoutingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.uyeGirisiButtonLayout.setOnClickListener{
            val intent = Intent(this@LoginPageRoutingActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.toplulukGirisiButtonLayout.setOnClickListener{
            val intent = Intent( this@LoginPageRoutingActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}