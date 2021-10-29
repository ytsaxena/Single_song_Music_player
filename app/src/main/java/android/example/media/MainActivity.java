package android.example.media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.wifi.aware.DiscoverySession;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView play;
    private ImageView pause;
    private SeekBar seekbar;
    private MediaPlayer mediaplayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         play =findViewById(R.id.play);
         seekbar =findViewById(R.id.seekBar);
         seekbar.setClickable(false);
     // Media PLayer using local storage
      mediaplayer =MediaPlayer.create(MainActivity.this,R.raw.ayekhuda);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mediaplayer.isPlaying()){
                    mediaplayer.start();
                    play.setImageResource(R.drawable.ic_baseline_pause_24);                }
                else{
                    mediaplayer.pause();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                }

            }
        });
      //  mediaplayer.start();
        seekbar.setMax(mediaplayer.getDuration());
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                    mediaplayer.seekTo(progress);
                seekbar.setProgress(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//create handler to set progress
        Handler handler;
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                seekbar.setProgress(msg.what);
            }
        };


        new Thread(new Runnable() {
          @Override
          public void run() {
              while (mediaplayer!=null) {
                  try{
                      if(mediaplayer.isPlaying())
                      {
                          Message message = new Message();
                          message.what = mediaplayer.getCurrentPosition();
                          handler.sendMessage(message);
                         Thread.sleep(1);
                      }
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }
      }).start();





        // media from web
//        mediaplayer = new MediaPlayer();
//        try {
//            mediaplayer.setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                Toast.makeText(MainActivity.this, "Playing........" ,Toast.LENGTH_SHORT).show();
//                mp.start();
//
//                seekbar.setMax(mediaplayer.getDuration());
//                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        if(fromUser)
//                        mediaplayer.seekTo(progress);
//
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//
//                    }
//                });
//            }
//        });
      //  mediaplayer.prepareAsync();




    }
}