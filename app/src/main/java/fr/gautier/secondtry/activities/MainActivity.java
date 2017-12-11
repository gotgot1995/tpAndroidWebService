package fr.gautier.secondtry.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import fr.gautier.secondtry.R;

public class MainActivity extends Activity {

    Button rawJsonActivityBtn;
    Button volley1ActivityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.rawJsonActivityBtn = findViewById(R.id.raw_json_activity_btn);
        this.rawJsonActivityBtn.setOnClickListener(x -> launchActivity("RawJsonActivity"));

        this.volley1ActivityBtn = findViewById(R.id.volley1_activity_btn);
        this.volley1ActivityBtn.setOnClickListener(x -> launchActivity("Volley1Activity"));
    }

    public void launchActivity(String className) {
        try {
            Intent i = new Intent(this,
                    Class.forName("fr.gautier.secondtry.activities." + className));
            startActivity(i);
        } catch (ClassNotFoundException e) {
            Toast.makeText(this, "Activity not found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
