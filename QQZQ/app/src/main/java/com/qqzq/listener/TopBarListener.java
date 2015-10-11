package com.qqzq.listener;

/**
 * Created by jie.xiao on 10/10/2015.
 */
public interface TopBarListener {

    public final static int LEFT = 0;
    public final static int RIGHT = 1;
    public final static int BOTH = 2;

    public void leftButtonClick();

    public void rightButtonClick();

    public int getButtonType();
}
