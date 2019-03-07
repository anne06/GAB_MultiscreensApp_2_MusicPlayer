package com.example.android.musicplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    private AudioManager audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * How to implement an android MediaPlayer ?
         */
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sorry_seems_to_be_the_hardest_word);
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        /**
         * Add button Listener on PLAY button
         */
        Button playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "In OnClick() Play button");
                mediaPlayer.start();
            }
        });

        /**
         * Add button Listener on PAUSE button
         */
        Button pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "In OnClick() Pause button");
                mediaPlayer.pause();
                }
        });

        /**
         * Add button Listener on + button
         */
        Button plusButton = findViewById(R.id.plus_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "In OnClick() + button");
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            }
        });

        /**
         * Add button Listener on - button
         */
        Button minusButton = findViewById(R.id.minus_button);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "In OnClick() - button");
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            }
        });
    }
}
