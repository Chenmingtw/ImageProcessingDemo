package com.example.chenming.imageprocessingdemo;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by ChenMing on 2017/7/4.
 */

public class BinarizationProcessor implements ImageProcessor {
    private final String TAG = "IPDemo/Binarization";

    @Override
    public Bitmap doProcess(Bitmap bmpIn) {
        Bitmap bmpOut = Bitmap.createBitmap(bmpIn.getWidth(), bmpIn.getHeight(), bmpIn.getConfig());
        int iPixel = 0;
        int iRed = 0;
        int iGreen = 0;
        int iBlue = 0;
        int iThreshold = 0;

        int bmpH = bmpIn.getHeight();
        int bmpW = bmpIn.getWidth();

        for(int y = 0; y < bmpH; y++) {
            for(int x = 0; x < bmpW; x++) {
                iPixel = bmpIn.getPixel(x ,y);
                iRed = Color.red(iPixel);
                iGreen = Color.green(iPixel);
                iBlue = Color.blue(iPixel);

                iThreshold = (iRed + iGreen + iBlue) / 3;
                if(iThreshold > 127) {
                    // Set as white
                    bmpOut.setPixel(x, y, Color.rgb(255, 255, 255));
                } else {
                    // Set as black
                    bmpOut.setPixel(x, y, Color.rgb(0, 0, 0));
                }
            }
        }

        return bmpOut;
    }
}
