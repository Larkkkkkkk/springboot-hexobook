package com.itheima.validation;

import com.itheima.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State,String> {    //实现ConstrainValidator接口<给哪个注解提供校验规则,校验的数据类型>

    //提供校验规则
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {  //value就是将来要校验的数据
        if(value==null){
            return false;
        }
        //只有是这两个其一才返回true
        if(value.equals("已发布")||value.equals("草稿")){
            return true;
        }
        return false;
    }

}
