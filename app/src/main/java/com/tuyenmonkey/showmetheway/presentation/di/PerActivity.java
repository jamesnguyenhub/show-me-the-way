package com.tuyenmonkey.showmetheway.presentation.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by tuyen on 1/5/2016.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
