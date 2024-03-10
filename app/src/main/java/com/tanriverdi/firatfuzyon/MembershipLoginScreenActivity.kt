package com.tanriverdi.firatfuzyon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tanriverdi.firatfuzyon.databinding.ActivityMembershipLoginScreenBinding

class MembershipLoginScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMembershipLoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembershipLoginScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.membershipCardViewContactUsText.setOnClickListener{
            //  Intent ile ContactUsActivity'yi başlat
            val intent = Intent(this@MembershipLoginScreenActivity,ContactUsActivity::class.java)
            startActivity(intent)
        }
    }
}