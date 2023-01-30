package com.jzapata.galeria

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val imageList = mutableListOf<Image>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemsListView: RecyclerView = findViewById(R.id.recyclerList);


        //llamar datos desde php pero con un echo json

        fetchData(this, "imagen");

        //Toast.makeText(this, "aqui", Toast.LENGTH_LONG).show()



        //println(imagenesFetch);

        //imagenes por ahora
        //val imagen1 = Image("Imagen 1", "https://picsum.photos/id/160/200/300","Esta es la imagen id 160","01/01/2023");
        //val imagen2 = Image("Imagen 2", "https://picsum.photos/id/260/200/300","Esta es la imagen id 260","02/01/2023");
        //val imagen3 = Image("Imagen 3", "https://picsum.photos/id/360/200/300","Esta es la imagen id 360","03/01/2023");
        //val imagen4 = Image("Imagen 4", "https://picsum.photos/id/460/200/300","Esta es la imagen id 460","04/01/2023");
        //val imagen5 = Image("Imagen 5", "https://picsum.photos/id/560/200/300","Esta es la imagen id 560","05/01/2023");
        //val imagen6 = Image("Imagen 6", "https://picsum.photos/id/660/200/300","Esta es la imagen id 660","06/01/2023");
        //val imagen7 = Image("Imagen 7", "https://picsum.photos/id/760/200/300","Esta es la imagen id 760","07/01/2023");
        //val imagen8 = Image("Imagen 8", "https://picsum.photos/id/860/200/300","Esta es la imagen id 860","08/01/2023");
        //val imagen9 = Image("Imagen 9", "https://picsum.photos/id/960/200/300","Esta es la imagen id 960","09/01/2023");

        //imageList.add(imagen1);
        //imageList.add(imagen2);
        //imageList.add(imagen3);
        //imageList.add(imagen4);
        //imageList.add(imagen5);
        //imageList.add(imagen6);
        //imageList.add(imagen7);
        //imageList.add(imagen8);
        //imageList.add(imagen9);

        itemsListView.layoutManager = GridLayoutManager(this, 2)
        itemsListView.adapter = ImagenAdapter(this, imageList)

        //val imageView1: ImageView = findViewById(R.id.imageView1);
        //imageToGLide(imageView1,"160");
        //imageView1.setOnClickListener{ detalleImagen("160"); }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //R.id.nav_reload_image -> Toast.makeText(this, "Option selected image", Toast.LENGTH_SHORT).show();
            R.id.nav_reload_image -> {
                Toast.makeText(this, "Refresh Imagenes", Toast.LENGTH_SHORT).show();
                fetchData(this, "imagen");
            };
            //R.id.nav_reload_gif -> Toast.makeText(this, "Option selected gif", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private fun fetchData(context: Context, tipo: String) {
        imageList.clear();
        val queue = Volley.newRequestQueue(context);
        var url = ""
        if (tipo == "imagen"){
            println("En Fetch Data Imagenes");
            url = "https://izisoft.cl/generate_json_test?tipo=imagen";
        }else{
            println("En Fetch Data gif");
            url = "https://izisoft.cl/generate_json_test?tipo=gif";
        }


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Procesar la respuesta JSON aquí
                try {

                    //val data = response.getString("imagenes");
                    val imagenesListJSON = response.getJSONArray("imagenes");
                    for (i in 0 until imagenesListJSON.length()) {
                        trabajarRequest(imagenesListJSON.getJSONObject(i));
                    }
                    cargarImagenes(context);
                    //Toast.makeText(context, "Data: $imagenesListJSON", Toast.LENGTH_LONG).show()
                } catch (e: JSONException){
                    Toast.makeText(context, "Error: $e", Toast.LENGTH_LONG).show();
                }
            },
            { error ->
                // Manejar el error aquí
                Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show();
            }
        )
        queue.add(jsonObjectRequest);
    }
    private fun trabajarRequest(item: JSONObject){
        //println(item);
        val newImagen = Image(
            item.getString("title"),
            item.getString("image_url"),
            item.getString("description"),
            item.getString("date"));
        //println(newImagen);
        imageList.add(newImagen);
    }

    private fun cargarImagenes(context: Context){
        val itemsListView: RecyclerView = findViewById(R.id.recyclerList);
        itemsListView.layoutManager = GridLayoutManager(context, 2)
        itemsListView.adapter = ImagenAdapter(context, imageList)
    }

    private fun detalleImagen(id: String){
        val intent  = Intent(this, vistaImagenDetalle::class.java);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
    private fun imageToGLide(view: ImageView, id: String){
        Glide
            .with(this)
            .load("https://picsum.photos/id/"+ id +"/200/300")
            .into(view)
    }
}