package com.material.nereeducation.helper;


import android.content.Context;
import android.graphics.Color;

import com.material.nereeducation.R;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AlphabetColor {

    private Context context;

    public AlphabetColor(Context context) {
        this.context = context;
    }

    public Integer getColorByAlphabet(Character input_alphabet)
    {
        HashMap<Character,String> dataColor = new LinkedHashMap<>();

        String arr[] = context.getResources().getStringArray(R.array.alphabet_color);

        int i = 0;
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            dataColor.put(alphabet,arr[i]);
            i++;
        }

        Character char_input_alphabet = input_alphabet;
        String value = dataColor.get(char_input_alphabet);
        return Color.parseColor(value);
    }
}
