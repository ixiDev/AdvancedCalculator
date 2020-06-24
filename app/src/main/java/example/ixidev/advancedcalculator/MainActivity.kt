package example.ixidev.advancedcalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val calculator = Calculator()
    private lateinit var inputText: EditText
    private lateinit var resultText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    private fun setupViews() {
        inputText = findViewById(R.id.inputTextView)
        resultText = findViewById(R.id.resualtTextView)
        inputTextView.hideKeyBoard()

    }

    fun onClearClick(view: View) {
        inputText.text = null
    }

    fun onNumberClick(view: View) {
        (view as Button).let {
            inputText.append(it.text)
        }
    }

    fun onOperationClick(view: View) {
        (view as Button).let {
            inputText.append(it.text)
        }
    }

    fun onEqualsButtonClick(view: View) {
        val expression = inputText.text.toString()
        val result = calculator.getResult(expression)
        resultText.text = "$result"
    }
}