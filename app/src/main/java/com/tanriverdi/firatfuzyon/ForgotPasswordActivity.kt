package com.tanriverdi.firatfuzyon

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.tanriverdi.firatfuzyon.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    private lateinit var binding :ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // Firebase Authentication ve Firestore bağlantıları oluşturuluyor
        auth= Firebase.auth
        firestore= Firebase.firestore


        // Firebase Dynamic Links dinleyicisi
        FirebaseDynamicLinks.getInstance().getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->

                // Uygulama bir Firebase Dynamic Link ile açıldığında bu blok çalışır
            var deepLink: Uri? = null
            if (pendingDynamicLinkData != null) {
                deepLink = pendingDynamicLinkData.link

                // Derin bağlantıyı işle
                handleDynamicLink(deepLink)
            }
        }
            .addOnFailureListener(this) { e ->

                // Bağlantı başarısız oldu
                Log.w(TAG, "getDynamicLink:onFailure", e)
            }


        // Şifre sıfırlama butonunun tıklama işlemi dinleniyor
        binding.forgotPasswordOnaylaTextButton.setOnClickListener {
            val email = binding.schoolEmailCardViewEditText.text.toString()

            // E-posta alanının boş olup olmadığı kontrol ediliyor
            if (email.isEmpty()) {
                Toast.makeText(this, "Lütfen okul e-postanızı girin.", Toast.LENGTH_SHORT).show()
            } else if (!email.endsWith("@firat.edu.tr")) {
                // E-posta adresinin uygunluğu kontrol ediliyor
                Toast.makeText(this, "Lütfen '@firat.edu.tr' Uzantılı Mail Adresini Giriniz", Toast.LENGTH_SHORT).show()
            } else {

                // Firebase Authentication üzerinden şifre sıfırlama e-postası gönderiliyor
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            // İşlem başarılı olduğunda kullanıcıya bilgilendirme yapılıyor
                            Toast.makeText(this, "Şifre sıfırlama e-postası başarıyla gönderildi. Lütfen e-postanızı kontrol edin.", Toast.LENGTH_LONG).show()

                            // Kullanıcı şifre sıfırlama e-postasını aldıktan sonra giriş ekranına yönlendiriliyor
                            val intent= Intent(this,MembershipLoginScreenActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {

                            // İşlem başarısız olduğunda kullanıcıya hata mesajı gösteriliyor
                            Toast.makeText(this, "Şifre sıfırlama e-postası gönderilemedi. Lütfen daha sonra tekrar deneyin.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }


    }

    // Firebase Dynamic Link işleniyor
    private fun handleDynamicLink(deepLink: Uri?) {
        if (deepLink != null) {

            // Derin bağlantıya göre uygun işlemler yapılıyor
            val intent = Intent(this, UyelikHomeActivity::class.java)
            intent.data = deepLink
            startActivity(intent)
        }

    }
}