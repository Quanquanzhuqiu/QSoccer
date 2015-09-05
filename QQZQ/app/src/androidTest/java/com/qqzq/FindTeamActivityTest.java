package com.qqzq;

import android.test.ActivityInstrumentationTestCase2;

import com.qqzq.ui.FindTeamActivity;
import com.robotium.solo.Solo;

/**
 * Created by jie.xiao on 15/9/5.
 */
public class FindTeamActivityTest extends ActivityInstrumentationTestCase2<FindTeamActivity> {

    private FindTeamActivity activity;
    private Solo solo;

    public FindTeamActivityTest() {
        super(FindTeamActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        assertNotNull(solo);
        solo.finishOpenedActivities();

    }

    public void test1_loadTeamListFromBackend(){
//        activity.loadTeamListFromBackend();
    }
}
