package com.samcotec.ahlalquran;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.samcotec.ahlalquran.LoadPage.SETTING;
import static java.lang.System.out;

/**
 * Created by PC07 on 10/11/2017.
 */

public class group_list_view extends Fragment {


    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<MyData> data_1ist = new ArrayList<>();


    public group_list_view(){

    }

    public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat,JSONArray jsonArray)
    {
        View view = inflater.inflate(R.layout.fragment_group_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        //List r;
        try{
            for(int j = 0; j < jsonArray.length(); j++){
                JSONObject row = jsonArray.getJSONObject(j);

                int ID =  row.getInt("ID");
                String gName =  row.getString("gname");
                int gAdmin = row.getInt("gadmin");
                int gType = row.getInt("gtype");
                int rType = row.getInt("rtype");
                String sDate = row.getString("sdate");
                String eDate = row.getString("edate");
                String nDays = row.getString("ndays");
                int sFrom = row.getInt("sfrom");
                int sTo = row.getInt("sto");
                int pagesCount = row.getInt("pagescount");
                String adminName = "-";
//                if( gAdmin == SETTING.getID() )
                if( gAdmin == SETTING.getAPIID() )
                 adminName = SETTING.getFullName();

                 out.println(row);
                MyData data = new MyData(ID,gName,sDate,eDate,adminName);
                data_1ist.add(data);
            }
        } catch (JSONException e)
        {
            Log.e("List View", e.getMessage(),e);
        }
        out.println(SETTING.getData());
       adapter = new CustomAdapter( App.getApp() ,data_1ist);
       recyclerView.setAdapter(adapter);

        return view;
    }

}
