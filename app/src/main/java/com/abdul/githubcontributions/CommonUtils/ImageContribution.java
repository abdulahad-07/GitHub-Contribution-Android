package com.abdul.githubcontributions.CommonUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.abdul.githubcontributions.model.ContributionModel;

import java.util.ArrayList;

public class ImageContribution {

    public static Bitmap generate(ArrayList<ContributionModel> contributions, int screen_width) {
        int sizeElement = screen_width / 53;
        Bitmap image = Bitmap.createBitmap(screen_width, 7 * sizeElement, Bitmap.Config.ARGB_8888);
        float lineX = (float) 0.0;
        float lineY = (float) 0.0;
        Canvas c = new Canvas(image);
        Paint p = new Paint();

        for (int index = 0; index < contributions.size(); index++) {
            ContributionModel contribution = contributions.get(index);
            if (index > 0 && index % 7 == 0) {
                lineY = 0;
                lineX += sizeElement;
            }
            p.setColor(Color.parseColor(contribution.getColor()));
            c.drawRect(lineX, lineY, lineX + sizeElement, lineY + sizeElement, p);
            lineY += sizeElement;
        }
        return image;
    }
}
