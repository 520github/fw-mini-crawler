package org.sunso.mini.crawler.annotation.request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunso520
 * @Title:RequestUrl
 * @Description: RequestUrl注解 获取request url值
 * @Created on 2023/10/30 13:25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestUrl {

}
