package com.abdul.githubcontributions.viewmodel;

import android.app.Application;

import com.abdul.githubcontributions.CommonUtils.Utils;
import com.abdul.githubcontributions.api.ApiUtil;
import com.abdul.githubcontributions.model.ContributionModel;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    private static boolean instantiated = false;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ContributionModel>> contributions = new MutableLiveData<>();
    private MutableLiveData<Integer> contributionCounts = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        if (!instantiated) {
            username.setValue("");
            contributions.setValue(new ArrayList<>());
            contributionCounts.setValue(0);
            instantiated = true;
        }
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return mIsUpdating;
    }

    public void setUsername(String username) {
        this.username.postValue(username);
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<ArrayList<ContributionModel>> getContributions() {
        return contributions;
    }

    public void fetchApiData() {
        mIsUpdating.setValue(true);
        ApiUtil.getServiceClass().getData(username.getValue() + "/contributions/").enqueue(new Callback<ResponseBody>() {
            @Override public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                assert response.body() != null;
                try {
                    String pattern = "(fill=\")(#[^\"]{6})(\" data-count=\")([^\"]{1,})(\" data-date=\")([^\"]{10})(\")";
                    String color_pattern = "(#[^\"]{6})";
                    String count_pattern = "(\"\\d+\")";
                    String date_pattern = "(\\d{4}-\\d{2}-\\d{2})";
                    ArrayList<ContributionModel> contributionModels = new ArrayList<>();
                    int counter = 0;
                    for (String match : Utils.getMatches(pattern, response.body().string(), false)) {
                        String color = Utils.getMatches(color_pattern, match, true).get(0);
                        int count = Integer.parseInt(Utils.getMatches(count_pattern, match, true).get(0).replace("\"", ""));
                        String date = Utils.getMatches(date_pattern, match, true).get(0);
                        contributionModels.add(new ContributionModel(color, count, date));
                        counter += count;
                    }
                    contributions.postValue(contributionModels);
                    contributionCounts.postValue(counter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mIsUpdating.postValue(false);
            }

            @Override public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mIsUpdating.postValue(false);
            }
        });
    }
}
