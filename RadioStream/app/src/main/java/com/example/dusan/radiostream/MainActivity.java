package com.example.dusan.radiostream;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.btn_play)
  Button btnPlay;

  private MediaPlayer mMediaPlayer;
  private String mRadioStationURL = "http://205.164.62.22:8075";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
      mMediaPlayer.pause();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (mMediaPlayer != null) {
      mMediaPlayer.start();
    }
  }

  @OnClick(R.id.btn_play)
  public void onClickPlay() {

    if (mMediaPlayer == null) {
      mMediaPlayer = new MediaPlayer();
      mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
          mp.start();
          Drawable button = ContextCompat.getDrawable(getApplicationContext(),
              android.R.drawable.ic_media_pause);
          btnPlay.setBackground(button);
        }
      });
      try {
        mMediaPlayer.setDataSource(mRadioStationURL);
        mMediaPlayer.prepareAsync();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      if (mMediaPlayer.isPlaying()) {
        Drawable button = ContextCompat.getDrawable(getApplicationContext(),
            android.R.drawable.ic_media_play);
        btnPlay.setBackground(button);
        mMediaPlayer.pause();
      } else {
        mMediaPlayer.start();
        Drawable button = ContextCompat.getDrawable(getApplicationContext(),
            android.R.drawable.ic_media_pause);
        btnPlay.setBackground(button);
      }
    }
  }

  @Override
  protected void onDestroy() {
    if (mMediaPlayer != null) {
      mMediaPlayer.release();
      mMediaPlayer = null;
    }
    super.onDestroy();
  }
}
