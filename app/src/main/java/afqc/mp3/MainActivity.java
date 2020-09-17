package afqc.mp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //iniciar las variables
    TextView playerPosition,playerDuration;
    SeekBar seekBar;
    ImageView btRew,btPlay,btPause,btFf;
    MediaPlayer mMediaPlayer;
    Handler mHandler = new Handler();
    Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asignar Variables

        playerPosition=(TextView)findViewById(R.id.player_position);
        playerDuration=(TextView)findViewById(R.id.player_duration);
        seekBar=(SeekBar) findViewById(R.id.seek_bar);
        btRew=findViewById(R.id.bt_rew);
        btPlay=findViewById(R.id.bt_play);
        btPause=findViewById(R.id.bt_pause);
        btFf=findViewById(R.id.bt_ff);



        //inicializar media player
        mMediaPlayer= MediaPlayer.create(this,R.raw.uno);
        // inicializamos runnable
        mRunnable = new Runnable() {
            @Override
            public void run() {
                // enviar progreso a seekbar

                seekBar.setProgress(mMediaPlayer.getCurrentPosition());
                // Handler post delay for 0.5 second
                mHandler.postDelayed(this,500);

            }
        };


        // obtener duracion de media player
        int duration = mMediaPlayer.getDuration();
        //convetir milisegundos a minutos y segundos
        String sDuration = convertFormat(duration);
        // enviar duracion al textview
        playerDuration.setText(sDuration);

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hide play boton
                btPlay.setVisibility(View.GONE);
                // Show pause boton
                btPause.setVisibility(View.VISIBLE);
                //start media player
                mMediaPlayer.start();
                //set max on seek bar
                seekBar.setMax(mMediaPlayer.getDuration());
                //start handler
                mHandler.postDelayed(mRunnable,0);







            }
        });
        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hide pause boton
                btPause.setVisibility(View.GONE);
                // Mostar play boton
                btPlay.setVisibility(View.VISIBLE);
                //pausar media player
                mMediaPlayer.pause();
                //Stop handler
                mHandler.removeCallbacks(mRunnable);
            }
        });

        btFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // obtener avanzado la position media player
                int currentPosition = mMediaPlayer.getCurrentPosition();
                // obtener la duracion de media player
                int duration= mMediaPlayer.getDuration();
                //verificar condicion
                if (mMediaPlayer.isPlaying() && duration != currentPosition){
                    // cuando media esta reproduciendo y la duracion no es igual a la position continua
                    // fast forward for 5 second
                    currentPosition= currentPosition + 5000;
                    // enviar current position a textview
                    playerPosition.setText(convertFormat(currentPosition));
                    //enviar progreso a seek bar
                    mMediaPlayer.seekTo(currentPosition);




                }
            }
        });


        btRew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // obtener continua posicion de media player
                int currentPosition = mMediaPlayer.getCurrentPosition();
                // Verificar condicion
                if (mMediaPlayer.isPlaying() && currentPosition > 5000){
                    // cuando media es reproducido y la corriente de position en greater than 5 segundos
                    //Rewind for 5 second
                     currentPosition = currentPosition - 5000;
                     // Obtener la corriente de la posicion
                    playerPosition.setText(convertFormat(currentPosition));
                    //enviar progreso a seek bar
                    mMediaPlayer.seekTo(currentPosition);





                }
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // Verificar condicion
                if (b){
                    // cuando drag the seek bar
                    //enviar progreso a seek bar
                    mMediaPlayer.seekTo(i);


                }
                // enviar curriente posicion en textview
                playerPosition.setText(convertFormat(mMediaPlayer.getCurrentPosition()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
       // hide pause boton
       btPause.setVisibility(View.GONE);
       // mostrar play boton
        btPlay.setVisibility(View.VISIBLE);
        // enviar media player a incioas posicion
        mMediaPlayer.seekTo(0);
    }
});






    }
    private String convertFormat(int duration){
        return String.format("%02d:%02d"
        , TimeUnit.MILLISECONDS.toMinutes(duration)
                ,TimeUnit.MILLISECONDS.toSeconds(duration)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));



    }



}