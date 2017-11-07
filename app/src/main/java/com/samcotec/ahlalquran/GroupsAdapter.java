package com.samcotec.ahlalquran;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.samcotec.ahlalquran.LoadPage.SETTING;
import static java.lang.System.out;


/**
 * Created by MyTh on 01/08/2017.
 */

class GroupsAdapter {

    static private final String TABLE = "telawah_groups";

    private String
            gName,
            sDate,
            eDate,
            nDays;

    private int
            oldID,
            ID,
            gAdmin,
            sFrom,
            sTo,
            gType,
            rType,
            pagesCount;

    private static JSONObject
            APIjsonR = null,
            APIr = null;

    private static JSONArray
            APIpublicG = null,
            APImyG = null;


    private boolean newOne;


    public GroupsAdapter(
            String groupName, String startDate, String endDate, String nameDays,
            int groupAdmin, int groupType, int readerType, int startFrom, int startTo, int pagesCount
    ){
        this.gName = groupName;
        this.sDate = startDate;
        this.eDate = endDate;
        this.nDays = nameDays;
        this.gAdmin = groupAdmin;
        this.gType = groupType;
        this.rType = readerType;
        this.sFrom = startFrom;
        this.sTo = startTo;
        this.pagesCount = pagesCount;
        this.newOne = true;
    }

    public GroupsAdapter( ){
        this.newOne = true;
    }

    @Override
    public String toString() {
        return "GroupsAdapter";
    }

    public GroupsAdapter( int ID ){

        JSONArray group = null ;
        try{
            group = dbClass.getDb().getData( TABLE ," ID = '"+ID+"'" ) ;
            if( group.length() > 0 ){
                JSONObject data = group.getJSONObject(0);

                this.ID = data.getInt("ID");
                this.gName = data.getString("gname");
                this.gAdmin = data.getInt("gid");
                this.gType = data.getInt("gtype");
                this.rType = data.getInt("rtype");
                this.sDate = data.getString("sdate");
                this.eDate = data.getString("edate");
                this.nDays = data.getString("ndays");
                this.sFrom = data.getInt("sfrom");
                this.sTo = data.getInt("sto");
                this.pagesCount = data.getInt("pagescount");
                this.oldID = this.ID;
            }
        }
        catch (Exception e ){
            Log.e( this.toString(), e.getMessage(), e);
        }

        this.newOne = group == null;
    }

    public static JSONObject getGroup( boolean debug ){
        JSONArray data = dbClass.getDb().getData( TABLE, null ) ;
        JSONObject group = new JSONObject();
        if ( data.length() > 0 )    {

            try {
                group = data.getJSONObject(0);
            }
            catch (Exception e){
                Log.e("GroupAD getGroup", "error no data");
            }
        }

        if( debug ) Log.d( "Debug GroupAD", data.toString() );
        return  group;
    }

    public static JSONArray getGroups( boolean debug ){
        JSONArray data = dbClass.getDb().getData( TABLE, null ) ;
        if (debug ) Log.d( "Debug GroupAD", data.toString() );
        return  data;
    }

    public static JSONArray getApiGroups(){
        //return  DB.getData(TABLE, null, true);

        nHRJ.ajaxDone done = new nHRJ.ajaxDone() {

            @Override
            public void ajax(String resualt) {


                try{
                    APIjsonR = new JSONObject( resualt );
                    APIr = APIjsonR.getJSONObject("client");
                    APIpublicG = APIr.getJSONArray("public");
                    APImyG = APIr.getJSONArray("my");
                    int myG = APImyG.length();
                    for(int k=0;k< myG; k++){
                        JSONObject row = APImyG.getJSONObject(k);
                        Integer ID =  row.getInt("ID");
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
                        String NameDays = row.getString("nameDays");
                        String membersNames = row.getString("names");
                        int membersIDS = row.getInt("ids");
                        String adminName = row.getString("adminName");
                        int countReaders = row.getInt("countReaders");
                        String rtypeName = row.getString("rtypeName");
                        String gtypeName = row.getString("gtypeName");

                        GroupsAdapter group = new GroupsAdapter(gName,sDate,eDate,nDays,gAdmin,gType,rType,sFrom,sTo,pagesCount);

                        group.doInSql();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        nHRJ.ajaxString doneS = new nHRJ.ajaxString(){
            @Override
            public String ajax(String resualt ) {

                return null;
            }
        };

        nHRJ.ajaxInt donei = new nHRJ.ajaxInt(){
            @Override
            public int ajax( String resualt ) {

                return 0;
            }
        };

        nHRJ c = new nHRJ( "ahl.samcotec.com/api/?getgroups&t=l,s", done, doneS, donei );
        c.setPostparam( "user", String.valueOf( SETTING.getMobile( ) ) );
        if( tools.amIConnected( App.getApp() ) )
            c.start();
        else
            tools.alertNoI( App.getApp() );

        JSONArray res = new JSONArray();
        res.put(APImyG);
        res.put(APIpublicG);
        out.println(APIpublicG);
        return res;

    }

    public boolean doInSql() {
        long thisId = 0 ;
        int upd = 0 ;

        try {
            JSONObject vals = new JSONObject();
            vals.put("gname", this.gName);
            vals.put("gadmin", this.gAdmin);
            vals.put("gtype", this.gType);
            vals.put("rtype", this.rType);
            vals.put("sdate", this.sDate);
            vals.put("edate", this.eDate);
            vals.put("ndays", this.nDays);
            vals.put("sfrom", this.sFrom);
            vals.put("sto", this.sTo);
            vals.put("pagescount", this.pagesCount);
//            out.println(vals);

            if (this.newOne) {
                thisId =  dbClass.getDb().add(TABLE, vals);
                this.ID = (int) thisId;
                this.oldID = this.ID;
                this.newOne = false;

            } else {

                thisId = (this.ID == this.oldID ? this.ID : this.oldID);
                upd = dbClass.getDb().upd(TABLE, vals, "ID = ?", new String[]{String.valueOf(thisId)});

                if (upd != 0 && this.ID != this.oldID) this.oldID = this.ID;

            }
        }
        catch (JSONException e){
            Log.e(this.toString(), e.getMessage(), e);
        }
        Log.d(this.toString(), "new ID " + String.valueOf(thisId) );
        return thisId != 0 ;
    }

    public JSONObject getData(){
        JSONObject result = new JSONObject();
        try {
            result.put("ID", this.ID);
            result.put("gname", this.gName);
            result.put("gadmin", this.gAdmin);
            result.put("gtype", this.gType);
            result.put("rtype", this.rType);
            result.put("sdate", this.sDate);
            result.put("edate", this.eDate);
            result.put("ndays", this.nDays);
            result.put("sfrom", this.sFrom);
            result.put("sto", this.sTo);
            result.put("pagescount", this.pagesCount);
//            result.put("agetype", this.ageType);
        }
        catch (JSONException e) {
            Log.e(this.toString() + " getData()", e.getMessage(),e );
        }

        return result ;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        if (this.ID != 0 )
            this.oldID = this.ID;
        this.ID = ID;
    }

    public String getTable() {
        return TABLE;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String getnDays() {
        return nDays;
    }

    public void setnDays(String nDays) {
        this.nDays = nDays;
    }

    public int getgAdmin() {
        return gAdmin;
    }

    public void setgAdmin(int gAdmin) {
        this.gAdmin = gAdmin;
    }

    public int getgType() {
        return gType;
    }

    public void setgType(int gType) {
        this.gType = gType;
    }

    public int getrType() {
        return rType;
    }

    public void setrType(int rType) {
        this.rType = rType;
    }

    public int getsFrom() {
        return sFrom;
    }

    public void setsFrom(int sFrom) {
        this.sFrom = sFrom;
    }

    public int getsTo() {
        return sTo;
    }

    public void setsTo(int sTo) {
        this.sTo = sTo;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }
}
