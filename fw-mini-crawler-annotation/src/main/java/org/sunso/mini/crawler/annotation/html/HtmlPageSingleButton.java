package org.sunso.mini.crawler.annotation.html;

import org.sunso.mini.crawler.common.enums.HttpRequestEventEndFlagEnum;
import org.sunso.mini.crawler.common.enums.HttpRequestEventTypeEnum;

import java.lang.annotation.*;

/**
 * @author sunso520
 * @Title:HtmlPageSingleButton
 * @Description: 通过模拟浏览器点击按钮方式获取分页数据的注解
 *
 * @Created on 2023/11/01 15:11
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlPageSingleButton {

    /**
     * 访问的url
     * @return
     */
    String url();

    /**
     * 是否在当前页面直接追加分页数据
     * @return
     */
    boolean singlePageAppendData() default false;

    /**
     * 加载页面后的等待时间
     * @return
     */
    long waitTime() default 0;

    /**
     * 获取数据的开始下标
     * @return
     */
    int startDataIndex() default 0;

    /**
     * 获取数据的结束下标
     * @return
     */
    int endDataIndex() default Integer.MAX_VALUE;

    /**
     * 分页按钮的样式选择器
     * @return
     */
    String cssSelector();

    /**
     * 触发事件类型
     * @return
     */
    HttpRequestEventTypeEnum eventType() default HttpRequestEventTypeEnum.click;

    /**
     * 事件的结束标记
     * @return
     */
    HttpRequestEventEndFlagEnum eventEndFlag() default HttpRequestEventEndFlagEnum.noSuchElement;

    /**
     * 事件允许最大处理次数
     * @return
     */
    int eventDoMaxNum() default 100;

    /**
     * 事件扩展数据
     * @return
     */
    String extendData() default "";

    /**
     * 是否复制浏览器扩展参数
     * @return
     */
    boolean copyOption() default false;

    /**
     * 从第几页开始获取数据
     * @return
     */
    int startPage() default 1;
}
