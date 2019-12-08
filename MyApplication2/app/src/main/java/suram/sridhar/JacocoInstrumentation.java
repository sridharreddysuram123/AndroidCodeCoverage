package suram.sridhar;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class JacocoInstrumentation  extends Instrumentation implements InstrumentActivityListener {
    public static String TAG = "JacocoInstrumentation:";
    private static String DEFAULT_COVERAGE_FILE_PATH = null;
    private final Bundle mResults = new Bundle();
    private Intent mIntent;
    private static final boolean LOGD = true;
    private boolean mCoverage = true;
    private String mCoverageFilePath;

    public JacocoInstrumentation() {
        Log.d(TAG, "HELLO SRIDHAR");
    }
    @Override
    public void onCreate(Bundle arguments) {
        Log.d(TAG, "onCreate(" + arguments + ")");
        super.onCreate(arguments);




        if(arguments != null && arguments.getString("coverageFile")!=null)
        {
            DEFAULT_COVERAGE_FILE_PATH = arguments.getString("coverageFile");
        }
        else if(isExternalStorageReadable()&&isExternalStorageWritable())
        {
            Log.d(TAG, "isExternalStorageReadable Coverage file-----" );

            DEFAULT_COVERAGE_FILE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/coverage.ec";
            Log.d(TAG, "Coverage file got created @ ：" + DEFAULT_COVERAGE_FILE_PATH);

        }else
        {
            // bad notation, better use NAME+TimeSeed because you might generate more than 1 corage file
            DEFAULT_COVERAGE_FILE_PATH = getContext().getFilesDir().getPath().toString() + "/coverage.ec";

        }

        Log.d(TAG, "Coverage file got created @ ：" + DEFAULT_COVERAGE_FILE_PATH);

        // bad notation, better use NAME+TimeSeed because you might generate more than 1 corage file
//        DEFAULT_COVERAGE_FILE_PATH = getContext().getFilesDir().getPath().toString() + "/coverage.ec";
        DEFAULT_COVERAGE_FILE_PATH = "sdcard/Download/coverage.ec";

        Log.d(TAG, DEFAULT_COVERAGE_FILE_PATH);
        File file = new File(DEFAULT_COVERAGE_FILE_PATH);
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                Log.d(TAG,"File Exception ："+e);
                e.printStackTrace();}
        }
        if(arguments != null) {
            mCoverageFilePath = arguments.getString("coverageFile");
            Log.d(TAG, "coverageFile:::" +mCoverageFilePath);
        }
        mIntent = new Intent(getTargetContext(), InstrumentActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        start();
    }
    @Override
    public void onStart() {
        super.onStart();
        Looper.prepare();
        // Register broadcast receiver and start InstrumentActivity
        EndEmmaBroadcast broadcast = new EndEmmaBroadcast();
        InstrumentActivity activity = (InstrumentActivity) startActivitySync(mIntent);

        activity.setInstrumentActivityListener(this);
        broadcast.setInstrumentActivityListener(this);
        activity.registerReceiver(broadcast, new IntentFilter("suram.sridhar.EndEmmaBroadcast"));

        Log.d(TAG, "EndEmmaBroadcast regisetred:::");

    }
    private String getCoverageFilePath() {
        if (mCoverageFilePath == null) {
            return DEFAULT_COVERAGE_FILE_PATH;
        } else {
            return mCoverageFilePath;
        }
    }

    private void generateCoverageReport() {
        Log.d(TAG, "generateCoverageReport():" + getCoverageFilePath());
        OutputStream out = null;
        try {
            out = new FileOutputStream(getCoverageFilePath(), true);


            Object agent = Class.forName("org.jacoco.agent.rt.RT")
                    .getMethod("getAgent")
                    .invoke(null);
            out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class)
                    .invoke(agent, false));
        } catch (Exception e) {
            Log.d(TAG, e.toString(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onActivityEnd() {
        if (LOGD)      Log.d(TAG, "onActivityFinished()");
        if (mCoverage) {
            generateCoverageReport();
        }
        finish(Activity.RESULT_OK, mResults);
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}