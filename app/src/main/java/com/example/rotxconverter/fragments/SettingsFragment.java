package com.example.rotxconverter.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rotxconverter.R;

import java.io.Serializable;

public class SettingsFragment extends Fragment implements Serializable {

    interface SettingsReceiver extends Serializable {
        void onReceiveSettings(RotConversion conversionLaw);
    }

    public interface SettingsChangedListener extends Serializable {
        void onSettingsChanged(RotConversion conversionLaw);
    }

    private static final String SAVED_CONVERSION_LAW = "savedConversionLaw";
    private static final String SAVED_RECEIVER = "savedReceiver";
    private static final String SAVED_SETTINGS_CHANGED_LISTENER = "savedSettingsChangedListener";

    private RotConversion conversionLaw;
    private SettingsReceiver receiver;
    private SettingsChangedListener listener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (conversionLaw == null) {
            if (savedInstanceState == null) {
                conversionLaw = new RotConversion();
            } else {
                conversionLaw = (RotConversion) savedInstanceState.getSerializable(SAVED_CONVERSION_LAW);
                receiver = (SettingsReceiver) savedInstanceState.getSerializable(SAVED_RECEIVER);
                listener = (SettingsChangedListener) savedInstanceState.getSerializable(SAVED_SETTINGS_CHANGED_LISTENER);
            }
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ((CheckBox) view.findViewById(R.id.settings_doLetterShift)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            view.findViewById(R.id.settings_letterShift).setEnabled(isChecked);
            if (isChecked) {
                ((CheckBox) view.findViewById(R.id.settings_doCustomShift)).setChecked(false);
            }
            conversionLaw.enableLetterShift(isChecked);
            listener.onSettingsChanged(conversionLaw);
        });
        ((CheckBox) view.findViewById(R.id.settings_doNumberShift)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            view.findViewById(R.id.settings_numberShift).setEnabled(isChecked);
            if (isChecked) {
                ((CheckBox) view.findViewById(R.id.settings_doCustomShift)).setChecked(false);
            }
            conversionLaw.enableNumberShift(isChecked);
            listener.onSettingsChanged(conversionLaw);
        });
        ((CheckBox) view.findViewById(R.id.settings_doCustomShift)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            view.findViewById(R.id.settings_customShift).setEnabled(isChecked);
            view.findViewById(R.id.settings_customAlphabet).setEnabled(isChecked);
            if (isChecked) {
                ((CheckBox) view.findViewById(R.id.settings_doLetterShift)).setChecked(false);
                ((CheckBox) view.findViewById(R.id.settings_doNumberShift)).setChecked(false);
            }
            conversionLaw.enableCustomShift(isChecked);
            listener.onSettingsChanged(conversionLaw);
        });
        ((EditText) view.findViewById(R.id.settings_letterShift)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (((EditText) view.findViewById(R.id.settings_letterShift)).getText().toString().isEmpty()) {
                    conversionLaw.setLetterShift(13);
                } else {
                    conversionLaw.setLetterShift(Integer.parseInt(s.toString()));
                }
                listener.onSettingsChanged(conversionLaw);
            }
        });
        ((EditText) view.findViewById(R.id.settings_numberShift)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (((EditText) view.findViewById(R.id.settings_numberShift)).getText().toString().isEmpty()) {
                    conversionLaw.setNumberShift(5);
                } else {
                    conversionLaw.setNumberShift(Integer.parseInt(s.toString()));
                }
                listener.onSettingsChanged(conversionLaw);
            }
        });
        ((EditText) view.findViewById(R.id.settings_customShift)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (((EditText) view.findViewById(R.id.settings_customShift)).getText().toString().isEmpty()) {
                    conversionLaw.setCustomShift(13);
                } else {
                    conversionLaw.setCustomShift(Integer.parseInt(s.toString()));
                }
                listener.onSettingsChanged(conversionLaw);
            }
        });
        ((EditText) view.findViewById(R.id.settings_customAlphabet)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                conversionLaw.setCustomAlphabet(s.toString().toCharArray());
                listener.onSettingsChanged(conversionLaw);
            }
        });

        CheckBox letterCheckBox = view.findViewById(R.id.settings_doLetterShift);
        CheckBox numberCheckBox = view.findViewById(R.id.settings_doNumberShift);
        CheckBox customCheckBox = view.findViewById(R.id.settings_doCustomShift);
        EditText letterShift = view.findViewById(R.id.settings_letterShift);
        EditText numberShift = view.findViewById(R.id.settings_numberShift);
        EditText customShift = view.findViewById(R.id.settings_customShift);
        EditText customAlphabet = view.findViewById(R.id.settings_customAlphabet);

        letterCheckBox.setChecked(conversionLaw.isLetterShiftEnabled());
        numberCheckBox.setChecked(conversionLaw.isNumberShiftEnabled());
        customCheckBox.setChecked(conversionLaw.isCustomShiftEnabled());
        letterShift.setText(String.valueOf(conversionLaw.getLetterShift()));
        numberShift.setText(String.valueOf(conversionLaw.getNumberShift()));
        customShift.setText(String.valueOf(conversionLaw.getCustomShift()));
        customAlphabet.setText(conversionLaw.getCustomAlphabet());

        letterShift.setEnabled(letterCheckBox.isChecked());
        numberShift.setEnabled(numberCheckBox.isChecked());
        customShift.setEnabled(customCheckBox.isChecked());

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle toSave) {
        toSave.putSerializable(SAVED_CONVERSION_LAW, conversionLaw);
        toSave.putSerializable(SAVED_RECEIVER, receiver);
        toSave.putSerializable(SAVED_SETTINGS_CHANGED_LISTENER, listener);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (receiver != null) {
            receiver.onReceiveSettings(conversionLaw);
        }
    }

    //

    public void setSettingsReceiver(SettingsReceiver receiver) {
        this.receiver = receiver;
    }

    public void setSavedSettingsChangedListener(SettingsChangedListener listener) { this.listener = listener; }

    public RotConversion getConversionLaw() {
        return conversionLaw;
    }

    public void setRotConversion(RotConversion convLaw) {
        conversionLaw = convLaw;
    }

}
