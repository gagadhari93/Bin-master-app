package com.example.anjana.binmaster;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anjana.binmaster.HomePage.HomePage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ComplainDetails extends AppCompatActivity {


    String url="http://192.168.8.102:8000/api/getComplainDetails";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_details);

        Bundle bundle = getIntent().getExtras();
        final int complainId = bundle.getInt("id");

        final TextView tvComplain = (TextView) findViewById(R.id.txtComplain);
        final  TextView tvReply= (TextView) findViewById(R.id.txtReply);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray=new JSONArray(response);

                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String complain=""+jsonObject.get("description");
                    String reply=""+jsonObject.get("reply");


                    if(reply.equals("null")){
                        tvComplain.setText(complain);
                        tvReply.setText("Not answered yet");
                        tvReply.setTextColor(Color.rgb(194,24,7));

                    }else {
                        tvComplain.setText(complain);
                        tvReply.setText(reply);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ComplainDetails.this,"Type Your Complain",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();

                params.put("complainId",""+complainId);


                return params;
            }
        };



        MySingleton.getInstance(ComplainDetails.this).addToRequestQueue(stringRequest);

    }
}
