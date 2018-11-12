package mk.ukim.finki.mpip;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ExplicitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);
    }

    protected void saveData(View view){
        EditText text = findViewById(R.id.input_info);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", text.getText().toString());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    protected void goBack(View view){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
