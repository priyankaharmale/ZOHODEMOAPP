package com.hnweb.demo.atticzipper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hnwebmarketing on 7/11/2017.
 */
public class AttaicRCalculator extends AppCompatActivity {

    Button Result,Reset;
    EditText Area1,Area2,RValue1,RValue2;
    TextView TotalArea,EquivalentRValue,EquivalentUFactor,EquivalentUA,Exit;
    ImageView BackArrow;
    String AreaA1,AreaA2,RValueR1,RValueR2,CalTotal_Area,TotalRValue,TotalUFactor,TotalUA;
    SharedPreferences.Editor editorcalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attic_rcalculator);
//==================================================================================================
        /*SharedPreferences Calculator objecs*/
        SharedPreferences prefcalculator = getApplicationContext().getSharedPreferences("MyPrefCalculator", MODE_PRIVATE);
        editorcalculator = prefcalculator.edit();
        AreaA1 = prefcalculator.getString("areaa1","areaa1");
        Log.e("calpref_AraA1",AreaA1);
        AreaA2 = prefcalculator.getString("areaa2","areaa2");
        Log.e("calpref_AreaA2",AreaA2);
        RValueR1 = prefcalculator.getString("rvalue1","rvalue1");
        Log.e("calpref_RValueR1",RValueR1);
        RValueR2 = prefcalculator.getString("rvalue2","rvalue2");
        Log.e("calpref_RValueR2",RValueR2);
        CalTotal_Area = prefcalculator.getString("totalarea","totalarea");
        Log.e("calpref_CalTotalArea",CalTotal_Area);
        TotalRValue = prefcalculator.getString("totalrvalue","totalrvalue");
        Log.e("calpref_TotalRValue",TotalRValue);
        TotalUFactor = prefcalculator.getString("totalufactor","totalufactor");
        Log.e("calpref_TotalUFactor",TotalUFactor);
        TotalUA = prefcalculator.getString("totalua","totalua");
        Log.e("calpref_TotalUA",TotalUA);
        //areaa1,areaa2,rvalue1,rvalue2,totalarea,totalrvalue,totalufactor,totalua
//==================================================================================================
        Result = (Button)findViewById(R.id.btn_result);
        Reset = (Button)findViewById(R.id.btn_reset);

        Area1 = (EditText)findViewById(R.id.et_area1);
        Area2 = (EditText)findViewById(R.id.et_area2);
        RValue1 = (EditText)findViewById(R.id.et_rvalue1);
        RValue2 = (EditText)findViewById(R.id.et_rvalue2);

        TotalArea = (TextView)findViewById(R.id.tv_totalarea);
        EquivalentRValue = (TextView)findViewById(R.id.tv_equi_rvalue);
        EquivalentUFactor = (TextView)findViewById(R.id.tv_equi_ufactor);
        EquivalentUA = (TextView)findViewById(R.id.tv_equi_ua);
        Exit = (TextView)findViewById(R.id.tv_calculator_exit);

        BackArrow = (ImageView)findViewById(R.id.img_backarrow_edit);
//==================================================================================================
        Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Area1.getText().toString().equals("")){
                    Area1.requestFocus();
                    Area1.setError("Enter Area1");
                    return;
                }else if (RValue1.getText().toString().equals("")){
                    RValue1.requestFocus();
                    RValue1.setError("Enter R-Value1");
                    return;
                }else if (Area2.getText().toString().equals("")){
                    Area2.requestFocus();
                    Area2.setError("Enter Area2");
                    return;
                }else if (RValue2.getText().toString().equals("")){
                    RValue2.requestFocus();
                    RValue2.setError("Enter R-Value2");
                    return;
                }else {

                    CalulationPartHere(Area1.getText().toString(),Area2.getText().toString(),
                            RValue1.getText().toString(),RValue2.getText().toString());

                }

            }
        });
//==================================================================================================
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Area1.setText("");                   Area2.setText("");
                RValue1.setText("");                 RValue2.setText("");
                TotalArea.setText("");               EquivalentRValue.setText("");
                EquivalentUFactor.setText("");       EquivalentUA.setText("");
                EquivalentRValue.setBackgroundDrawable(null);
            }
        });
//==================================================================================================
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AttaicRCalculator.this,SplashActivity.class);
                startActivity(i);
                finish();
            }
        });
//==================================================================================================
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AttaicRCalculator.this,SplashActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
//==================================================================================================
    public  void CalulationPartHere(String A1,String A2,String R1,String R2){

        int Total_Area = Integer.parseInt(A1) + Integer.parseInt(A2);
        Log.d("Total_Area", String.valueOf(Total_Area));
        TotalArea.setText(String.valueOf(Total_Area));

        float A1R_Value = (1 / Float.parseFloat(R1)) * Float.parseFloat(A1);
        Log.e("A1R_Value", String.valueOf(A1R_Value));

        float A2R_Value = (1 / Float.parseFloat(R2)) * Float.parseFloat(A2);
        Log.d("A2R_Value", String.valueOf(A2R_Value));

        float U_Factor =  (A1R_Value + A2R_Value)/Float.parseFloat(String.valueOf(Total_Area));
        Log.e("U_Factor", String.valueOf(U_Factor));

        String s = String.format("%.3f", U_Factor);
        Log.d("RoundOFFValue", s);

        EquivalentUFactor.setText(s);

        float UA_Value = Float.parseFloat(s) * Float.parseFloat(String.valueOf(Total_Area));
        Log.e("UA_Value", String.valueOf(UA_Value));

        String UA_RoundOff = String.format("%.3f", UA_Value);
        EquivalentUA.setText(UA_RoundOff);

        float R_Value = (1 /Float.parseFloat(s));
        Log.d("R_Value", String.valueOf(R_Value));

        String R_RoundOff = String.format("%.2f", R_Value);
        Log.e("R_ValueRoundOFF", String.valueOf(R_RoundOff));

        EquivalentRValue.setText(R_RoundOff);
        EquivalentRValue.setBackgroundColor(Color.parseColor("#D74F1F"));

        /*EquivalentRValue.setBackgroundDrawable(getResources().getDrawable(R.drawable.third_page_third_inpoot));
        EquivalentRValue.setTextSize(23);
        EquivalentRValue.setPadding(15,5,0,0);*/

        int ii = Math.round(R_Value);
        Log.e("MathRound", String.valueOf(ii));
//==================================================================================================
        /* Cached the Calculator A1,A2,R1,R2,TotalArea,TotalRValue,TotalUFactor,TotalUA */
        /*Values puts into SharedPreferences Editor*/
        //areaa1,areaa2,rvalue1,rvalue2,totalarea,totalrvalue,totalufactor,totalua
        editorcalculator.putString("areaa1",A1);
        editorcalculator.putString("areaa2",A2);
        editorcalculator.putString("rvalue1",R1);
        editorcalculator.putString("rvalue2",R2);
        editorcalculator.putString("totalarea",String.valueOf(Total_Area));
        editorcalculator.putString("totalrvalue",R_RoundOff);
        editorcalculator.putString("totalufactor",s);
        editorcalculator.putString("totalua",UA_RoundOff);
        // Save the changes in SharedPreferences
        editorcalculator.commit(); // commit changes
//==================================================================================================
    }
//==================================================================================================
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AttaicRCalculator.this,SplashActivity.class);
        startActivity(i);
        this.finish();
    }
//==================================================================================================
}
