package com.tanriverdi.firatfuzyon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.tanriverdi.firatfuzyon.databinding.ActivityOnboardingScreenBinding

class OnboardingScreenActivity : AppCompatActivity() {


    private lateinit var binding : ActivityOnboardingScreenBinding
    private lateinit var onboardingScreenAdapter : OnboardingScreenAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // SharedPreferences nesnesi oluştur ve "onboardingShown" değerini kontrol et
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val onboardingShown = sharedPreferences.getBoolean("onboardingShown", false)


        // Eğer onboarding daha önce gösterilmediyse
        if (!onboardingShown){

            // Onboarding ekran adaptörünü oluşturma
            onboardingScreenAdapter = OnboardingScreenAdapter(this,this)

            // ViewPager2'ye adaptörü atama
            binding.viewPager2.adapter = onboardingScreenAdapter

            // ViewPager2'ye sayfa geçiş efekti ekleme
            binding.viewPager2.setPageTransformer{ page , position ->
                val absPosition = Math.abs(position)
                page.alpha= 1-absPosition
            }


            // ViewPager2 sayfa değişimlerini takip et ve son sayfaya geldiğinde SharedPreferences'e kaydetme
            binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == onboardingScreenAdapter.itemCount - 1) {
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("onboardingShown", true)
                        editor.apply()
                    }
                }
            })
        }else{

            // Eğer onboarding daha önce gösterildiyse, Activity'e geçiş yap ve bu aktiviteyi kapat
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }



    }
}