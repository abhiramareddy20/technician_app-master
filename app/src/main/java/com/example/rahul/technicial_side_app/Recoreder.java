package com.example.rahul.technicial_side_app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Recoreder extends PhoneStateListener implements SensorEventListener {
    Context context;
    AudioManager audioManager;
    MediaRecorder recorder;
    private SensorManager mSensorManager;
    private Sensor myLightSensor;
    private boolean CallState;
    private float sensorState;

    public Recoreder(Context context) {
        this.context = context;
        mSensorManager = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        myLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        audioManager = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
        if (myLightSensor == null){
            Log.i("On Receive", "Not Support");
        }else{
            mSensorManager.registerListener(this,myLightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                System.out.println("My Call IDLE");
                CallState = false;
                StartAudioSpeacker();
                StopRecording();
                System.out.println("Is phone speaker : "+ audioManager.isSpeakerphoneOn());
                if (audioManager.isSpeakerphoneOn()) {
                    audioManager.setSpeakerphoneOn(false);
                    mSensorManager.unregisterListener(this);
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                System.out.println("My Call OFFHOOK");
                CallState = true;
                StartAudioSpeacker();
                StartRecording();
                System.out.println("Is phone speaker : "+ audioManager.isSpeakerphoneOn());
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                System.out.println("My Call RINGING");
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
            Log.i("Sensor Changed", "Accuracy :" + accuracy);
        }
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            Log.i("Sensor Changed", "onSensor Change :" + event.values[0]);
            sensorState = event.values[0];
            StartAudioSpeacker();
        }
    }

    public void StartAudioSpeacker(){
        if (CallState && sensorState == 1.0) {
            audioManager = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setSpeakerphoneOn(true);
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            audioManager.setStreamVolume(AudioManager.MODE_IN_CALL, audioManager.getStreamMaxVolume(AudioManager.MODE_IN_CALL), 1);
            System.out.println("Is phone speaker : "+ audioManager.isSpeakerphoneOn());
        }else{
            audioManager = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setSpeakerphoneOn(false);
            audioManager.setStreamVolume(AudioManager.MODE_IN_CALL, audioManager.getStreamMaxVolume(AudioManager.MODE_IN_CALL), 1);
            System.out.println("Speaker Volume :"+ audioManager.getStreamMaxVolume(AudioManager.MODE_IN_CALL));
            System.out.println("Is phone speaker : "+ audioManager.isSpeakerphoneOn());
        }
    }
    public void StartRecording(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(this.getFullSdPath());
        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        recorder.start();   // Recording is now started
        Log.i(this.getClass().getName(), "Start Recording");
    }

    public void StopRecording(){
        recorder.stop();
        recorder.reset();
        recorder.release();
        Log.i(this.getClass().getName(), "Stop Recording");
    }

    public String getFullSdPath(){
        File sdCard = new File(Environment.getExternalStorageDirectory()    + "/RecordMyVoice");
        if (!sdCard.exists()) {
            sdCard.mkdir();
        }
        File file = new File(Environment.getExternalStorageDirectory()+"/RecordMyVoice/",new Date().getTime()+".3gp");
        System.out.println("Full path of record sound is : "+file.getAbsolutePath());
        return file.getAbsolutePath();
    }
}
