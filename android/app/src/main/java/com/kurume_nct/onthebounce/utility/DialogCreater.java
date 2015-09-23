package com.kurume_nct.onthebounce.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by minto on 2015/09/22.
 */
public class DialogCreater {
    public static AlertDialog.Builder createOnlyMessage(String message, Context context){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        alertDialog.setMessage(message);
        //確認ボタンん処理を設定する
        alertDialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
                //setResult(RESULT_OK);
            }
        });
        alertDialog.create();
        return alertDialog;
    }
}
