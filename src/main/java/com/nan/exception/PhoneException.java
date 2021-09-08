package com.nan.exception;

import com.nan.enums.ResultEnum;

public class PhoneException extends RuntimeException{
    public PhoneException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
    }

    public PhoneException(String error) {
        super(error);
    }
}
