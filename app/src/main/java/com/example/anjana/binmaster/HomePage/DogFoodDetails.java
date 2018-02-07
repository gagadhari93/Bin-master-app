package com.example.anjana.binmaster.HomePage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anjana.binmaster.MySingleton;
import com.example.anjana.binmaster.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DogFoodDetails extends AppCompatActivity {

    String url="http://192.168.8.100:8000/api/dogFooditemClick";

    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_food_details);



        prefs=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor=prefs.edit();


        Bundle bundle = getIntent().getExtras();
        final int id = bundle.getInt("id");

        final TextView tvev1 = (TextView) findViewById(R.id.ev1);
        final TextView tvrv1 = (TextView) findViewById(R.id.rv1);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    tvev1.setText(""+jsonObject.getDouble("expectedDogFoodQuantity"));

                    tvrv1.setText(""+jsonObject.getDouble("realDogFoodQuantity"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("id",""+id);
                return params;
            }
        };
        MySingleton.getInstance(DogFoodDetails.this).addToRequestQueue(stringRequest);






    }
}
