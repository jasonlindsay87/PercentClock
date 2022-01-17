package com.e.percentclock;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {


    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        //Retrieve the time//
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

        String dayString = String.format("%.2f", MainActivity.getDayPercent()) + "%";
        String monthString = String.format("%.2f", MainActivity.getMonthPercent()) + "%";
        String yearString = String.format("%.2f", MainActivity.getYearPercent()) + "%";

        //Construct the RemoteViews object//
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        //Update TextView Texts//
        views.setTextViewText(R.id.appwidget_day, calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()).toUpperCase(Locale.ROOT));
        views.setTextViewText(R.id.appwidget_day_text, dayString);
        views.setTextViewText(R.id.appwidget_month, calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()).toUpperCase(Locale.ROOT));
        views.setTextViewText(R.id.appwidget_month_text, monthString);
        views.setTextViewText(R.id.appwidget_year, Integer.toString(calendar.get(Calendar.YEAR)));
        views.setTextViewText(R.id.appwidget_year_text, yearString);

        //Create an Intent with the AppWidgetManager.ACTION_APPWIDGET_UPDATE action//
        Intent intentUpdate = new Intent(context, Widget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        //Update the current widget instance only, by creating an array that contains the widget’s unique ID//
        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        //Wrap the intent as a PendingIntent, using PendingIntent.getBroadcast()//
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(context, appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);

        //Send the pending intent in response to the user tapping the ‘Update’ TextView//
        views.setOnClickPendingIntent(R.id.appwidget_refresh, pendingUpdate);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final Context cont = context;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

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

}
