package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewActivity extends AppCompatActivity {

    private List<ModelOceny> listaOcen = new ArrayList<ModelOceny>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Bundle tobolek = getIntent().getExtras();
        int liczbaOcen = tobolek.getInt("liczbaOcen");

        for(int i = 0; i < liczbaOcen; i++) {
            int temp = i + 1;
            String nazwa = "ocena " + temp;
            ModelOceny ocena = new ModelOceny(nazwa);
            ocena.setOcena(3);
            listaOcen.add(ocena);
        }

        final InteraktywnyAdapterTablicy adapter = new InteraktywnyAdapterTablicy(this, listaOcen);
        ListView lista = (ListView)findViewById(R.id.oceny);
        lista.setAdapter(adapter);

        final Button b = (Button)findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle tobolek = new Bundle();
                tobolek.putFloat("srednia", liczSrednia());
                Intent zamiar = new Intent();
                zamiar.putExtras(tobolek);
                setResult(RESULT_OK,zamiar);
                finish();
            }
        });
    }

    protected float liczSrednia(){
        float x = 0;
        for (int i = 0; i < listaOcen.size(); i++) {
            x += listaOcen.get(i).getOcena();
        }
        x = x / (float) listaOcen.size();
        return x;
    }
}

class ModelOceny {
    private String nazwa;
    private int ocena;

    public ModelOceny(String n) {
        nazwa = n;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getOcena() {
        return ocena;
    }

    public void setNazwa(String n) {
        nazwa = n;
    }

    public void setOcena(int o) {
        ocena = o;
    }
}

class InteraktywnyAdapterTablicy extends ArrayAdapter<ModelOceny> {
    private List<ModelOceny> listaOcen;
    private Activity kontekst;

    public InteraktywnyAdapterTablicy(Activity kontekst, List<ModelOceny> listaOcen) {
        super(kontekst, R.layout.ocena, listaOcen);
        this.kontekst = kontekst;
        this.listaOcen = listaOcen;
    }

    @Override
    public View getView(int numerWiersza, View widokDoRecyklingu, ViewGroup parent) {
        View widok = null;
        if (widokDoRecyklingu == null) {
            //utworzenie layoutu na podstawie xml
            LayoutInflater pompka = kontekst.getLayoutInflater();
            widok = pompka.inflate(R.layout.ocena, null);
            widok.setVisibility(View.VISIBLE);

            RadioGroup grupaOceny = (RadioGroup)widok.findViewById(R.id.grupaOceny);
            final View nowyWidok = widok;
            grupaOceny.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    ModelOceny ocena =(ModelOceny)group.getTag();
                    RadioButton radio =(RadioButton)nowyWidok.findViewById(group.getCheckedRadioButtonId());
                    ocena.setOcena(Integer.valueOf(radio.getText().toString()));
                }
            });
            grupaOceny.setTag(listaOcen.get(numerWiersza));
        } else {
            widok = widokDoRecyklingu;
            RadioGroup grupaOceny = (RadioGroup)widok.findViewById(R.id.grupaOceny);
            grupaOceny.setTag(listaOcen.get(numerWiersza));
        }
        TextView text = (TextView)widok.findViewById(R.id.textView4);
        text.setText(listaOcen.get(numerWiersza).getNazwa());
        RadioGroup grupaOceny = (RadioGroup)widok.findViewById(R.id.grupaOceny);
        switch(listaOcen.get(numerWiersza).getOcena()) {
            case 2:
                grupaOceny.check(R.id.radioButton);
                break;

            case 3:
                grupaOceny.check(R.id.radioButton2);
                break;

            case 4:
                grupaOceny.check(R.id.radioButton3);
                break;

            case 5:
                grupaOceny.check(R.id.radioButton4);
                break;
        }
        return widok;
    }
}