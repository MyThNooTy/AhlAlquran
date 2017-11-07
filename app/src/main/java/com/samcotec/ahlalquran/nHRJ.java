package com.samcotec.ahlalquran;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by MyTh on 4/7/2017.
 * http://192.168.1.5/app/api/
 */

class nHRJ {
    private final String
            SC = "AHLQ" ,
            METHOD = "POST";


    public http nHRJR ;
    private int TIMEOUT = 10000;
    private String
            USER_AGENT = "Mozilla/5.0",
            CHARSET = "UTF-8",
    //            METHOD = "GET",
    TYPE = "JSON",
            GET_PARAM = "",
            POST_PARAM = "",
            URL = "http://";


    public nHRJ(String url, ajaxDone callBack, ajaxString callBackS, ajaxInt callBackI) {

        nHRJR = new http(callBack, callBackS, callBackI);
        if(!url.isEmpty()) setUrl(url);

    }

    public interface ajaxDone {
        void ajax(String a);
    }


    public interface ajaxString {
        String ajax(String a);
    }

    public interface ajaxInt {
        int ajax(String a);
    }


    public String start(){
        nHRJR.execute();
        return "a";
    }


    public void setUSER_AGENT(String Ua){
        USER_AGENT = Ua ;
    }
    public String getUSER_AGENT(){
        return USER_AGENT ;
    }

    public  void setCharset(String charset){
        CHARSET = charset ;
    }
    public String getCharset(){
        return CHARSET ;
    }

    public void setUrl(String url){
        URL += url ;
    }
    public String getUrl(){
        if(!GET_PARAM.isEmpty()) setUrl( '?' + GET_PARAM );
        return URL ;
    }

    public  void setMethod(String method){

//        METHOD = method ;
    }
    public String getMethod(){
        return METHOD ;
    }

    public  void setGetParam(String name, String value){
        Map<String, String> parameters = new HashMap<>();
        parameters.put(name, value);
        String f = null;
        try {
            f =  ParameterStringBuilder.getParamsString(parameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        GET_PARAM += (!GET_PARAM.isEmpty() ? '&' : "" ) + f ;
    }
    public String getGetParam(){
        return GET_PARAM;
    }

    public  void setPostparam(String name, String value){
        Map<String, String> parameters = new HashMap<>();
        parameters.put(name, value);
        String f = null;
        try {
            f =  ParameterStringBuilder.getParamsString(parameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        POST_PARAM += (!POST_PARAM.isEmpty() ? '&' : "" ) + f ;
    }
    public String getPostParam(){
        return POST_PARAM;
    }

    public  void setTimeOut(Integer timeOut){
        TIMEOUT = timeOut ;
    }
    public Integer getTimeOut(){
        return TIMEOUT;
    }

    public class http extends AsyncTask<String, Void, String> {
        private ajaxDone __done;
        private ajaxString string;
        private ajaxInt integ;

        public http( ajaxDone callBack, ajaxString callBackS, ajaxInt callBackI ){
            __done = callBack;
            string = callBackS;
            integ = callBackI;
        }

        @Override
        protected String doInBackground(String[] params) {
//            ProgressDialog pro = new ProgressDialog( MainActivity.MY_CONTEXT );
//            pro.setProgress(0);
//            final ProgressDialog show = pro.show( MainActivity.MY_CONTEXT, "", "5", true);


            String r = null;
            try {
                r = nHRJR.startReq();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return r;
        }


        @Override
        protected void onPostExecute(String message) {
            //process message
            super.onPostExecute(message);
            this.__done.ajax(message);
            this.string.ajax(message);
            this.integ.ajax(message);


        }


        private String startReq() throws Exception {

            URL obj = new URL(getUrl());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setConnectTimeout(getTimeOut());
            con.setReadTimeout(getTimeOut());

            con.setRequestMethod(getMethod());
            con.setRequestProperty("User-Agent", getUSER_AGENT());
            con.setRequestProperty("Accept-Language", "*;q=0.5");
            setPostparam("ver",SC);
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(getPostParam());
            wr.flush();
            wr.close();


            int responseCode = con.getResponseCode();

            StringBuilder result = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(con.getInputStream())));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            con.disconnect();
            return result.toString() ;
            //System.out.println(data);
        }
    }
}
