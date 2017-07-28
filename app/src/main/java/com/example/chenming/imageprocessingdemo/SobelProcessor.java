package com.example.chenming.imageprocessingdemo;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by ChenMing on 2017/7/6.
 * Image processing by Sobel Edge Detection with two 3x3 matrices
 */

public class SobelProcessor implements ImageProcessor {
    private final String TAG = "IPDemo/Sobel";

    @Override
    public Bitmap doProcess(Bitmap bmpIn) {
        Bitmap bmpOut = Bitmap.createBitmap(bmpIn.getWidth(), bmpIn.getHeight(), bmpIn.getConfig());
        int iPixel = 0;
        int iRed = 0;
        int iGreen = 0;
        int iBlue = 0;
        int iGray = 0;
        int dobFilterResult1 = 0;
        int dobFilterResult2 = 0;
        int dobFilterResultMax = 0;

        final double[][] dobHSobel =  {
                {-1, -2, -1},
                { 0,  0,  0},
                { 1,  2,  1}
        };

        final double[][] dobVSobel = {
                {-1,  0,  1},
                {-2,  0,  2},
                {-1,  0,  1}
        };

        int bmpH = bmpIn.getHeight();
        int bmpW = bmpIn.getWidth();

        for(int y = 1; y < bmpH - 1; y++) {
            for (int x = 1; x < bmpW - 1; x++) {
                dobFilterResult1 = 0;
                dobFilterResult2 = 0;

                for (int fwY = -1; fwY <= 1; fwY++) {
                    for (int fwX = -1; fwX <= 1; fwX++) {
                        iPixel = bmpIn.getPixel(x + fwX ,y + fwY);
                        iRed = Color.red(iPixel);
                        iGreen = Color.green(iPixel);
                        iBlue = Color.blue(iPixel);

                        // Convert RGB to Gray by Y formula in YUV
                        //iGray = (int)(0.299 * iRed + 0.587 * iGreen + 0.114 * iBlue);
                        iGray = (299 * iRed + 587 * iGreen + 114 * iBlue) / 1000;
                        dobFilterResult1 += (iGray * dobHSobel[fwY + 1][fwX + 1]);
                        dobFilterResult2 += (iGray * dobVSobel[fwY + 1][fwX + 1]);
                    }
                }

                dobFilterResult1 = Math.abs(dobFilterResult1);
                dobFilterResult2 = Math.abs(dobFilterResult2);
                dobFilterResultMax = Math.max(dobFilterResult1, dobFilterResult2);
                bmpOut.setPixel(x, y, Color.rgb(dobFilterResultMax, dobFilterResultMax, dobFilterResultMax));
            }
        }

        return bmpOut;
    }
}
