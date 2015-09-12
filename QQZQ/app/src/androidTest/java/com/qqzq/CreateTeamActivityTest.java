package com.qqzq;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qqzq.subitem.team.activity.CreateTeamActivity;
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
    private EditText edit_team_province;
    private EditText edit_team_city;
    private EditText edit_team_detail;
    private CheckBox cbox_5_persons;
    private CheckBox cbox_7_persons;
    private CheckBox cbox_9_persons;
    private CheckBox cbox_11_persons;
    private RadioGroup radio_group_join_config;
    private ImageView iv_logo;
    private TextView tv_camera;
    private TextView tv_photo;

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
        edit_team_id = (EditText) createTeamActivity.findViewById(R.id.edt_team_id);
        edit_team_name = (EditText) createTeamActivity.findViewById(R.id.edt_team_name);
        edit_team_province = (EditText) createTeamActivity.findViewById(R.id.edt_team_province);
        edit_team_city = (EditText) createTeamActivity.findViewById(R.id.edt_team_city);
        edit_team_detail = (EditText) createTeamActivity.findViewById(R.id.edt_team_detail);
        radio_group_join_config = (RadioGroup) createTeamActivity.findViewById(R.id.radio_group_join_config);
        cbox_5_persons = (CheckBox) createTeamActivity.findViewById(R.id.cbox_5_persons);
        cbox_7_persons = (CheckBox) createTeamActivity.findViewById(R.id.cbox_7_persons);
        cbox_9_persons = (CheckBox) createTeamActivity.findViewById(R.id.cbox_9_persons);
        cbox_11_persons = (CheckBox) createTeamActivity.findViewById(R.id.cbox_11_persons);
        iv_logo = (ImageView) createTeamActivity.findViewById(R.id.iv_logo);
        tv_camera = (TextView) createTeamActivity.findViewById(R.id.btn_alter_pic_camera);
        tv_photo = (TextView) createTeamActivity.findViewById(R.id.btn_alter_pic_photo);
    }

    @Override
    protected void tearDown() throws Exception {
        assertNotNull(solo);
        solo.finishOpenedActivities();

    }

/*    public void test1_preconditions() throws InterruptedException {
        assertNotNull(tv_commit);
        assertNotNull(edit_team_id);
    }

    public void test2_prepareRequestJson() {
        solo.enterText(edit_team_id, "111111");
        RequestJsonParameter<EntTeamInfo> mParameters = createTeamActivity.prepareRequestJson();
        assertNotNull(mParameters);
        assertNotNull(mParameters.getParameter().getTeamno());
        System.out.println(mParameters.getParameter().getTeamno());
    }*/

    public void test3_commit() throws InterruptedException {
        solo.enterText(edit_team_id, "4");
        solo.enterText(edit_team_name,"国际米兰");
        solo.enterText(edit_team_province,"四川");
        solo.enterText(edit_team_city,"成都");
        solo.enterText(edit_team_detail,"意甲俱乐部");
        solo.clickOnView(radio_group_join_config.getChildAt(0));
        solo.clickOnView(cbox_11_persons);
        assertEquals(View.VISIBLE, tv_commit.getVisibility());
        solo.clickOnView(tv_commit);
        Thread.sleep(10*1000);

    }

/*    public void test4_uploadLogo() throws InterruptedException {
        solo.clickOnView(iv_logo);
        solo.clickOnView(tv_camera);
        Thread.sleep(30*1000);
    }*/
}
