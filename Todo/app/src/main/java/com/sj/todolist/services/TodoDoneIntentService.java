package com.sj.todolist.services;

import android.app.IntentService;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.sj.todolist.database.TodoContract;
import com.sj.todolist.utils.NotificationHelper;
import com.sj.todolist.utils.WidgetHelper;

public class TodoDoneIntentService extends IntentService {

    public TodoDoneIntentService() {
        super(TodoDoneIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        long id = intent.getLongExtra("id", 0);

        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoContract.TodoEntry.COLUMN_TODO_DONE, TodoContract.TodoEntry.TODO_DONE);
        getContentResolver().update(ContentUris.withAppendedId(TodoContract.TodoEntry.CONTENT_URI, id), contentValues, null, null);

        WidgetHelper.updateWidget(TodoDoneIntentService.this);

        new NotificationHelper(TodoDoneIntentService.this).dismissNotification((int) id);

        Toast.makeText(TodoDoneIntentService.this, "Todo Done.", Toast.LENGTH_SHORT).show();
    }
}
