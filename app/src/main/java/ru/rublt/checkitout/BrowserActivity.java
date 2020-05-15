package ru.rublt.checkitout;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class BrowserActivity extends AppCompatActivity {
         //Websyte
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_main);

        WebView webView = (WebView)findViewById(R.id.webView);
        Uri data = getIntent().getData();
        webView.loadUrl(data.toString());

        //Websyte
    }
}

