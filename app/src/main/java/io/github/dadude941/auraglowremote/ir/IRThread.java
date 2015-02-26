package io.github.dadude941.auraglowremote.ir;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by Miguel on 26/02/2015.
 */
public class IRThread {
    private final Context context;
    private ConsumerIrManager irManager;
    public static final int MSG_NAME = 1;
    
    public IRThread(Context context){
        this.context = context;
    }
    
    public Handler getHandler(){
        return handler;
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            //noinspection ResourceType
            irManager = (ConsumerIrManager)context.getSystemService(Context.CONSUMER_IR_SERVICE);
            irManager.transmit(38000, (int[]) msg.obj);
        }
    };


}
