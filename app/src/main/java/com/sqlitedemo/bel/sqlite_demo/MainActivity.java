package com.sqlitedemo.bel.sqlite_demo;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nameEdit,ageEdit,genderEdit,idEdit;
    private Button addButton,showButton,updateButton,deleteButton;
    SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteHelper=new SQLiteHelper(this);

        nameEdit=(EditText)findViewById(R.id.name_id);
        ageEdit=(EditText)findViewById(R.id.age_id);
        genderEdit=(EditText)findViewById(R.id.gender_id);
        idEdit=(EditText)findViewById(R.id.uid_id);

        addButton=(Button)findViewById(R.id.add_btn_id);
        showButton=(Button)findViewById(R.id.show_btn_id);
        updateButton=(Button)findViewById(R.id.update_btn_id);
        deleteButton=(Button)findViewById(R.id.delete_btn_id);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameEdit.getText().toString();
                String age=ageEdit.getText().toString();
                String gender=genderEdit.getText().toString();

                long rowID=sqLiteHelper.addData(name,age,gender);
                if(rowID==-1)
                {
                    Toast.makeText(MainActivity.this, "ADD failed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "ADD success", Toast.LENGTH_SHORT).show();
                    nameEdit.setText("");
                    ageEdit.setText("");
                    genderEdit.setText("");
                }
            }
        });
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=sqLiteHelper.displayData();
                if(cursor.getCount()==0)
                {
                    showData("ERROR","NO DATA FOUND");
                }
                StringBuffer stringBuffer=new StringBuffer();
                while (cursor.moveToNext())
                {
                    stringBuffer.append("ID "+cursor.getString(0)+"\n");
                    stringBuffer.append("NAME "+cursor.getString(1)+"\n");
                    stringBuffer.append("AGE "+cursor.getString(2)+"\n");
                    stringBuffer.append("GENDER "+cursor.getString(3)+"\n\n");
                }
                showData("Result Set",stringBuffer.toString());
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=idEdit.getText().toString();
                String name=nameEdit.getText().toString();
                String age=ageEdit.getText().toString();
                String gender=genderEdit.getText().toString();
                Boolean isUpdate=sqLiteHelper.updateData(id,name,age,gender);
                if(isUpdate==true)
                {
                    Toast.makeText(MainActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=idEdit.getText().toString();
                int rid=sqLiteHelper.deleteData(id);
                if(rid>0)
                {
                    Toast.makeText(MainActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void showData(String title,String data)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(data);
        builder.setCancelable(true);
        builder.show();
    }
}
