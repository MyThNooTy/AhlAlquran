package com.samcotec.ahlalquran;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * Created by MyTh on 09/10/2017.
 */

class AJAX extends AsyncTask<String, Void, String> {

    public JSONObject
            POST = new JSONObject(),
            FILES = new JSONObject();

    public String
            user = "",
            veros = "android|2.1.0";

    private final String
            SC = "AHLQ",
            METHOD = "POST";

    private int TIMEOUT = 600000;

    private boolean start = false, showLoding = false;
    private HttpURLConnection connection = null;
    private DataOutputStream dos = null;
    private DataInputStream inStream = null;

    private Context Class = null;

    private String
            responseFromServer = "",
            lineEnd = "\r\n",
            twoHyphens = "--",
            boundary = "*****",
            Url = "http://";

    public CallBack myCallBack;

    @RequiresApi(api = Build.VERSION_CODES.M)
    AJAX(Context Context, String URL, boolean showLoding, CallBack callBack) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.Url += URL;
        this.Class = Context;
        this.start = isConnected( this.Class );
        this.showLoding = showLoding;
        this.myCallBack =  callBack;

        if ( this.showLoding ){
//            this.dialog = ProgressDialog.show( this.Class,"","Loading..");
//            this.dialog.show();
        }
    }

    public interface CallBack {
        void ajax( String result );
    }

    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {


        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));

        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    public static String getParamsString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    static boolean isConnected(Context context) {
        if (context == null)
            return false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (activeNetwork != null) { // connected to the internet
//            if ( activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ) {
//                // connected to wifi
//                Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
//            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE ) {
//                // connected to the mobile provider's data plan
//                Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            // not connected to the internet
//        }
//         out.println( "ASdf" );
//         out.println( cm.getActiveNetwork() );
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return activeNetwork != null ;
    }


    @Override
    protected String doInBackground(String[] params) {
        if (this.start) {
            this.responseFromServer = "";
            try {

                //------------------ CLIENT REQUEST

                // open a URL connection to the Servlet
                URL url = new URL(this.Url);
                // Open a HTTP connection to the URL
                this.connection = (HttpURLConnection) url.openConnection();
                // TimeOute server
                this.connection.setConnectTimeout(this.TIMEOUT);
                // Allow Inputs
                this.connection.setDoInput(true);
                // Allow Outputs
                this.connection.setDoOutput(true);
                // Don't use a cached copy.
                this.connection.setUseCaches(false);
                // Use a post method.
                this.connection.setRequestMethod(this.METHOD);
                this.connection.setRequestProperty("Connection", "Keep-Alive");
                this.connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + this.boundary);

                this.dos = new DataOutputStream(this.connection.getOutputStream());

                this.dos.writeBytes(this.twoHyphens + this.boundary + this.lineEnd);
                this.dos.writeBytes("Content-Disposition: form-data; name=\"ver\";" + this.lineEnd);
                this.dos.writeBytes(this.lineEnd);
                this.dos.writeBytes(this.SC);
                this.dos.writeBytes(this.lineEnd);
                this.dos.writeBytes(this.twoHyphens + this.boundary + this.twoHyphens + this.lineEnd);


                if (this.FILES.length() > 0) {

                    int bytesRead, bytesAvailable, bufferSize;
                    byte[] buffer;
                    int maxBufferSize = 1024 * 1024;
                    FileInputStream fileInputStream = null;
                    Iterator<String> itr = this.FILES.keys();

                    while (itr.hasNext()) {
                        String fileName = itr.next();
                        Object filePath = this.FILES.get(fileName);

                        fileInputStream = new FileInputStream(new File(filePath.toString()));
                        this.dos.writeBytes(this.twoHyphens + this.boundary + this.lineEnd);
                        this.dos.writeBytes("Content-Disposition: form-data; name=" + fileName + ";filename=\"" + filePath + "\"" + this.lineEnd);
                        this.dos.writeBytes(this.lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];
                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {
                            this.dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        }

                        // send multipart form data necesssary after file data...
                        this.dos.writeBytes(this.lineEnd);
                        this.dos.writeBytes(this.twoHyphens + this.boundary + this.twoHyphens + this.lineEnd);


                        // close streams
                        Log.d("Debug", "File is written");
                        fileInputStream.close();
                    }
                }

                if (this.POST.length() > 0) {

                    this.dos.writeBytes(this.twoHyphens + this.boundary + this.lineEnd);
                    this.dos.writeBytes("Content-Disposition: form-data; name=\"data\";" + this.lineEnd);
                    this.dos.writeBytes(this.lineEnd);

//                this.dos.writeBytes( "Sdf" );
                    this.dos.writeBytes(this.POST.toString());


                    this.dos.writeBytes(this.lineEnd);
                    this.dos.writeBytes(this.twoHyphens + this.boundary + this.twoHyphens + this.lineEnd);
                }


                this.dos.writeBytes(this.twoHyphens + this.boundary + this.lineEnd);
                this.dos.writeBytes("Content-Disposition: form-data; name=\"user\";" + this.lineEnd);
                this.dos.writeBytes(this.lineEnd);
                this.dos.writeBytes(this.user);
                this.dos.writeBytes(this.lineEnd);
                this.dos.writeBytes(this.twoHyphens + this.boundary + this.twoHyphens + this.lineEnd);


                this.dos.writeBytes(this.twoHyphens + this.boundary + this.lineEnd);
                this.dos.writeBytes("Content-Disposition: form-data; name=\"time\";" + this.lineEnd);
                this.dos.writeBytes(this.lineEnd);
                this.dos.writeBytes( new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH).format( new Date().getTime() ) );
                this.dos.writeBytes(this.lineEnd);
                this.dos.writeBytes(this.twoHyphens + this.boundary + this.twoHyphens + this.lineEnd);


                this.dos.writeBytes(this.twoHyphens + this.boundary + this.lineEnd);
                this.dos.writeBytes("Content-Disposition: form-data; name=\"veros\";" + this.lineEnd);
                this.dos.writeBytes(this.lineEnd);
                this.dos.writeBytes(this.veros);
                this.dos.writeBytes(this.lineEnd);
                this.dos.writeBytes(this.twoHyphens + this.boundary + this.twoHyphens + this.lineEnd);


                this.dos.flush();
                this.dos.close();


            } catch (IOException ioe) {
                Log.e("AJAX ioe Error ", "error: " + ioe.getMessage(), ioe);
            } catch (Exception e) {
                Log.e("AJAX Error", "error: " + e.getMessage());
            }


            //------------------ read the SERVER RESPONSE
            try ( InputStream is = this.connection.getInputStream() ) {
                BufferedReader lines = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                while (true) {
                    String line = lines.readLine();
                    if ( line == null )  {
                        lines.close();
                        break;
                    }
                    this.responseFromServer += line;
                }
            } catch (IOException e) {
                Log.e("MyTh AJAX ioex", "error: " + e.getMessage(), e);
            }


            this.connection.disconnect();
            return this.responseFromServer;
        }
        return "";
    }

    @Override
    protected void onPostExecute (String message){
        //process message
        super.onPostExecute(message);
        myCallBack.ajax(message);
        if ( this.showLoding ){
//            this.dialog.hide();
        }
    }
}
