package io.github.dadude941.auraglowremote.ui;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import io.github.dadude941.auraglowremote.R;
import io.github.dadude941.auraglowremote.nec.NECFactory;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private ConsumerIrManager irManager;
    private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //noinspection ResourceType
        irManager = (ConsumerIrManager)getSystemService(Context.CONSUMER_IR_SERVICE);
        testButton = (Button)findViewById(R.id.btn_test);
        testButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int[] pattern = NECFactory
                .createCommand()
                .addInteger(0x00F7C03F, (byte)32)
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
