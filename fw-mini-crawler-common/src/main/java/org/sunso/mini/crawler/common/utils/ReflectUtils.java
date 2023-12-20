package org.sunso.mini.crawler.common.utils;

import org.reflections.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Set;

public class ReflectUtils {

    public static boolean isAnnotationPresentRecursion(Field field, Class<? extends Annotation> annotationClass) {
        return getAnnotationRecursion(field, annotationClass) != null;
    }

    public static boolean isAnnotationPresentRecursion(Class<? extends Annotation> targetClass, Class<? extends Annotation> annotationClass) {
        return getAnnotationRecursion(targetClass, annotationClass) != null;
    }

    public static <T extends Annotation> T getAnnotationRecursion(Field field, Class<T> annotationClass) {
        if (field.isAnnotationPresent(annotationClass)) {
            return field.getAnnotation(annotationClass);
        }
        for (Annotation annotation: field.getAnnotations()) {
            T result = getAnnotationRecursion(annotation.annotationType(), annotationClass);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public static <T extends Annotation> T getAnnotationRecursion(Class<? extends Annotation> targetClass, Class<T> annotationClass) {
        if (targetClass.getName().startsWith("java.lang")) {
            return null;
        }
        Annotation annotations[] = targetClass.getAnnotations();
        for (Annotation annotation: annotations) {
            if (annotation.annotationType() == annotationClass) {
                return annotationClass.cast(annotation);
            }
            T result = getAnnotationRecursion(annotation.annotationType(), annotationClass);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

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
