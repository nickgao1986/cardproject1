
建立子项目EmotionRain
去掉test automation等内容
compile project(':emotionRain')

buildToolsVersion "25.0.0"
minSdkVersion 15
compile 'com.android.support:support-v4:23.1.1'


如何在Github发布自己的compile包

classpath 'com.github.dcendents:android-maven-gradle-plugin:1.5'


apply plugin: 'com.github.dcendents.android-maven'
group='com.github.trevonnling'

https://jitpack.io/
https://github.com/nickgao1986/cardProject

maven { url 'https://jitpack.io' }

