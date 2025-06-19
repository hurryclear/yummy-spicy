package com.yummy.annotation;

import com.yummy.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * self-defined annotation for marking the method need ...
 */

@Target(ElementType.METHOD) // mark that this annotation is used for method
@Retention(RetentionPolicy.RUNTIME) //???
public @interface AutoFill {
    OperationType value();
}
