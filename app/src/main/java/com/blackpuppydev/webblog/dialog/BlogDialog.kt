package com.blackpuppydev.webblog.dialog

import android.app.Dialog
import android.content.ComponentCallbacks
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.blackpuppydev.webblog.R
import kotlinx.android.synthetic.main.blog_dialog.*

class BlogDialog(context: Context) : Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.blog_dialog)
        setCancelable(true)

        dialog_cancel.setOnClickListener {
            dismiss()
        }

    }

    fun setMessage(title: String?,detail:String?) {
        dialog_title.text = title
        dialog_detail.text = detail
    }


    companion object{

        fun show(context: Context?, title: String?,detail:String?): BlogDialog? {
            val dialog = BlogDialog(context!!)
            dialog.setMessage(title,detail)
            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }

    }

}