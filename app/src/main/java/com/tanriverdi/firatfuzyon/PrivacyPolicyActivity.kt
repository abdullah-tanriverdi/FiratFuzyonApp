package com.tanriverdi.firatfuzyon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import com.tanriverdi.firatfuzyon.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivacyPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Gizlilik Politikası URL'sini ayarla
        val webSettings : WebSettings = binding.webView.settings

        // JavaScript'i etkinleştir
        webSettings.javaScriptEnabled=true


        //Gösterilecek Olan Gizlilik Politilakası URL'si ayarlama
        val privacyPolicyUrl = "https://www.lipsum.com/"
        binding.webView.loadUrl(privacyPolicyUrl)
    }
}