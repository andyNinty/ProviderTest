package andy.lee.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String newId;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addData = (Button) findViewById(R.id.add_data);
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 添加数据
                Uri uri = Uri.parse("content://andy.lee.myrecyclerview.provider/user");
                ContentValues values = new ContentValues();
                values.put("avatar_id", 1234);
                values.put("user_name", "andy");
                values.put("phone_number", "18365297886");
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);
                if (newId != null) {
                    Toast.makeText(MainActivity.this, "insert success", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询数据
                Uri uri = Uri.parse("content://andy.lee.myrecyclerview.provider/user");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        int avatarId = cursor.getInt(cursor.getColumnIndex("avatar_id"));
                        String name = cursor.getString(cursor.getColumnIndex("user_name"));
                        String number = cursor.getString(cursor.getColumnIndex("phone_number"));
                        mList.add(name + "\n" + number);
                        Log.d("MainActivity", "avatar id is " + avatarId);
                        Log.d("MainActivity", "name is " + name);
                        Log.d("MainActivity", "phone is " + number);
                    }
                    mListView.setAdapter(mAdapter);
                    cursor.close();
                }
            }
        });
        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新数据
                Uri uri = Uri.parse("content://andy.lee.myrecyclerview.provider/user/" + newId);
                ContentValues values = new ContentValues();
                values.put("avatar_id", 4321);
                values.put("user_name", "andy1");
                values.put("phone_number", "18765297886");
                getContentResolver().update(uri, values, null, null);
            }
        });
        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除数据
                Uri uri = Uri.parse("content://andy.lee.myrecyclerview.provider/user/" + newId);
                getContentResolver().delete(uri, null, null);
            }
        });
    }
}
