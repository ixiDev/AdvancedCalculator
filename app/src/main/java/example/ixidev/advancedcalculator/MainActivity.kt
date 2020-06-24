package example.ixidev.advancedcalculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    private fun setupViews() {
        inputTextView.hideKeyBoard()
    }

    fun onClearClick(view: View) {}
    fun onNumberClick(view: View) {}
    fun onOperationClick(view: View) {}
    fun onEqualsButtonClick(view: View) {}
}