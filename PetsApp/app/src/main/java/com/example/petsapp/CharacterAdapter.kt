package com.example.petsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CharacterAdapter(private var characterList: List<Character>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterImage: ImageView = itemView.findViewById(R.id.characterImage)
        val characterName: TextView = itemView.findViewById(R.id.characterName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characterList[position]
        holder.characterName.text = character.name
        // Load image using Glide
        Glide.with(holder.characterImage.context)
            .load(character.image)
            .placeholder(R.drawable.ic_placeholder) // Placeholder image
            .error(R.drawable.ic_error) // Error image
            .into(holder.characterImage)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    // Add this method to update the adapter's data dynamically
    fun updateData(newCharacterList: List<Character>) {
        characterList = newCharacterList
        notifyDataSetChanged()
    }
}
