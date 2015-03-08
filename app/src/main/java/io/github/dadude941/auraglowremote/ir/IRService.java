package io.github.dadude941.auraglowremote.ir;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.net.Uri;
import android.util.Log;

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

    private ConsumerIrManager irManager;

    public IRService() {
        super("IRService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("[onHandleIntent]", "Received " + intent.getAction());

        if (intent.getAction().equals(ACTION_NAME)) {
            int value = intent.getIntExtra("key", AuraGlowCodes.COLOR_GREEN);
            int[] pattern = formatMessage(value);
            Log.e("[onHandleIntent]", "Transmitting: " + pattern);
            irManager = (ConsumerIrManager) this.getSystemService(Context.CONSUMER_IR_SERVICE);
            irManager.transmit(38000, pattern);
        }
    }

    private int[] formatMessage(int val) {
        return NECFactory
                .createCommand()
                .addInteger(val, (byte) 32)
                .getPattern(5);
    }

}
