package edu.upenn.cis350.hw4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    public static final int StartButtonClickActivity_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = (Spinner) findViewById(R.id.difficulty_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void onStartButtonClick(View v) {
        Spinner spinner = (Spinner) findViewById(R.id.difficulty_spinner);
        String difficultyLevel = spinner.getSelectedItem().toString();
        Intent i = new Intent(this, QuizActivity.class);
        i.putExtra("DIFFICULTY_LEVEL", difficultyLevel);
        startActivityForResult(i, StartButtonClickActivity_ID);
    }
}
