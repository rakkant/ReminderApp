package com.example.rakkant.reminderapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends Activity {

    private ArrayList<String> taskList;
    private ArrayAdapter<String> taskAdapter;
    ListView listview;


    private TextView DateTimeTxt;

    private final int ADD_TASK_REQUEST = 1;
    private BroadcastReceiver TimeReciever;
    private final String PREFS_TASKS = "tasks";
    private final String KEY_TASKS_LIST = "list";

    Button reminderbtn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        DateTimeTxt = (TextView) findViewById(R.id.dateTimeTxt);
        listview = (ListView) findViewById(R.id.taskListview);
        reminderbtn = (Button) findViewById(R.id.reminderbtn);
        taskList = new ArrayList<String>();

        String savedList = getSharedPreferences(PREFS_TASKS, MODE_PRIVATE).getString(KEY_TASKS_LIST, null);
        if (savedList != null) {
            String[] items = savedList.split(",");
            taskList = new ArrayList<String>(Arrays.asList(items));
        }



        taskAdapter = createAdapter(new ArrayList<Task>());
        listview.setAdapter(taskAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                taskSelected(i);
            }
        });

        TimeReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                    DateTimeTxt.setText(getCurrentTimeStamp());
                }
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
        DateTimeTxt.setText(getCurrentTimeStamp());
        registerReceiver(TimeReciever, new IntentFilter(Intent.ACTION_TIME_TICK));
    }



    public void addTaskClicked(View view) {
        Intent intent = new Intent(MainActivity.this, TaskDetail.class);
        startActivityForResult(intent, ADD_TASK_REQUEST);
    }

    public void reminderClicked(View view){

        Intent i=new Intent(MainActivity.this, AlarmManager.class);
        startActivity(i);

    }
    private static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm // dd-MM-yyyy");
        Date now = new Date();
        String strDate = sdf.format(now);
        return strDate;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_TASK_REQUEST) {
            if (resultCode == RESULT_OK){
                String task = data.getStringExtra(TaskDetail.EXTRA_TASK_DESCRIPTION);
                taskList.add(task);
                taskAdapter.notifyDataSetChanged();
            }
        }
    }

    private void taskSelected(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alertDialogBuilder.setTitle("Have you done it yet ?");
        alertDialogBuilder
                .setMessage(taskList.get(position))
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        taskList.remove(position);
                        taskAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),"Task is deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }


    private ArrayAdapter<String> createAdapter(ArrayList<Task> books){
        return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskList);

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}