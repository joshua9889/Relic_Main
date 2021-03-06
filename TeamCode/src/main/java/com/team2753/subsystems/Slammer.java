package com.team2753.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static com.team2753.subsystems.Slammer.SLAMMER_State.INTAKING;
import static com.team2753.subsystems.Slammer.SLAMMER_State.SCORING;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 1/25/2018.
 */

public class Slammer implements Subsystem{

    public enum SLAMMER_State {
        INTAKING, HOLDING, SCORING, SLIDING
    }

    private SLAMMER_State currentSlammerState = INTAKING;
    private ElapsedTime slammerTimer = new ElapsedTime();

    public enum STOPPER_State {
        CLOSED, OPEN
    }

    private STOPPER_State currentStopperState = STOPPER_State.OPEN;
    private ElapsedTime stopperTimer = new ElapsedTime();

    private Servo left_slammer, right_slammer = null;

    private Servo stopServo = null;
    private static final double ARMUP = 0.35;
    private static final double ARMDOWN = 0.8;

    private LinearOpMode ref = null;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init(LinearOpMode linearOpMode, boolean auto) {
        this.ref = linearOpMode;

        left_slammer = linearOpMode.hardwareMap.get(Servo.class, "left_slammer");
        right_slammer = linearOpMode.hardwareMap.get(Servo.class, "right_slammer");
        right_slammer.setDirection(Servo.Direction.REVERSE);

        stopServo = linearOpMode.hardwareMap.get(Servo.class, "slammer_stop");

        currentStopperState = STOPPER_State.OPEN;
        currentSlammerState = SLAMMER_State.INTAKING;


        setStopperState(STOPPER_State.CLOSED);
        retract();

    }

    @Override
    public void zeroSensors() {}

    @Override
    public void stop() {}

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Slammer Position", left_slammer.getPosition());
    }

    /**
     * @param state Set the wanted state of the slammer and stopper
     * @return if the slammer is finished
     */
    private boolean firstIntakeRun = true;
    public boolean setSlammerState(SLAMMER_State state){
        if(state != currentSlammerState){
            switch (state){
                case INTAKING:
                    if(setStopperState(STOPPER_State.OPEN) && !firstIntakeRun) {
                        retract();
                        if(slammerTimer.milliseconds()>500) {
                            setStopperState(STOPPER_State.CLOSED);
                            firstIntakeRun = true;
                            currentSlammerState = state;
                        }
                    } else {
                        slammerTimer.reset();
                        firstIntakeRun = false;
                    }
                    break;
                case HOLDING:
                    if(setStopperState(STOPPER_State.OPEN)) {
                        setSlammerPosition(190);
                        currentSlammerState = state;
                    }
                    break;
                case SCORING:
                    if(setStopperState(STOPPER_State.OPEN)) {
                        score();
                        currentSlammerState = state;
                    }
                    break;
                case SLIDING:
                    if (setStopperState(STOPPER_State.OPEN)) {
                        setSlammerPosition(110);
                        currentSlammerState = state;
                    }
                    break;
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean setStopperState(STOPPER_State state){
        if (state != currentStopperState) {
            switch (state){
                case OPEN:
                    stopperUp();
                    stopperTimer.reset();
                    break;
                case CLOSED:
                    stopperDown();
                    stopperTimer.reset();
                    break;
            }
            currentStopperState = state;
            return false;
        } else {
            return stopperTimer.milliseconds() > 300;
        }
    }

    public void setSlammerPosition(double angle){
        double leftPosition = ((angle+11)/360.0);
        double rightPosition = ((angle+0)/360.0);
        left_slammer.setPosition(Math.min(Math.max(0, leftPosition), 1));
        right_slammer.setPosition(Math.min(Math.max(0, rightPosition), 1));
    }

    public void stopperDown(){
        stopServo.setPosition(ARMDOWN);
    }

    public void stopperUp(){
        stopServo.setPosition(ARMUP);
    }

    public void score(){
        setSlammerPosition(90);
    }

    public void retract(){
        setSlammerPosition(237);
    }

    public void autoSlam(){
        while (ref.opModeIsActive() && !setSlammerState(SCORING)) Thread.yield();
        waitForTick(400);
        while (ref.opModeIsActive() && !setSlammerState(INTAKING)) Thread.yield();
    }

    private void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)runtime.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        runtime.reset();
    }

    @Override
    public String toString() {
        return "Slammer";
    }
}
