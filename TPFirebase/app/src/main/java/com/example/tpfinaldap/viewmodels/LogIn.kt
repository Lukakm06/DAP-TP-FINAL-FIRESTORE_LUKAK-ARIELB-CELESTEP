package com.example.tpfinaldap.viewmodels

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tpfinaldap.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogIn : Fragment() {

    lateinit var botonLogin: Button
    lateinit var botonRegistrar: Button
    lateinit var mail: EditText
    lateinit var pass: EditText
    lateinit var textoMail: String
    lateinit var textoPass: String
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        botonRegistrar = view.findViewById(R.id.loginRegister)
        botonLogin = view.findViewById(R.id.loginButton)
        mail = view.findViewById(R.id.loginEmail)
        pass = view.findViewById(R.id.loginPass)
        auth = Firebase.auth

        botonRegistrar.setOnClickListener {
            findNavController().navigate(R.id.action_login_screen_to_register_screen)
        }

        botonLogin.setOnClickListener {
            textoMail = mail.text.toString()
            textoPass = pass.text.toString()

            auth.signInWithEmailAndPassword(textoMail, textoPass).addOnCompleteListener { }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(ContentValues.TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        findNavController().navigate(R.id.action_login_screen_to_list_screen)
                    } else {
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(context, "El mail y/o contraseña ingresados son incorrectos", Toast.LENGTH_SHORT,).show()
                    } } }
        return view
    } }