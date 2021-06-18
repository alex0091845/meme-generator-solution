package org.tritonhacks.memegenerator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GetMemeActivity extends AppCompatActivity {

    final static String URL = "https://meme-api.herokuapp.com/gimme/cleanmemes/50";
    final static String POSTLINK_KEY = "postLink";
    final static String SUBREDDIT_KEY = "subreddit";
    final static String TITLE_KEY = "title";
    final static String URL_KEY = "url";

    private ArrayList<RandomMeme> memeList;
    private String memeUrl;

    private ImageView imgVMeme;
    private Button btnGetAnotherMeme;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_meme);

        initViews();

        if(savedInstanceState == null) {
            getMeme();
        }
    }

    /**
     * Initializes all necessary views.
     */
    private void initViews() {
        this.btnGetAnotherMeme = findViewById(R.id.btn_get_another_meme);
        this.getAnotherMeme.setOnClickListener(v -> getMeme());

        this.imgVMeme = findViewById(R.id.imgVMeme);
    }

    /**
     * Get a meme either through request or load the meme if list is not empty.
     */
    private void getMeme() {
        if (isMemeListEmpty()) {
            getRequest();
        } else {
            loadMeme();
        }
    }

    /**
     * Saves the current state. Namely the URL of the current meme.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(URL_KEY, this.memeUrl);
        super.onSaveInstanceState(outState);
    }

    /**
     * Restores the previous state, with URL of the previously viewed meme.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.memeUrl = savedInstanceState.getString(URL_KEY);
        Picasso.get().load(this.memeUrl).into(this.imgVMeme);
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Helper method to load the meme image onto ImageView
     */
    private void loadMeme() {
        this.memeUrl = popMeme();
        Picasso.get().load(this.memeUrl).into(this.imgVMeme);
    }

    /**
     * Make a GET request to the memes by the URL string.
     */
    private void getRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                response -> {

                    // Gets 50 to 100 memes formatted as a json String and converts it into a
                    // generic JsonObject
                    JsonObject randomMemes = (JsonObject) JsonParser.parseString(response);

                    // Converts the JsonObject (randomMemes) into a JsonArray
                    JsonArray jsonMemesArray = randomMemes.getAsJsonArray("memes");

                    // Turns the JsonArray (jsonMemesArray) into an ArrayList of RandomMeme objects
                    // and store it inside this.memeList, which contains RandomMeme objects
                    this.memeList = jsonArrayToMemeList(jsonMemesArray);

                    // load a meme from memeList
                    loadMeme();

                }, error -> {
            // make a Toast message to show error. Needs to be polished.
            // TODO
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        });

        queue.add(stringRequest);
    }

    /**
     * Converts a JsonArray into a RandomMeme ArrayList
     * @param jsonArray
     */
    private ArrayList<RandomMeme> jsonArrayToMemeList(JsonArray jsonArray) {
        // TODO for students
        ArrayList<RandomMeme> randomMemeList = new ArrayList<>();

        for(int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = ((JsonObject) jsonArray.get(i));
            String postLink = jsonObject.get(POSTLINK_KEY).getAsString();
            String subreddit = jsonObject.get(SUBREDDIT_KEY).getAsString();
            String title = jsonObject.get(TITLE_KEY).getAsString();
            String url = jsonObject.get(URL_KEY).getAsString();

            // TODO for students
            RandomMeme randomMeme = new RandomMeme(postLink, subreddit, title, url);

            randomMemeList.add(randomMeme);
        }

        return randomMemeList;
    }

    /**
     * Removes the last meme object in the array and gets its url.
     *
     * @return url as a String
     */
    private String popMeme() {
        RandomMeme meme = this.memeList.remove(this.memeList.size() - 1);
        return meme.getUrl();
    }

    /**
     * Checks if memeList is null or empty. SHORT-CIRCUITING
     *
     * @return true if memeList is empty, false otherwise
     */
    private boolean isMemeListEmpty() {
        return this.memeList == null || this.memeList.size() == 0;
    }
}
