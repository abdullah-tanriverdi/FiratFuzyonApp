package com.tanriverdi.firatfuzyon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.tanriverdi.firatfuzyon.databinding.ActivityMembershipLoginScreenBinding


class MembershipLoginScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMembershipLoginScreenBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembershipLoginScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Buton işlevlerini ayarla
        buttons()

        // Firebase yetkilendirme örneğini al
       auth =Firebase.auth

    }



    // Butonların tıklama işlevlerini tanımla
    fun buttons(){

        // İletişim ekranını başlatan buton
        binding.membershipCardViewContactUsText.setOnClickListener{
            //  Intent ile ContactUsActivity'yi başlat
            val intent = Intent(this@MembershipLoginScreenActivity,ContactUsActivity::class.java)
            startActivity(intent)
        }


        // Gizlilik politikası ekranını başlatan buton
        binding.membersipSecurityText.setOnClickListener{
            // Intent ile PrivacyPolicyActivity'yi başlat
            val intent = Intent(this@MembershipLoginScreenActivity,PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }


        // Üye olma ekranını başlatan buton
        binding.membershipSignUpText.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        // Şifremi unuttum ekranını başlatan buton
        binding.membershipCardViewForgotPasswordText.setOnClickListener {
            val intent= Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)

        }

        // Giriş yapma butonu
        binding.membershipCardViewLoginTextButton.setOnClickListener {
            val email = binding.membershipCardViewUsernameEditText.text.toString()
          val password = binding.membershipCardViewPasswordEditText.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Lütfen e-posta ve şifrenizi girin", Toast.LENGTH_SHORT).show()
            } else if (!email.endsWith("@firat.edu.tr")) {
                Toast.makeText(this, "Sadece @firat.edu.tr uzantılı e-posta adresleri kabul edilmektedir", Toast.LENGTH_SHORT).show()
            } else {
                // Firebase üzerinden giriş yap
                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    // Başarılı giriş
                    Toast.makeText(this, "Giriş başarılı", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, UyelikProfilScreenActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    // Giriş başarısız
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}