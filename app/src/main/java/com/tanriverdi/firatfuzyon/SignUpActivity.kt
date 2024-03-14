package com.tanriverdi.firatfuzyon

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tanriverdi.firatfuzyon.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {


    // Gerekli değişkenlerin tanımlanması
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val verificationTimeOut: Long = 1 * 20 * 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Firebase yetkilendirme ve firestore nesnelerinin oluşturulması
        auth = Firebase.auth
        firestore = Firebase.firestore


        // "Zaten üye misiniz?" metni tıklandığında giriş ekranına geçiş yapılması
        binding.alreadyText.setOnClickListener {
            val intent = Intent(this, MembershipLoginScreenActivity::class.java)
            startActivity(intent)
            finish()
        }


        // "Devam Et" butonu tıklandığında kayıt işlemlerinin başlatılması
        binding.signUpCardViewContinueTextButton.setOnClickListener {
            signUp()
        }
    }



    // Kullanıcının kayıt işlemlerinin gerçekleştirildiği fonksiyon
    private fun signUp() {
        val email = binding.schoolEmailCardViewEditText.text.toString()
        val password = binding.passwordCardViewEditText.text.toString()

        // E-posta ve şifre alanlarının boş olup olmadığının kontrolü
        if (email.isEmpty() || password.isEmpty()) {
            showToast("Lütfen tüm alanları doldurun.")
        } else if (!email.endsWith("@firat.edu.tr")) {
            showToast("Lütfen geçerli bir '@firat.edu.tr' e-posta adresi girin.")
        } else {

            // Firebase üzerinden kullanıcı oluşturma işlemi
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // E-posta onayını gönderme işlemi
                    sendEmailVerification()
                    showToast("E-posta gönderildi, lütfen onaylayın.")
                } else {
                    showToast("Kayıt başarısız. Lütfen bilgilerinizi kontrol edin.")
                }
            }
        }
    }

    // E-posta onayını gönderme işleminin gerçekleştirildiği fonksiyon
    private fun sendEmailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                // E-posta gönderildi, onay süresi başlatılıyor
                showToast("E-posta gönderildi, lütfen 20 saniye içinde onaylayın.")
                Handler(Looper.getMainLooper()).postDelayed({
                    register()
                }, verificationTimeOut)
            } else {

                // E-posta gönderilemediğinde geri bildirim ve hata işlemleri
                showToast("E-posta gönderilemedi. Lütfen daha sonra tekrar deneyin.")
                startVerificationTimeOut()
            }
        }
    }


    // Kullanıcının kaydını tamamlama işleminin gerçekleştirildiği fonksiyon
    private fun register() {
        val user = auth.currentUser


        // Kullanıcı bilgilerini güncelleme ve e-posta onay durumunu kontrol etme
        user?.reload()?.addOnSuccessListener {
            val updatedUser = auth.currentUser
            if (updatedUser != null && updatedUser.isEmailVerified) {

                // Firestore'a kullanıcının e-posta bilgisini kaydetme işlemi
                val email = binding.schoolEmailCardViewEditText.text.toString()
                val postMap = hashMapOf<String, Any>()
                postMap["email"] = email

                firestore.collection("names").add(postMap).addOnSuccessListener {
                    showToast("Veri başarıyla kaydedildi.")

                    // Giriş ekranına geçiş
                    val intent = Intent(this, MembershipLoginScreenActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    showToast("Veri kaydedilemedi. Lütfen tekrar deneyin.")
                }
            } else {
                // E-posta onay alınamadığında geri bildirim ve kullanıcının silinmesi
                showToast("E-posta onayı alınamadığı için kayıt yapılamadı.")
                auth.currentUser?.delete()?.addOnCompleteListener { deleteTask ->
                    if (deleteTask.isSuccessful) {
                        showToast("E-posta onayı alınamadığı için kullanıcı silindi.")
                    }
                }
            }
        }?.addOnFailureListener { exception ->
            Log.e("Register", "User reload failed: ${exception.message}")
        }
    }


    // E-posta onay süresinin bitmesi durumunda gerçekleştirilecek işlemler
    private fun startVerificationTimeOut() {
        val countDownTimer = object : CountDownTimer(verificationTimeOut, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                auth.currentUser?.delete()?.addOnCompleteListener { deleteTask ->
                    if (deleteTask.isSuccessful) {
                        showToast("E-posta onayı alınamadığı için kullanıcı silindi.")
                    }
                }
            }
        }.start()
    }

    // Kullanıcıya geri bildirim verme işleminin gerçekleştirildiği fonksiyon
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
