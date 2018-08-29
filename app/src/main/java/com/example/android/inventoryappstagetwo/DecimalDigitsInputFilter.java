package com.example.android.inventoryappstagetwo;

import android.text.InputFilter;
import android.text.Spanned;

// https://stackoverflow.com/questions/5357455/limit-decimal-places-in-android-edittext?page=1&tab=votes#tab-top
// by peceps

/**
 * Input filter that limits the number of decimal digits that are allowed to be
 * entered.
 */
public class DecimalDigitsInputFilter implements InputFilter {

    private final int decimalDigits;

    /**
     * Constructor.
     *
     * @param decimalDigits maximum decimal digits
     */
    public DecimalDigitsInputFilter(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    @Override
    public CharSequence filter(CharSequence source,
                               int start,
                               int end,
                               Spanned dest,
                               int dstart,
                               int dend) {


        int dotPos = -1;
        int len = dest.length();
        for (int i = 0; i < len; i++) {
            char c = dest.charAt(i);
            if (c == '.' || c == ',') {
                dotPos = i;
                break;
            }
        }
        if (dotPos >= 0) {

            // protects against many dots
            if (source.equals(".") || source.equals(","))
            {
                return "";
            }
            // if the text is entered before the dot
            if (dend <= dotPos) {
                return null;
            }
            if (len - dotPos > decimalDigits) {
                return "";
            }
        }

        return null;
    }

}