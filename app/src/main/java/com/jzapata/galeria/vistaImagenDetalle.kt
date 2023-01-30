package com.jzapata.galeria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class vistaImagenDetalle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_imagen_material)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        val ima_title: String? = intent.getStringExtra("ima_title");
        val image_url: String? = intent.getStringExtra("image_url");
        val description: String? = intent.getStringExtra("description");
        val ima_date: String? = intent.getStringExtra("ima_date");

        val cardViewTitle = findViewById<TextView>(R.id.cardViewTitle)
        cardViewTitle.text = ima_title;
        val cardViewDate = findViewById<TextView>(R.id.cardViewDate)
        cardViewDate.text = "Fecha post: " + ima_date;
        val cardViewDesc = findViewById<TextView>(R.id.cardViewDesc)
        cardViewDesc.text = "Descripción: " + description;

        val imageViewDet: ImageView = findViewById(R.id.cardViewImagen);
        Glide
            .with(this)
            .load(image_url)
            .into(imageViewDet)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        finish()
    }
}