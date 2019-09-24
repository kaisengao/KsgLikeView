# KsgLikeView
Android UI动画 仿直播点赞飘心动画效果

### 添加依赖
``` gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

``` gradle  
 dependencies {
	implementation 'com.github.kaisengao:KsgLikeView:1.0.1'
}
```

### 效果Image
<img src="https://github.com/kaisengao/KsgLikeView/blob/master/images/45409B7666542572C7EF027817D2F2F4.jpg" width="200" height="370"/> <img src="https://github.com/kaisengao/KsgLikeView/blob/master/images/E608FBEAE81F351609EB9C877D6CC4B4.2019-09-24%2013_09_59.gif" width="200" height="370"/>

### Create View

```java
 <com.kaisengao.likeview.like.KsgLikeView
     android:id="@+id/live_view"
     android:layout_width="75dp"
     android:layout_height="0dp"
     android:layout_marginTop="100dp"
     app:ksg_default_image="@drawable/heart0"
     app:ksg_enter_duration="1500"
     app:ksg_curve_duration="4500"
     app:layout_constraintDimensionRatio="H,1:4"
     app:layout_constraintLeft_toLeftOf="parent"
     app:layout_constraintRight_toRightOf="parent"
     app:layout_constraintTop_toTopOf="parent"/>
```
### Add Images

添加单张图片资源
```java
mLikeView.addLikeImage(R.drawable.heart0);
```
数组形式
```java
Integer[] images = {R.drawable.heart1, R.drawable.heart2};
mLikeView.addLikeImages(images);
```
集合形式
```java
List<Integer> images = new ArrayList<>();
images.add(R.drawable.heart0);
images.add(R.drawable.heart1);
mLikeView.addLikeImages(images);
```
### SendFavor

```java
mLikeView.addFavor();
```
### Xml attributes
 attribute  | description 
 ------------- | -------------
 ksg_default_image | 默认favor图片（重要，因为是获取图片宽高的关键）
 ksg_enter_duration | 出场动画 时长
 ksg_curve_duration | 贝赛尔曲线动画 时长
 
 
