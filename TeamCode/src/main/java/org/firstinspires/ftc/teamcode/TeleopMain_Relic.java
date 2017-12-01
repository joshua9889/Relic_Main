package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/4/2017.
 */

/* This program is based off of Vortex_Main-master's TeleopMain_Vortex by S Turner*/

@TeleOp(name = "Teleop Main: Relic Recovery", group = "Relic_Recovery")

public class TeleopMain_Relic extends LinearOpMode
{

    /* Motors */
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private DcMotor flipMotor;

    /*Servos*/

    private Servo jewelArm;
    private Servo claw1;
    private Servo claw2;

    /*Sensors*/
    private ColorSensor jewelColor;

    final static double CLAW1OPEN = 1.0;
    final static double CLAW1CLOSED = 0.0;
    final static double CLAW2OPEN = 0.0;
    final static double CLAW2CLOSED = 1.0;
    final static double ARMUP = 0.75;
    final static double ARMDOWN = 0.725;

    @Override
    public void runOpMode()  {

        //Initialize the motors.
        Init init = new Init();
        init.initTeleop(hardwareMap);

        leftMotor = init.getLeftMotor();
        rightMotor = init.getRightMotor();
        flipMotor = init.getFlipMotor();
        jewelArm = init.getJewelArm();
        claw1 = init.getClaw1();
        claw2 = init.getClaw2();

        //jewelColor = init.getJewelColor();

        telemetry.addData("Say", "Hello Driver");
        telemetry.update();

        //jewelArm.setPosition(0.5);


        waitForStart();

        jewelArm.setPosition(ARMUP);

        //claw1.setPosition(0.2);
        //claw2.setPosition(0.8);

        while(opModeIsActive()) {

            /*Controls*/

            /* Drivetrain Controls */
            float leftThrottle = gamepad1.left_stick_y;
            float rightThrottle = gamepad1.right_stick_y;

            /* Clip the left and right throttle values so that they never exceed +/- 1.  */
            leftThrottle = Range.clip(leftThrottle,-1,1);
            rightThrottle = Range.clip(rightThrottle,-1,1);

            /* Scale the throttle values to make it easier to control the robot more precisely at slower speeds.  */
            leftThrottle = (float) scaleInput(leftThrottle);
            rightThrottle = (float) scaleInput(rightThrottle);

            rightMotor.setPower(rightThrottle);
            leftMotor.setPower(leftThrottle);

            //claw1.setPosition(gamepad2.left_trigger);

            if(gamepad2.left_bumper){
                jewelArm.setPosition(ARMDOWN);

            }
            else{
                jewelArm.setPosition(ARMUP);
            }


            if(gamepad2.dpad_up){
                flipMotor.setPower(-0.9);
            }
            else if (gamepad2.dpad_down){
                flipMotor.setPower(0.9);
            }
            else
            {
                flipMotor.setPower(0.0);
            }

            if(gamepad2.left_bumper){
                claw1.setPosition(CLAW1CLOSED);
            }
            else{
                claw1.setPosition(CLAW1OPEN);
            }

            if(gamepad2.right_bumper){
                claw2.setPosition(CLAW2CLOSED);
            }
            else{
                claw2.setPosition(CLAW2OPEN);
            }


        }

    }

    double scaleInput(double dVal)   {
        double[] scaleArray = {
                0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24, 0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00
                //to use a different scale,0 list alternate scale values here and comment out the line above
        };

        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16)  {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0)  {
            dScale = -scaleArray[index];
        }  else {
            dScale = scaleArray[index];
        }

        return dScale;
    }

}
