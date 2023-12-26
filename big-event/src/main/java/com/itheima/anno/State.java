package com.itheima.anno;

import com.itheima.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import java.util.List;

//元注解就是对现有的注解进行解释说明的注解
@Documented  //元注解
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})  //元注解 就是可以用到在哪里地方
@Retention(RetentionPolicy.RUNTIME) //元注解  在哪个阶段可以保留
@Constraint(validatedBy = {StateValidation.class})  //指示哪个类写的具体的校验标准
public @interface State {
    //1.提供校验失败后的提示信息
    String message() default "state只能是已发布或者草稿这两个状态";
    //2.指定注解分组(分组注解)  默认是Default分组
    Class<?>[] groups() default {};
    //3.负载  获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
