package com.jzapata.galeria

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide

class ImagenAdapter(private val context: Context, private val list: MutableList<Image>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.item_imagen, parent, false)
        return ViewHolder(view, list)
    }

    override fun getItemCount() = list.size;

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lista = list[position];
        holder.textoImagen.text = lista.title;
        holder.currentPosition = position;
        Glide.with(context)
            .load(lista.image_url)
            .into(holder.vistaImagen);


    }
}
class ViewHolder(itemView: View, private val list: MutableList<Image>) : RecyclerView.ViewHolder(itemView) {
    val textoImagen: TextView = itemView.findViewById(R.id.itemTextView);
    val vistaImagen: ImageView = itemView.findViewById(R.id.imagenVista);

    var currentPosition = 0;

    init {
        itemView.setOnClickListener {
            val data = list[currentPosition];
            // Implementar acción de clic aquí
            //println(data);
            val intent = Intent(itemView.context, vistaImagenDetalle::class.java);
            intent.putExtra("ima_title", data.title);
            intent.putExtra("image_url", data.image_url);
            intent.putExtra("description", data.description);
            intent.putExtra("ima_date", data.date);
            itemView.context.startActivity(intent);
            //(itemView.context as Activity).finish();

        }
    }
}