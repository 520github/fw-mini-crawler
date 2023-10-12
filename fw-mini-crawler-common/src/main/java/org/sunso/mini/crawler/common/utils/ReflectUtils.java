package org.sunso.mini.crawler.common.utils;

import org.reflections.ReflectionUtils;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Set;

public class ReflectUtils {
    public static boolean containSuperType(Class<?> type, Class<?> superType) {
        Set<Class<?>> superClassSet = ReflectionUtils.getAllSuperTypes(type);
        if (superClassSet == null) {
            return false;
        }
        if (superClassSet.contains(superType)) {
            return true;
        }
//        for(Class clazz: superClassSet) {
//            if (clazz.equals(superType)) {
//                return true;
//            }
//        }
        return false;
    }
    public static Class getGenericClass(Type type, int i) {
        if (type instanceof ParameterizedType) { // 处理泛型类型
            return getGenericClass((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) { // 处理泛型擦拭对象
            return getGenericClass(((TypeVariable) type).getBounds()[0], 0);
        } else {// class本身也是type，强制转型
            return (Class) type;
        }
    }

    private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
        } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
            return getGenericClass(((TypeVariable) genericClass).getBounds()[0], 0);
        } else {
            return (Class) genericClass;
        }
    }
}
