# KsgLikeView
Android 贝塞尔曲线点赞飘心动画效果

[![](https://jitpack.io/v/kaisengao/KsgLikeView.svg)](https://jitpack.io/#kaisengao/KsgLikeView)

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
	implementation 'com.github.kaisengao:KsgLikeView:1.2.2'
}
```

### 效果图
稍等 正在制作中...


### 基本使用

```java
 <com.kaisengao.likeview.like.KsgLikeView
     android:id="@+id/live_view"
     android:layout_width="75dp"
     android:layout_height="0dp"
     android:layout_marginTop="100dp"
     app:ksg_enter_duration="1500"
     app:ksg_curve_duration="4500"
     app:layout_constraintDimensionRatio="H,1:4"
     app:layout_constraintLeft_toLeftOf="parent"
     app:layout_constraintRight_toRightOf="parent"
     app:layout_constraintTop_toTopOf="parent"/>
```
### 添加资源

添加单张图片资源
```java
mLikeView.addLikeImage(R.drawable.heart0);
```
数组形式
```java
mLikeView.addLikeImages(
    R.drawable.heart0, R.drawable.heart1, R.drawable.heart2,
    R.drawable.heart3, R.drawable.heart4, R.drawable.heart5,
    R.drawable.heart6, R.drawable.heart7, R.drawable.heart8);
```
集合形式
```java
List<Integer> images = new ArrayList<>();
images.add(R.drawable.heart0);
images.add(R.drawable.heart1);
mLikeView.addLikeImages(images);
```
### 发送

```java
mLikeView.addFavor();
```
### Xml attributes
 attribute  | description 
 ------------- | -------------
 ksg_enter_duration | 进入动画 时长
 ksg_curve_duration | 路径动画 时长
 
 ###更新内容
 #### 1.2.1版本：优化整体代码，加入路径缓存
 #### 1.2.2版本：新功能，在列表中使用该View
 #### 	  （警告：在列表中使用如果调用了Notify刷新就会导致动画消失，因为Item刷新机制问题View会重新创建）
 ####      建议有需求的小伙伴手动刷新Item中的View较好。后续我会想想办法解决。
 

:kissing_heart:
 
