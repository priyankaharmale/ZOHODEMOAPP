package com.hnweb.demo.atticzipper;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

/**
 * Created by hnwebmarketing on 21/03/2018.
 */
public class InterNachiLoginActivity extends AppCompatActivity {

    Button InternachiSubmit;
    SharedPreferences.Editor editor;
    ProgressDialog pd;

    ImageView iv_memberId, iv_lastName, iv_email;
    String Internachinumber, InternachiLastName,  InternachiEmail,internachimemberNo, lastname, email,isFirstLogin;
    EditText MemberNo, LastName, et_email;
    TextView DashboardExit;
    String userId;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internachi_login_layout);
//==================================================================================================
        /*Initialize Components*/
        MemberNo = (EditText) findViewById(R.id.et_member_no);
        LastName = (EditText) findViewById(R.id.et_last_name);
        et_email = (EditText) findViewById(R.id.et_email_id);
        InternachiSubmit = (Button) findViewById(R.id.btn_submit);

        iv_memberId = (ImageView) findViewById(R.id.iv_memberNo);
        iv_lastName = (ImageView) findViewById(R.id.iv_lastName);
        iv_email = (ImageView) findViewById(R.id.iv_email);

        DashboardExit = (TextView) findViewById(R.id.tv_dashboardform_exit);
//==================================================================================================
        /*SharedPreferences objecs*/
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        Internachinumber = pref.getString("internachinumber", "internachinovalue");
        //Log.e("profile-",Internachinumber);
        InternachiLastName = pref.getString("internachilastname", "internachilastvalue");
        InternachiEmail = pref.getString("internachilastemail", "internachiemailvalue");

         userId = pref.getString(AppConstants.KEY_Id,null);
        //Log.e("sharadpref_UserID-",InternachiLastName);
        /*if SharedPreferences values are available then we put this data into edittext*/
        System.out.println("Internachinumber" + Internachinumber);
        if (Internachinumber.equals("internachinovalue")) {
            Log.d("member-no", "member-no");
        } else {
            MemberNo.setText(Internachinumber);
        }

        if (InternachiLastName.equals("internachilastvalue")) {
            Log.d("member-lastname", "member-lastname");
        } else {
            LastName.setText(InternachiLastName);
        }
        if (InternachiEmail.equals("internachiemailvalue")) {
            Log.d("member-email", "member-email");
        } else {
            et_email.setText(InternachiEmail);
        }
//==================================================================================================
        iv_memberId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemberNo.setText("");
            }
        });
        iv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_email.setText("");
            }
        });
        iv_lastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LastName.setText("");
            }
        });
        /*Onclick Event of Submit Button*/

        InternachiSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*validate the either one*/
                //if (MemberNo.getText().toString().equals("") && LastName.getText().toString().equals("")){
                ValidationMethods vm = new ValidationMethods();
                if (MemberNo.getText().toString().equals("")) {
                    MemberNo.requestFocus();
                    MemberNo.setError("Enter Internachi Number");
                    return;
                } else if (LastName.getText().toString().equals("")) {
                    LastName.requestFocus();
                    LastName.setError("Enter Last Name");
                    return;
                } else if (!vm.isValidEmail(et_email.getText().toString())) {
                    et_email.setError("Enter Valid Email-Id");
                    et_email.requestFocus();
                    return;
                } else {
                    if (Internachinumber.equals("internachinovalue")) {
                        Log.d("member-no", "member-no");

                        internachimemberNo = MemberNo.getText().toString().trim();
                        lastname = LastName.getText().toString().trim();
                        email = et_email.getText().toString().trim();
                        Log.d("Data", "Internachi member No is :- " + internachimemberNo + " Last Name is :- " + lastname);
                    /*Values puts into SharedPreferences Editor*/
                        editor.putString("internachinumber", internachimemberNo);
                        editor.putString("internachilastname", lastname);
                        editor.putString("internachilastemail", email);
                        // Save the changes in SharedPreferences
                        editor.commit(); // commit changes

                        Intent i = new Intent(InterNachiLoginActivity.this, InterNachiActivity.class);
                        startActivity(i);
                        finish();
                        Log.d("member-no", "member-no");
                    } else {
                        internachimemberNo = MemberNo.getText().toString().trim();
                        lastname = LastName.getText().toString().trim();
                        email = et_email.getText().toString().trim();
                        Log.d("Data", "Internachi member No is :- " + internachimemberNo + " Last Name is :- " + lastname);
                    /*Values puts into SharedPreferences Editor*/
                        editor.putString("internachinumber", internachimemberNo);
                        editor.putString("internachilastname", lastname);
                        editor.putString("internachilastemail", email);
                        // Save the changes in SharedPreferences
                        editor.commit(); // commit changes

                        addRecord(internachimemberNo, lastname, email);
                    /*Clear Edittext Values*/
                        MemberNo.setText("");
                        LastName.setText("");
                        et_email.setText("");
                    }

                }
            }
        });
//==================================================================================================
        DashboardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InterNachiLoginActivity.this, SplashActivity.class);
                startActivity(i);
                finish();
            }
        });
//==================================================================================================
    }

    private void addRecord(final String internachimemberNo, final String lastname, final String email) {

        pd = ProgressDialog.show(InterNachiLoginActivity.this, null, "Please wait....", false, false);

        String url="https://crm.zoho.com/crm/private/xml/Leads/updateRecords?";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
               // "https://creator.zoho.com/api/zoho_jeff1135/xml/internachi-referral-page/form/InterNACHIForm/record/update?",
                "https://crm.zoho.com/crm/private/xml/Leads/updateRecords?",


                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("res12345" + response);


                        System.out.println("responsde" + response);
                        XmlToJson xmlToJson = new XmlToJson.Builder(response).build();

// convert to a JSONObject
                        try {
                            JSONObject jsonObject = xmlToJson.toJson();
                            System.out.println("JSON dsfd1" + jsonObject.toString());

                            JSONObject jsonObjectresp = jsonObject.getJSONObject("response");
                            JSONObject jsonObjectresulet = jsonObjectresp.getJSONObject("result");
                            message =jsonObjectresulet.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("response...." + response);

                        if(message.equals("Record(s) already exists"))
                        {

                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_LONG).show();

                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:

                                            dialog.dismiss();
                                    }
                                }
                            };
                            AlertDialog.Builder builder = new AlertDialog.Builder(InterNachiLoginActivity.this);
                            builder.setTitle("Email Already Exists");
                            builder.setCancelable(false);
                            builder.setMessage("").setPositiveButton("OK", dialogClickListener).show();

                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Data Update Successfully..", Toast.LENGTH_LONG).show();

                            pd.dismiss();

                            Intent i = new Intent(InterNachiLoginActivity.this, InterNachiActivity.class);
                            startActivity(i);
                            finish();


                    }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println("jsonexeption" + error.toString());
                        pd.dismiss();
                        String reason = AppUtils.getVolleyError(InterNachiLoginActivity.this, error);
                        showAlertDialog(reason);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("newFormat", "2");
                    params.put("authtoken", "f09911e450cac4cf373c2920f14f7429");  //fa8743c9c718294243433a52cdb6cdf7
                    params.put("scope", "crmapi");
                    params.put("wfTrigger", "true");
                    String postData="<Leads>" +
                            "<row no=\"1\">" +
                            "<FL val=\"membership ID\">"+internachimemberNo+"</FL>" +
                            "<FL val=\"Last Name\">"+lastname+"</FL>" +
                            "<FL val=\"Email\">"+email+"</FL>" +
                            "<FL val=\"Internachi Member Number\">"+internachimemberNo + "  --  (" + lastname + ")"+"</FL>"+
                            "</row>" +
                            "</Leads>";
                    params.put("xmlData",postData);
                    params.put("id", userId);


                    System.out.println("Usser_id"+userId);




                } catch (Exception e) {
                    System.out.println("atticerror" + e.toString());
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

    //==================================================================================================
    private void showAlertDialog(String errorMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(InterNachiLoginActivity.this);
        builder.setTitle("Message")
                .setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
//==================================================================================================
}
