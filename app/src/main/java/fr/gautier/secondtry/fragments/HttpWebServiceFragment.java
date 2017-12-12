package fr.gautier.secondtry.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import fr.gautier.secondtry.R;
import fr.gautier.secondtry.http.HttpWrapper;


public class HttpWebServiceFragment extends Fragment {
    private TextView appStatusTxt;
    private TextView resultTxt;
    private Button testWebServiceBtn;

    public HttpWebServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_raw_json, container, false);

        this.appStatusTxt = fragmentView.findViewById(R.id.app_status_txt);
        this.resultTxt = fragmentView.findViewById(R.id.result_txt);
        this.testWebServiceBtn = fragmentView.findViewById(R.id.raw_web_service_btn);

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.testWebServiceBtn
                .setOnClickListener(x -> new HttpAsyncTask().execute("http://hmkcode.appspot.com/rest/controller/get.json"));

        if (isConnected()) {
            this.appStatusTxt.setTextColor(getResources().getColor(R.color.colorConnected));
            this.appStatusTxt.setText(R.string.app_status_connected);
        } else {
            this.appStatusTxt.setTextColor(getResources().getColor(R.color.colorNoInternet));
            this.appStatusTxt.setText(R.string.app_status_no_internet);
        }
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return HttpWrapper.GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(HttpWebServiceFragment.this
                    .getActivity().getBaseContext(),
                    R.string.http_response_received, Toast.LENGTH_LONG).show();

            try {
                JSONObject jsonObject = new JSONObject(result);
                HttpWebServiceFragment.this.resultTxt.setText(jsonObject.toString(1));
            } catch (JSONException e) {
                HttpWebServiceFragment.this.resultTxt.setText(result);
                e.printStackTrace();
            }
        }
    }
}
