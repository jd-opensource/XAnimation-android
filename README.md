# XAnimation

## 1）项目介绍
本库是基于Lottie源码二次开发，可以将Lottie使用的json动画文件中的动画属性应用到Android原生控件上，快速完成原生控件动画的开发迭代，支持的Android原生属性动画有：

- 位移
- 透明度
- 旋转
- 缩放

## 2）演示效果
![](https://storage.360buyimg.com/xview/xanimation/xanimation-demo.gif)

## 3）使用方式

```java
XAnimation xAnimation = new XAnimation();
XAnimationOptions xAnimationOptions = new XAnimationOptions()
    //json文件assets地址或在线url链接
    .withLottieUrl(XAnimationConstants.ASSETS_FADE_IN_ANI)
    .withTargetView(mTargetView);

或者

xAnimationOptions.withLottieUrl(XAnimationConstants.ASSETS_FADE_IN_ANI)
    .withAnimationType(XAnimationType.TYPE_ASSIGN_VIEW)
    .withLayerName("logo512.png")//指定json中的"logo192.png"图层动画应用到原生控件上
    .withResourceEntryName("img_left");//原生控件id名称

xAnimation.startViewAnimation(mContext, xAnimationOptions);
```

| XAnimationOptions属性 | 含义                                                          |
|---------------------|-------------------------------------------------------------|
| targetView          | 原生控件                                                        |
| lottieUrl           | 在线json文件链接                                                  |
| layerName           | json文件中图层名称“nm”                                             |
| repeatCount         | 动画播放次数，-1: 循环播放，0：执行1次                                      |
| speed               | 动画播放速度                                                      |
| animationType       | 0: 指定原生控件执行动画、1: 指定原生容器里子控件执行动画、2: 指定json图层动画、3: 监听json动画进度 |
| resourceEntryName   | 原生控件 id 名称                                                  |

## 4）展望
未来，XAnimaiton通过动态下发Lottie url链接，可以使APP不发版的情况下，将APP中任意控件展示不同的动画，即动画动态下发。

## 5）License
```
Copyright 2022 JD, Inc. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```