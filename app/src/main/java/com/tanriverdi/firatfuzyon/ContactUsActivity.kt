package com.tanriverdi.firatfuzyon

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tanriverdi.firatfuzyon.databinding.ActivityContactUsBinding

class ContactUsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityContactUsBinding

    // İnternet erişimi izni isteme için kullanılan kod
    private val INTERNET_PERMISSION_CODE = 1

    // E-posta gönderme işlemi için kullanılan requestCode
    private val EMAIL_REQUEST_CODE = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // Gönderme butonuna tıklanınca e-posta gönderme fonksiyonu çağrılır
        binding.contactUsSendTextButton.setOnClickListener{
            sendEmail()
        }

        // İnternet erişimi izni kontrolü ve talebi
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.INTERNET),
                INTERNET_PERMISSION_CODE
            )
        }

    }




    // E-posta gönderme işlemini gerçekleştiren fonksiyon
    fun sendEmail() {
            // EditText'lerden kullanıcının girdiği bilgileri al
            val konu = binding.contactUsTitleEditText.text.toString()
            val aciklama = binding.contactUsDescriptionEditText.text.toString()

          // Başlık ve açıklama alanlarının boş olup olmadığını kontrol et
            if (konu.isBlank()  || aciklama.isBlank()){
                Toast.makeText(this, "Başlık Ve Açıklama Alanını Doldur", Toast.LENGTH_SHORT).show()
            }else{

                // E-posta içeriğini oluştur
                val emailContent = "AÇIKLAMA:  $aciklama"

                // E-posta gönderme işlemi için Intent oluştur
                val emailIntent = Intent(Intent.ACTION_SEND)

                // E-posta türünü belirle (RFC 822 standartlarına uygun olarak)
                emailIntent.type = "message/rfc822"

                // E-posta alıcı adresini belirle (Array halinde, birden fazla alıcı eklemek mümkündür)
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("tanriverdi.firatuni@gmail.com"))

                // E-posta konusunu belirle
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "$konu")

                // E-posta içeriğini belirle
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailContent)

                try {

                    // E-posta gönderme işlemini başlat
                    startActivityForResult(Intent.createChooser(emailIntent, "E-posta Gönder"), EMAIL_REQUEST_CODE)
                } catch (ex: android.content.ActivityNotFoundException) {

                    // E-posta gönderme uygulaması bulunamazsa hata mesajı göster
                    Toast.makeText(this, "E-posta gönderme uygulaması bulunamadı.", Toast.LENGTH_SHORT)
                        .show()
                }
            }



    }


    // E-posta gönderme işlemi sonucunda çağrılan metod
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // E-posta gönderme işlemi tamamlandığında, bir sonraki aktiviteye yönlendirme
        if (requestCode == EMAIL_REQUEST_CODE) {

            val intent = Intent(this@ContactUsActivity,MembershipLoginScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // İzin isteği sonucunda çağrılan metod
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // İnternet erişimi izni verildiyse e-posta gönderme işlemine devam et
        if (requestCode == INTERNET_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                sendEmail()
            } else {

                Toast.makeText(this, "İnternet erişimi izni verilmedi.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}