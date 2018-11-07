package edu.temple.browserlab;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class Tabs extends Fragment {
    Context context;
    WebView webView;
    String URL_KEY="http://www.Bing.com";
    String inputURL;
    String URL_KEYr;
    Button Forward,Back;
    public String currentURL;
    public Tabs() {
        // Required empty public constructor
    }

    public void onCreate (@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        inputURL= bundle.getString(URL_KEY);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V =inflater.inflate(R.layout.fragment_tabs, container, false);
        webView= V.findViewById(R.id.webView);
        Forward= V.findViewById(R.id.Forward);
        Back=V.findViewById(R.id.Back);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
        Forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoForward()) {
                    webView.goForward();
                    URL_KEYr=webView.getUrl();
                    currentURL=webView.getUrl();
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack()) {
                    webView.goBack();
                    URL_KEYr=webView.getUrl();
                    currentURL=webView.getUrl();
                   // webView.
                }
            }
        });
        return V;
    }

    public void urlLoad(String URL){
        inputURL=URL;
        //if(!inputURL.regionMatches(0,"Https://www.",0,10)||!inputURL.regionMatches(0,"www.",0,5))
        //inputURL="Https://www."+inputURL;

        Urlfromtextview(inputURL);

    }

    private void Urlfromtextview(final String URL) {
        new Thread() {
            public void run() {
                    StringBuilder sb = new StringBuilder();
                    String sd=URL;
                    Message msg = Message.obtain();
                    msg.obj = sd.toString();
                    URL_KEYr=sd.toString(); //LEFT OFF HERE
                    currentURL=sd.toString();
                    responseHandler.sendMessage(msg);
            }
        }.start();
    }
    @Override
    public void onResume() {
        webView.loadUrl(URL_KEYr);
        currentURL=webView.getUrl();
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context=this.context;
    }

    public static Tabs newInstance(String URL)
    {
        Tabs tabfrag = new Tabs();
        Bundle bundle = new Bundle();
        bundle.putString(tabfrag.URL_KEY,URL);
        tabfrag.setArguments(bundle);
        return tabfrag;
    }
    Handler responseHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //WebView object loading data
            webView.loadUrl((String) msg.obj);
            //display.setText((String) msg.obj);
            return false;
        }
    });

    private class AWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String Url) {
         view.loadUrl(Url);
         currentURL=Url; //LEFT OFF HERE
         return true;
        };
    }


}
