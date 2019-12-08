package suram.sridhar;

import android.util.Log;


public class InstrumentActivity extends AddNumber {
    public static String TAG = "InstrumentActivity";
    private InstrumentActivityListener listener;

    public void setInstrumentActivityListener(InstrumentActivityListener listener) {
        this.listener = listener;
    }

    // Generate output report when the activity is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        super.finish();
        if (listener != null) {
            listener.onActivityEnd();
        }
    }
}