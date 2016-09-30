# CommonUtil2

* 集成了开发常用的工具类库lib_common
* demo里演示了mvp模式和dagger2的用法,以及rxjava的相关示例

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
