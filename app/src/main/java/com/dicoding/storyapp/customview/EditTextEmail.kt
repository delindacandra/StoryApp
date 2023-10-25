package com.dicoding.dicodingstoryapp.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class EditTextEmail : AppCompatEditText {
    constructor(context: Context) : super(context) { init() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { init() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init() }

    private fun init(){
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int,
                                       lengthAfter: Int
            ) {
                val isEmailValid = text.toString().endsWith("@gmail.com") || text.toString().endsWith("@dicoding.com")
                if(isEmailValid){
                    setError(null)
                }else{
                    setError("Email tidak valid")
                }
            }

            override fun afterTextChanged(text: Editable) {

            }
        })
    }

}