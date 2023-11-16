package com.example.tpfinaldap.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tpfinaldap.R

class PokemonAdapter(
    pokemonList: ArrayList<Pokemon>,
    private val onDeleteClick : (Int)->Unit,
    private val onEditClick : (Int) -> Unit,
    private val onItemClick: (Int) -> Unit

): RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>(){
    private var pokemonList: ArrayList<Pokemon>

    init {
        this.pokemonList = pokemonList
    }

    class PokemonViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val textpokeNum= view.findViewById<TextView>(R.id.tvPokeNum)
        private val textpokeName= view.findViewById<TextView>(R.id.tvPokeName)
        val edit = view.findViewById<Button>(R.id.botonEditar)
        val eliminar = view.findViewById<Button>(R.id.botonEliminar)

        fun render(pokemonModel: Pokemon){
            textpokeNum.text = pokemonModel.pokeNum
            textpokeName.text = pokemonModel.pokeName
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.item_pokemon, parent, false))
    }
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = pokemonList[position]
        holder.render(item)
        holder.eliminar.setOnClickListener {
            onDeleteClick(position)
        }
        holder.edit.setOnClickListener {
            onEditClick(position)
        }
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }
    override fun getItemCount(): Int = pokemonList.size

}