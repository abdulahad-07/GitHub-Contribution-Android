package com.abdul.githubcontributions.CommonUtils;

import android.content.Context;

import com.abdul.githubcontributions.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static KProgressHUD hud;

    public static void showLoadingDialog(final Context context) {

        try {
            hud = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f)
                    .setWindowColor(context.getResources().getColor(R.color.colorPrimaryDark))
                    .setLabel(context.getResources().getString(R.string.please_wait))
                    .setCancellable(true)
                    .setCancellable(dialogInterface -> {
//                            Toast.makeText(context, "You " +
//                                    "cancelled manually!", Toast
//                                    .LENGTH_SHORT).show();
                    });


            if (!hud.isShowing()) {
                hud.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideLoadingDialog() {
        try {
            if (hud != null && hud.isShowing()) {
                hud.dismiss();
                hud = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getMatches(String regex, String text, boolean firstOnly) {
        ArrayList<String> matches = new ArrayList<>();
        Matcher m = Pattern.compile(regex).matcher(text);
        while (m.find()) {
            matches.add(firstOnly ? m.group(1) : m.group());
        }
        return matches;
    }
}
