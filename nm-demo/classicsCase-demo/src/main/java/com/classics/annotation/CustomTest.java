package com.classics.annotation;

import java.lang.annotation.*;

/**
 * @author: gl_stars
 * @data: 2020年 09月 15日 14:53
 **/
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomTest {

    String name() default "";
}
