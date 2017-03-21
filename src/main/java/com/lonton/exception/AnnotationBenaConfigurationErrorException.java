package com.lonton.exception;

/*
 * @author  cwt
 * 当解析注解错误时报出此异常
 */
public class AnnotationBenaConfigurationErrorException extends ConfigurationErrorException{
    
    /**  */
    private static final long serialVersionUID = 1L;

    public AnnotationBenaConfigurationErrorException(String message) {
        super(message);
    }
}
