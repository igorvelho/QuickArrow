package com.southdev.quickarrow.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    private ImageView _imgMaster;
    private ImageButton _btnUp;
    private ImageButton _btnDown;
    private ImageButton _btnLeft;
    private ImageButton _btnRight;
    Random _rnd = new Random();
    private int[] _imageArray = {R.drawable.up_grey, R.drawable.down_grey, R.drawable.left_grey, R.drawable.right_grey};
    private List<Integer> _arrows = new ArrayList<Integer>();
    private List<Integer> _arrowsLast = new ArrayList<Integer>();
    private int _lastImage = 0;
    private int _currentImage;
    private TextView _txtCountDown;
    private String _highscore;
    private TextView _txtScore;
    private int _score;
    private Countdown _counter;
    private RelativeLayout _relative;
    private boolean _running;
    private Context _context;
    private AlertDialog _alertFail;
    private AlertDialog.Builder _alertBuilder;
    private static final String AD_UNIT_ID = "ca-app-pub-2274766311409448/3344452960";
    private AdView adView;

    private float start = (float) 1.0;
    private float end = (float) 0.3;
    private int _clickRights = 0;
    private boolean _wrongClick = false;
    private boolean _paused = false;
    private Typeface _font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(AD_UNIT_ID);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainLayout);
        layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("5E99CFC56BFED08EE2951B1BCD1685F4")
                .build();

        adView.loadAd(adRequest);

        _context = this;

        _arrows.add(R.drawable.up);
        _arrows.add(R.drawable.down);
        _arrows.add(R.drawable.left);
        _arrows.add(R.drawable.right);
        _arrowsLast = new ArrayList<Integer>(_arrows);
        _font = Typeface.createFromAsset(getAssets(), "GameCube.ttf");

        _score = 0;

        _imgMaster = (ImageView) findViewById(R.id.imgArrow);

        _relative = (RelativeLayout) findViewById(R.id.mainLayout);

        _txtCountDown = (TextView) findViewById(R.id.txtCountdown);
        _txtCountDown.setTypeface(_font);
        _txtCountDown.setTextColor(Color.BLACK);

        _txtScore = (TextView) findViewById(R.id.txtScore);
        _txtScore.setTypeface(_font);
        _txtScore.setTextColor(Color.BLACK);

        _btnUp = (ImageButton) findViewById(R.id.btnUp);
        _btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
                if (_currentImage == 0 && (Integer) _btnUp.getTag() == R.drawable.up) {
                    AddScore();
                    LoadNext();
                } else if (_currentImage == 1 && (Integer) _btnUp.getTag() == R.drawable.down) {
                    AddScore();
                    LoadNext();
                } else if (_currentImage == 2 && (Integer) _btnUp.getTag() == R.drawable.left) {
                    AddScore();
                    LoadNext();
                } else if (_currentImage == 3 && (Integer) _btnUp.getTag() == R.drawable.right) {
                    AddScore();
                    LoadNext();
                } else
                    RemoveScore();
            }
        });

        _btnDown = (ImageButton) findViewById(R.id.btnDown);
        _btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
                if (_currentImage == 0 && (Integer) _btnDown.getTag() == R.drawable.up) {
                    AddScore();
                    LoadNext();
                } else if (_currentImage == 1 && (Integer) _btnDown.getTag() == R.drawable.down) {
                    AddScore();
                    LoadNext();
                } else if (_currentImage == 2 && (Integer) _btnDown.getTag() == R.drawable.left) {
                    AddScore();
                    LoadNext();
                } else if (_currentImage == 3 && (Integer) _btnDown.getTag() == R.drawable.right) {
                    AddScore();
                    LoadNext();
                } else
                    RemoveScore();
            }
        });

        _btnLeft = (ImageButton) findViewById(R.id.btnLeft);
        _btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
                if (_currentImage == 0 && (Integer) _btnLeft.getTag() == R.drawable.up) {
                    AddScore();
                    LoadNext();
                } else if (_currentImage == 1 && (Integer) _btnLeft.getTag() == R.drawable.down) {
                    AddScore();
                    LoadNext();
                } else if (_currentImage == 2 && (Integer) _btnLeft.getTag() == R.drawable.left) {
                    AddScore();
                    LoadNext();
                } else if (_currentImage == 3 && (Integer) _btnLeft.getTag() == R.drawable.right) {
                    AddScore();
                    LoadNext();
                } else
                    RemoveScore();
            }
        });

        _btnRight = (ImageButton) findViewById(R.id.btnRight);
        _btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
                if (_currentImage == 0 && (Integer) _btnRight.getTag() == R.drawable.up) {
                    AddScore();
                    LoadNext();
                } else if (_currentImage == 1 && (Integer) _btnRight.getTag() == R.drawable.down) {
                    AddScore();
                    LoadNext();
                } else if (_currentImage == 2 && (Integer) _btnRight.getTag() == R.drawable.left) {
                    AddScore();
                    LoadNext();
                } else if (_currentImage == 3 && (Integer) _btnRight.getTag() == R.drawable.right) {
                    AddScore();
                    LoadNext();
                } else
                    RemoveScore();
            }
        });

        _alertBuilder = new AlertDialog.Builder(this);
        _alertBuilder.setCancelable(false);
        _alertBuilder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        _txtScore.setText("0");
                        dialog.cancel();
                    }
                }
        );

        LoadNext();
        LoadHS();
        RandomizeButtons();
        Toast toast = Toast.makeText(this, "Click the arrows to start...", Toast.LENGTH_SHORT);
        toast.show();

        StartAnimations();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
        _paused = false;
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        _paused = true;
        super.onPause();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }

    /**
     * Called before the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        // Destroy the AdView.
        if (adView != null) {
            adView.destroy();
        }
        _paused = true;
        super.onDestroy();
    }

    private void StartAnimations() {
        AlphaAnimation anim = new AlphaAnimation(start, end);
        anim.setDuration(1000);
        anim.setRepeatCount(2);
        _btnUp.setAnimation(anim);

        anim = new AlphaAnimation(start, end);
        anim.setDuration(1000);
        anim.setRepeatCount(2);
        _btnLeft.setAnimation(anim);

        anim = new AlphaAnimation(start, end);
        anim.setDuration(1000);
        anim.setRepeatCount(2);
        _btnRight.setAnimation(anim);

        anim = new AlphaAnimation(start, end);
        anim.setDuration(1000);
        anim.setRepeatCount(2);
        _btnDown.setAnimation(anim);
    }

    private void LoadNext() {
        _wrongClick = false;
        RandomizeButtons();
        _currentImage = _rnd.nextInt(4);
        if (_currentImage == 4)
            _currentImage = 0;
        if (_currentImage == _lastImage) {
            if (_currentImage <= 3 && _currentImage > 0)
                _currentImage--;
            else if (_currentImage >= 0)
                _currentImage++;
        }
        System.out.println("Current: " + _currentImage);
        _lastImage = _currentImage;
        _imgMaster.setImageResource(_imageArray[_currentImage]);
        /*if (_clickRights == 15) {
            Toast toast = Toast.makeText(this, "+10", Toast.LENGTH_SHORT);

            toast.setGravity(Gravity.RIGHT | Gravity.TOP, +_txtCountDown.getLeft() - 120, _txtCountDown.getTop() + (_txtCountDown.getBottom() - _txtCountDown.getTop()) / 2);
            toast.show();
            long remainintTime = Long.valueOf(_txtCountDown.getText().toString().split(":")[1].trim());
            System.out.print("tempo restante: " + String.valueOf(remainintTime));
            _counter.cancel();
            _counter = new Countdown(((remainintTime * 1000) + 10000), 1000);
            _counter.start();
            _clickRights = 1;
        } else {
            _clickRights++;
        }*/
    }

    private void Start() {
        if (_running == false) {
            _btnUp.clearAnimation();
            _btnDown.clearAnimation();
            _btnLeft.clearAnimation();
            _btnRight.clearAnimation();
            adView.setVisibility(View.GONE);
            _running = true;
            _score = 0;
            _counter = new Countdown(700, 100);
            _counter.start();
            _txtScore.setText("0");
        }
    }

    private void LoadHS() {
        try {
            SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
            String strSavedMem1 = sharedPreferences.getString("HS", null);
            if (strSavedMem1 == null)
                strSavedMem1 = "0";
            _highscore = strSavedMem1;
        } catch (Exception e) {
            SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("HS", String.valueOf(_score));
            editor.commit();
        }
    }

    private void SaveHS() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String strSavedMem1 = sharedPreferences.getString("HS", null);
        if (strSavedMem1 != null) {
            if (Integer.valueOf(strSavedMem1) < _score) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("HS", String.valueOf(_score));
                editor.commit();
            }
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("HS", String.valueOf(_score));
            editor.commit();
        }
    }

    public void AddScore() {

        MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
        mp.start();
        _score++;
        _txtScore.setText(String.valueOf(_score));
        _counter.cancel();
        _counter = new Countdown(700, 100);
        _counter.start();
    }

    public void RemoveScore() {
        _counter.cancel();
        MediaPlayer mp = MediaPlayer.create(this, R.raw.error);
        mp.start();
        _running = false;
        adView.setVisibility(View.VISIBLE);
        _btnDown.setEnabled(false);
        _btnLeft.setEnabled(false);
        _btnRight.setEnabled(false);
        _btnUp.setEnabled(false);
        _txtCountDown.setText("TIME: 7");
        _txtCountDown.setTextColor(Color.BLACK);
        _wrongClick = true;
        SaveHS();
        LoadHS();
        _btnDown.setEnabled(true);
        _btnLeft.setEnabled(true);
        _btnRight.setEnabled(true);
        _btnUp.setEnabled(true);
        _clickRights = 0;

        ShowScore();

        StartAnimations();
    }

    public void ShowScore() {
        final Dialog dialog = new Dialog(_context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(_context);
        final View vScore = inflater.inflate(R.layout.score, null, false);
        vScore.setDrawingCacheEnabled(true);
        vScore.buildDrawingCache(true);
        dialog.setContentView(vScore);

        TextView textHeader = (TextView) dialog.findViewById(R.id.textHeader);
        textHeader.setTypeface(_font);

        TextView textName = (TextView) dialog.findViewById(R.id.textScoreName);
        textName.setTypeface(_font);
        TextView textName2 = (TextView) dialog.findViewById(R.id.textHighscoreName);
        textName2.setTypeface(_font);

        TextView text = (TextView) dialog.findViewById(R.id.textScore);
        text.setText("" + _score);
        text.setTypeface(_font);
        TextView text2 = (TextView) dialog.findViewById(R.id.textHighscore);
        text2.setText("" + _highscore);
        text2.setTypeface(_font);



        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _score = 0;
                RandomizeButtons();
                dialog.dismiss();
            }
        });

        Button dialogButtonShare = (Button) dialog.findViewById(R.id.dialogButtonShare);
        // if button is clicked, close the custom dialog
        dialogButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bmScreen = vScore.getDrawingCache();
                Uri imageUri = saveImage(_context, bmScreen);
                v.setDrawingCacheEnabled(false);

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                share.putExtra(Intent.EXTRA_SUBJECT, "Quick Arrow Score");
                share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.southdev.quickarrow.app");
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(share, "Share it"));

                _score = 0;
                RandomizeButtons();

                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public static Uri saveImage(Context context, Bitmap image) {
        try {

            CharSequence timeStampFormatted = DateFormat.format("yyyyMMdd_HHmmss", new Date());

            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

            File file = new File(storageDir, "QuickArrowScore_" + timeStampFormatted + ".jpg");

            OutputStream out = new FileOutputStream(file);

            image.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.close();

            ScanImageFolderByNewImageFile(context, file);

            return Uri.fromFile(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void ScanImageFolderByNewImageFile(Context context, File imageFile) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(imageFile);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public void RandomizeButtons() {
        //if (_score < 10) {
            _arrows.clear();
            _arrows.add(R.drawable.left);
            _arrows.add(R.drawable.right);
            _arrows.add(R.drawable.up);
            _arrows.add(R.drawable.down);

        /*} else if (_score >= 10 && _score < 30) {
            _arrows.clear();
            _arrows.add(R.drawable.right);
            _arrows.add(R.drawable.left);
            _arrows.add(R.drawable.down);
            _arrows.add(R.drawable.up);
        } else {
            while (_arrows.get(0) == _arrowsLast.get(0) ||
                    _arrows.get(1) == _arrowsLast.get(1) ||
                    _arrows.get(2) == _arrowsLast.get(2) ||
                    _arrows.get(3) == _arrowsLast.get(3)) {
                Collections.shuffle(_arrows);
            }
        }*/
        _arrowsLast = new ArrayList<Integer>(_arrows);
        _btnLeft.setBackgroundResource(_arrows.get(0));
        _btnLeft.setTag(_arrows.get(0));
        _btnLeft.setOnTouchListener(new ButtonHighlighterOnTouchListener(_btnLeft, _arrows.get(0)));

        _btnRight.setBackgroundResource(_arrows.get(1));
        _btnRight.setTag(_arrows.get(1));
        _btnRight.setOnTouchListener(new ButtonHighlighterOnTouchListener(_btnRight, _arrows.get(1)));

        _btnUp.setBackgroundResource(_arrows.get(2));
        _btnUp.setTag(_arrows.get(2));
        _btnUp.setOnTouchListener(new ButtonHighlighterOnTouchListener(_btnUp, _arrows.get(2)));

        _btnDown.setBackgroundResource(_arrows.get(3));
        _btnDown.setTag(_arrows.get(3));
        _btnDown.setOnTouchListener(new ButtonHighlighterOnTouchListener(_btnDown, _arrows.get(3)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class Countdown extends CountDownTimer {
        public Countdown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
//            if(_wrongClick) {
            if (_paused == false) {
                ShowScore();
            }
            _clickRights = 0;
            _txtCountDown.setText("TIME UP");
            SaveHS();
            LoadHS();
            _running = false;
            adView.setVisibility(View.VISIBLE);
            StartAnimations();
            // }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            System.out.print("tempo restante no ontick: " + String.valueOf(millisUntilFinished));
            /*if (millisUntilFinished / 1000 == 10) {
                _txtCountDown.setTextColor(Color.YELLOW);
            } else if (millisUntilFinished / 1000 == 5) {
                _txtCountDown.setTextColor(Color.RED);
            } else{
                _txtCountDown.setTextColor(Color.BLACK);
            }*/

            _txtCountDown.setText("Time: " + String.valueOf(millisUntilFinished / 100));
        }
    }
}
