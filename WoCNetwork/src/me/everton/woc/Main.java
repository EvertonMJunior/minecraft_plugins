package me.everton.woc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Main extends Activity {
    private EditText user;
    private EditText pass;
    private WebView wv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initialize();
    }

    private void initialize(){
        wv = (WebView) findViewById(R.id.webView);
        wv.loadUrl("http://wocpvp.com/app");
    }

}
