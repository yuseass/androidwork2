package com.example.sea.androidwork2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    //数据库适配对象,用于操作数据库
    DBAdapter db = null;
    ListviewAdapter listviewAdapter = null;

    private Button insert, delete, update, query;
    private EditText name, age, height;

    private ListView listview = null;
    public static List<Map<String, String>> list = new LinkedList<Map<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //创建数据库适配对象
        db = new DBAdapter(this);

        //创建数据库
        if (!db.createDB()) {
            Toast.makeText(this, "创建数据库失败!", Toast.LENGTH_SHORT).show();
            return;
        }


        db.deleteAll();



        insert = (Button) findViewById(R.id.insert);
        delete = (Button) findViewById(R.id.delete);
        update = (Button) findViewById(R.id.update);
        query = (Button) findViewById(R.id.select);

        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        height = (EditText) findViewById(R.id.height);


        listview = (ListView) findViewById(R.id.listview);

        list = getData();

        //创建自定义的Adapter对象--内部数据与ListView各项的对应关系在自定义的Adapter中实现
        listviewAdapter = new ListviewAdapter(this);
        //为ListView设置Adapter
        listview.setAdapter(listviewAdapter);
        listview.setOnItemClickListener(myListener);

        insert.setOnClickListener(insertListener);//插入监听
        delete.setOnClickListener(deleteListener);//删除监听
        update.setOnClickListener(updateListener);//修改监听
        query.setOnClickListener(queryListener);//查询监听

    }

    //ListView点击行事件的监听函数--不同于之前按钮的监听,整个ListView用一个监听器
    private int selected = -1;//-1为未选中状态
    AdapterView.OnItemClickListener myListener = new AdapterView.OnItemClickListener() {


        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            // TODO Auto-generated method stub

            if (selected == -1) {
                selected = arg2;
                arg1.setBackgroundColor(Color.rgb(255, 184, 0));
                name.setText(list.get(selected).get("name"));
                age.setText(list.get(selected).get("age"));
                height.setText(list.get(selected).get("height"));


            } else if (selected == arg2) {
                selected = -1;
                arg1.setBackgroundColor(Color.rgb(255, 255, 255));
                name.setText(null);
                age.setText(null);
                height.setText(null);


            } else {
                listview.getChildAt(selected).setBackgroundColor(Color.rgb(255, 255, 255));
                arg1.setBackgroundColor(Color.rgb(255, 184, 0));
                selected = arg2;
                name.setText(list.get(selected).get("name"));
                age.setText(list.get(selected).get("age"));
                height.setText(list.get(selected).get("height"));
            }

        }
    };

    //增加监听器
    Button.OnClickListener insertListener=new Button.OnClickListener(){


        @Override
        public void onClick(View v) {
            if(name.getText()!=null&&age.getText()!=null&&height.getText()!=null)
                if(db.insert(name.getText().toString(),
                    Integer.parseInt(age.getText().toString()),
                    Float.parseFloat(height.getText().toString()))){

                list=getData();
                listviewAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                name.setText("");
                age.setText("");
                height.setText("");

            }

        }
    };


    //删除监听器
    Button.OnClickListener deleteListener=new Button.OnClickListener(){


        @Override
        public void onClick(View v) {
            if(selected!=-1){
                list.remove(selected);
                listviewAdapter.notifyDataSetChanged();
                if(db.delete(Integer.parseInt(list.get(selected).get("id").toString()))) {
                    selected = -1;
                    Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    age.setText("");
                    height.setText("");
                }
            }
        }
    };

    //修改监听器
    Button.OnClickListener updateListener=new Button.OnClickListener(){


        @Override
        public void onClick(View v) {
            if(selected!=-1){
                int id=Integer.parseInt(list.get(selected).get("id"));
               if(db.update(id,name.getText().toString(),
                        Integer.parseInt(age.getText().toString()),
                        Float.parseFloat(height.getText().toString()))) {
                   list=getData();
                   listviewAdapter.notifyDataSetChanged();
                   Toast.makeText(MainActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                   name.setText("");
                   age.setText("");
                   height.setText("");
               }
            }
        }
    };

    //查询监听器
    Button.OnClickListener queryListener=new Button.OnClickListener(){


        @Override
        public void onClick(View v) {
                String rs=db.query(Integer.parseInt(age.getText().toString()));
                Toast.makeText(MainActivity.this,rs,Toast.LENGTH_SHORT).show();
            }

    };









    private List<Map<String, String>> getData() {
        //List对象
        list.clear();
        Map map;


        Cursor cursor = db.queryAll();
        while (cursor.moveToNext()) {
            map = new HashMap<String, String>();
            map.put("id", cursor.getString(0));
            map.put("name", cursor.getString(1));
            map.put("age", cursor.getString(2));
            map.put("height", cursor.getString(3));
            list.add(map);
        }
        cursor.close();
        return list;
    }
}
