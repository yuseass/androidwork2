package com.example.sea.androidwork2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static com.example.sea.androidwork2.MainActivity.list;

final class ViewHolder{
    public TextView id;
    public TextView name;
    public TextView age;
    public TextView height;

}


public final class ListviewAdapter extends BaseAdapter {

    //实例化布局对象---用于实例化每行的布局->View对象
    private LayoutInflater mInflater;

    public ListviewAdapter(Context context){

        this.mInflater = LayoutInflater.from(context);
    }

    //获取ListView的总行数
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    //获取指定的一行所对应的View对象--不存在的话则创建之
    // position--当前要显示的数据的位置(行号)
    // convertView--可利用的以前的View对象(上下滚动时,利用旧View对象显示新内容),
    //                       如果此项为空,则需要动态创建新的View对象
    // parent--父控件(上层的ListView)

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        //本行对应的容器对象
        ViewHolder holder = null;

        //如果该行的View为空, 则动态创建新的View
        //利用已有的View显示新数据,可以减少内存占用,优化响应速度
        if (convertView == null) {
            //先创建容器对象,以便后来向其中填充当前行中的控件对象
            holder=new ViewHolder();

            //实例化ListView的一行, root参数为空说明此View的父控件默认为为上层的ListView
            convertView = mInflater.inflate(R.layout.item, null);
            //获取内部的各个控件对象, 保存到容器对象中, 以后直接取来用即可--每个子控件对象只用一次findViewById()
            holder.id = (TextView)convertView.findViewById(R.id.id);
            holder.name = (TextView)convertView.findViewById(R.id.name);
            holder.age = (TextView)convertView.findViewById(R.id.age);
            holder.height = (TextView)convertView.findViewById(R.id.height);



            //设置容器对象为ListView当前行的Tag--建立容器类对象与ListView当前行的联系
            convertView.setTag(holder);
        }
        else {   //如果该行的View已经存在,则通过标记获取该行对应的对象
            holder = (ViewHolder)convertView.getTag();
        }



        //设置该行内的控件对象对应的属性，Adapter的作用（List<--->ListView）--- 如果不用ViewHolder则需要频繁使用findViewByID
        holder.id.setText((String)list.get(position).get("id"));
        holder.name.setText((String)list.get(position).get("name"));
        holder.age.setText((String)list.get(position).get("age"));
        holder.height.setText((String)list.get(position).get("height"));

        return convertView;//返回当前行对应的View对象
    }



}



