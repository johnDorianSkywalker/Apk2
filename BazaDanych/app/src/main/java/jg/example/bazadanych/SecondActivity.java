package jg.example.bazadanych;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    public ZarzadzajDanymi zd;

    public Button BtnSELECT;
    TextView textViewResults = (TextView) findViewById( (R.id.textViewResults));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        zd = new ZarzadzajDanymi (this);

        BtnSELECT = (Button) findViewById(R.id.SELECTBtn);
        BtnSELECT.setOnClickListener(this);
    }

    public void showData (Cursor c) {
        while(c.moveToNext()) {
            Log.i(c.getString(1), c.getString(2) );
        }
    }

    public void onClick (View v) {
        BtnSELECT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(zd.selectAll());
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.kryteria_menu, menu);
        return true;
    }

}