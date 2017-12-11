package fr.gautier.secondtry.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.gautier.secondtry.R;


public class Volley1ActivityFragment extends Fragment {
    private EditText userNameTxt;
    private TextView repoListTxt;
    private Button searchBtn;

    RequestQueue requestQueue;

    private String baseUrl = "https://api.github.com/users/";

    private String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_volley1, container, false);

        this.userNameTxt = fragmentView.findViewById(R.id.username_editText);
        this.repoListTxt = fragmentView.findViewById(R.id.repoListTxt);
        this.searchBtn = fragmentView.findViewById(R.id.search_btn);

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchBtn.setOnClickListener(x -> getRepoList(this.userNameTxt.getText().toString()));

        this.requestQueue = Volley.newRequestQueue(this.getActivity());
    }

    public void getRepoList(String username) {
        this.url = String.format("%s%s%s", this.baseUrl, username, "/repos");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, this.url,
                response -> {
                    if (response.length() > 0) {
                        setRepoListTxt("");
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObj = response.getJSONObject(i);
                                String repoName = jsonObj.get("name").toString();
                                String lastUpdated = jsonObj.get("updated_at").toString();
                                addToRepoList(repoName, lastUpdated);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        setRepoListTxt(getResources().getString(R.string.no_repo_found));
                    }
                },
                error -> {
                    setRepoListTxt(getResources().getString(R.string.rest_api_error));
                }
        );

        this.requestQueue.add(jsonArrayRequest);
    }

    private void addToRepoList(String repoName, String lastUpdated) {
        // This will add a new repo to our list.
        // It combines the repoName and lastUpdated strings together.
        // And then adds them followed by a new line (\n\n make two new lines).
        String strRow = repoName + " / " + lastUpdated;
        String currentText = this.repoListTxt.getText().toString();
        this.repoListTxt.setText(String.format("%s\n\n%s", currentText, strRow));
    }

    public void clearRepoList() {
        this.repoListTxt.setText("");
    }

    public void setRepoListTxt(String s) {
        this.repoListTxt.setText(s);
    }
}
