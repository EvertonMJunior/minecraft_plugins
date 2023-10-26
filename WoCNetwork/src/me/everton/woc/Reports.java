package me.everton.woc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Reports extends Activity {
    private EditText user;
    private EditText pass;
    private ImageView button;
    private View.OnClickListener buttonClick;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports);

        button = (ImageView) findViewById(R.id.logoReports);

        buttonClick = new View.OnClickListener() {
            public void onClick(View v){
                setContentView(R.layout.main);
            }
        };
        button.setOnClickListener(buttonClick);
    }

    @Override
    public void onBackPressed (){
        super.onBackPressed();
        setContentView(R.layout.main);
    }
}
