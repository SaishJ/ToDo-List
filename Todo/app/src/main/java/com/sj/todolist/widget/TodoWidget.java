package com.sj.todolist.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.sj.todolist.R;
import com.sj.todolist.activities.MainActivity;
import com.sj.todolist.activities.TodoAddActivity;
import com.sj.todolist.activities.TodoDetailsActivity;

public class TodoWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.todo_widget);

        rv.setOnClickPendingIntent(R.id.todo_text_view_widget, PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0));

        rv.setOnClickPendingIntent(R.id.todo_add_button_widget, PendingIntent.getActivity(context, 0, new Intent(context, TodoAddActivity.class), 0));

        Intent intent = new Intent(context, TodoWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        rv.setRemoteAdapter(R.id.todo_list_view_widget, intent);
        rv.setEmptyView(R.id.todo_list_view_widget, R.id.empty_view_widget);

        rv.setPendingIntentTemplate(R.id.todo_list_view_widget, PendingIntent.getActivity(context, 0, new Intent(context, TodoDetailsActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));

        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            updateWidgetListData(context);
        }
    }

    private void updateWidgetListData(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, TodoWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.todo_list_view_widget);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

