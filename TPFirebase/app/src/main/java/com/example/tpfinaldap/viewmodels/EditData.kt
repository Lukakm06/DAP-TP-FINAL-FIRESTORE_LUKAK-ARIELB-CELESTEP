package com.example.tpfinaldap.viewmodels

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tpfinaldap.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditData : Fragment() {
    private lateinit var idCompartido: sharedData
    private var db = Firebase.firestore

    private lateinit var editNum: EditText
    private lateinit var editName: EditText
    private lateinit var editType1: EditText
    private lateinit var editType2: EditText
    private lateinit var idPokemon: String

    private lateinit var editButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_data, container, false)

        editNum = view.findViewById(R.id.editPokeNum)
        editName = view.findViewById(R.id.editPokeName)
        editType1 = view.findViewById(R.id.editPokeType1)
        editType2 = view.findViewById(R.id.editPokeType2)
        editButton = view.findViewById(R.id.editButton)

        db = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.observe(viewLifecycleOwner) { data1 ->

        db.collection("Pokemon").document(data1).get().addOnSuccessListener {

            editNum.setText(it.data?.get("Number").toString())
            editName.setText(it.data?.get("Name").toString())
            editType1.setText(it.data?.get("Type1").toString())
            editType2.setText(it.data?.get("Type2").toString())
            idPokemon = it.data?.get("idPokemon").toString()

        }.addOnFailureListener {
            Toast.makeText(context, "no se encontraron datos", Toast.LENGTH_SHORT).show()
        }

        editButton.setOnClickListener {

            val superHeroeNuevo = hashMapOf(
                "Number" to editNum.text.toString(),
                "Name" to editName.text.toString(),
                "Type1" to editType1.text.toString(),
                "Type2" to editType2.text.toString(),
                "idPokemon" to idPokemon
            )

            db.collection("Pokemon").document(data1).set(superHeroeNuevo)
                .addOnSuccessListener {
                    Toast.makeText(context, "subido", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "no se pudo subir", Toast.LENGTH_SHORT).show()
                }

            findNavController().navigate(R.id.action_edit_pokemon_to_list_screen)
        } }

        return view
    }
}