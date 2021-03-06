package com.team2753.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.team2753.Team2753Linear;

import static com.team2753.auto.AutoParams.AUTO;
import static com.team2753.auto.AutoParams.autoSpeed;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 1/30/2018.
 */

@Autonomous(name = "Auto Test", group = "test")
@Disabled
public class fullAutoTest extends Team2753Linear{

    //Runs position b1 for now bc its the closest on test field

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart("B1 Multiglyph", AUTO);

        int i = 0;

        while(opModeIsActive() && i == 0) {


            //score cryptokey
            SetStatus("Cryptokey");
            //glyphScoreB1(this);

            //grab more glyphs
            SetStatus("Multiglyph");
            telemetry.update();
            //multiGlyphPos1();

            Robot.getDrive().encoderDrive(autoSpeed, -4, -4, 3);


            //score extra glyphs

            //park
            SetStatus("Parking");
            telemetry.update();

            i++;
        }

        finalAction();
    }
}
