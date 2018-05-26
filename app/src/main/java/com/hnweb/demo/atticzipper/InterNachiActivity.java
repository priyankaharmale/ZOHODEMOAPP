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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hnweb.demo.atticzipper.R.id.et_zipcode;

/**
 * Created by hnwebmarketing on 6/6/2017.
 */
public class InterNachiActivity extends AppCompatActivity {

    String message;
    JSONObject jsonObjectresulet;
    Button InternachiFormSubmit;
    SharedPreferences.Editor editor, editorcalculator;
    String Internachinumber, InternachiLastName, InternachiEmail, internachimemberNo, lastname, isFirstLogin;
    String AreaA1, AreaA2, RValueR1, RValueR2, TotalArea, TotalRValue, TotalUFactor, TotalUA;
    String CalAreaA1, CalAreaA2, CalRValueR1, CalRValueR2, CalTotalArea, CalTotalRValue, CalTotalUFactor, CalTotalUA;
    EditText InterncahiMemberNo, InterncahiFirstName, InterncahiLastName, InterncahiEmailId, InterncahiMbNo,
            InterncahiCity, InterncahiState, InterncahiWidth, InterncahiLength, InterncahiProduct, InterncahiPropertyAdd,
            Interncahiquestion, Interncahizipcode;
    ProgressDialog pd;
    TextView FormExit;
    String str_leadSource;
    Spinner Question_Spinner, Product_Spinner, HomeOwner_Spinner;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internachi_layout);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
//==================================================================================================
        /*Initialize Components*/
        InterncahiMemberNo = (EditText) findViewById(R.id.et_internachi_member_no_value);
        InterncahiFirstName = (EditText) findViewById(R.id.et_first_name);
        InterncahiLastName = (EditText) findViewById(R.id.et_last_name);
        InterncahiEmailId = (EditText) findViewById(R.id.et_email_id);
        InterncahiPropertyAdd = (EditText) findViewById(R.id.et_property_add);
        //Interncahiquestion = (EditText)findViewById(R.id.et_question);
        InterncahiMbNo = (EditText) findViewById(R.id.et_mobile_no);
        InterncahiCity = (EditText) findViewById(R.id.et_city);
        InterncahiState = (EditText) findViewById(R.id.et_state);
        InterncahiWidth = (EditText) findViewById(R.id.et_width);
        InterncahiLength = (EditText) findViewById(R.id.et_length);
        //InterncahiProduct = (EditText)findViewById(R.id.et_product_recommend);
        Interncahizipcode = (EditText) findViewById(et_zipcode);
        InternachiFormSubmit = (Button) findViewById(R.id.btn_interncahi_submit);

        FormExit = (TextView) findViewById(R.id.tv_interform_exit);

        Question_Spinner = (Spinner) findViewById(R.id.spinner_question);
        Product_Spinner = (Spinner) findViewById(R.id.spinner_product);
        HomeOwner_Spinner = (Spinner) findViewById(R.id.spinner_homeowner);
//==================================================================================================
        /*SharedPreferences objecs*/
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        Internachinumber = pref.getString("internachinumber", "internachinovalue");
        //Log.e("profile-",Internachinumber);
        InternachiLastName = pref.getString("internachilastname", "internachilastvalue");
        InternachiEmail = pref.getString("internachilastemail", "internachiemailvalue");
        //Log.e("sharadpref_UserID-",InternachiLastName);
        /*if SharedPreferences values are available then we put this data into edittext*/
        if (Internachinumber.equals("internachinovalue")) {
            Log.d("member-no", "member-no");
        } else {
            InterncahiMemberNo.setText(Internachinumber + "  --  (" + InternachiLastName + ")");
        }
        isFirstLogin = pref.getString("internachiFirstTime", null);
        System.out.println("LoingIs" + isFirstLogin);

        if (InternachiLastName.equals("internachilastvalue")) {
            Log.d("member-email", "member-email");
        } else {
            InterncahiLastName.setText(InternachiLastName);
        }
//==================================================================================================
        /*SharedPreferences Calculator objecs*/
        SharedPreferences prefcalculator = getApplicationContext().getSharedPreferences("MyPrefCalculator", MODE_PRIVATE);
        editorcalculator = prefcalculator.edit();
        AreaA1 = prefcalculator.getString("areaa1", "areaa1");
        Log.e("calpref_AraA1", AreaA1);
        AreaA2 = prefcalculator.getString("areaa2", "areaa2");
        Log.e("calpref_AreaA2", AreaA2);
        RValueR1 = prefcalculator.getString("rvalue1", "rvalue1");
        Log.e("calpref_RValueR1", RValueR1);
        RValueR2 = prefcalculator.getString("rvalue2", "rvalue2");
        Log.e("calpref_RValueR2", RValueR2);
        TotalArea = prefcalculator.getString("totalarea", "totalarea");
        Log.e("calpref_TotalArea", TotalArea);
        TotalRValue = prefcalculator.getString("totalrvalue", "totalrvalue");
        Log.e("calpref_TotalRValue", TotalRValue);
        TotalUFactor = prefcalculator.getString("totalufactor", "totalufactor");
        Log.e("calpref_TotalUFactor", TotalUFactor);
        TotalUA = prefcalculator.getString("totalua", "totalua");
        Log.e("calpref_TotalUA", TotalUA);
//==================================================================================================
        List<String> listquestion = new ArrayList<String>();
        listquestion.add("Select any Answer");
        listquestion.add("Attic ladder cover");
        listquestion.add("Attic scuttle hole");
        listquestion.add("Crawl space cover");
        listquestion.add("Knee wall cover");
        listquestion.add("Other");

        ArrayAdapter<String> questionadapter =
                new ArrayAdapter<String>(InterNachiActivity.this, R.layout.support_simple_spinner_dropdown_item, listquestion);
        questionadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Question_Spinner.setAdapter(questionadapter);

        List<String> listproduct = new ArrayList<String>();
        listproduct.add("Select any Product");
        listproduct.add("az25559    25 x 55 x 9");
        listproduct.add("az255513 25 x 55 x 13");
        listproduct.add("az306013 30 x 60 x 13");
        listproduct.add("azScut243616 24 x 36 x 16");
        listproduct.add("azScut243316 24 x 33 x 16");
        listproduct.add("azScut242416  24 x 24 x 16");
        listproduct.add("azCrawl24366 24 x 36 x 6");

        ArrayAdapter<String> productadapter =
                new ArrayAdapter<String>(InterNachiActivity.this, R.layout.support_simple_spinner_dropdown_item, listproduct);
        productadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Product_Spinner.setAdapter(productadapter);

        List<String> listhomeowner = new ArrayList<String>();
        listhomeowner.add("Select option");
        listhomeowner.add("Yes");
        listhomeowner.add("No");

        ArrayAdapter<String> homeowneradapter =
                new ArrayAdapter<String>(InterNachiActivity.this, R.layout.support_simple_spinner_dropdown_item, listhomeowner);
        homeowneradapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        HomeOwner_Spinner.setAdapter(homeowneradapter);
//==================================================================================================
        // Spinner click listener
        Question_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), Find_Crave_Spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Product_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), AgeFrom.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        HomeOwner_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), AgeTo.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//==================================================================================================
        /*Onclick Event of InterNACHI Form Submit Button*/
        InternachiFormSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidationMethods vm = new ValidationMethods();
                if (!vm.isValidName(InterncahiFirstName.getText().toString())) {
                    InterncahiFirstName.setError("Enter Valid First Name");
                    InterncahiFirstName.requestFocus();
                    return;
                } else if (!vm.isValidName(InterncahiLastName.getText().toString())) {
                    InterncahiLastName.setError("Enter Valid Last Name");
                    InterncahiLastName.requestFocus();
                    return;
                } else if (InterncahiMemberNo.getText().toString().equals("")) {
                    InterncahiMemberNo.setError("Enter Valid InterNachi Number");
                    InterncahiMemberNo.requestFocus();
                    return;
                } else if (!vm.isValidEmail(InterncahiEmailId.getText().toString())) {
                    InterncahiEmailId.setError("Enter Valid Email-Id");
                    InterncahiEmailId.requestFocus();
                    return;
                } else if (isFirstLogin.equals("2")) {
                    if (InterncahiEmailId.getText().toString().equals(InternachiEmail)) {
                        /*Toast.makeText(InterNachiActivity.this,"Please Enter the another Email Id",Toast.LENGTH_SHORT).show();
                        return;*/
                        showAlertDialog("Please Enter the another Email Id");
                        return;
                    } else if (!vm.isValidLocation(InterncahiPropertyAdd.getText().toString())) {
                        InterncahiPropertyAdd.setError("Enter Valid Property Address");
                        InterncahiPropertyAdd.requestFocus();
                        return;
                    }/*else if (!vm.isValidLocation(Interncahiquestion.getText().toString())) {
                    Interncahiquestion.setError("Enter Valid Answer");
                    Interncahiquestion.requestFocus();
                    return;
                }*/ else if (!vm.isValidLocation(InterncahiMbNo.getText().toString())) {
                        InterncahiMbNo.setError("Enter Valid Mobile Number");
                        InterncahiMbNo.requestFocus();
                        return;
                    } else if (!vm.isValidName(InterncahiCity.getText().toString())) {
                        InterncahiCity.setError("Enter Valid City");
                        InterncahiCity.requestFocus();
                        return;
                    } else if (!vm.isValidName(InterncahiState.getText().toString())) {
                        InterncahiState.setError("Enter Valid State");
                        InterncahiState.requestFocus();
                        return;
                    } else if (!vm.isValidLocation(InterncahiWidth.getText().toString())) {
                        InterncahiWidth.setError("Enter Valid Width");
                        InterncahiWidth.requestFocus();
                        return;
                    } else if (!vm.isValidLocation(InterncahiLength.getText().toString())) {
                        InterncahiLength.setError("Enter Valid Length");
                        InterncahiLength.requestFocus();
                        return;
                    } else if (!vm.isValidLocation(Interncahizipcode.getText().toString())) {
                        Interncahizipcode.setError("Enter Valid Zip Code");
                        Interncahizipcode.requestFocus();
                        return;
                    }/*else if (!vm.isValidLocation(InterncahiProduct.getText().toString())) {
                    InterncahiProduct.setError("Enter Valid Product");
                    InterncahiProduct.requestFocus();
                    return;
                }*/ else {

                        if (Question_Spinner.getSelectedItem().toString().equals("Select any Answer")) {
                            Toast.makeText(InterNachiActivity.this, "Please Select a Question", Toast.LENGTH_SHORT).show();
                        } else if (Product_Spinner.getSelectedItem().toString().equals("Select any Product")) {
                            Toast.makeText(InterNachiActivity.this, "Please Choose Product Recommendation", Toast.LENGTH_SHORT).show();
                        } else if (HomeOwner_Spinner.getSelectedItem().toString().equals("Select option")) {
                            Toast.makeText(InterNachiActivity.this, "Please Select Does homeowner want coupon?", Toast.LENGTH_SHORT).show();
                        } else {

                            final String internachino = InterncahiMemberNo.getText().toString();
                            final String firstname = InterncahiFirstName.getText().toString();
                            final String lastname = InterncahiLastName.getText().toString();
                            final String email = InterncahiEmailId.getText().toString();
                            final String address = InterncahiPropertyAdd.getText().toString();
                            //String answer = Interncahiquestion.getText().toString();
                            final String answernew = Question_Spinner.getSelectedItem().toString();
                            final String mbno = InterncahiMbNo.getText().toString();
                            final String city = InterncahiCity.getText().toString();
                            final String state = InterncahiState.getText().toString();
                            final String width = InterncahiWidth.getText().toString();
                            final String length = InterncahiLength.getText().toString();
                            //String product = InterncahiProduct.getText().toString();
                            final String productnew = Product_Spinner.getSelectedItem().toString();
                            final String homeownernew = HomeOwner_Spinner.getSelectedItem().toString();
                            final String zipcode = Interncahizipcode.getText().toString();

                            //AreaA1,AreaA2,RValueR1,RValueR2,TotalArea,TotalRValue,TotalUFactor,TotalUA
                            //areaa1,areaa2,rvalue1,rvalue2,totalarea,totalrvalue,totalufactor,totalua
                            //CalAreaA1,CalAreaA2,CalRValueR1,CalRValueR2,CalTotalArea,CalTotalRValue,CalTotalUFactor,CalTotalUA;

                            if (AreaA1.equals("areaa1")) {
                                Log.d("areaa1", AreaA1);
                                CalAreaA1 = "";
                            } else {
                                Log.e("areaa1", AreaA1);
                                CalAreaA1 = AreaA1;
                            }

                            if (AreaA2.equals("areaa2")) {
                                Log.d("areaa2", AreaA2);
                                CalAreaA2 = "";
                            } else {
                                Log.e("areaa2", AreaA2);
                                CalAreaA2 = AreaA2;
                            }

                            if (RValueR1.equals("rvalue1")) {
                                Log.d("rvalue1", RValueR1);
                                CalRValueR1 = "";
                            } else {
                                Log.e("rvalue1", RValueR1);
                                CalRValueR1 = RValueR1;
                            }

                            if (RValueR2.equals("rvalue2")) {
                                Log.d("rvalue2", RValueR2);
                                CalRValueR2 = "";
                            } else {
                                Log.e("rvalue2", RValueR2);
                                CalRValueR2 = RValueR2;
                            }

                            if (TotalArea.equals("totalarea")) {
                                Log.d("totalarea", TotalArea);
                                CalTotalArea = "";
                            } else {
                                Log.e("totalarea", TotalArea);
                                CalTotalArea = TotalArea;
                            }

                            if (TotalRValue.equals("totalrvalue")) {
                                Log.d("totalrvalue", TotalRValue);
                                CalTotalRValue = "";
                            } else {
                                Log.e("totalrvalue", TotalRValue);
                                CalTotalRValue = TotalRValue;
                            }

                            if (TotalUFactor.equals("totalufactor")) {
                                Log.d("totalufactor", TotalUFactor);
                                CalTotalUFactor = "";
                            } else {
                                Log.e("totalufactor", TotalUFactor);
                                CalTotalUFactor = TotalUFactor;
                            }

                            if (TotalUA.equals("totalua")) {
                                Log.d("totalua", TotalUA);
                                CalTotalUA = "";
                            } else {
                                Log.e("totalua", TotalUA);
                                CalTotalUA = TotalUA;
                            }

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(InterNachiActivity.this);
                            builder1.setMessage("Did you use the calculator at this clients home?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            addRecord(internachino, firstname, lastname, email, mbno, city,
                                                    state, width, length, productnew, address, answernew,
                                                    homeownernew, zipcode, CalAreaA1, CalAreaA2, CalRValueR1,
                                                    CalRValueR2, CalTotalArea, CalTotalRValue, CalTotalUFactor,
                                                    CalTotalUA, Internachinumber);

                                            dialog.cancel();
                                        }
                                    });
                            builder1.setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            addRecord(internachino, firstname, lastname, email, mbno, city,
                                                    state, width, length, productnew, address, answernew,
                                                    homeownernew, zipcode, "", "", "", "", "", "", "", "", Internachinumber);

                                            dialog.cancel();

                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }

                    }
                } else if (isFirstLogin.equals("1")) {
                    if (!vm.isValidLocation(InterncahiPropertyAdd.getText().toString())) {
                        InterncahiPropertyAdd.setError("Enter Valid Property Address");
                        InterncahiPropertyAdd.requestFocus();
                        return;
                    }/*else if (!vm.isValidLocation(Interncahiquestion.getText().toString())) {
                    Interncahiquestion.setError("Enter Valid Answer");
                    Interncahiquestion.requestFocus();
                    return;
                }*/ else if (!vm.isValidLocation(InterncahiMbNo.getText().toString())) {
                        InterncahiMbNo.setError("Enter Valid Mobile Number");
                        InterncahiMbNo.requestFocus();
                        return;
                    } else if (!vm.isValidName(InterncahiCity.getText().toString())) {
                        InterncahiCity.setError("Enter Valid City");
                        InterncahiCity.requestFocus();
                        return;
                    } else if (!vm.isValidName(InterncahiState.getText().toString())) {
                        InterncahiState.setError("Enter Valid State");
                        InterncahiState.requestFocus();
                        return;
                    } else if (!vm.isValidLocation(InterncahiWidth.getText().toString())) {
                        InterncahiWidth.setError("Enter Valid Width");
                        InterncahiWidth.requestFocus();
                        return;
                    } else if (!vm.isValidLocation(InterncahiLength.getText().toString())) {
                        InterncahiLength.setError("Enter Valid Length");
                        InterncahiLength.requestFocus();
                        return;
                    } else if (!vm.isValidLocation(Interncahizipcode.getText().toString())) {
                        Interncahizipcode.setError("Enter Valid Zip Code");
                        Interncahizipcode.requestFocus();
                        return;
                    }/*else if (!vm.isValidLocation(InterncahiProduct.getText().toString())) {
                    InterncahiProduct.setError("Enter Valid Product");
                    InterncahiProduct.requestFocus();
                    return;
                }*/ else {

                        if (Question_Spinner.getSelectedItem().toString().equals("Select any Answer")) {
                            Toast.makeText(InterNachiActivity.this, "Please Select a Question", Toast.LENGTH_SHORT).show();
                        } else if (Product_Spinner.getSelectedItem().toString().equals("Select any Product")) {
                            Toast.makeText(InterNachiActivity.this, "Please Choose Product Recommendation", Toast.LENGTH_SHORT).show();
                        } else if (HomeOwner_Spinner.getSelectedItem().toString().equals("Select option")) {
                            Toast.makeText(InterNachiActivity.this, "Please Select Does homeowner want coupon?", Toast.LENGTH_SHORT).show();
                        } else {

                            final String internachino = InterncahiMemberNo.getText().toString();
                            final String firstname = InterncahiFirstName.getText().toString();
                            final String lastname = InterncahiLastName.getText().toString();
                            final String email = InterncahiEmailId.getText().toString();
                            final String address = InterncahiPropertyAdd.getText().toString();
                            //String answer = Interncahiquestion.getText().toString();
                            final String answernew = Question_Spinner.getSelectedItem().toString();
                            final String mbno = InterncahiMbNo.getText().toString();
                            final String city = InterncahiCity.getText().toString();
                            final String state = InterncahiState.getText().toString();
                            final String width = InterncahiWidth.getText().toString();
                            final String length = InterncahiLength.getText().toString();
                            //String product = InterncahiProduct.getText().toString();
                            final String productnew = Product_Spinner.getSelectedItem().toString();
                            final String homeownernew = HomeOwner_Spinner.getSelectedItem().toString();
                            final String zipcode = Interncahizipcode.getText().toString();

                            //AreaA1,AreaA2,RValueR1,RValueR2,TotalArea,TotalRValue,TotalUFactor,TotalUA
                            //areaa1,areaa2,rvalue1,rvalue2,totalarea,totalrvalue,totalufactor,totalua
                            //CalAreaA1,CalAreaA2,CalRValueR1,CalRValueR2,CalTotalArea,CalTotalRValue,CalTotalUFactor,CalTotalUA;

                            if (AreaA1.equals("areaa1")) {
                                Log.d("areaa1", AreaA1);
                                CalAreaA1 = "";
                            } else {
                                Log.e("areaa1", AreaA1);
                                CalAreaA1 = AreaA1;
                            }

                            if (AreaA2.equals("areaa2")) {
                                Log.d("areaa2", AreaA2);
                                CalAreaA2 = "";
                            } else {
                                Log.e("areaa2", AreaA2);
                                CalAreaA2 = AreaA2;
                            }

                            if (RValueR1.equals("rvalue1")) {
                                Log.d("rvalue1", RValueR1);
                                CalRValueR1 = "";
                            } else {
                                Log.e("rvalue1", RValueR1);
                                CalRValueR1 = RValueR1;
                            }

                            if (RValueR2.equals("rvalue2")) {
                                Log.d("rvalue2", RValueR2);
                                CalRValueR2 = "";
                            } else {
                                Log.e("rvalue2", RValueR2);
                                CalRValueR2 = RValueR2;
                            }

                            if (TotalArea.equals("totalarea")) {
                                Log.d("totalarea", TotalArea);
                                CalTotalArea = "";
                            } else {
                                Log.e("totalarea", TotalArea);
                                CalTotalArea = TotalArea;
                            }

                            if (TotalRValue.equals("totalrvalue")) {
                                Log.d("totalrvalue", TotalRValue);
                                CalTotalRValue = "";
                            } else {
                                Log.e("totalrvalue", TotalRValue);
                                CalTotalRValue = TotalRValue;
                            }

                            if (TotalUFactor.equals("totalufactor")) {
                                Log.d("totalufactor", TotalUFactor);
                                CalTotalUFactor = "";
                            } else {
                                Log.e("totalufactor", TotalUFactor);
                                CalTotalUFactor = TotalUFactor;
                            }

                            if (TotalUA.equals("totalua")) {
                                Log.d("totalua", TotalUA);
                                CalTotalUA = "";
                            } else {
                                Log.e("totalua", TotalUA);
                                CalTotalUA = TotalUA;
                            }

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(InterNachiActivity.this);
                            builder1.setMessage("Did you use the calculator at this clients home?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            addRecord(internachino, firstname, lastname, email, mbno, city,
                                                    state, width, length, productnew, address, answernew,
                                                    homeownernew, zipcode, CalAreaA1, CalAreaA2, CalRValueR1,
                                                    CalRValueR2, CalTotalArea, CalTotalRValue, CalTotalUFactor,
                                                    CalTotalUA, Internachinumber);

                                            dialog.cancel();
                                        }
                                    });
                            builder1.setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            addRecord(internachino, firstname, lastname, email, mbno, city,
                                                    state, width, length, productnew, address, answernew,
                                                    homeownernew, zipcode, "", "", "", "", "", "", "", "", Internachinumber);

                                            dialog.cancel();

                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }

                    }
                }
            }
        });
//==================================================================================================
        FormExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InterNachiActivity.this, SplashActivity.class);
                startActivity(i);
                finish();
            }
        });
//==================================================================================================
    }
//==================================================================================================
  /*  private void addRecord(final String internachino,final String firstname, final String lastname,
                           final String email, final String mbno,final String city,
                           final String state, final String width,final String length,
                           final String product,final String address,final String answer,
                           final String homeownernew,final String zipcode,
                           final String A1,final String A2,final String R1,
                           final String R2,final String Total_Area,final String RValue,
                           final String Ufactor,final String TotalUA, final String Internachinumber1) {

        pd = ProgressDialog.show(InterNachiActivity.this,null,"Please wait....",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                //"https://creator.zoho.com/api/arshads.bwd/json/idi-attic-zipper-info-please/form/IDI_Attic_Zipper_Info_Please/record/add/",
                "https://creator.zoho.com/api/zoho_jeff1135/xml/internachi-referral-page/form/InterNACHIForm/record/add/"
                ,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("res" + response);

                        Toast.makeText(getApplicationContext(), "Data Saved Successfully..", Toast.LENGTH_SHORT).show();

                        InterncahiMemberNo.setText("");
                        InterncahiFirstName.setText("");
                        InterncahiLastName.setText("");
                        InterncahiEmailId.setText("");
                        InterncahiPropertyAdd.setText("");
                        //Interncahiquestion.setText("");
                        InterncahiMbNo.setText("");
                        InterncahiCity.setText("");
                        InterncahiState.setText("");
                        InterncahiWidth.setText("");
                        InterncahiLength.setText("");
                        //InterncahiProduct.setText("");
                        Interncahizipcode.setText("");
                        Question_Spinner.setSelection(0);
                        Product_Spinner.setSelection(0);
                        HomeOwner_Spinner.setSelection(0);

                        CalAreaA1 = "";    CalAreaA2 = "";  CalRValueR1 = ""; CalRValueR2 = "";
                        CalTotalArea = ""; CalTotalRValue = ""; CalTotalUFactor = ""; CalTotalUA = "";

                        editor.putString("internachiFirstTime", "2");
                        editor.commit();
                        //areaa1,areaa2,rvalue1,rvalue2,totalarea,totalrvalue,totalufactor,totalua
                        editorcalculator.remove("areaa1");
                        editorcalculator.remove("areaa2");
                        editorcalculator.remove("rvalue1");
                        editorcalculator.remove("rvalue2");
                        editorcalculator.remove("totalarea");
                        editorcalculator.remove("totalrvalue");
                        editorcalculator.remove("totalufactor");
                        editorcalculator.remove("totalua");
                        editorcalculator.clear().commit();

                        pd.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println("jsonexeption" + error.toString());
                        pd.dismiss();
                        String reason = AppUtils.getVolleyError(InterNachiActivity.this, error);
                        showAlertDialog(reason);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {

                    params.put("authtoken", "09d889f47004b5bf91dc82bd33c42e2c");  //fa8743c9c718294243433a52cdb6cdf7
                    params.put("scope", "creatorapi");
                    params.put("Added_User", "1");
                    params.put("Added_Time", "01");
                    params.put("Modified_User", "1");
                    params.put("Modified_Time", "1");
                    params.put("Added_User_IP_Address", "1");
                    params.put("Modified_User_IP_Address", "1");

                    if(isFirstLogin.equals("1"))
                    {
                        params.put("lead_source", "new internachi member");
                    }else
                    {
                        params.put("lead_source", "new internachi homeowner");
                    }
                    params.put("First_Name", firstname);
                    params.put("Last_Name", lastname);
                    params.put("Email_Address", email);
                    params.put("Property_Address", address);
                    params.put("city", city);
                    params.put("state", state);
                    params.put("Internachi_Member_Number", internachino);
                    params.put("Is_this_a_scuttle_hole_attic_ladder_kneewall_or_crawl_space_cover", answer);
                    params.put("width", width);
                    params.put("Length_value", length);
                    params.put("product", product);
                    params.put("Mobile_Number", mbno);
                    params.put("does_homeowner_want_coupon", homeownernew);
                    params.put("Zip_Code", zipcode);
                    params.put("operating_system", "Android");

                    params.put("Attic_Square_Feet_Total", A1);
                    params.put("Attic_Door_Entry_Square_Feet", A2);
                    params.put("Attic_R_Value_Not_Including_Entry", R1);
                    params.put("Attic_Door_Entry_R_Value", R2);
                    params.put("Total_Area", Total_Area);
                    params.put("Equivalent_Total_R_Value", RValue);
                    params.put("Equivalent_Total_U_Factor", Ufactor);
                    params.put("Equivalent_Total_UA", TotalUA);
                    params.put("membership_ID",Internachinumber1);

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
*///==================================================================================================

    private void addRecord(final String internachino, final String firstname, final String lastname,
                           final String email, final String mbno, final String city,
                           final String state, final String width, final String length,
                           final String product, final String address, final String answer,
                           final String homeownernew, final String zipcode,
                           final String A1, final String A2, final String R1,
                           final String R2, final String Total_Area, final String RValue,
                           final String Ufactor, final String TotalUA, final String Internachinumber1) {


        if (isFirstLogin.equals("1")) {

            str_leadSource = "New internachi member";
        } else {
            str_leadSource = "New internachi homeowner";

        }

        pd = ProgressDialog.show(InterNachiActivity.this, null, "Please wait....", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                //"https://creator.zoho.com/api/arshads.bwd/json/idi-attic-zipper-info-please/form/IDI_Attic_Zipper_Info_Please/record/add/",
              /*  "https://creator.zoho.com/api/zoho_jeff1135/xml/internachi-referral-page/form/InterNACHIForm/record/add/"*/
                AppConstants.INSERTTDATAGREENBOX_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("res123......" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONObject jsonObject2 = jsonObject.getJSONObject("response");
                            JSONObject jsonObjectresp = jsonObject.getJSONObject("response");
                            jsonObjectresulet = jsonObjectresp.getJSONObject("result");
                            message = jsonObjectresulet.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("JSONException...", e.toString());
                        }
                        System.out.println("response...." + response);

                        if (message.equals("Record(s) already exists")) {
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(InterNachiActivity.this);
                            builder.setTitle("Email Already Exists");
                            builder.setCancelable(false);
                            builder.setMessage("").setPositiveButton("OK", dialogClickListener).show();

                        } else {
                            InterncahiMemberNo.setText("");
                            InterncahiFirstName.setText("");
                            InterncahiLastName.setText("");
                            InterncahiEmailId.setText("");
                            InterncahiPropertyAdd.setText("");
                            //Interncahiquestion.setText("");
                            InterncahiMbNo.setText("");
                            InterncahiCity.setText("");
                            InterncahiState.setText("");
                            InterncahiWidth.setText("");
                            InterncahiLength.setText("");
                            //InterncahiProduct.setText("");
                            Interncahizipcode.setText("");
                            Question_Spinner.setSelection(0);
                            Product_Spinner.setSelection(0);
                            HomeOwner_Spinner.setSelection(0);

                            CalAreaA1 = "";
                            CalAreaA2 = "";
                            CalRValueR1 = "";
                            CalRValueR2 = "";
                            CalTotalArea = "";
                            CalTotalRValue = "";
                            CalTotalUFactor = "";
                            CalTotalUA = "";

                            editor.putString("internachiFirstTime", "2");
                            editor.commit();
                            //areaa1,areaa2,rvalue1,rvalue2,totalarea,totalrvalue,totalufactor,totalua
                            editorcalculator.remove("areaa1");
                            editorcalculator.remove("areaa2");
                            editorcalculator.remove("rvalue1");
                            editorcalculator.remove("rvalue2");
                            editorcalculator.remove("totalarea");
                            editorcalculator.remove("totalrvalue");
                            editorcalculator.remove("totalufactor");
                            editorcalculator.remove("totalua");
                            editorcalculator.clear().commit();

                            Toast.makeText(getApplicationContext(), "Data Saved Successfully..", Toast.LENGTH_LONG).show();
                            try {
                                JSONObject jsonObjectrecordDetails = jsonObjectresulet.getJSONObject("recorddetail");
                                JSONArray jsonArrayFL = jsonObjectrecordDetails.getJSONArray("FL");
                                for (int j = 0; j < jsonArrayFL.length(); j++) {
                                    JSONObject jsonObjectpostion = jsonArrayFL.getJSONObject(j);
                                    String val = jsonObjectpostion.getString("val");
                                    if (val.equals("Id")) {

                                        String content = jsonObjectpostion.getString("content");

                                        if (isFirstLogin.equals("1")) {
                                            SharedPreferences.Editor editor = pref.edit();
                                            editor.putString(AppConstants.KEY_Id, content);
                                            System.out.println("ID123..." + content);
                                            editor.commit();
                                        }
                                    }
                                }
                            } catch (JSONException e) {

                            }

                            pd.dismiss();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println("jsonexeption" + error.toString());
                        pd.dismiss();
                        String reason = AppUtils.getVolleyError(InterNachiActivity.this, error);
                        showAlertDialog(reason);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Map<String, String> boolean_params = new HashMap<String, String>();
                try {

                   /* params.put("authtoken", "09d889f47004b5bf91dc82bd33c42e2c");  //fa8743c9c718294243433a52cdb6cdf7
                    params.put("scope", "creatorapi");
                    params.put("Added_User", "1");
                    params.put("Added_Time", "01");
                    params.put("Modified_User", "1");
                    params.put("Modified_Time", "1");
                    params.put("Added_User_IP_Address", "1");
                    params.put("Modified_User_IP_Address", "1");

                    if(isFirstLogin.equals("1"))
                    {
                        params.put("lead_source", "new internachi member");
                    }else
                    {
                        params.put("lead_source", "new internachi homeowner");
                    }
                    params.put("First_Name", firstname);
                    params.put("Last_Name", lastname);
                    params.put("Email_Address", email);
                    params.put("Property_Address", address);
                    params.put("city", city);
                    params.put("state", state);
                    params.put("Internachi_Member_Number", internachino);
                    params.put("Is_this_a_scuttle_hole_attic_ladder_kneewall_or_crawl_space_cover", answer);
                    params.put("width", width);
                    params.put("Length_value", length);
                    params.put("product", product);
                    params.put("Mobile_Number", mbno);
                    params.put("does_homeowner_want_coupon", homeownernew);
                    params.put("Zip_Code", zipcode);
                    params.put("operating_system", "Android");

                    params.put("Attic_Square_Feet_Total", A1);
                    params.put("Attic_Door_Entry_Square_Feet", A2);
                    params.put("Attic_R_Value_Not_Including_Entry", R1);
                    params.put("Attic_Door_Entry_R_Value", R2);
                    params.put("Total_Area", Total_Area);
                    params.put("Equivalent_Total_R_Value", RValue);
                    params.put("Equivalent_Total_U_Factor", Ufactor);
                    params.put("Equivalent_Total_UA", TotalUA);
                    params.put("membership_ID",Internachinumber1);*/


                    params.put("authtoken", "f09911e450cac4cf373c2920f14f7429");
                    params.put("scope", "crmapi");
                    params.put("newFormat", "1");
                    params.put("wfTrigger", "true");

                    String postData = "<Leads>\n" +
                            "<row no=\"1\">\n" +

                            "<FL val=\"Lead Source\">" + str_leadSource + "</FL>\n" +

                            "<FL val=\"First Name\">" + firstname + "</FL>\n" +
                            "<FL val=\"Last Name\">" + lastname + "</FL>\n" +
                            "<FL val=\"Email\">" + email + "</FL>\n" +

                            "<FL val=\"Property Address\">" + address + "</FL>\n" +
                            "<FL val=\"Internachi Member Number\">" + internachino + "</FL>\n" +
                            "<FL val=\"This Is A?\">" + answer + "</FL>\n" +
                            "<FL val=\"Width\">" + width + "</FL>\n" +
                            "<FL val=\"Length\">" + length + "</FL>\n" +
                            "<FL val=\"Product\">" + product + "</FL>\n" +

                            "<FL val=\"Does homeowner want coupon\">" + homeownernew + "</FL>\n" +
                            "<FL val=\"OperatingSystem\">" + "Android" + "</FL>\n" +
                            "<FL val=\"Attic Square Feet-Total\">" + A1 + "</FL>\n" +
                            "<FL val=\"Attic Door Entry Square Feet\">" + A2 + "</FL>\n" +
                            "<FL val=\"Attic R Value Not Including Entry\">" + R1 + "</FL>\n" +
                            "<FL val=\"Attic Door Entry R Value\">" + R2 + "</FL>\n" +
                            "<FL val=\"Total Area\">" + Total_Area + "</FL>\n" +
                            "<FL val=\"Equivalent Total R-Value\">" + RValue + "</FL>\n" +
                            "<FL val=\"Equivalent Total U-Factor\">" + Ufactor + "</FL>\n" +
                            "<FL val=\"Equivalent Total UA\">" + TotalUA + "</FL>\n" +
                            "<FL val=\"membership ID\">" + Internachinumber1 + "</FL>\n" +

                            "<FL val=\"City\">" + city + "</FL>\n" +
                            "<FL val=\"State\">" + state + "</FL>\n" +
                            "<FL val=\"Zip Code\">" + zipcode + "</FL>\n" +
                            "<FL val=\"Mobile\">" + mbno + "</FL>\n" +
                            // "<FL val=\"Phone\">" + landlineNo + "</FL>\n" +

                            "<FL val=\"Email Opt Out\">true</FL>\n" +
                            "<FL val=\"Description\">" + " " + "</FL>\n" +
                            "<FL val=\"Designation\">" + " " + "</FL>\n" +
                            "<FL val=\"Fax\">" + " " + "</FL>\n" +

                            "<FL val=\"Industry\">" + " " + "</FL>\n" +
                            "<FL val=\"Website\">" + " " + " </FL>\n" +
                            "<FL val=\"No of Employees\">" + " " + "</FL>\n" +
                            "<FL val=\"Annual Revenue\"> " + " " + "</FL>\n" +
                            "<FL val=\"Country\">" + " " + "</FL>\n" +


                            "</row>\n" +
                            "</Leads>"; // TODO get your final output
                    params.put("xmlData", postData);


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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(InterNachiActivity.this, SplashActivity.class);
        startActivity(i);
        this.finish();
    }

    //==================================================================================================
    private void showAlertDialog(String errorMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(InterNachiActivity.this);
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
