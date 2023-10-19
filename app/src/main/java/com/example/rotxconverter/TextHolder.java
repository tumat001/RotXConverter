package com.example.rotxconverter;

import java.io.Serializable;

class TextHolder implements Serializable {

    public interface TextChangedListener {
        void textChanged(String newText);
    }

    private String text;
    private TextChangedListener listener;

    TextHolder(String text, TextChangedListener listener) {
        this.text = text;
        this.listener = listener;
    }

    @Override
    public String toString() { return text; }

    public void setText(String text) {
        this.text = text;
        if (listener != null) {
            listener.textChanged(text);
        }
    }

    public String getText() {
        return text;
    }

    public TextChangedListener getListener() {
        return listener;
    }
}
