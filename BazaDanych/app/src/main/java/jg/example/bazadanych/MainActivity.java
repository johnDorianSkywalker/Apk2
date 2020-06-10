package jg.example.bazadanych;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;
import android.database.Cursor;
import android.util.Log;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public Button btnInsert;
    public Button btnDelete;
    public Button btnSelect;
    public Button btnSearch;
    public EditText editPesel;
    public EditText editName;
    public EditText editSurname;
    public EditText editAge;
    public EditText editSex;
    public EditText editDelete;
    public EditText editSearch;


    public ZarzadzajDanymi dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new ZarzadzajDanymi (this);

        btnInsert = (Button) findViewById(R.id.buttonInsert);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        btnSelect = (Button) findViewById(R.id.buttonSelectAll);
        btnSearch = (Button) findViewById(R.id.buttonSearch);

        editPesel = (EditText) findViewById(R.id.editTextPesel);
        editName = (EditText) findViewById(R.id.editTextImie);
        editSurname = (EditText) findViewById(R.id.editTextNazwisko);
        editAge = (EditText) findViewById(R.id.editTextWiek);
        editSex = (EditText) findViewById(R.id.editTextPlec);
        editDelete = (EditText) findViewById(R.id.editTextUsun);
        editSearch = (EditText) findViewById(R.id.editTextWyszukaj);

        btnSelect.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    public void showData (Cursor c) {
        while(c.moveToNext()) {
            Log.i(c.getString(1), c.getString(2) );
        }
    }

    @Override
    public void onClick (View v) {
switch (v.getId()) {
    case R.id.buttonInsert:
        dm.insert(Integer.parseInt(editPesel.getText().toString()), editName.getText().toString(),
                editSurname.getText().toString(), Integer.parseInt(editAge.getText().toString()), editSex.getText().toString());
        break;
    case R.id.buttonSelectAll:
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
startActivity(startIntent); // showData(dm.selectAll()); Przeniesione do SecondActivity //
            }
        });

        break;
    case R.id.buttonSearch:
        showData(dm.searchName(editSearch.getText().toString()));
        break;
    case R.id.buttonDelete:
        dm.delete(editDelete.getText().toString());
        break;
}
    }

}