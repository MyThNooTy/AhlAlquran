package com.samcotec.ahlalquran;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import static java.lang.System.out;

/**
 * Created by MyTh on 05/08/2017.
 */

public class tools {
    public static boolean amIConnected(Context Context){
        ConnectivityManager connectivityManager = (ConnectivityManager)Context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return  (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ;
    }

    public static void alertNoI(Context context){
          AlertDialog alertDialog = new AlertDialog.Builder(context).create();
          alertDialog.setTitle("ERROR");
          alertDialog.setMessage("No internet connection");
          alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "OK",
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {
                          dialog.dismiss();
                      }
                  });
          alertDialog.show();
    }

    public static void alert(Context context, String title, String msg){

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle( title );
        alertDialog.setMessage( msg );
        alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }



}
