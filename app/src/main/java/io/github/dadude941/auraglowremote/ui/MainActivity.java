package io.github.dadude941.auraglowremote.ui;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.github.dadude941.auraglowremote.AuraGlowCodes;
import io.github.dadude941.auraglowremote.R;
import io.github.dadude941.auraglowremote.nec.NECFactory;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private ConsumerIrManager irManager;
    private EditText editText;
    private boolean onOff = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //noinspection ResourceType
        irManager = (ConsumerIrManager)getSystemService(Context.CONSUMER_IR_SERVICE);

        findViewById(R.id.red_button).setOnClickListener(this);
        findViewById(R.id.green_button).setOnClickListener(this);
        findViewById(R.id.blue_button).setOnClickListener(this);
        findViewById(R.id.onOff_button).setOnClickListener(this);
        findViewById(R.id.up_button).setOnClickListener(this);
        findViewById(R.id.down_button).setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);

    }

    @Override
    public void onClick(View v) {
        int val = 0;

        switch (v.getId()){
            case R.id.red_button:
                val = AuraGlowCodes.COLOR_RED;
                break;
            case R.id.green_button:
                val = AuraGlowCodes.COLOR_GREEN;
                break;
            case R.id.blue_button:
                val = AuraGlowCodes.COLOR_BLUE;
                break;
            case R.id.onOff_button:
                if (onOff){
                    val = AuraGlowCodes.TURN_OFF;
                } else {
                    val = AuraGlowCodes.TURN_ON;
                }
                onOff=!onOff;
                break;
            case R.id.up_button:
                val = AuraGlowCodes.BRIGHTNESS_UP;
                break;
            case R.id.down_button:
                val = AuraGlowCodes.BRIGHTNESS_DOWN;
                break;
        }
        Log.wtf("[fuck]", Integer.toHexString(val));
        editText.setText(Integer.toHexString(val));

        int[] pattern = NECFactory
                .createCommand()
                .addInteger(val, (byte)32)
                .getPattern(5);
        irManager.transmit(38000, pattern);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
