#include <stdio.h>
#include "SampleDLL.h"
JNIEXPORT int JNICALL Java_SampleDLL_add(JNIEnv *env, jobject javaobj, jint num1, jint num2) 
{
	return num1+num2;
}