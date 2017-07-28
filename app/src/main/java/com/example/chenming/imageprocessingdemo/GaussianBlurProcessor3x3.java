package com.example.chenming.imageprocessingdemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by ChenMing on 2017/7/5.
 * Image processing by Gaussian Blur with 3x3 matrix
 */

public class GaussianBlurProcessor3x3 implements ImageProcessor {
    private final String TAG = "IPDemo/GaussianBlur3x3";

    @Override
    public Bitmap doProcess(Bitmap bmpIn) {
        Bitmap bmpOut = Bitmap.createBitmap(bmpIn.getWidth(), bmpIn.getHeight(), bmpIn.getConfig());
        int iPixel = 0;
        int iRed = 0;
        int iGreen = 0;
        int iBlue = 0;
        double dobGaussianRed = 0;
        double dobGaussianGreen = 0;
        double dobGaussianBlue = 0;
        double dobScale = 16.0f;

        final double[][] dobAGaussian =  {
                {1, 2, 1},
                {2 ,4, 2},
                {1, 2, 1}
        };

        int bmpH = bmpIn.getHeight();
        int bmpW = bmpIn.getWidth();

        for(int y = 1; y < bmpH - 1; y++) {
            for (int x = 1; x < bmpW - 1; x++) {
                dobGaussianRed = 0;
                dobGaussianGreen = 0;
                dobGaussianBlue = 0;

                for (int fwY = -1; fwY <= 1; fwY++) {
                    for (int fwX = -1; fwX <= 1; fwX++) {
                        iPixel = bmpIn.getPixel(x + fwX ,y + fwY);
                        iRed = Color.red(iPixel);
                        iGreen = Color.green(iPixel);
                        iBlue = Color.blue(iPixel);

                        dobGaussianRed += (iRed * dobAGaussian[fwY + 1][fwX + 1]);
                        dobGaussianGreen += (iGreen * dobAGaussian[fwY + 1][fwX + 1]);
                        dobGaussianBlue += (iBlue * dobAGaussian[fwY + 1][fwX + 1]);
                    }
                }

                dobGaussianRed =dobGaussianRed / dobScale;
                dobGaussianGreen = dobGaussianGreen /dobScale;
                dobGaussianBlue = dobGaussianBlue / dobScale;
                bmpOut.setPixel(x, y, Color.rgb((int)dobGaussianRed, (int)dobGaussianGreen, (int)dobGaussianBlue));
            }
        }
        //Log.d(TAG, "Density=" + bmpIn.getDensity() + ", Width=" + bmpIn.getWidth() + ", Height=" + bmpIn.getHeight());
        return bmpOut;
    }
}
