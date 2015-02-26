package io.github.dadude941.auraglowremote.ui;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import io.github.dadude941.auraglowremote.AuraGlowCodes;
import io.github.dadude941.auraglowremote.R;
import io.github.dadude941.auraglowremote.ir.IRThread;
import io.github.dadude941.auraglowremote.nec.NECFactory;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private IRThread irThread;

    private Handler handler;
    private boolean onOff = false;
    private static HashMap<Integer, Integer> codeLookup = new HashMap<>();
    static {
        codeLookup.put(R.id.up_button, AuraGlowCodes.BRIGHTNESS_UP);
        codeLookup.put(R.id.down_button, AuraGlowCodes.BRIGHTNESS_DOWN);

        codeLookup.put(R.id.green_button, AuraGlowCodes.COLOR_GREEN);
        codeLookup.put(R.id.red_button, AuraGlowCodes.COLOR_RED);
        codeLookup.put(R.id.blue_button, AuraGlowCodes.COLOR_BLUE);
        codeLookup.put(R.id.red_button_2, AuraGlowCodes.COLOR_RED_2);
        codeLookup.put(R.id.blue_button_2, AuraGlowCodes.COLOR_BLUE_2);
        codeLookup.put(R.id.green_button_2, AuraGlowCodes.COLOR_GREEN_2);
        codeLookup.put(R.id.red_button_3, AuraGlowCodes.COLOR_RED_3);
        codeLookup.put(R.id.blue_button_3, AuraGlowCodes.COLOR_BLUE_3);
        codeLookup.put(R.id.green_button_3, AuraGlowCodes.COLOR_GREEN_3);
        codeLookup.put(R.id.red_button_4, AuraGlowCodes.COLOR_RED_4);
        codeLookup.put(R.id.blue_button_4, AuraGlowCodes.COLOR_BLUE_4);
        codeLookup.put(R.id.green_button_4, AuraGlowCodes.COLOR_GREEN_4);

        codeLookup.put(R.id.flash_button, AuraGlowCodes.PATTERN_FLASH);
        codeLookup.put(R.id.fade_button, AuraGlowCodes.PATTERN_FADE);
        codeLookup.put(R.id.smooth_button, AuraGlowCodes.PATTERN_SMOOTH);
        codeLookup.put(R.id.strobe_button, AuraGlowCodes.PATTERN_STROBE);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        irThread = new IRThread(getApplicationContext());
        handler = irThread.getHandler();

        findViewById(R.id.onOff_button).setOnClickListener(this);
        findViewById(R.id.up_button).setOnClickListener(this);
        findViewById(R.id.down_button).setOnClickListener(this);

        findViewById(R.id.red_button).setOnClickListener(this);
        findViewById(R.id.green_button).setOnClickListener(this);
        findViewById(R.id.blue_button).setOnClickListener(this);

        findViewById(R.id.red_button_2).setOnClickListener(this);
        findViewById(R.id.green_button_2).setOnClickListener(this);
        findViewById(R.id.blue_button_2).setOnClickListener(this);

        findViewById(R.id.red_button_3).setOnClickListener(this);
        findViewById(R.id.green_button_3).setOnClickListener(this);
        findViewById(R.id.blue_button_3).setOnClickListener(this);

        findViewById(R.id.red_button_4).setOnClickListener(this);
        findViewById(R.id.green_button_4).setOnClickListener(this);
        findViewById(R.id.blue_button_4).setOnClickListener(this);

        findViewById(R.id.flash_button).setOnClickListener(this);
        findViewById(R.id.fade_button).setOnClickListener(this);
        findViewById(R.id.strobe_button).setOnClickListener(this);
        findViewById(R.id.smooth_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int val = 0;

        switch (v.getId()){
            case R.id.onOff_button:
                if (onOff){
                    val = AuraGlowCodes.TURN_OFF;
                } else {
                    val = AuraGlowCodes.TURN_ON;
                }
                onOff=!onOff;
                break;
            default:
                val = codeLookup.get(v.getId());
                break;
        }
        Log.wtf("[fuck]", Integer.toHexString(val));

        int[] pattern = NECFactory
                .createCommand()
                .addInteger(val, (byte)32)
                .getPattern(5);

        Message msg = handler.obtainMessage(IRThread.MSG_NAME, pattern);
        handler.sendMessage(msg);

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
