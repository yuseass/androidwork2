# androidwork2
android第二次试验
实验内容	1. 创建一个SQLiteOpenHelper子类
（1）实现数据库创建功能；
（2）实现升级功能；
2. 实现数据库的增删改查操作
（1）上方控件中输入数值，点击添加，则将数据存入数据库中，同时下方的ListView添加一行；
（2）点击下方的一行，则该行内容显示到上方的的控件中，修改其内容，然后点击修改按钮，则更新数据库和ListView；
（3）先用（2）中操作选择一行，然后点击删除按钮，则从数据库和界面中删除选中的数据；
（4）上方输入年龄，点击查询按钮，则将查询结果（可能多个）用Toast显示出来。


算法描述及实验步骤	
1.	给应用设计布局。
2.	创建继承BaseAdapter的ListviewAdapter类，作为显示数据库内容的listview适配器.
3.	创建继承SQLiteOpenHelper的MyDBOpenHelper类，用于创建数据库和更新。创建DBAdapterl类，作为数据库适配器，封装了数据库操作常用方法
4.	在MainActivity中，给各个按钮以及ListView项添加监听器
