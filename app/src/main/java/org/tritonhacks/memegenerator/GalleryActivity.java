package org.tritonhacks.memegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    ImageView imageView;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ArrayList<MemeTemplate> templates = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        this.imageView = findViewById(R.id.imageView);
        this.recyclerView = findViewById(R.id.recyclerView);
        this.gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        this.recyclerView.setLayoutManager(gridLayoutManager);

        // Create GET request for meme templates
        final String getMemeTemplatesApiUrl = "https://api.imgflip.com/get_memes";
        final StringRequest r = new StringRequest(Request.Method.GET, getMemeTemplatesApiUrl,
                response -> {
                    // Get JSON array of meme template objects
                    final JsonArray templatesArr = ((JsonObject) JsonParser.parseString(response))
                            .getAsJsonObject("data")
                            .getAsJsonArray("memes");

                    // Populate list of meme templates from JSON array
                    this.templates.ensureCapacity(templatesArr.size());
                    for (final JsonElement templateElem : templatesArr) {
                        final JsonObject templateObj = templateElem.getAsJsonObject();
                        final String id = templateObj.get("id").getAsString();
                        final String url = templateObj.get("url").getAsString();
                        final int boxCount = templateObj.get("box_count").getAsInt();
                        this.templates.add(new MemeTemplate(id, url, boxCount));
                    }

                    // Populate recycler view with list of meme templates
                    final DataAdapter dataAdapter = new DataAdapter(getApplicationContext(), templates, t -> {
                        final Intent intent = new Intent(GalleryActivity.this, EditMemeActivity.class);
                        intent.putExtra("id", t.getId());
                        intent.putExtra("url", t.getUrl());
                        intent.putExtra("boxCount", t.getBoxCount());
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(dataAdapter);
                },
                error -> Log.e("Volley request error", String.valueOf(error)));

        // Schedule GET request for meme templates
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(r);
    }
}
