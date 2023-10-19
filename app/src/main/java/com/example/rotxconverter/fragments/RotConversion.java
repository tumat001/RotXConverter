package com.example.rotxconverter.fragments;

import java.io.Serializable;

public class RotConversion implements Serializable {

    SettingsPackage settings;

    RotConversion() { settings = new SettingsPackage(); }
    RotConversion(SettingsPackage settings) {
        this.settings = settings;
    }

    static class SettingsPackage implements Serializable {
        private int letterShift, numberShift, customShift;
        private boolean doLetterShift, doNumberShift, doCustomShift;
        private char[] customAlphabet;

        SettingsPackage() {
            letterShift = 13;
            numberShift = 5;
            customShift = 0;
            doLetterShift = true;
            doNumberShift = false;
            doCustomShift = false;
            customAlphabet = new char[0];
        }
    }

    public SettingsPackage getSettings() {
        return settings;
    }

    //

    public void setLetterShift(int shift) { settings.letterShift = shift; }
    public void setNumberShift(int shift) { settings.numberShift = shift; }
    public void setCustomShift(int shift) { settings.customShift = shift; }
    public void enableLetterShift(boolean enable) { settings.doLetterShift = enable; }
    public void enableNumberShift(boolean enable) { settings.doNumberShift = enable; }
    public void enableCustomShift(boolean enable) { settings.doCustomShift = enable; }
    public void setCustomAlphabet(char[] customAlphabet) { settings.customAlphabet = customAlphabet; }

    public int getLetterShift() { return settings.letterShift; }
    public int getNumberShift() { return settings.numberShift; }
    public int getCustomShift() { return settings.customShift; }
    public boolean isLetterShiftEnabled() { return settings.doLetterShift; }
    public boolean isNumberShiftEnabled() { return settings.doNumberShift; }
    public boolean isCustomShiftEnabled() { return settings.doCustomShift; }
    public String getCustomAlphabet() { return new String(settings.customAlphabet); }

    public String convertString(String toConv) {
        char[] returnVal = toConv.toCharArray();

        if (!settings.doCustomShift) {
            for (int i = 0; i < returnVal.length; i++) {
                if (settings.doLetterShift) {
                    if (returnVal[i] >= 65 && returnVal[i] <= 90) {
                        returnVal[i] += (settings.letterShift % 26);
                        if (returnVal[i] > 90) {
                            returnVal[i] -= 26; //LENGTH OF ALPHABET
                        }
                    } else if (returnVal[i] >= 97 && returnVal[i] <= 122) {
                        returnVal[i] += (settings.letterShift % 26);
                        if (returnVal[i] > 122) {
                            returnVal[i] -= 26;
                        }
                    }
                }
                if (settings.doNumberShift) {
                    if (returnVal[i] >= 48 && returnVal[i] <= 57) {
                        returnVal[i] += (settings.numberShift % 10);
                        if (returnVal[i] > 57) {
                            returnVal[i] -= 10; //LENGTH OF SINGLE DIGIT NUMBER LINE
                        }
                    }
                }
            }
        } else {
            char[] sourceC = settings.customAlphabet;

            for (int i = 0; i < returnVal.length; i++) {
                aa: for (int j = 0; j < sourceC.length; j++) {
                    if (sourceC[j] == returnVal[i]) {
                        int shift = (j + settings.customShift) % sourceC.length;
                        returnVal[i] = sourceC[shift];
                        break aa;
                    }
                }
            }
        }

        return new String(returnVal);
    }

}
