package com.example.rotxconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public abstract class AbstractActWithConvertMenuItem extends AppCompatActivity {

    private MainActivity.AppDetails appDetails;

    @Override
    protected void onCreate(Bundle toSave) {
        super.onCreate(toSave);
        if (toSave != null) {
            appDetails = (MainActivity.AppDetails) toSave.getSerializable(MainActivity.INTENT_APP_DETAILS);
        } else if (getIntent() != null) {
            appDetails = (MainActivity.AppDetails) getIntent().getSerializableExtra(MainActivity.INTENT_APP_DETAILS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle toSave) {
        super.onSaveInstanceState(toSave);
        toSave.putSerializable(MainActivity.INTENT_APP_DETAILS, appDetails);
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra(MainActivity.INTENT_APP_DETAILS, appDetails);
        super.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_with_convert, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuItem_convert) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.INTENT_APP_DETAILS, appDetails);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
