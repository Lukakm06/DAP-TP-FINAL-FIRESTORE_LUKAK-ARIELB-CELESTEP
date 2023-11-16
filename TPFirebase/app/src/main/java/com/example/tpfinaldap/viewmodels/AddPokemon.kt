package com.example.tpfinaldap.viewmodels

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tpfinaldap.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddPokemon : Fragment() {

    companion object;

    private lateinit var textNum: EditText
    private lateinit var textName: EditText
    private lateinit var textType1: EditText
    private lateinit var textType2: EditText
    private var db = Firebase.firestore
    private lateinit var addButton: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_pokemon, container, false)

        textNum = view.findViewById(R.id.addPokeNum)
        textName = view.findViewById(R.id.addPokeName)
        textType1 = view.findViewById(R.id.addPokeType1)
        textType2 = view.findViewById(R.id.addPokeType2)
        addButton = view.findViewById(R.id.uploadButton)

        addButton.setOnClickListener {

            val documentId:String = db.collection("Pokemon").document().id

            val pokemonNuevo = hashMapOf(
                "Number" to textNum.text.toString(),
                "Name" to textName.text.toString(),
                "Type1" to textType1.text.toString(),
                "Type2" to textType2.text.toString(),
                "idPokemon" to documentId
            )

            db.collection("Pokemon").document(documentId).set(pokemonNuevo)
                .addOnSuccessListener {
                    Toast.makeText(context, "subido", Toast.LENGTH_SHORT).show()}
                .addOnFailureListener {
                    Toast.makeText(context, "no se pudo subir",Toast.LENGTH_SHORT).show() }

            findNavController().navigate(R.id.action_add_pokemon_to_list_screen)
        }

        return view
    }

}