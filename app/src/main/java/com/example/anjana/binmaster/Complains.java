package com.example.anjana.binmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anjana.binmaster.HomePage.HomePage;
import com.example.anjana.binmaster.HomePage.ListItemDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Complains extends AppCompatActivity {

    String url="http://192.168.8.102:8000/api/sendComplain";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Button btnSendComplain;
    TextView txtComplain;


    String complainListUrl="http://192.168.8.102:8000/api/getComplainsList";
    List<String> dates;
    List<Integer> ids;
    ArrayAdapter<String> adapter;
    ListView complainList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complains);

        prefs=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor=prefs.edit();

        btnSendComplain=(Button)findViewById(R.id.btnSendComplain);
        txtComplain = (EditText)findViewById(R.id.txtComplins);


       final String  uId=prefs.getString("uId","");
       final String areaId=""+prefs.getInt("areaId",0);







        btnSendComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String complain=txtComplain.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            String res=""+jsonObject.get("message");

                            if(res.equals("Success")){
                                Toast.makeText(Complains.this,"Complain Sent!",Toast.LENGTH_LONG).show();
                                Intent i =new Intent(Complains.this, HomePage.class);
                                startActivity(i);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Complains.this,"Type Your Complain",Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();

                        params.put("txtComplain",complain);
                        params.put("uId",uId);
                        //params.put("areaId",areaId);

                        return params;
                    }
                };



                MySingleton.getInstance(Complains.this).addToRequestQueue(stringRequest);

            }
        });





        //complain list----------------------------------------------------------------------------------------


        complainList = (ListView)findViewById(R.id.complainList);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, complainListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dates = new ArrayList<String>();
                ids = new ArrayList<Integer>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0;i<jsonArray.length();i++)

                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        dates.add(jsonObject.getString("created_at"));
                        ids.add(jsonObject.getInt("id"));
                    }
                    adapter = new ArrayAdapter<String>(Complains.this,android.R.layout.simple_expandable_list_item_1,dates);
                    complainList.setAdapter(adapter);


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


        MySingleton.getInstance(Complains.this).addToRequestQueue(stringRequest);



        complainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedId = (int)ids.get(position);
                Intent intent = new Intent(Complains.this,ComplainDetails.class);
                intent.putExtra("id",selectedId);
                startActivity(intent);

            }
        });








    }
}
