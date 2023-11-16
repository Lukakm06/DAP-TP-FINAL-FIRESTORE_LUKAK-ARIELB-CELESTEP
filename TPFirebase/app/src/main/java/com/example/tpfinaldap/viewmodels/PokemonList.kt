package com.example.tpfinaldap.viewmodels

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpfinaldap.R
import com.example.tpfinaldap.classes.Pokemon
import com.example.tpfinaldap.classes.PokemonAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class PokemonList : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonList: ArrayList<Pokemon>
    private var db = Firebase.firestore
    private lateinit var botonAdd: Button
    private lateinit var newPokeID: String
    private lateinit var idCompartido: sharedData

    private lateinit var adapter : PokemonAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pokemon_list, container, false)

        db = FirebaseFirestore.getInstance()
        recyclerView = view.findViewById(R.id.recyclerPokemon)
        recyclerView.layoutManager = LinearLayoutManager(context)
        pokemonList = arrayListOf()
        botonAdd = view.findViewById(R.id.addButton)

        initRecyclerView()

        botonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_list_screen_to_add_pokemon)
        }
        return view
    }

    private fun initRecyclerView() {
        db.collection("Pokemon").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val pokemon:Pokemon? = data.toObject<Pokemon>(Pokemon::class.java)
                    pokemonList.add(pokemon!!)
                }

                adapter = PokemonAdapter(pokemonList,
                    onDeleteClick = {position -> deletePokemon(position)
                },
                    onEditClick = {position -> editPokemon(position)
                },
                    onItemClick = {position -> seePokemonData(position)})

                recyclerView.adapter = adapter
            }
        }.addOnFailureListener {
            Toast.makeText(context, it.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    fun seePokemonData(position:Int) {

        newPokeID = pokemonList[position].idPokemon.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = newPokeID

        findNavController().navigate(R.id.action_list_screen_to_pokemon_description)
    }

    fun editPokemon(position: Int) {
        newPokeID = pokemonList[position].idPokemon.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = newPokeID

        findNavController().navigate(R.id.action_list_screen_to_edit_pokemon)
    }

    fun deletePokemon (position : Int){

        db.collection("Pokemon").document(pokemonList[position].idPokemon.toString()).delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Pokemon Eliminado", Toast.LENGTH_SHORT).show()
                adapter.notifyItemRemoved(position)
                pokemonList.removeAt(position)
            }
            .addOnFailureListener { Toast.makeText(requireContext(),"No se puedo eliminar el Pokemon", Toast.LENGTH_SHORT).show() }
    }
}