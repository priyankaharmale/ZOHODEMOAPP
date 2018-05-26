package com.hnweb.demo.atticzipper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button submit, Cancel;
    EditText et_firstname,et_lastname,et_number,et_email,et_howmnay,et_home,et_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_firstname=(EditText) findViewById(R.id.et_firstname);
        et_lastname=(EditText)findViewById(R.id.et_lastname);
        et_number=(EditText) findViewById(R.id.et_number);
        et_email=(EditText)findViewById(R.id.et_emailid);
        et_howmnay=(EditText) findViewById(R.id.et_howmany);
        et_home=(EditText)findViewById(R.id.et_home);
        et_area=(EditText) findViewById(R.id.et_area);

        submit = (Button)findViewById(R.id.btn_submit);
        Cancel = (Button)findViewById(R.id.btn_cancel);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstname = et_firstname.getText().toString();
                String lastname = et_lastname.getText().toString();
                String number = et_number.getText().toString();
                String email = et_email.getText().toString();
                String howmnay = et_howmnay.getText().toString();
                String home = et_home.getText().toString();
                String area = et_area.getText().toString();



                addRecord(firstname,lastname,number,email,howmnay,home,area);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et_firstname.setText("");
                et_lastname.setText("");
                et_number.setText("");
                et_email.setText("");
                et_howmnay.setText("");
                et_home.setText("");
                et_area.setText("");

            }
        });

    }

    private void addRecord(final String firstname, final String lastname,
                           final String number, final String email,
                           final String howmnay, final String home,
                           final String area)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://creator.zoho.com/api/arshads.bwd/json/idi-attic-zipper-info-please/form/IDI_Attic_Zipper_Info_Please/record/add/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("res" + response);

                        Toast.makeText(getApplicationContext(), "Data Saved Successfully..", Toast.LENGTH_LONG).show();

                        et_firstname.setText("");
                        et_lastname.setText("");
                        et_number.setText("");
                        et_email.setText("");
                        et_howmnay.setText("");
                        et_home.setText("");
                        et_area.setText("");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println("jsonexeption" + error.toString());
                    }
                }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {

                    params.put("authtoken", "04382003954705cafcd6e009d473a888");
                    params.put("scope", "creatorapi");
                    params.put("Added User", "1");
                    params.put("Added Time", "01");
                    params.put("Last Modified User", "1");
                    params.put("Last Modified Time", "1");
                    params.put("Added User IP Address", "1");
                    params.put("Modified User IP Address", "1");
                    params.put("First_Name", firstname);
                    params.put("Last_Name", lastname);
                    params.put("Number", number);
                    params.put("Email", email);
                    params.put("How_many_attics_is_your_company_in_each_month_Approximately", howmnay);
                    params.put("Are_you_a_contractor_or_homeowner_or_other", home);
                    params.put("What_areas_of_Georgia_do_you_serve", area);



                } catch (Exception e) {
                    System.out.println("arsherror" + e.toString());
                }


                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

    }








}
