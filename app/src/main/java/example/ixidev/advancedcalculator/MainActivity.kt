package example.ixidev.advancedcalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
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
        inputText.doOnTextChanged { text, _, _, _ ->
            onCalculExpression()
        }

    }

    fun onClearClick(view: View) {
        inputText.text = null
    }

    fun onNumberClick(view: View) {
        (view as Button).let {
            onAppend(it.text)
        }
    }

    fun onOperationClick(view: View) {
        (view as Button).let {
            onAppend(it.text)
        }
    }

    private fun onAppend(char: CharSequence) {
        inputText.append(char)
    }

    fun onEqualsButtonClick(view: View) {
        inputText.setText(resultText.text)
    }

    private fun onCalculExpression() {
        val expression = inputText.toText().trim()
        try {
            if (calculator.isExpressionCorrect(expression)) {
                val result = calculator.getResult(expression)
                resultText.text = "$result"
            } else resultText.text = "0.0"
        } catch (e: Exception) {
        }
    }
}