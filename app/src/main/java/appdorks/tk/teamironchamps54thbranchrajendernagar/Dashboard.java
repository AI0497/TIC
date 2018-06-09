package appdorks.tk.teamironchamps54thbranchrajendernagar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity
{

    public static final String TAG = "Dashboard";

    private Button mBtnMemberList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_new_member);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(getApplicationContext(), AddMember.class));
                Log.i(TAG, "onClick(fab_add_new_member): fab clicked, starting Add Member Activity");
            }
        });

        mBtnMemberList = findViewById(R.id.btn_member_list);
        mBtnMemberList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MemberListActivity.class));
                Log.i(TAG, "onClick(btn_member_list): btn clicked, starting Member List Activity");
            }
        });

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.i(TAG, "onRestart: activity restarted, taking to MainActivity");
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    /*TODO: add logs for events*/

}
