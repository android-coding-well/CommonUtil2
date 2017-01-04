# CommonUtil2

* 集成了开发常用的工具类库lib_common
* demo里演示了mvp模式和dagger2的用法,以及rxjava的相关示例

## 通知
此库已拆分为

[AndroidUtils](https://github.com/huweijian5/AndroidUtils)

[AndroidBase](https://github.com/huweijian5/AndroidBase)


### 说明

* 为了避免引入第三方库导致工程依赖多个版本的问题，如android support库
* 故建议在个人的工程目录下的build.gradle下加入以下变量，具体请看此[build.gradle](https://github.com/huweijian5/CommonUtil2/blob/master/build.gradle)
```
ext{
    minSdkVersion = 16
    targetSdkVersion = 25
    compileSdkVersion = 25
    buildToolsVersion = '25.0.1'

    // App dependencies
   supportLibraryVersion = '25.0.1'
    junitVersion = '4.12'
    espressoVersion = '2.2.2'
    glideVersion='3.7.0'
}
```	
* 请注意，对于此库已有的变量，命名请保持一致

### 引用

* 如果需要用到lib_common,做法如下：
* Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```	
* and then,add the dependecy:
```
dependencies {
	        compile 'com.github.huweijian5:CommonUtil2:latest_version'
}
```
* 其中latest_version请到[releases](https://github.com/huweijian5/CommonUtil2/releases)中查看
