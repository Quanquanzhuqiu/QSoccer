package com.qqzq;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qqzq.entity.EntTeamInfo;
import com.qqzq.entity.RequestJsonParameter;
import com.qqzq.ui.CreateTeamActivity;
import com.robotium.solo.Solo;

/**
 * Created by jie.xiao on 15/9/3.
 */
public class CreateTeamActivityTest extends ActivityInstrumentationTestCase2<CreateTeamActivity> {

    private CreateTeamActivity createTeamActivity;
    private Solo solo;
    private TextView tv_commit;
    private EditText edit_team_name;
    private EditText edit_team_id;
    private EditText edit_team_location;
    private EditText edit_team_detail;
    private CheckBox cbox_5_persons;
    private CheckBox cbox_7_persons;
    private CheckBox cbox_9_persons;
    private CheckBox cbox_11_persons;
    private RadioGroup radio_group_join_config;

    public CreateTeamActivityTest() {
        super(CreateTeamActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());

//        setActivityInitialTouchMode(false);

        createTeamActivity = getActivity();
        tv_commit = (TextView) createTeamActivity.findViewById(R.id.tv_commit);
        edit_team_id = (EditText) createTeamActivity.findViewById(R.id.edit_team_id);
        edit_team_name = (EditText) createTeamActivity.findViewById(R.id.edit_team_name);
        edit_team_location = (EditText) createTeamActivity.findViewById(R.id.edit_team_location);
        edit_team_detail = (EditText) createTeamActivity.findViewById(R.id.edit_team_detail);
        radio_group_join_config = (RadioGroup) createTeamActivity.findViewById(R.id.radio_group_join_config);
        cbox_5_persons = (CheckBox) createTeamActivity.findViewById(R.id.cbox_5_persons);
        cbox_7_persons = (CheckBox) createTeamActivity.findViewById(R.id.cbox_7_persons);
        cbox_9_persons = (CheckBox) createTeamActivity.findViewById(R.id.cbox_9_persons);
        cbox_11_persons = (CheckBox) createTeamActivity.findViewById(R.id.cbox_11_persons);
    }

    @Override
    protected void tearDown() throws Exception {
        assertNotNull(solo);
        solo.finishOpenedActivities();

    }

    public void test1_preconditions() throws InterruptedException {
        assertNotNull(tv_commit);
        assertNotNull(edit_team_id);
        Thread.sleep(10*1000);
    }

    public void test2_prepareRequestJson() {
        solo.enterText(edit_team_id, "111111");
        RequestJsonParameter<EntTeamInfo> mParameters = createTeamActivity.prepareRequestJson();
        assertNotNull(mParameters);
        assertNotNull(mParameters.getParameter().getTeamno());
        System.out.println(mParameters.getParameter().getTeamno());
    }

    public void test3_commit() throws InterruptedException {
        solo.enterText(edit_team_id, "222222");
        solo.enterText(edit_team_name,"ac milan");
        solo.enterText(edit_team_location,"四川省成都市");
        solo.enterText(edit_team_detail,"意甲俱乐部");
        solo.clickOnView(radio_group_join_config.getChildAt(0));
        solo.clickOnView(cbox_11_persons);
        assertEquals(View.VISIBLE, tv_commit.getVisibility());
        solo.clickOnView(tv_commit);

    }
}
