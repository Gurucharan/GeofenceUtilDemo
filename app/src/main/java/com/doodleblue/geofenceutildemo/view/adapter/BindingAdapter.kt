package com.doodleblue.geofenceutildemo.view.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.doodleblue.geofenceutildemo.R
import com.doodleblue.geofenceutildemo.util.Type

@BindingAdapter("app:watcher")
fun EditText.bindTextWatcher(button: Button) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            button.isEnabled = false
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            button.isEnabled = this@bindTextWatcher.text.toString().isNotEmpty()
        }

    })
}

@BindingAdapter("app:showError", "app:type", requireAll = false)
fun EditText.bindErrorMessage(
    showError: Boolean, type: Type?
) {
    if (showError) {
        when (type) {
            Type.NAME -> this.error = resources.getString(R.string.required_name_error_message)
            Type.LATITUDE -> this.error = resources.getString(R.string.required_latitude_error_message)
            Type.LONGITUDE -> this.error = resources.getString(R.string.required_longitude_error_message)
        }
    }
}