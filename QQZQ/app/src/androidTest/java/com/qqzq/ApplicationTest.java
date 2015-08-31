package com.qqzq;

import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<BaseApplication> {

    private BaseApplication baseApplication;


    public ApplicationTest() {
        super(BaseApplication.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //获取application之前必须调用的方法
        createApplication();
        //获取待测试的application
        baseApplication = getApplication();
    }

    public void testPostJson() {

        baseApplication.getJson();

    }
}