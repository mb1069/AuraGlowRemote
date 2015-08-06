package io.github.dadude941.auraglowremote.ir;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.util.Log;

import java.util.Arrays;

import io.github.dadude941.auraglowremote.AuraGlowCodes;
import io.github.dadude941.auraglowremote.nec.NECFactory;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class IRService extends IntentService {
    public static final String ACTION_NAME = "IR_MESSAGE";

    private ConsumerIrManager manager;

    public IRService() {
        super("IRService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("[onHandleIntent]", "Received " + intent.getAction());

        if (intent.getAction().equals(ACTION_NAME)) {
            int value = intent.getIntExtra("key", AuraGlowCodes.COLOR_GREEN);
            int[] pattern = formatMessage(value);
            Log.e("[onHandleIntent]", "Transmitting: " + Arrays.toString(pattern));
            manager = (ConsumerIrManager) this.getSystemService(Context.CONSUMER_IR_SERVICE);
            manager.transmit(38000, pattern);
        }
    }

    private int[] formatMessage(int val) {
        return NECFactory
                .createCommand()
                .addInteger(val, (byte) 32)
                .getPattern(5);
    }

}
