package com.qqzq;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qqzq.config.Constants;
import com.qqzq.activity.RegisterActivity;
import com.robotium.solo.Solo;

/**
 * Created by jie.xiao on 15/9/9.
 */
public class RegisterActivityTest extends ActivityInstrumentationTestCase2<RegisterActivity> {

    private RegisterActivity activity;
    private Solo solo;

    private ImageView iv_return;
    private EditText edt_select_location;
    private EditText edt_phone_no;
    private EditText edt_verify_code;
    private EditText edt_password;
    private EditText edt_password_retype;
    private CheckBox cbox_qqzq_agreement;
    private TextView tv_qqzq_agreement;
    private Button btn_verify_code;
    private Button btn_register;

    public RegisterActivityTest() {
        super(RegisterActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        Bundle extras = new Bundle();
        extras.putString(Constants.EXTRA_SELECTED_LOCATION, "ABCDEFG");
        extras.putString(Constants.EXTRA_SELECTED_PROVINCE_CODE, "1");
        extras.putString(Constants.EXTRA_SELECTED_CITY_CODE, "10");
        intent.putExtras(extras);
        setActivityIntent(intent);

        initUI();
    }

    private void initUI() {
        solo = new Solo(getInstrumentation(), getActivity());
        activity = getActivity();
        iv_return = (ImageView) activity.findViewById(R.id.iv_return);
        tv_qqzq_agreement = (TextView) activity.findViewById(R.id.tv_qqzq_agreement);
        edt_select_location = (EditText) activity.findViewById(R.id.edt_select_location);
        edt_phone_no = (EditText) activity.findViewById(R.id.edt_phone_no);
        edt_verify_code = (EditText) activity.findViewById(R.id.edt_verify_code);
        edt_password = (EditText) activity.findViewById(R.id.edt_password);
        edt_password_retype = (EditText) activity.findViewById(R.id.edt_password_retype);
        cbox_qqzq_agreement = (CheckBox) activity.findViewById(R.id.cbox_qqzq_agreement);
        btn_verify_code = (Button) activity.findViewById(R.id.btn_verify_code);
        btn_register = (Button) activity.findViewById(R.id.btn_register);
    }

    @Override
    protected void tearDown() throws Exception {
        assertNotNull(solo);
        solo.finishOpenedActivities();

    }

    /*public void test1_sms_verify_code(){

    }*/

    public void test2_register() throws InterruptedException {

//        solo.enterText(edt_select_location, "XXXXX");
        solo.enterText(edt_phone_no, "13688390128");
        solo.enterText(edt_verify_code, "12345");
        solo.enterText(edt_password, "1234");
        solo.enterText(edt_password_retype, "1234");
        solo.clickOnView(cbox_qqzq_agreement);
        solo.clickOnView(btn_register);

        Thread.sleep(20 * 1000);
//        solo.waitForActivity(LoginActivity.class);
    }

}
