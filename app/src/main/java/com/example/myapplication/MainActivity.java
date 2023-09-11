package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {



    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap <String,String> hashMap = new HashMap<>();

    ListView listView;
   ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);




 //===============================================================================


        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        // Your JSON array request URL
        String url = "https://testbdraian.000webhostapp.com/apps/video.json";

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);
                        // Loop through the array elements
                        for(int i=0; i < response.length(); i++){
                            try {
                                // Get current JSON object
                                JSONObject jsonObject = response.getJSONObject(i);

                                // Get the current object's fields (replace these keys with your actual keys)
                                String title = jsonObject.getString("title");
                                String video_id = jsonObject.getString("video_id");

                                hashMap = new HashMap<>();
                                hashMap.put("title", title);
                                hashMap.put("video_id", video_id);
                                arrayList.add(hashMap);





                            }
                            catch (JSONException e) {
                                // JSON parsing error
                                e.printStackTrace();
                            }
                        }MyAdapter myAdapter = new MyAdapter();
                        listView.setAdapter(myAdapter);





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        // Handle error
                        Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);





// ===========================================================================================





    }
    //=============================== Creating my adapter ============================

public class MyAdapter extends BaseAdapter{

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(MainActivity.this.LAYOUT_INFLATER_SERVICE);
        View myView = layoutInflater.inflate(R.layout.item, null );


        TextView tvTitle = myView.findViewById(R.id.tvTitle);
        ImageView imageThumb = myView.findViewById(R.id.imageThumb);

        HashMap<String,String> hashMap = arrayList.get(i);
        String title = hashMap.get("title");
        String video_id = hashMap.get("video_id");

        tvTitle.setText(title);

        String url =  "https://img.youtube.com/vi/"+video_id+"/0.jpg";
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.cooper)
                .error(R.drawable.cooper)
                .into(imageThumb);



        return myView;
    }
}



    //=============================== Creating my adapter ============================

}