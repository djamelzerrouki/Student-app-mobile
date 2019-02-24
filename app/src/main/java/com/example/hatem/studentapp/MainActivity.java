package com.example.hatem.studentapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    List<String> nameNote=new ArrayList<>();
    Button b ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRequestQueue = Volley.newRequestQueue(this);
        fetchJsonResponse();

         b= findViewById(R.id.emploi);
         b.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://10.42.0.1/etu/"));
                 startActivity(i);
             }
         });
        final ListView listview = (ListView) findViewById(R.id.listview);


        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, nameNote);
        listview.setAdapter(adapter);



    }




    private void fetchJsonResponse() {
        // Pass second argument as "null" for GET requests
        JsonArrayRequest req =  new JsonArrayRequest(
                Request.Method.GET,
                "http://10.42.0.1/etu/android/notes.php?id=yacine",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Do something with response
                        //mTextView.setText(response.toString());
                        Log.i("khaled1",response.toString());
                        // Process the JSON
                        try{

                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                JSONObject student = response.getJSONObject(i);

                                Log.i("khaled3",student.toString());
                                // Get the current student (json object) data
                                String name = student.getString("name");
                                String note = student.getString("note");

                                nameNote.add(name+"\t"+note);
                                Log.i("khaled2",nameNote.toString());
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred

                    }
                }
        );


        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }



}

