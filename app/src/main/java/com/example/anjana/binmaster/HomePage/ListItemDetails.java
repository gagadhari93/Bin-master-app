package com.example.anjana.binmaster.HomePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anjana.binmaster.LocationPicker;
import com.example.anjana.binmaster.LoginActivity;
import com.example.anjana.binmaster.MySingleton;
import com.example.anjana.binmaster.R;
import com.example.anjana.binmaster.RegSuccessPage;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ListItemDetails extends AppCompatActivity {

    String url="http://192.168.8.100:8000/api/itemClick";

    SharedPreferences prefs;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_details);

        prefs=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor=prefs.edit();


        Bundle bundle = getIntent().getExtras();
        final int id = bundle.getInt("id");


        final TextView tvev1 = (TextView) findViewById(R.id.ev1);
        final  TextView tvev2 = (TextView) findViewById(R.id.ev2);
        final TextView tvev3 = (TextView) findViewById(R.id.ev3);
        final TextView tvev4 = (TextView) findViewById(R.id.ev4);
        final TextView tvev5 = (TextView) findViewById(R.id.ev5);
        final TextView tvev6 = (TextView) findViewById(R.id.ev6);
        final TextView tvrv1 = (TextView) findViewById(R.id.rv1);
        final TextView tvrv2 = (TextView) findViewById(R.id.rv2);
        final TextView tvrv3 = (TextView) findViewById(R.id.rv3);
        final TextView tvrv4 = (TextView) findViewById(R.id.rv4);
        final TextView tvrv5 = (TextView) findViewById(R.id.rv5);
        final TextView tvrv6 = (TextView) findViewById(R.id.rv6);
        final TextView state = (TextView) findViewById(R.id.state);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    tvev1.setText(""+jsonObject.getInt("expectedOrganicQuantity"));
                    tvev2.setText(""+jsonObject.getInt("expectedPlasticQuantity"));
                    tvev3.setText(""+jsonObject.getInt("expectedPaperQuantity"));
                    tvev4.setText(""+jsonObject.getInt("expectedGlassQuantity"));
                    tvev5.setText(""+jsonObject.getInt("expectedMetalQuantity"));
                    tvev6.setText(""+jsonObject.getInt("expectedElectronicQuantity"));
                    tvrv1.setText(""+jsonObject.getInt("realOrganicQuantity"));
                    tvrv2.setText(""+jsonObject.getInt("realPlasticQuantity"));
                    tvrv3.setText(""+jsonObject.getInt("realPaperQuantity"));
                    tvrv4.setText(""+jsonObject.getInt("realGlassQuantity"));
                    tvrv5.setText(""+jsonObject.getInt("realMetalQuantity"));
                    tvrv6.setText(""+jsonObject.getInt("realElectronicQuantity"));
                    state.setText(""+jsonObject.getInt("state"));


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
        MySingleton.getInstance(ListItemDetails.this).addToRequestQueue(stringRequest);

    }
}
