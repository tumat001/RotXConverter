package com.example.rotxconverter.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rotxconverter.R;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextContainerFragment extends Fragment implements SettingsFragment.SettingsReceiver, Serializable {

    private static final String SAVED_CURR_TEXT = "savedCurrText";
    private static final String SAVED_CONVERSION_LAW = "savedConversionLaw";
    private static final String SAVED_LISTENER = "savedListener";

    public interface ConversionAppliedListener extends Serializable {
        void onConvert(RotConversion conversionLaw);
        void onTextChanged(String s);
    }

    public TextContainerFragment() {
        // Required empty public constructor
    }

    private RotConversion conversionLaw;
    private ConversionAppliedListener listener;
    private String currText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            if (conversionLaw == null) {
                conversionLaw = new RotConversion();
            }
        } else {
            if (conversionLaw == null) {
                conversionLaw = (RotConversion) savedInstanceState.getSerializable(SAVED_CONVERSION_LAW);
            }
            listener = (ConversionAppliedListener) savedInstanceState.getSerializable(SAVED_LISTENER);
            currText = savedInstanceState.getString(SAVED_CURR_TEXT);
        }

        View view = inflater.inflate(R.layout.fragment_text_container, container, false);

        //Convert behavior
        (view.findViewById(R.id.textContainer_convertButton)).setOnClickListener(triggerView -> {
            EditText textBody = view.findViewById(R.id.textContainer_textBody);
            int caretPos = textBody.getSelectionStart();
            textBody.setText(conversionLaw.convertString(textBody.getText().toString()));
            textBody.setSelection(caretPos);
            if (listener != null) {
                listener.onConvert(conversionLaw);
            }
        });
        ((EditText) view.findViewById(R.id.textContainer_textBody)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currText = s.toString();
                listener.onTextChanged(currText);
            }
        });

        if (currText != null) {
            ((EditText) view.findViewById(R.id.textContainer_textBody)).setText(currText);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle toSave) {
        super.onSaveInstanceState(toSave);
        toSave.putSerializable(SAVED_CONVERSION_LAW, conversionLaw);
        toSave.putSerializable(SAVED_LISTENER, listener);
        toSave.putString(SAVED_CURR_TEXT, currText);
    }

    public void onReceiveSettings(RotConversion conversionLaw) {
        this.conversionLaw = conversionLaw;
    }

    public void setConversionAppliedListener(ConversionAppliedListener listener) {
        this.listener = listener;
    }

    public void setText(String text) {
        currText = text;
    }

}
