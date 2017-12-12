package fr.gautier.secondtry.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import fr.gautier.secondtry.R;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rawJsonActivityBtn = findViewById(R.id.raw_json_activity_btn);
        rawJsonActivityBtn.setOnClickListener(view -> launchActivity("HttpWebServiceActivity"));

        Button volley1ActivityBtn = findViewById(R.id.volley1_activity_btn);
        volley1ActivityBtn.setOnClickListener(view -> launchActivity("Volley1Activity"));

        Button springTemplateBtn = findViewById(R.id.spring_activity_btn);
        springTemplateBtn.setOnClickListener(view -> launchActivity("SpringTemplateActivity"));
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
