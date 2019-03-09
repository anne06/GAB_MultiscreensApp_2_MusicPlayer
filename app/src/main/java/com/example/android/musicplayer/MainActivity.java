package com.example.android.musicplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 *
 * Gestion de l'audio : Important a savoir
 *
 * MediaPlayer gere play(), pause(), stop()...
 * il faut penser a liberer cette ressource: plusieurs endroits
 *     - dans le MediaPlayer.OnCompletionListener : quand la chanson est finie
 *     - avant un start(), au cas ou l'utilisateur aurait appuye plusieurs fois sur le bouton de lecture
 *     - dans la methode onStop() : si l'application passe en arriere plan
 *
 *
 *  **** Le pb de ca, c'est quand on fait pause() puis atart(), la chanson repart du debut...
 *  **** cette solution de release ne marche pas sur un morceau long
 *  **** ou alors, il y a un moyen de garder la position ou on se trouve dans la chanson
 *
 */
public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    @Override
    protected void onStop() {
        super.onStop();
      //  releaseMediaPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * How to implement an android MediaPlayer ?
         */
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);



        /**
         * Add button Listener on PLAY button
         */
        Button playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "In OnClick() Play button");

                /***
                 * We ask for an mAudioManager focus
                 */
                // Request audio focus for playback
                /*int result = mAudioManager.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback*/
                    //releaseMediaPlayer();
                mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sorry_seems_to_be_the_hardest_word);
                mMediaPlayer.start();
                //}


                /**
                 * Add a listener to handle the end of the song
                 */
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        /**
                         * At the end of the song, we have to release the MediaPlayer resource
                         */
                        Toast.makeText(getApplicationContext(), "I'm done", Toast.LENGTH_SHORT).show();
                        Log.d(LOG_TAG, "Morceau termine");
                    }
                });
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
                mMediaPlayer.pause();
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
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
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
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            }   
        });


    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        Log.d(LOG_TAG, "In the release Method");
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }
}
