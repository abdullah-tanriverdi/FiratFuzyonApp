package com.tanriverdi.firatfuzyon

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2


// RecyclerView.Adapter sınıfını extend eden OnboardingScreenAdapter sınıfı
class OnboardingScreenAdapter(private val context : Context , private val activity : AppCompatActivity) : RecyclerView.Adapter<OnboardingScreenAdapter.ViewHolder>() {



    // Onboarding ekranları için kullanılacak layout kaynakları
    private val onboardingLayouts = intArrayOf(
        R.layout.onboarding_layout1,
        R.layout.onboarding_layout2,
        R.layout.onboarding_layout3,
        R.layout.onboarding_layout4,
    )

    // ViewPager2 nesnesi için değişken tanımlanma
    private lateinit var viewPager: ViewPager2



    // onCreateViewHolder fonksiyonu: ViewHolder nesnesini oluşturur ve döndürür
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingScreenAdapter.ViewHolder {

        val layoutInflater =LayoutInflater.from(context)
        val view = layoutInflater.inflate(viewType , parent , false)
        viewPager = (activity as OnboardingScreenActivity).findViewById(R.id.viewPager2)
        return ViewHolder(view)


    }


    // onBindViewHolder fonksiyonu: Belirtilen pozisyondaki verileri görünüme bağlar
    override fun onBindViewHolder(holder: OnboardingScreenAdapter.ViewHolder, position: Int) {

        // Pozisyona göre işlemler
        when(position){
            0 -> {
                holder.nextButton.setOnClickListener{
                    viewPager.setCurrentItem(1,true)
                }
            }

            1 ->{
                holder.nextButton.setOnClickListener {
                    viewPager.setCurrentItem(2,true)
                }

                holder.prevButton.setOnClickListener {
                    viewPager.setCurrentItem(0,true)
                }
            }

            2 -> {
                holder.nextButton.setOnClickListener {
                    viewPager.setCurrentItem(3,true)
                }

                holder.prevButton.setOnClickListener {
                    viewPager.setCurrentItem(1,true)
                }
            }

            3 -> {

                holder.nextButton.setOnClickListener {
                    activity.startActivity(Intent(context , MainActivity::class.java))
                    activity.finish()
                }

                holder.prevButton.setOnClickListener {
                    viewPager.setCurrentItem(2, true)
                }
            }
        }

    }


    // getItemCount fonksiyonu: Veri kümesindeki öğe sayısını döndürür
    override fun getItemCount(): Int {

        return onboardingLayouts.size

    }

    // getItemViewType fonksiyonu: Belirtilen pozisyondaki öğenin türünü döndürür
    override fun getItemViewType(position: Int): Int {
        return onboardingLayouts[position]
    }


    // ViewHolder sınıfı, her bir RecyclerView öğesi için kullanılan görünüm elemanlarını içerir
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val nextButton : ImageView = itemView.findViewById(R.id.nextButton)
        val prevButton : ImageView = itemView.findViewById(R.id.prevButton)
    }
}