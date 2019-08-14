package com.example.characters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.characters.model.User;

public class MainActivity extends AppCompatActivity {

   private Button enterBtn;
   private EditText inputTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputTxt = findViewById(R.id.inputTxt);
        enterBtn = findViewById(R.id.enterBtn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(inputTxt.getText().toString()) || (inputTxt.getText().toString()).length() < 2){
                    inputTxt.setText("");
                    inputTxt.setHint(getResources().getString(R.string.error));
                    inputTxt.setHintTextColor(getResources().getColor(R.color.colorAccent)); return;
                }
                startActivity(createIntent(GameStartActivity.class));
            }
        });
    }

    private Intent createIntent(Class<?> t){
        Intent intent = new Intent(this, t);
        User user = new User(inputTxt.getText().toString());
        intent.putExtra("user", user);
        return intent;
    }
}
