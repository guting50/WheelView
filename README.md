[![](https://jitpack.io/v/goodgt/WheelView.svg)](https://jitpack.io/#goodgt/WheelView)

## 防ios时间选择器
#### 使用方法：
#### 依赖
将其添加到根build.gradle文件（而不是模块build.gradle文件）中：

```Xml
    allprojects {
        repositories {
            maven { url "https://jitpack.io" }
        }
    }
```
然后，将库添加到模块中 build.gradle
```Xml
    dependencies {
        implementation 'com.github.goodgt:WheelView:1.0.6'
    }
```
#### 代码中使用
##### 时间选择器：年月日时分，没有秒
###### 构造函数
```Jafa
     /**
     * @param context
     * @param minDate      最小可以选择的时间，默认不能选择今天以前的
     * @param defaultDate  当前选中的时间，默认为今天
     * @param endYear      最大可以选择的年份，比如2020年，默认比当前年大一年
     * @param resultFormat 返回的时间格式，默认为 yyyy-MM-dd HH:mm:ss
     * @param minInterval  分钟最小的间隔时间，默认为5分钟
     * @param ymdCallBack  点击确定回调接口
     */
    public TimeSelectorUtils(Context context, Date minDate, Date defaultDate, int endYear, String resultFormat, int minInterval, YMDCallBack ymdCallBack)；
```
###### 设置点击外面是否关闭
```Java
    void setOutsideTouchable(boolean outsideTouchable)
```
###### 使用
```Jafa
    new TimeSelectorUtils(MainActivity.this, new TimeSelectorUtils.YMDCallBack() {
        @Override
        public void setDate(PopupWindow popupWindow, String date) {
            Toast.makeText(MainActivity.this, date, Toast.LENGTH_LONG).show();
            popupWindow.dismiss();
        }
    }).getYmdPop().showAsDropDown(view);
```
###### 布局文件 pop_ymdpicker.xml
如果想修改布局，可以新建一个同名的布局文件，注意：文件名和布局中的id都不要变
```Xml
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#90000000"
        android:orientation="vertical">

        <View
            android:id="@+id/view_cancle"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1" />

        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="48dp">

            <View
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="#FFFFFF" />

            <RelativeLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content">

                <TextView
                    android:id="@+id/tv_cancle"
                    android:layout_width="wrap_content"
                    android:layout_height="match_parent"
                    android:gravity="center_vertical"
                    android:paddingLeft="12dp"
                    android:paddingRight="12dp"
                    android:text="取消"
                    android:textColor="#999999"
                    android:textSize="15sp" />

                <TextView
                    android:id="@+id/tv_sure"
                    android:layout_width="wrap_content"
                    android:layout_height="match_parent"
                    android:layout_alignParentRight="true"
                    android:gravity="center_vertical"
                    android:paddingLeft="12dp"
                    android:paddingRight="12dp"
                    android:text="确定"
                    android:textColor="#008577"
                    android:textSize="15sp" />
            </RelativeLayout>
        </FrameLayout>

        <View
            android:layout_width="match_parent"
            android:layout_height="2px"
            android:background="@android:color/white" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="240dp"
            android:layout_alignParentBottom="true"
            android:background="@android:color/white"
            android:orientation="horizontal">

            <com.gt.wheelview.LoopView
                android:id="@+id/loop_year"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_gravity="center"
                android:layout_weight="1"
                app:awv_centerTextSize="15sp"
                app:awv_isLoop="false"
                app:awv_outerTextSize="15sp" />

            <com.gt.wheelview.LoopView
                android:id="@+id/loop_month"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_gravity="center"
                android:layout_weight="1"
                app:awv_centerTextSize="15sp"
                app:awv_isLoop="false"
                app:awv_outerTextSize="15sp" />

            <com.gt.wheelview.LoopView
                android:id="@+id/loop_day"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_gravity="center"
                android:layout_weight="1"
                app:awv_centerTextSize="15sp"
                app:awv_isLoop="false"
                app:awv_outerTextSize="15sp" />

            <com.gt.wheelview.LoopView
                android:id="@+id/loop_hour"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_gravity="center"
                android:layout_weight="1"
                app:awv_centerTextSize="15sp"
                app:awv_isLoop="false"
                app:awv_outerTextSize="15sp" />

            <com.gt.wheelview.LoopView
                android:id="@+id/loop_minute"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_gravity="center"
                android:layout_weight="1"
                app:awv_centerTextSize="15sp"
                app:awv_isLoop="false"
                app:awv_outerTextSize="15sp" />
        </LinearLayout>

    </LinearLayout>
```
##### LoopView的使用
###### 自定义属性
```Xml
    <declare-styleable name="WheelView">
        <attr name="awv_centerTextColor" format="color" /> <!--当前选中项的字体颜色-->
        <attr name="awv_outerTextColor" format="color" /> <!--未选中项的字体颜色-->
        <attr name="awv_centerTextSize" format="dimension" /><!--当前选中项的字体大小-->
        <attr name="awv_outerTextSize" format="dimension" /><!--未选中项的字体大小-->
        <attr name="awv_dividerTextColor" format="color" /><!--中间分割线的颜色-->
        <attr name="awv_itemsVisibleCount" format="integer" /><!--可见项目的数量-->
        <attr name="awv_isLoop" format="boolean" /><!--是否循环滚动 默认为true-->
    </declare-styleable>
```
###### 布局中使用
```Xml
    <com.gt.wheelview.LoopView
        android:id="@+id/loop_day"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_gravity="center"
        android:layout_weight="1"
        app:awv_centerTextSize="15sp"
        app:awv_isLoop="false"
        app:awv_outerTextSize="15sp" />
```
###### 代码中使用
设置数据
```Java
 void setItems(List<String> items);
```
设置默认选中项
```Java
 void setInitPosition(int initPosition);
```
获取选中项
```Java
 int getSelectedItem();
```
