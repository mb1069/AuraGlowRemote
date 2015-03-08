package io.github.dadude941.auraglowremote.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import io.github.dadude941.auraglowremote.AuraGlowCodes;
import io.github.dadude941.auraglowremote.R;
import io.github.dadude941.auraglowremote.ir.IRService;

/**
 * Implementation of App Widget functionality.
 */
public class TestWidget extends AppWidgetProvider {
    public final static String action = "TEST_WIDGET.BUTTON";
    private final static String extraKey = "key";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d("[onUpdate]", "Widget setup");
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.test_widget);

        //On click listener for On Button
        Intent intent = new Intent(context, TestWidget.class);
        intent.setAction(action);
        intent.putExtra(extraKey, "ON");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.on_button, pendingIntent);

        //On click listener for Off Button
        Intent intent2 = new Intent(context, TestWidget.class);
        intent2.setAction(action);
        intent2.putExtra(extraKey, "OFF");

        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.off_button, pendingIntent2);

        ComponentName watchWidget = new ComponentName(context, TestWidget.class);
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getStringExtra(extraKey) != null) {
            Intent irServiceIntent = new Intent(context, IRService.class);
            irServiceIntent.setAction(IRService.ACTION_NAME);
            if (intent.getStringExtra(extraKey).equals("ON")) {
                irServiceIntent.putExtra("key", AuraGlowCodes.TURN_ON);
            } else {
                irServiceIntent.putExtra("key", AuraGlowCodes.TURN_OFF);
            }
            context.startService(irServiceIntent);
            Log.d("[onReceive]", "Sending service request to IRService");
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.test_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}


