package com.sj.todolist.utils;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

import com.sj.todolist.widget.TodoWidget;

public class WidgetHelper {

    public static void updateWidget(Context context) {
        Intent intent = new Intent(context, TodoWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        context.sendBroadcast(intent);
    }
}
