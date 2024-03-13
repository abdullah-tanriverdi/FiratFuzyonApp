package com.tanriverdi.firatfuzyon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.tanriverdi.firatfuzyon.databinding.ActivityUyelikHomeBinding

class UyelikHomeActivity : AppCompatActivity() {



    private lateinit var binding :ActivityUyelikHomeBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUyelikHomeBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)


        auth= Firebase.auth
        firestore= Firebase.firestore

        getData()
    }

    fun getData(){
        firestore.collection("names").addSnapshotListener{ value, eror->
            if (eror!=null){
                Toast.makeText(this, eror.localizedMessage, Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }else{
                if (value != null && !value.isEmpty) {
                    val documents = value.documents
                    for (i in documents){

                        val email = i.getString("email") as String

                        if(email==auth.currentUser?.email){
                            binding.textView.text = email
                            return@addSnapshotListener
                        }
                    }

                }
            }

        }
    }
}