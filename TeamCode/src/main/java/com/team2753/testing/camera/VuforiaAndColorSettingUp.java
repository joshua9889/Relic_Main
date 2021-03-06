package com.team2753.testing.camera;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.team2753.Team2753Linear;
import com.team2753.libs.subsystems.VuMark;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import static com.team2753.libs.subsystems.VuMark.SaveImage;
import static com.team2753.libs.subsystems.VuMark.blue;
import static com.team2753.libs.subsystems.VuMark.red;

/**
 * Created by joshua9889 on 1/13/2018.
 */

@Autonomous(name = "Setting Up VuCam")
@Disabled
public class VuforiaAndColorSettingUp extends LinearOpMode {
    // Use your licence key in place of Constants.kVuforiaLicenceKey
    VuMark vuMark = new VuMark(Team2753Linear.vuforiaKey);

    @Override
    public void runOpMode() throws InterruptedException {
        vuMark.setup(VuforiaLocalizer.CameraDirection.BACK);

        vuMark.update();

        Bitmap bm = vuMark.getBm(20);

        // Scan area for red and blue pixels
        for (int x = (bm.getWidth()/2)+(bm.getWidth()/6); x < ((bm.getWidth()/2)+(18*bm.getWidth()/64)); x++) {
            for (int y = (2*bm.getHeight() / 5) + (bm.getHeight() / 2); y < bm.getHeight(); y++) {
                int pixel = bm.getPixel(x,y);
                int redValue = red(pixel);
                int blueValue = blue(pixel);

                if(redValue>blueValue) {
                    bm.setPixel(x,y, Color.YELLOW);
                }
                else if(blueValue>redValue) {
                    bm.setPixel(x, y, Color.CYAN);
                }
            }
        }

//        for (int x =(bm.getWidth()/2)+(bm.getWidth()/5); x < ((bm.getWidth()/2)+(2*bm.getWidth()/5)); x++ ){
//            bm.setPixel(x, (2*bm.getHeight() / 5) + (bm.getHeight() / 2), Color.GREEN);
//        }
//
//        for (int y = (2*bm.getHeight() / 5) + (bm.getHeight() / 2); y < bm.getHeight(); y++){
//            bm.setPixel((bm.getWidth()/2)+(bm.getWidth()/5), y, Color.GREEN);
//        }

        SaveImage(bm);
    }
}