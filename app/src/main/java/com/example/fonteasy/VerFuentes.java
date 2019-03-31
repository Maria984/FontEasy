package com.example.fonteasy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.TextInputLayout;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static com.example.fonteasy.Constants.ITALIC_DEFAULT;
import static com.example.fonteasy.Constants.WEIGHT_DEFAULT;
import static com.example.fonteasy.Constants.WEIGHT_MAX;
import static com.example.fonteasy.Constants.WIDTH_DEFAULT;
import static com.example.fonteasy.Constants.WIDTH_MAX;

public class VerFuentes extends AppCompatActivity {

    private static final String TAG = "VerFuentes";

    private Handler mHandler = null;

    private TextView mDownloadableFontTextView;
    private SeekBar mWidthSeekBar;
    private SeekBar mWeightSeekBar;
    private SeekBar mItalicSeekBar;
    private CheckBox mBestEffort;
    private Button mRequestDownloadButton;

    private ArraySet<String> mFamilyNameSet;

    private TextView Header;

    private Typeface Encode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_fuentes);





        String fuente2 = "fuentes/Encode.ttf";
        this.Encode = Typeface.createFromAsset(getAssets(),fuente2);

        Header = (TextView) findViewById(R.id.Header);
        Header.setTypeface(Encode);

        initializeSeekBars();
        mFamilyNameSet = new ArraySet<>();
        mFamilyNameSet.addAll(Arrays.asList(getResources().getStringArray(R.array.family_names)));

        mDownloadableFontTextView = findViewById(R.id.textview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.family_names));
        final TextInputLayout familyNameInput = findViewById(R.id.auto_complete_family_name_input);
        final AutoCompleteTextView autoCompleteFamilyName = findViewById(
                R.id.auto_complete_family_name);
        autoCompleteFamilyName.setAdapter(adapter);
        autoCompleteFamilyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count,
                                          int after) {
                // No op
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (isValidFamilyName(charSequence.toString())) {
                    familyNameInput.setErrorEnabled(false);
                    familyNameInput.setError("");
                } else {
                    familyNameInput.setErrorEnabled(true);
                    familyNameInput.setError(getString(R.string.invalid_family_name));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No op
            }
        });

        mRequestDownloadButton = findViewById(R.id.button_request);
        mRequestDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String familyName = autoCompleteFamilyName.getText().toString();
                if (!isValidFamilyName(familyName)) {
                    familyNameInput.setErrorEnabled(true);
                    familyNameInput.setError(getString(R.string.invalid_family_name));
                    Toast.makeText(
                            VerFuentes.this,
                            R.string.invalid_input,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                requestDownload(familyName);
                mRequestDownloadButton.setEnabled(false);
            }
        });
        mBestEffort = findViewById(R.id.checkbox_best_effort);
    }


    private void requestDownload(String familyName) {
        QueryBuilder queryBuilder = new QueryBuilder(familyName)
                .withWidth(progressToWidth(mWidthSeekBar.getProgress()))
                .withWeight(progressToWeight(mWeightSeekBar.getProgress()))
                .withItalic(progressToItalic(mItalicSeekBar.getProgress()))
                .withBestEffort(mBestEffort.isChecked());
        String query = queryBuilder.build();

        Log.d(TAG, "Requesting a font. Query: " + query);
        FontRequest request = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                query,
                R.array.com_google_android_gms_fonts_certs);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        FontsContractCompat.FontRequestCallback callback = new FontsContractCompat
                .FontRequestCallback() {
            @Override
            public void onTypefaceRetrieved(Typeface typeface) {
                mDownloadableFontTextView.setTypeface(typeface);
                progressBar.setVisibility(View.GONE);
                mRequestDownloadButton.setEnabled(true);
            }

            @Override
            public void onTypefaceRequestFailed(int reason) {
                Toast.makeText(VerFuentes.this,
                        getString(R.string.request_failed, reason), Toast.LENGTH_LONG)
                        .show();
                progressBar.setVisibility(View.GONE);
                mRequestDownloadButton.setEnabled(true);
            }
        };
        FontsContractCompat
                .requestFont(VerFuentes.this, request, callback,
                        getHandlerThreadHandler());
    }

    private void initializeSeekBars() {
        mWidthSeekBar = findViewById(R.id.seek_bar_width);
        int widthValue = (int) (100 * (float) WIDTH_DEFAULT / (float) WIDTH_MAX);
        mWidthSeekBar.setProgress(widthValue);
        final TextView widthTextView = findViewById(R.id.textview_width);
        widthTextView.setText(String.valueOf(widthValue));
        mWidthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                widthTextView
                        .setText(String.valueOf(progressToWidth(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mWeightSeekBar = findViewById(R.id.seek_bar_weight);
        float weightValue = (float) WEIGHT_DEFAULT / (float) WEIGHT_MAX * 100;
        mWeightSeekBar.setProgress((int) weightValue);
        final TextView weightTextView = findViewById(R.id.textview_weight);
        weightTextView.setText(String.valueOf(WEIGHT_DEFAULT));
        mWeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightTextView
                        .setText(String.valueOf(progressToWeight(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mItalicSeekBar = findViewById(R.id.seek_bar_italic);
        mItalicSeekBar.setProgress((int) ITALIC_DEFAULT);
        final TextView italicTextView = findViewById(R.id.textview_italic);
        italicTextView.setText(String.valueOf(ITALIC_DEFAULT));
        mItalicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromuser) {
                italicTextView
                        .setText(String.valueOf(progressToItalic(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private boolean isValidFamilyName(String familyName) {
        return familyName != null && mFamilyNameSet.contains(familyName);
    }

    private Handler getHandlerThreadHandler() {
        if (mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("fonts");
            handlerThread.start();
            mHandler = new Handler(handlerThread.getLooper());
        }
        return mHandler;
    }

    /**
     * Converts progress from a SeekBar to the value of width.
     * @param progress is passed from 0 to 100 inclusive
     * @return the converted width
     */
    private float progressToWidth(int progress) {
        return progress == 0 ? 1 : progress * WIDTH_MAX / 100;
    }

    /**
     * Converts progress from a SeekBar to the value of weight.
     * @param progress is passed from 0 to 100 inclusive
     * @return the converted weight
       */
    private int progressToWeight(int progress) {
        if (progress == 0) {
            return 1; // The range of the weight is between (0, 1000) (exclusive)
        } else if (progress == 100) {
            return WEIGHT_MAX - 1; // The range of the weight is between (0, 1000) (exclusive)
        } else {
            return WEIGHT_MAX * progress / 100;
        }
    }

    /**
     * Converts progress from a SeekBar to the value of italic.
     * @param progress is passed from 0 to 100 inclusive.
     * @return the converted italic
     */
    private float progressToItalic(int progress) {
        return (float) progress / 100f;
    }
    private void screenshoot() {
        Date date = new Date();
        CharSequence now = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        String filename = Environment.getExternalStorageDirectory() + "/ScreenShooter/" + now + ".jpg";

        View root = getWindow().getDecorView();
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(root.getDrawingCache());
        root.setDrawingCacheEnabled(false);

        File file = new File(filename);
        file.getParentFile().mkdirs();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/*");
            startActivity(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void perfil (View view){
        Intent perfil = new Intent (this, Main2Activity.class);
        startActivity(perfil);
    }

    public void volver (View view){
        Intent volver = new Intent (this, Main2Activity.class);
        startActivity(volver);

    }

}
