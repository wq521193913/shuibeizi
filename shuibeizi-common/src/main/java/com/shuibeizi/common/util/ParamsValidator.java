package com.shuibeizi.common.util;

import com.shuibeizi.common.exception.CustomException;
import net.sf.json.JSONArray;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/4/28 0028 10:48
 * @modified:
 */
public class ParamsValidator {
    private static class InstanceObj{
        private static final ParamsValidator instance = new ParamsValidator();
    }

    public static ParamsValidator getInstance(){
        return InstanceObj.instance;
    }

    public <T> void getValidator(T var1, Class[] groups) throws CustomException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = null;
        if(null != groups && groups.length > 0){
            constraintViolations = validator.validate(var1,groups);
        }else {
            constraintViolations = validator.validate(var1);
        }
        List<String> messageList = null;
        if(constraintViolations.size() > 0){
            messageList = new ArrayList<>();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                messageList.add(constraintViolation.getMessage());
            }
            throw new CustomException(JSONArray.fromObject(messageList).toString());
        }
    }
}
