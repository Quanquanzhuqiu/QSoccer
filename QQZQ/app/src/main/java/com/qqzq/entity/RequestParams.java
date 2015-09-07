package com.qqzq.entity;

/**
 * Created by jie.xiao on 15/9/3.
 */
public class RequestParams<T> {

    private T parameter;

    public T getParameter() {
        return parameter;
    }

    public void setParameter(T parameter) {
        this.parameter = parameter;
    }
}
