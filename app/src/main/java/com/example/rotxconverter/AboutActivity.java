package com.example.rotxconverter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class AboutActivity extends AbstractActWithConvertMenuItem {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setSupportActionBar(findViewById(R.id.about_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((ListView) findViewById(R.id.about_aboutListEntries)).setOnItemClickListener((parent, view, pos, id) -> {
            if (pos == 0) {
                Intent intent = new Intent(this, AboutAppActivity.class);
                startActivity(intent);
            } else if (pos == 1) {

            }
        });
        ((ListView) findViewById(R.id.about_rotXDetailsListEntries)).setOnItemClickListener((parent, view, pos, id) -> {
            if (pos == 0) {

            } else if (pos == 1) {

            } else if (pos == 2) {

            } else if (pos == 3) {

            }
        });
    }
}
