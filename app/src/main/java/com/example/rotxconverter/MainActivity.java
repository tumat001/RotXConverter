package com.example.rotxconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rotxconverter.fragments.RotConversion;
import com.example.rotxconverter.fragments.SettingsFragment;
import com.example.rotxconverter.fragments.TextContainerFragment;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements TextHolder.TextChangedListener, TextContainerFragment.ConversionAppliedListener, SettingsFragment.SettingsChangedListener {

    static final String INTENT_APP_DETAILS = "intentAppDetails";
    private static final String SAVED_APP_DETAILS = "savedAppDetails";

    private TextHolder textHolder;
    private RotConversion conversionLawOfSettingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.main_toolbar));

        TextContainerFragment textContainerFragment = new TextContainerFragment();
        SettingsFragment settingsFragment = new SettingsFragment();
        textHolder = new TextHolder("", this);

        settingsFragment.setSettingsReceiver(textContainerFragment);
        settingsFragment.setSavedSettingsChangedListener(this);
        textContainerFragment.setConversionAppliedListener(this);

        if (savedInstanceState == null) {
            if (getIntent().getSerializableExtra(INTENT_APP_DETAILS) != null) {
                AppDetails details = (AppDetails) getIntent().getSerializableExtra(INTENT_APP_DETAILS);

                textContainerFragment.setText(details.textHolder.getText());
                textContainerFragment.onReceiveSettings(details.conversionLaw);
                settingsFragment.setRotConversion(details.conversionLaw);
            } else if (getIntent().getStringExtra(Intent.EXTRA_TEXT) != null) {
                textContainerFragment.setText(getIntent().getStringExtra(Intent.EXTRA_TEXT));
            }
        } else if (savedInstanceState != null) {
            AppDetails details = (AppDetails) savedInstanceState.getSerializable(SAVED_APP_DETAILS);
            textHolder = details.getTextHolder();
            conversionLawOfSettingsFragment = details.conversionLaw;
        }

        ViewPager pager = findViewById(R.id.main_viewPager);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return textContainerFragment;
                } else if (position == 1) {
                    return settingsFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int pos) {
                if (pos == 0) {
                    return "Text";
                } else if (pos == 1) {
                    return "Settings";
                }
                return null;
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle toSave) {
        super.onSaveInstanceState(toSave);
        toSave.putSerializable(SAVED_APP_DETAILS, new AppDetails(conversionLawOfSettingsFragment, textHolder));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_with_about_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuItem_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            intent.putExtra(INTENT_APP_DETAILS, new AppDetails(conversionLawOfSettingsFragment, textHolder));
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.menuItem_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, textHolder.getText());
            intent = Intent.createChooser(intent, "Share Via");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onConvert(RotConversion conv) {
        //Toast.makeText(this, String.valueOf(testId), Toast.LENGTH_SHORT).show();
    }

    //FROM TEXT HOLDER
    public void textChanged(String s) {
        //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void onTextChanged(String s) {
        textHolder.setText(s);
    }

    public void onSettingsChanged(RotConversion conversionLaw) {
        conversionLawOfSettingsFragment = conversionLaw;
    }

    //

    static class AppDetails implements Serializable {

        private RotConversion conversionLaw;
        private TextHolder textHolder;

        AppDetails(RotConversion conversionLaw, TextHolder textHolder) {
            this.conversionLaw = conversionLaw;
            this.textHolder = textHolder;
        }

        RotConversion getConversionLaw() {
            return this.conversionLaw;
        }

        TextHolder getTextHolder() {
            return textHolder;
        }

    }

}
