package example.ixidev.advancedcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


class MainActivity extends AppCompatActivity {
    private Calculator calculator = new Calculator();
    private EditText inputText;
    private TextView resultText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        inputText = findViewById(R.id.inputTextView);
        resultText = findViewById(R.id.resualtTextView);
        UtilsKt.hideKeyBoard(inputText);
    }

    public void onClearClick(View view) {
        inputText.setText(null);
    }

    public void onNumberClick(View view) {
        Button b = (Button) view;
        inputText.append(b.getText());
    }

    public void onOperationClick(View view) {
        Button b = (Button) view;
        inputText.append(b.getText());
    }

    public void onEqualsButtonClick(View view) {
        String expression = inputText.getText().toString();
        double result = calculator.getResult(expression);
        resultText.setText(String.valueOf(result));
    }
}