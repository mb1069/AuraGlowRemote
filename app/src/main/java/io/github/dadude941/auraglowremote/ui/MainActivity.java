package io.github.dadude941.auraglowremote.ui;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import io.github.dadude941.auraglowremote.AuraGlowCodes;
import io.github.dadude941.auraglowremote.R;
import io.github.dadude941.auraglowremote.ir.IRService;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
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

        int[] buttonIds = {R.id.onOff_button, R.id.up_button, R.id.down_button, R.id.red_button, R.id.green_button, R.id.blue_button, R.id.red_button_2, R.id.green_button_2, R.id.blue_button_2, R.id.red_button_3, R.id.green_button_3, R.id.blue_button_3, R.id.red_button_4, R.id.green_button_4, R.id.blue_button_4, R.id.flash_button, R.id.fade_button, R.id.strobe_button, R.id.smooth_button};
        for (int id: buttonIds){
            findViewById(id).setOnClickListener(this);
        }

        Intent intent = new Intent(this, TestWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(intent);
        Log.dd("[onCreate]", "Sent Setup Broadcast");
    }

    @Override
    public void onClick(View v) {
        int val;

        switch (v.getId()) {
            case R.id.onOff_button:
                if (onOff) {
                    val = AuraGlowCodes.TURN_OFF;
                } else {
                    val = AuraGlowCodes.TURN_ON;
                }
                onOff = !onOff;
                break;
            default:
                val = codeLookup.get(v.getId());
                break;
        }
        sendMessage(val);
    }


    private void sendMessage(int val) {
        Intent irServiceIntent = new Intent(this, IRService.class);
        irServiceIntent.putExtra("key", val);
        irServiceIntent.setAction("IR_MESSAGE");
        Log.d("[mainActivity.sendMsg]", Integer.toHexString(val));
        this.startService(irServiceIntent);
    }


}
