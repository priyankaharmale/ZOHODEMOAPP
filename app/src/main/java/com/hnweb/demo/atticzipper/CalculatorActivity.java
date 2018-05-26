package com.hnweb.demo.atticzipper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by hnwebmarketing on 6/12/2017.
 */
public class CalculatorActivity extends AppCompatActivity {

    WebView myWebView;
    int PIC_WIDTH;
    String formURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        formURL = "http://www.residentialenergydynamics.com/Portals/0/REDCalc/Free/REDCalcFree.html?tool=ParallelPathRValue&_=2016-07-06_01:30";

        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl(formURL);
        //myWebView.loadUrl("http://www.residentialenergydynamics.com/REDCalcFree/Tools/ParallelPathEquivalentRValue");

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.getUseWideViewPort();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //myWebView.setInitialScale(85);
        myWebView.setWebViewClient(new WebViewClient());

        //Make sure No cookies are created
        CookieManager.getInstance().setAcceptCookie(false);

       //Make sure no caching is done
        myWebView.getSettings().setCacheMode(webSettings.LOAD_NO_CACHE);
        myWebView.getSettings().setAppCacheEnabled(false);
        myWebView.clearHistory();
        myWebView.clearCache(true);

       //Make sure no autofill for Forms/ user-name password happens for the app
        myWebView.clearFormData();
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setSavePassword(false);
        myWebView.getSettings().setSaveFormData(false);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CalculatorActivity.this,SplashActivity.class);
        startActivity(i);
        this.finish();
    }


}
