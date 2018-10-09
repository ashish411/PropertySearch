package com.example.ashis.propertysearch;

import android.graphics.Color;

/**
 * Created by ashis on 10/15/2017.
 */

public class Utility {

    public static int getColor(int pos){
        int value;
        switch (pos){
            case 0:
                value= Color.WHITE;
                break;
            case 1:
                value = Color.RED;
                break;
            case 2:
                value = Color.GREEN;
                break;
            case 3:
                value= Color.BLUE;
                break;
            case 4:
                value =Color.YELLOW;
                break;
            default:value=0;
                break;
        }
        return value;
    }
}
