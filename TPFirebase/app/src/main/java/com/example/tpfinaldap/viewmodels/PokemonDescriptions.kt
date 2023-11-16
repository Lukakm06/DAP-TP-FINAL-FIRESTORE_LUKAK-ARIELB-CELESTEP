package com.example.tpfinaldap.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.tpfinaldap.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PokemonDescriptions : Fragment() {

    private lateinit var idCompartido: sharedData
    private var db = Firebase.firestore

    private lateinit var pokeNum: TextView
    private lateinit var pokeName: TextView
    private lateinit var pokeType1: TextView
    private lateinit var pokeType2: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pokemon_description, container, false)

        pokeNum = view.findViewById(R.id.pokeNum)
        pokeName = view.findViewById(R.id.pokeName)
        pokeType1 = view.findViewById(R.id.pokeType1)
        pokeType2 = view.findViewById(R.id.pokeType2)

        db = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.observe(viewLifecycleOwner) { data1 ->

            db.collection("Pokemon").document(data1).get().addOnSuccessListener {

                pokeNum.text = (it.data?.get("Number").toString())
                pokeName.text = (it.data?.get("Name").toString())
                pokeType1.text = (it.data?.get("Type1").toString())
                pokeType2.text = (it.data?.get("Type2").toString())

            }.addOnFailureListener {
                Toast.makeText(context, "no se encontraron datos", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

}