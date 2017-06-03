package com.example.rakkant.reminderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class TaskDetail extends AppCompatActivity {
    public static final String EXTRA_TASK_DESCRIPTION = "task";

    private EditText desc;
    private  Spinner mySpinner;
    private String array_spinner[];
    private static final String DEFAULT_LOCAL = "Choose Amount";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_detail);

        desc = (EditText) findViewById(R.id.descriptionText);

        mySpinner=(Spinner) findViewById(R.id.spinner);
        array_spinner = getResources().getStringArray(R.array.amount_arrays);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,array_spinner);
        mySpinner.setAdapter(adapter);
        mySpinner.setSelection(adapter.getPosition(DEFAULT_LOCAL));


    }

    public void AfterClicked(View view) {
        String taskDescription = desc.getText().toString();

        if (!taskDescription.isEmpty()) {
            Intent result = new Intent();
            result.putExtra(EXTRA_TASK_DESCRIPTION, taskDescription);
            setResult(RESULT_OK, result);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    public void AfterSelected(View view) {
        String text = mySpinner.getSelectedItem().toString();

        if (!text.contentEquals("Choose Amount")) {
            Intent result = new Intent();
            result.putExtra(EXTRA_TASK_DESCRIPTION, text);
            setResult(RESULT_OK, result);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}