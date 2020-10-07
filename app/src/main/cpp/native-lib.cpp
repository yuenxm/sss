#define LOG_TAG "NDK"
//#define LOG_NDEBUG 0

#include <jni.h>
#include <string>
#include <errno.h>
#include <android/log.h>
#include <marioApi.h>
#include <stdio.h>

#define STR_LEN 256

extern "C" JNIEXPORT jstring JNICALL
Java_com_blueeyescloud_ext_devicemaster_jni_NativeHelper_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    FILE   *stream;
    char   data[256];
    char    cmd[] = "su -c \"getprop ro.serialno\"";
    int     len;
    stream = popen(cmd, "r" );
    len = fread( data, sizeof(char), sizeof(data), stream);
    pclose(stream );
    if(len >= 0) {
        data[len] = '\0';
    }
    __android_log_print(ANDROID_LOG_INFO, LOG_TAG,"%s  data %s", cmd, data);
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
#if 0
    //int result = system("getprop ro.board.platform  > /data/data/com.example.myapplication/a.txt");
    //int result = system("setprop ro.board.platform  xiaomi");
    //__android_log_print(ANDROID_LOG_INFO, LOG_TAG,"getprop ro.board.platform  res %d", result);
    char cmd[] = "su -c \"echo 'longitude=118.11401:latitude=24.46876' > /data/gps/fifo\"";
    int result = system(cmd);
    //__android_log_print(ANDROID_LOG_INFO, LOG_TAG,"getprop ro.board.platform  res %d", result);
    __android_log_print(ANDROID_LOG_INFO, LOG_TAG,"%s  res %d %s", cmd, result, strerror(errno));
//   char data[256];
    marioInit(HUAWEI);
//   getBrand(data, sizeof(data));
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
   //return env->NewStringUTF(data);
#endif
}

extern "C" JNIEXPORT int JNICALL
Java_com_blueeyescloud_ext_devicemaster_jni_NativeHelper_marioInit(JNIEnv* env, jobject /* this */,  jint platform, jstring cacheDir) {
    int res;
    const char* cacheDirChar = env->GetStringUTFChars(cacheDir, 0);
    HOST_PLATFORM cPlatform = (HOST_PLATFORM) platform;
    res = marioInit(cPlatform, cacheDirChar);
    return (jint) res;
}

extern "C" JNIEXPORT int JNICALL
Java_com_blueeyescloud_ext_devicemaster_jni_NativeHelper_setAttrsBurst(JNIEnv* env, jobject /* this */,  jstring jsonStr){
    int res;
    const char * attrs = env->GetStringUTFChars(jsonStr, 0);
    res = setAttrBurst(attrs);
    env->ReleaseStringUTFChars(jsonStr, attrs);
    return (jint) res;
}

extern "C" JNIEXPORT int JNICALL
Java_com_blueeyescloud_ext_devicemaster_jni_NativeHelper_setGps(JNIEnv* env, jobject /* this */, jdouble longitude, jdouble latitude){
    int res;
    res = setGps(longitude, latitude);
    return (jint) res;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_blueeyescloud_ext_devicemaster_jni_NativeHelper_getAttr(JNIEnv* env, jobject /* this */,  jstring attr){
    int res;
    char value[STR_LEN];
    const char * attrChar = env->GetStringUTFChars(attr, 0);
    memset(value, 0, sizeof(value));
    res = getAttr(attrChar, value, sizeof(value));
    return env->NewStringUTF(value);
}

extern "C" JNIEXPORT jint JNICALL
Java_com_blueeyescloud_ext_devicemaster_jni_NativeHelper_checkAttr(JNIEnv* env, jobject /* this */,  jstring attr, jstring value){
    int res;
    const char * valueChar = env->GetStringUTFChars(value, 0);
    const char * key = env->GetStringUTFChars(attr, 0);
    res = checkAttr(key, valueChar);
    return (jint) res;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_blueeyescloud_ext_devicemaster_jni_NativeHelper_clearApp(JNIEnv* env, jobject /* this */,  jstring app){
    int res;
    char value[STR_LEN];
    const char * appChar = env->GetStringUTFChars(app, 0);

    res = clearApp(appChar);
    return (jint) res;
}