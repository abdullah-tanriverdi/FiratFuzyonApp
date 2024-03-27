package com.tanriverdi.firatfuzyon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.tanriverdi.firatfuzyon.databinding.ActivityUyelikProfilScreenBinding

class UyelikProfilScreenActivity : AppCompatActivity() {


    private lateinit var binding: ActivityUyelikProfilScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUyelikProfilScreenBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)


    }


}