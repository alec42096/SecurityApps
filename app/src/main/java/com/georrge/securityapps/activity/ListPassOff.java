package com.georrge.securityapps.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.georrge.securityapps.R;
import com.georrge.securityapps.contollers.AppListAdapter;

import java.util.LinkedList;
import java.util.List;

public class ListPassOff extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_off);
        PackageManager pm = this.getPackageManager();


        // create list of un-lock apps
        Intent intent = getIntent();
        List<String> po_apps = new LinkedList<>();
        int count = 0;
        String name_apps;
        do{
            name_apps = intent.getStringExtra("key_" + count++);
            if(name_apps != null)
                po_apps.add(name_apps);
        }while(name_apps != null);

        System.out.println("+++");
        System.out.println(po_apps);
        // Create ResolveInfo-List for unlock_apps (pass off apps)
        Intent main=new Intent(Intent.ACTION_MAIN, null);
        main.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> launchers = pm.queryIntentActivities(main, 0);

        List<ResolveInfo> unlock_launchers = new LinkedList<>();

        for(ResolveInfo lauch : launchers)
            for(String unlock_app : po_apps)
                if(lauch.activityInfo.packageName.equals(unlock_app))
                    unlock_launchers.add(lauch);

        launchers.clear();

        // create list view of inst.app.
        AppListAdapter appListAdapter = new AppListAdapter(this, pm, unlock_launchers);// create adaptor for  list view
        ListView appList = (ListView) findViewById(R.id.offPass_appList);       // get a main view list
        appList.setAdapter(appListAdapter);
        appList.setOnItemClickListener(this);           // set onclick method by items in list-view
        appList.clearChoices();
        appListAdapter.notifyDataSetChanged();
    }

    /* Clear input for  all apps */
    public void allSetPass(View view) {
        System.out.println("+++++++++++++++allSetPass");
    }

    /* It is method of selection for list view
*  Current item has visible buttons, other hidden buttons
* */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

    }
}
