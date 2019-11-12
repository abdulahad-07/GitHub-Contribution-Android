package com.abdul.githubcontributions;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.Toast;

import com.abdul.githubcontributions.CommonUtils.ImageContribution;
import com.abdul.githubcontributions.CommonUtils.Utils;
import com.abdul.githubcontributions.databinding.ActivityMainBinding;
import com.abdul.githubcontributions.interfaces.CustomOnclickInterface;
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
        final ImageView iv_contributions = mBinding.getRoot().findViewById(R.id.iv_contributions);

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mViewModel.init();

        mViewModel.getContributions().observe(this, contributions -> {
            assert contributions != null;
            iv_contributions.setImageBitmap(ImageContribution.generate(contributions, getScreenWidth()));
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

    private int getScreenWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    @Override public void onClick() {
        if (mModel.getUsername().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.pl_enter_username), Toast.LENGTH_SHORT).show();
            return;
        }
        mViewModel.setUsername(mModel.getUsername());
    }
}
