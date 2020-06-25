package example.ixidev.advancedcalculator

import android.text.InputType
import android.widget.EditText

/**
 * Created by ABDELMAJID ID ALI on 18/06/2020.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
fun EditText.hideKeyBoard() {
    this.setRawInputType(InputType.TYPE_NULL)
}

fun EditText.toText(): String {
    return this.text.toString()
}