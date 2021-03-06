package com.example.anjana.binmaster;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Points extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String uId;
    String url="http://192.168.8.102:8000/api/viewPoints";
    String totalRupees,remaining,redeem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);


        prefs=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor=prefs.edit();
        uId=prefs.getString("uId",null);


        final TextView tvTotalRupees = (TextView) findViewById(R.id.txtTotalRs);
        final  TextView tvRemaining= (TextView) findViewById(R.id.txtRemaining);
        final TextView tvRedeem = (TextView) findViewById(R.id.txtConverted);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    totalRupees=""+jsonObject.get("totalRupees");
                    remaining  =""+jsonObject.get("remaining");
                     redeem=""+jsonObject.get("redeem");
                    tvTotalRupees.setText(totalRupees);
                    tvRemaining.setText(remaining);
                    tvRedeem.setText(redeem);



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

                params.put("uId",uId);
                return params;
            }
        };


        MySingleton.getInstance(Points.this).addToRequestQueue(stringRequest);

    }
}
