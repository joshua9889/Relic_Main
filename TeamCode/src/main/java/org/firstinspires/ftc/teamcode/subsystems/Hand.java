package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.Thread.sleep;

/**
 * Subsystem for claw/hand thingy
 * Created by joshua9889 on 12/10/2017.
 *
 * edited by David Zheng
 */

public class Hand implements Subsystem {
    private Servo claw1, claw2 = null;
    private Servo grabBottom, grabTop = null;
    //private Servo clawf1, clawf2 = null;
    //private Servo clawb1, clawb2 = null;

    // Positions for servos
    private final static double CLAWSIDEOPEN = 0.5;
    private final static double CLAWSIDECLOSED = 0.0;
    //private final static double CLAWCLOSE = 1.0;


    @Override
    public void init(LinearOpMode linearOpMode, boolean auto) {
        claw1 = linearOpMode.hardwareMap.servo.get("grab1");
        claw2 = linearOpMode.hardwareMap.servo.get("grab2");
        claw2.setDirection(Servo.Direction.REVERSE);


        /*
        clawf1 = linearOpMode.hardwareMap.servo.get("grab_front1");
        clawf2 = linearOpMode.hardwareMap.servo.get("grab_front2");
        clawb1 = linearOpMode.hardwareMap.servo.get("grab_back1");
        clawb2 = linearOpMode.hardwareMap.servo.get("grab_back2");

        clawf2.setDirection(Servo.Direction.REVERSE);
        clawb2.setDirection(Servo.Direction.REVERSE);
        */


        grabBottom = linearOpMode.hardwareMap.servo.get("grab_bottom");
        grabTop = linearOpMode.hardwareMap.servo.get("grab_top");
        stop();


    }

    @Override
    public void zeroSensors() {}

    @Override
    public void stop() {
        // Just move it a little farther back then normal
        claw1.setPosition(0.6);
        claw2.setPosition(0.6);
        grabBottom.setPosition(0);
        grabTop.setPosition(0);

    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Grab1", claw1.getPosition());
        telemetry.addData("Grab2", claw2.getPosition());
        telemetry.addData("GrabTop", grabTop.getPosition());
        telemetry.addData("GrabBottom", grabBottom.getPosition());
    }

    // Open the hand
    public void openClaw(){
        claw1.setPosition(CLAWSIDEOPEN);
        claw2.setPosition(CLAWSIDEOPEN);
        grabBottom.setPosition(0.3);
        grabTop.setPosition(0.35);
    }

    // Close the hand
    public void closeSide(){
        claw1.setPosition(CLAWSIDECLOSED);
        claw2.setPosition(CLAWSIDECLOSED + 0.05);
    }

    public void closeTop() throws InterruptedException {
        grabBottom.setPosition(0.3);
        sleep(100);
        closeSide();
        grabTop.setPosition(1.0);

    }

    public void closeBottom() {
        closeSide();
        grabTop.setPosition(0.5);
        grabBottom.setPosition(1.0);
    }

    public void bottomHold(){
        grabBottom.setPosition(0);
    }


}
