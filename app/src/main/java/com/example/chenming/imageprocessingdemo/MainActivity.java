package com.example.chenming.imageprocessingdemo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "ImageProcessingDemo";
    private final int DEFAULT_DRAWABLE_ID = R.drawable.hello320x240;
    private static final int MSG_IMG_UPDATE = 1;
    private ImageView imgSrc;
    private Button btnReset;
    private Button btnBinary;
    private Button btnGauss33;
    private Button btnGauss99;
    private Button btnSobel;
    private ImageProcessor imgProcessor = null;
    private Bitmap bmpSrc = null;
    private Bitmap bmpBuffer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        imgSrc = (ImageView) findViewById(R.id.imageView_source);
        btnReset = (Button) findViewById(R.id.button_reset);
        btnBinary = (Button) findViewById(R.id.button_binarization);
        btnGauss33 = (Button) findViewById(R.id.button_gaussian_blur33);
        btnGauss99 = (Button) findViewById(R.id.button_gaussian_blur99);
        btnSobel = (Button) findViewById(R.id.button_test_filter);

        bmpSrc = BitmapFactory.decodeResource(this.getResources(), DEFAULT_DRAWABLE_ID);
        bmpBuffer = Bitmap.createBitmap(bmpSrc);
        imgSrc.setImageBitmap(bmpSrc);

        /* Button access */
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSrc.setImageBitmap(bmpSrc);
                bmpBuffer = bmpSrc;
                Toast.makeText(getApplicationContext(), R.string.toast_reset, Toast.LENGTH_SHORT).show();
            }
        });

        btnBinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgProcessor = new BinarizationProcessor();
                doImageProcess();
            }
        });

        btnGauss33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgProcessor = new GaussianBlurProcessor3x3();
                doImageProcess();
            }
        });

        btnGauss99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgProcessor = new GaussianBlurProcessor5x5();
                doImageProcess();
            }
        });

        btnSobel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgProcessor = new SobelProcessor();
                doImageProcess();
            }
        });
    }

    private void doImageProcess() {
        final ProgressDialog pDlg = new ProgressDialog(MainActivity.this);
        pDlg.setTitle(R.string.dlg_processing_title);
        pDlg.setMessage(getString(R.string.dlg_processing_message));
        pDlg.setIndeterminate(true);
        pDlg.setCancelable(false);
        pDlg.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(imgProcessor != null)
                        bmpBuffer = imgProcessor.doProcess(bmpBuffer);

                    // Update ImageView
                    Message m = new Message();
                    m.what = MSG_IMG_UPDATE;
                    mHandler.sendMessage(m);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(pDlg.isShowing())
                        pDlg.dismiss();
                }
            }
        }).start();
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_IMG_UPDATE:
                    if(bmpBuffer != null) {
                        imgSrc.setImageBitmap(bmpBuffer);
                        Toast.makeText(getApplicationContext(), R.string.toast_process_ok, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.toast_process_fail, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}
