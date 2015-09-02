package com.qqzq.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.qqzq.BaseActivity;
import com.qqzq.R;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.network.GsonRequest;

/**
 * Created by jie.xiao on 8/31/2015.
 */
public class CreateTeamActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        init();
    }

    private void init(){
        tv_commit = (TextView)findViewById(R.id.tv_commit);
        edit_team_id = (EditText)findViewById(R.id.edit_team_id);
        edit_team_name = (EditText)findViewById(R.id.edit_team_name);
        edit_team_location = (EditText)findViewById(R.id.edit_team_location);
        edit_team_detail = (EditText)findViewById(R.id.edit_team_detail);
        radio_group_join_config = (RadioGroup) findViewById(R.id.radio_group_join_config);
        cbox_5_persons = (CheckBox) findViewById(R.id.cbox_5_persons);
        cbox_7_persons = (CheckBox) findViewById(R.id.cbox_7_persons);
        cbox_9_persons = (CheckBox) findViewById(R.id.cbox_9_persons);
        cbox_11_persons = (CheckBox) findViewById(R.id.cbox_11_persons);

        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeRequest(new GsonRequest<EntTeamInfo>(Constants.API_CREATE_TEAM, EntTeamInfo.class,
                        responseListener(), errorListener()));
            }
        });
    }

    private EntTeamInfo initTeamInfo(){

        String teamId = edit_team_id.getText().toString();
        String teamName = edit_team_name.getText().toString();
        String teamLocation = edit_team_location.getText().toString();
        String teamDetail = edit_team_detail.getText().toString();

        String join_config = null;
        for(int i=0;i<radio_group_join_config.getChildCount();i++){
            RadioButton radioButton = (RadioButton) radio_group_join_config.getChildAt(i);
            if(radioButton.isChecked()){
                join_config = radioButton.getText().toString();
                break;
            }
        }

        String soccerPersons = null;
        if(cbox_5_persons.isChecked()){
            soccerPersons = "5";
        }else if (cbox_7_persons.isChecked()){
            soccerPersons = "7";
        }else if (cbox_9_persons.isChecked()){
            soccerPersons = "9";
        }else if (cbox_11_persons.isChecked()){
            soccerPersons = "11";
        }

        EntTeamInfo entTeamInfo = new EntTeamInfo();
        entTeamInfo.setTeamno(teamId);
        entTeamInfo.setTeamname(teamName);
        entTeamInfo.setJoinconfig(join_config);
        entTeamInfo.setOftensoccerpernum(soccerPersons);
        entTeamInfo.setSumary(teamDetail);
        return entTeamInfo;
    }

    private Response.Listener<EntTeamInfo> responseListener() {
        return new Response.Listener<EntTeamInfo>() {
            @Override
            public void onResponse(EntTeamInfo response) {
                System.out.println(response);
//                mTvResult.setText(new Gson().toJson(response));
            }
        };
    }
}
