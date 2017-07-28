package com.example.chenming.imageprocessingdemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by ChenMing on 2017/7/14.
 * Image processing by Gaussian Blur with 5x5 matrix
 */

public class GaussianBlurProcessor5x5 implements ImageProcessor {
    private final String TAG = "IPDemo/GaussianBlur5x5";

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

        final double[][] dobAGaussian =  {
                {1, 2,  4, 2, 1},
                {2 ,4,  8, 4, 2},
                {4, 8, 16, 8, 4},
                {2 ,4,  8, 4, 2},
                {1, 2,  4, 2, 1}
        };


        int blurRadius = dobAGaussian.length / 2;
        double dobScale = 0;

        for (int m = 0; m < dobAGaussian.length; m++) {
            for (int n = 0; n < dobAGaussian[m].length; n++) {
                dobScale += dobAGaussian[m][n];
            }
        }

        //Log.d(TAG, "blurRadius=" + blurRadius + ", dobScale=" + dobScale);

        int bmpH = bmpIn.getHeight();
        int bmpW = bmpIn.getWidth();

        for(int y = blurRadius; y < bmpH - blurRadius; y++) {
            for (int x = blurRadius; x < bmpW - blurRadius; x++) {
                dobGaussianRed = 0;
                dobGaussianGreen = 0;
                dobGaussianBlue = 0;

                for (int fwY = -blurRadius; fwY <= blurRadius; fwY++) {
                    for (int fwX = -blurRadius; fwX <= blurRadius; fwX++) {
                        iPixel = bmpIn.getPixel(x + fwX ,y + fwY);
                        iRed = Color.red(iPixel);
                        iGreen = Color.green(iPixel);
                        iBlue = Color.blue(iPixel);

                        dobGaussianRed += (iRed * dobAGaussian[fwY + blurRadius][fwX + blurRadius]);
                        dobGaussianGreen += (iGreen * dobAGaussian[fwY + blurRadius][fwX + blurRadius]);
                        dobGaussianBlue += (iBlue * dobAGaussian[fwY + blurRadius][fwX + blurRadius]);
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
