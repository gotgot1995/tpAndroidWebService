package fr.gautier.secondtry.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import fr.gautier.secondtry.R;
import fr.gautier.secondtry.activities.SpringTemplateActivity;
import fr.gautier.secondtry.entities.Greeting;

public class SpringTemplateActivityFragment extends Fragment {
    private TextView springResultTxt;
    private Button springTemplateBtn;

    public SpringTemplateActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_spring_template, container, false);

        this.springResultTxt = fragmentView.findViewById(R.id.spring_result_txt);
        this.springTemplateBtn = fragmentView.findViewById(R.id.spring_web_service_btn);

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.springTemplateBtn.setOnClickListener(v -> {
            new HttpRequestTask().execute();
        });
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return restTemplate.getForObject(url, Greeting.class);
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            SpringTemplateActivityFragment.this.springResultTxt.setText(
                    String.format("id: %s\n\ncontent: %s", greeting.getId(), greeting.getContent())
            );
        }

    }
}
