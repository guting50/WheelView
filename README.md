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
        implementation 'com.github.goodgt:WheelView:1.0.3'
    }
```
#### 代码中使用
##### 时间选择器
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
可以设置需要显示的默认时间，可以设置回显得时间格式，默认不能选择今天以前的日期，后期优化。
