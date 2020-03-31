package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    boolean isNameOkay = false;
    boolean isSurnameOkay = false;
    boolean isNumberOkay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText e1 = (EditText)findViewById(R.id.editText);
        final EditText e2 = (EditText)findViewById(R.id.editText2);
        final EditText e3 = (EditText)findViewById(R.id.editText3);
        final Button b = (Button)findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                intent.putExtra("liczbaOcen", Integer.parseInt(e3.getText().toString()));
                startActivityForResult(intent, 1);
            }
        });

        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = e1.getText().toString();
                if (text.isEmpty()) {
                    Toast grzanka = Toast.makeText(getApplicationContext(), "Imie nie może być puste", Toast.LENGTH_SHORT);
                    grzanka.show();
                    isNameOkay = false;
                } else if (!Pattern.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$", text)) {
                    Toast grzanka = Toast.makeText(getApplicationContext(), "Nieprawidłowy format", Toast.LENGTH_SHORT);
                    grzanka.show();
                    isNameOkay = false;
                } else {
                    isNameOkay = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isNumberOkay && isNameOkay && isSurnameOkay) {
                    b.setVisibility(View.VISIBLE);
                } else {
                    b.setVisibility(View.INVISIBLE);
                }
            }
        });

        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = e2.getText().toString();
                if (text.isEmpty()) {
                    Toast grzanka = Toast.makeText(getApplicationContext(), "Nazwisko nie może być puste", Toast.LENGTH_SHORT);
                    grzanka.show();
                    isSurnameOkay = false;
                } else if (!Pattern.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$", text)) {
                    Toast grzanka = Toast.makeText(getApplicationContext(), "Nieprawidłowy format", Toast.LENGTH_SHORT);
                    grzanka.show();
                    isSurnameOkay = false;
                } else {
                    isSurnameOkay = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isNumberOkay && isNameOkay && isSurnameOkay) {
                    b.setVisibility(View.VISIBLE);
                } else {
                    b.setVisibility(View.INVISIBLE);
                }
            }
        });

        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = e3.getText().toString();
                if (text.isEmpty()) {
                    Toast grzanka = Toast.makeText(getApplicationContext(), "Pole nie może być puste", Toast.LENGTH_SHORT);
                    grzanka.show();
                    isNumberOkay = false;
                } else {
                    int liczba = Integer.parseInt(text);
                    if (liczba < 5 || liczba > 15) {
                        Toast grzanka = Toast.makeText(getApplicationContext(), "Liczba od 5 do 15", Toast.LENGTH_SHORT);
                        grzanka.show();
                        isNumberOkay = false;
                    } else {
                        isNumberOkay = true;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isNumberOkay && isNameOkay && isSurnameOkay) {
                    b.setVisibility(View.VISIBLE);
                } else {
                    b.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    protected void onActivityResult(int kodZadania, int kodWyniku, Intent dane) {
        super.onActivityResult(kodZadania, kodWyniku, dane);
        if (kodWyniku == RESULT_OK) {
            Bundle tobolek = dane.getExtras();
            float liczba = tobolek.getFloat("srednia");
            TextView text = (TextView)findViewById(R.id.textView5);
            text.setText("Twoja srednia to " + liczba);
            Button b = (Button)findViewById(R.id.button);
            if (liczba >= 3.0) {
                b.setText("Super :)");
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast grzanka = Toast.makeText(getApplicationContext(), "Gratulacje otrzymujesz zaliczenie", Toast.LENGTH_LONG);
                        grzanka.show();
                        finish();
                    }
                });
            } else {
                b.setText("Tym razem nie poszło");
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast grzanka = Toast.makeText(getApplicationContext(), "Wysylam podanie o zaliczenie poprawkowe", Toast.LENGTH_LONG);
                        grzanka.show();
                        finish();
                    }
                });
            }
        }
    }

}
