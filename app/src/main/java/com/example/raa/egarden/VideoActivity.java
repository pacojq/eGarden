package com.example.raa.egarden;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    public final static String PATH = "android.resource://com.example.raa.egarden/";

    private VideoView videoView;
    private MediaController mediaController;

    private Button btnAutomation;
    private Button btnWildlife;
    private Button btnCompost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.videoView = findViewById(R.id.videoView);
        this.mediaController = new	MediaController(this);
        this.mediaController.setMediaPlayer(this.videoView);
        this.videoView.setMediaController(this.mediaController);

        String path = PATH + R.raw.domotica;
        this.videoView.setVideoPath(path);

        this.btnAutomation = findViewById(R.id.btnAutomation);
        this.btnAutomation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)	{
                if (videoView.isPlaying())
                    videoView.pause();
                String path = PATH + R.raw.domotica;
                videoView.setVideoPath(path);
            }
        });

        this.btnWildlife = findViewById(R.id.btnWildlife);
        this.btnWildlife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)	{
                if (videoView.isPlaying())
                    videoView.pause();
                String path = PATH + R.raw.wildlife_friendly;
                videoView.setVideoPath(path);
            }
        });

        this.btnCompost = findViewById(R.id.btnCompost);
        this.btnCompost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)	{
                if (videoView.isPlaying())
                    videoView.pause();
                String path = PATH + R.raw.compost;
                videoView.setVideoPath(path);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(VideoActivity.this, MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}
