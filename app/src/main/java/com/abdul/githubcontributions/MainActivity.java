package com.abdul.githubcontributions;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.abdul.githubcontributions.CommonUtils.Utils;
import com.abdul.githubcontributions.databinding.ActivityMainBinding;
import com.abdul.githubcontributions.interfaces.CustomOnclickInterface;
import com.abdul.githubcontributions.model.ContributionModel;
import com.abdul.githubcontributions.model.MainActivityModel;
import com.abdul.githubcontributions.viewmodel.MainActivityViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity implements CustomOnclickInterface {

    private MainActivityModel mModel = new MainActivityModel();
    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setModel(mModel);
        mBinding.setCustomClick(this);

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mViewModel.init();
        mViewModel.getContributions().observe(this, contributions -> {
            assert contributions != null;
            for (ContributionModel contribution : contributions) {
                Log.d("TAG", "contributionsList: " + contribution.getColor() + " " + contribution.getCount() + " " + contribution.getDate());
            }
        });

        mViewModel.getUsername().observe(this, username -> {
            mViewModel.fetchApiData();
        });

        mViewModel.getIsLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                Utils.showLoadingDialog(MainActivity.this);
            } else {
                Utils.hideLoadingDialog();
            }
        });
    }

    @Override public void onClick() {
        if (mModel.getUsername().isEmpty()) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        mViewModel.setUsername(mModel.getUsername());
    }
}
