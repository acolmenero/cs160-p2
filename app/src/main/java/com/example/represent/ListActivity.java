package com.example.represent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {
    ListView representatives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        representatives = (ListView) findViewById(R.id.list_view);
        final String[] values = new String[] {"rep name", "rep name", "rep name", "rep name", "rep name"};
        MyAdapter adapter = new MyAdapter(this, values);
        representatives.setAdapter(adapter);
        representatives.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListActivity.this, values[i], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
