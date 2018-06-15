package appdorks.tk.teamironchamps54thbranchrajendernagar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_menu, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemid = item.getItemId();
        switch (itemid)
        {
            case R.id.menu_item_about_app :
                Toast.makeText(this, "show about the app activity here", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_change_password :
                Toast.makeText(this, "show dialog to change app password", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "this is a bug, please report!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    protected void onRestart()
    {
        super.onRestart();
        Log.i(TAG, "onRestart: activity restarted, taking to MainActivity");
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }*/

}
