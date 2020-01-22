package com.omise.tamboon.utility

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.omise.tamboon.R
import java.text.SimpleDateFormat

class Utils {
    companion object {
        fun convertToDateString(date: String): String {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'").parse(date))
        }

        fun showDialog(context: Context): Dialog {
            val mLoadingDialog = Dialog(context)
            mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mLoadingDialog.setCancelable(false)
            mLoadingDialog.setContentView(R.layout.dialog_loading)
            mLoadingDialog.show()
            return mLoadingDialog
        }
    }
}