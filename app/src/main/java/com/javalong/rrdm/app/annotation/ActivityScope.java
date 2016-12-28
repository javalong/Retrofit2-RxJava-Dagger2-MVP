package com.javalong.rrdm.app.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by javalong on 16-12-27.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope{
}
