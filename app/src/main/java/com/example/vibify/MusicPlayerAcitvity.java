package com.example.vibify;

import static com.example.vibify.MymediapPlayer.currentIndex;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerAcitvity extends AppCompatActivity {
TextView song_title,total_time,current_time;
SeekBar seek_bar;
ImageView music_icon,playing,next_play,pause_back;
    ArrayList<AudioModel> songList;
    AudioModel currentSong;
    MediaPlayer mediaPlayer=MymediapPlayer.getInstance();
    int x=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music_player_acitvity);

        song_title=findViewById(R.id.song_title);
        total_time=findViewById(R.id.total_time);
        current_time=findViewById(R.id.current_time);
        seek_bar=findViewById(R.id.seek_bar);
        playing=findViewById(R.id.playing);
        next_play=findViewById(R.id.next_play);
        pause_back=findViewById(R.id.back_play);
        music_icon=findViewById(R.id.music_icon);

        song_title.setSelected(true);

        songList=(ArrayList<AudioModel>)getIntent().getSerializableExtra("List");
        setResourceWithMusic();


        // Periodically update UI to reflect MediaPlayer state
        MusicPlayerAcitvity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {

                    seek_bar.setProgress(mediaPlayer.getCurrentPosition());
                    current_time.setText(convertMMSS(mediaPlayer.getCurrentPosition()+""));

                    if(mediaPlayer.isPlaying()){
                        playing.setImageResource(R.drawable.play);
                        music_icon.setRotation(x++);
                    }else{
                        playing.setImageResource(R.drawable.pause);

                    }
                }
                new Handler().postDelayed(this, 100); // Updates UI every 100ms
            }
        });


// SeekBar change listener
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);  // Move media player to the new position
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Handle the start of user interaction (optional)
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Handle the end of user interaction (optional)
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void setResourceWithMusic(){
        currentSong=songList.get(MymediapPlayer.currentIndex);
        song_title.setText(currentSong.getTitle());
        current_time.setText(convertMMSS(currentSong.getDuration()));
        total_time.setText(convertMMSS(String.valueOf(currentSong.getDuration())));

       playing.setOnClickListener(v->pauseMusic());
       next_play.setOnClickListener(v->nextMusic());
       pause_back.setOnClickListener(v->previousMusic());
       playMusic();
    }



    public void playMusic(){
      mediaPlayer.reset();
      try{
          mediaPlayer.setDataSource(currentSong.getPath());
          mediaPlayer.prepare();
          mediaPlayer.start();

          seek_bar.setProgress(0);
          seek_bar.setMax(mediaPlayer.getDuration());
      }catch (Exception e){
          e.printStackTrace();
      }

    }


    public void nextMusic(){
        if(MymediapPlayer.currentIndex==songList.size()-1){
            MymediapPlayer.currentIndex=0;
            return;
        }
      MymediapPlayer.currentIndex+=1;
      mediaPlayer.reset();
      setResourceWithMusic();

    }

    public void previousMusic(){
        if(MymediapPlayer.currentIndex==0){
            return;
        }
        MymediapPlayer.currentIndex-=1;
        mediaPlayer.reset();
        setResourceWithMusic();
    }

    public void pauseMusic(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            music_icon.setRotation(x);

        }else{
            mediaPlayer.start();
        }
    }

    public static String convertMMSS(String Duration) {
        // Parse the input Duration (which is in milliseconds as a String)
        long millis = Long.parseLong(Duration);

        // Calculate minutes and seconds
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes);

        // Format the time as "MM:SS" with two digits for both minutes and seconds
        return String.format("%02d:%02d", minutes, seconds);
    }

}