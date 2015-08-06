package io.github.dadude941.auraglowremote.ui;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.HashMap;

import io.github.dadude941.auraglowremote.AuraGlowCodes;
import io.github.dadude941.auraglowremote.R;
import io.github.dadude941.auraglowremote.ir.IRService;


public class MainActivity extends Activity implements View.OnClickListener {
    private boolean onOff = false;

    private static HashMap<Integer, Integer> codeLookup = new HashMap<>();
    private static HashMap<Integer, Integer> buttonColors = new HashMap<>();
    static {
        setupButton(R.id.up_button, AuraGlowCodes.BRIGHTNESS_UP, null);
        setupButton(R.id.down_button, AuraGlowCodes.BRIGHTNESS_DOWN, null);

        setupButton(R.id.green_button, AuraGlowCodes.COLOR_GREEN, 0xFF00AF46);
        setupButton(R.id.red_button, AuraGlowCodes.COLOR_RED, 0xFFFE363E);
        setupButton(R.id.blue_button, AuraGlowCodes.COLOR_BLUE, 0xFF0066CC);

        setupButton(R.id.red_button_2, AuraGlowCodes.COLOR_RED_2, 0xFFEB5D23);
        setupButton(R.id.blue_button_2, AuraGlowCodes.COLOR_BLUE_2, 0xFFAA87FF);
        setupButton(R.id.green_button_2, AuraGlowCodes.COLOR_GREEN_2, 0xFF28C3E7);

        setupButton(R.id.red_button_3, AuraGlowCodes.COLOR_RED_3, 0xFFEF5A20);
        setupButton(R.id.blue_button_3, AuraGlowCodes.COLOR_BLUE_3, 0xFFDF64F4);
        setupButton(R.id.green_button_3, AuraGlowCodes.COLOR_GREEN_3, 0xFF28B6F4);

        setupButton(R.id.red_button_4, AuraGlowCodes.COLOR_RED_4, 0xFFCDB92C);
        setupButton(R.id.blue_button_4, AuraGlowCodes.COLOR_BLUE_4, 0xFFFF4CCF);
        setupButton(R.id.green_button_4, AuraGlowCodes.COLOR_GREEN_4, 0xFF00FCFF);

        setupButton(R.id.flash_button, AuraGlowCodes.PATTERN_FLASH, null);
        setupButton(R.id.fade_button, AuraGlowCodes.PATTERN_FADE, null);
        setupButton(R.id.smooth_button, AuraGlowCodes.PATTERN_SMOOTH, null);
        setupButton(R.id.strobe_button, AuraGlowCodes.PATTERN_STROBE, null);
    }

    private static void setupButton(Integer id, Integer code, Integer color){
        codeLookup.put(id, code);
        if (color!=null) {
            buttonColors.put(id, color);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        int[] buttonIds = {R.id.onOff_button, R.id.up_button, R.id.down_button, R.id.red_button, R.id.green_button, R.id.blue_button, R.id.red_button_2, R.id.green_button_2, R.id.blue_button_2, R.id.red_button_3, R.id.green_button_3, R.id.blue_button_3, R.id.red_button_4, R.id.green_button_4, R.id.blue_button_4, R.id.flash_button, R.id.fade_button, R.id.strobe_button, R.id.smooth_button};
        for (int id: buttonIds){
            findViewById(id).setOnClickListener(this);
            Integer color = buttonColors.get(id);
            if (color!=null){
                findViewById(id).getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            }

        }

        Intent intent = new Intent(this, TestWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(intent);
        Log.d("[onCreate]", "Sent Setup Broadcast");
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
