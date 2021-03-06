package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by joshua9889 on 12/10/2017.
 */

public class Jewel implements Subsystem {

    private Servo arm, wrist;

    final private static double ARMUP = 0.94;
    final private static double ARMDOWN = 0.25;

    final private static double CenterJewelWrist = 0.49;
    final private static double LeftJewelWrist = 1.0;
    final private static double RightJewelWrist = 0.0;

    @Override
    public void init(LinearOpMode linearOpMode, boolean auto) {
        arm = linearOpMode.hardwareMap.get(Servo.class, "jewel_arm");
        wrist = linearOpMode.hardwareMap.get(Servo.class, "jewel_flicker");

        if(auto)
            retract(true);
        else
            retract(false);
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void stop() {
        retract(true);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Jewel Arm Position", arm.getPosition());
        telemetry.addData("Jewel Wrist Position", wrist.getPosition());
    }

    // Deploy jewel mech
    public void deploy(boolean center){
        arm.setPosition(ARMDOWN);
        if(center)
            center();
    }

    // Retract jewel mech
    public void retract(boolean center){
        arm.setPosition(ARMUP);
        if(center)
            center();
    }

    public void center(){wrist.setPosition(CenterJewelWrist);}

    public void left(){
        wrist.setPosition(LeftJewelWrist);
    }

    public void right(){
        wrist.setPosition(RightJewelWrist);
    }

    public void leftHit(){
        wrist.setPosition((CenterJewelWrist+LeftJewelWrist)/2);
    }

    public void rightHit(){
        wrist.setPosition((CenterJewelWrist+RightJewelWrist)/2);
    }

    @Override
    public String toString() {
        return "Jewel";
    }
}
