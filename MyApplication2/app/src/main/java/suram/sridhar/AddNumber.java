package suram.sridhar;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddNumber extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView1 = new TextView(this);
        textView1.setText("Enter Number 1:");
        TextView textView2 = new TextView(this);
        textView2.setText("Enter Number 2:");
        textView1.setText("Enter Number 1:");
        TextView textView3 = new TextView(this);
        textView3.setText("Sum:");

        final EditText result = new EditText(this);
        result.setEnabled(false);
        result.setText(":Result:");
        result.setId(R.id.result);

        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setId(R.id.number1);

        final EditText editText2 = new EditText(this);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText2.setId(R.id.number2);

        final Button button = new Button(this);
        button.setText("Calculate SUM ");
        button.setId(R.id.sum);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long number1 = Long.parseLong(editText.getText().toString());
                    Long number2 = Long.parseLong(editText2.getText().toString());
                    Long results = number1 + number2;
                    result.setText(results.toString());
                    hideSoftKeyBoard();
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setText("some thing bad happened with the input data");
                }
            }
        });


        final Button button2 = new Button(this);
        button2.setText("Calculate Subtract ");
        button2.setId(R.id.subtract);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long number1 = Long.parseLong(editText.getText().toString());
                    Long number2 = Long.parseLong(editText2.getText().toString());
                    Long results = number1 - number2;
                    result.setText(results.toString());
                    hideSoftKeyBoard();
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setText("some thing bad happened with the input data");
                }
            }
        });

        linearLayout.setLayoutParams(layoutParams);

        linearLayout.addView(textView1);
        linearLayout.addView(editText);
        linearLayout.addView(textView2);
        linearLayout.addView(editText2);

        linearLayout.addView(textView3);
        linearLayout.addView(result);
        linearLayout.addView(button);
        linearLayout.addView(button2);

        setContentView(linearLayout);
        Log.d("lifecycle", "onCreate invoked");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle", "onStart invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle", "onResume invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle", "onPause invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle", "onStop invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle", "onRestart invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "onDestroy invoked");
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
