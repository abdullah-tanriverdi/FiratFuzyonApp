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

        buttons()

       auth =Firebase.auth

    }



    fun buttons(){
        binding.membershipCardViewContactUsText.setOnClickListener{
            //  Intent ile ContactUsActivity'yi başlat
            val intent = Intent(this@MembershipLoginScreenActivity,ContactUsActivity::class.java)
            startActivity(intent)
        }

        binding.membersipSecurityText.setOnClickListener{
            // Intent ile PrivacyPolicyActivity'yi başlat
            val intent = Intent(this@MembershipLoginScreenActivity,PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }


        binding.membershipSignUpText.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.membershipCardViewForgotPasswordText.setOnClickListener {
            val intent= Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)

        }

        binding.membershipCardViewLoginTextButton.setOnClickListener {
            val email = binding.membershipCardViewUsernameEditText.text.toString()
          val password = binding.membershipCardViewPasswordEditText.text.toString()

           if (email.equals("") || password.equals("")){
               Toast.makeText(this, "basasrızı", Toast.LENGTH_SHORT).show()

            }else{
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    val intent = Intent(this,UyelikHomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_SHORT).show()

                }
           }


        }
    }
}