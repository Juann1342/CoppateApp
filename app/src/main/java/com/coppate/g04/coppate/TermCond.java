package com.coppate.g04.coppate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TermCond extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_cond);
    }

    public void onBackPressed() {
        TermCond.this.finish();
        overridePendingTransition(R.anim.reingreso, R.anim.nothing);

    }
}
