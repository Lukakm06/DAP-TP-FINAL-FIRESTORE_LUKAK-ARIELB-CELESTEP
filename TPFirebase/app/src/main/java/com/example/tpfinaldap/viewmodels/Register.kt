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

class Register : Fragment() {

    lateinit var botonRegistrar: Button
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var confPassword: EditText
    lateinit var username: EditText
    lateinit var datoMail: String
    lateinit var datoPassword: String
    lateinit var datoConfPassword: String
    lateinit var nombreUsuario: String
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        botonRegistrar = view.findViewById(R.id.registerButton)
        email = view.findViewById(R.id.registerEmail)
        password = view.findViewById(R.id.registerPass)
        confPassword = view.findViewById(R.id.registerRePass)
        username = view.findViewById(R.id.registerName)

        botonRegistrar.setOnClickListener {

            datoMail = email.text.toString()
            datoPassword = password.text.toString()
            datoConfPassword = confPassword.text.toString()
            nombreUsuario = username.text.toString()

            if (nombreUsuario.length < 4 ) {
                Toast.makeText(context, "El nombre de Usuario es muy corto", Toast.LENGTH_SHORT,).show()
            }
            else if (datoPassword.length < 6) {
                Toast.makeText(context, "La contraseña debe contener al menos 6 caracteres", Toast.LENGTH_SHORT,).show()
            }
            else if (datoPassword == datoConfPassword) {
                auth = Firebase.auth
                auth.createUserWithEmailAndPassword(datoMail, datoPassword).addOnCompleteListener {  }
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(ContentValues.TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            findNavController().navigate(R.id.action_register_screen_to_list_screen)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(context, "Su mail ya esta registrado o no existe", Toast.LENGTH_SHORT,).show()
                        } } }
            else {
                Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT,).show()
            } }
        return view
    } }