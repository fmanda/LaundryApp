package com.fma.laundryapp.helper;

import android.content.Context;

import com.fma.laundryapp.R;

import java.util.Random;

/**
 * Created by fma on 8/28/2017.
 */

public class DesignHelper {
    public static int getRandomLightColor(Context context, int lastColor){
        int[] colors = context.getResources().getIntArray(R.array.lightColors);
        int maxtry = 5;
        while (true) {
            maxtry--;
            int randomColor = colors[new Random().nextInt(colors.length)];
            if (lastColor!=randomColor) {
                return randomColor;
            }
            if (maxtry<=0) return randomColor;
        }
    }
}
