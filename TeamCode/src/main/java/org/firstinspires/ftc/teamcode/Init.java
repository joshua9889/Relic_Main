package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.Thread.sleep;

/**
 * Created by David Zheng | FTC 2753 Team Overdrive on 11/4/2017.
 */


/*This program is based off of Vortex_Main-master's Init by S Turner*/

public class Init {
    private HardwareMap hardwareMap;

    /* Motors */
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private DcMotor flipMotor;
    //private DcMotor slideMotor;

    /* Servos */
    private Servo jewelArm;
    private Servo claw1;
    private Servo claw2;

    /* Sensors */
    private ColorSensor jewelColor;

    public Init ()
    {
        leftMotor = null;
        rightMotor = null;
        flipMotor = null;
        //slideMotor = null;
        jewelArm = null;
        claw1 = null;
        claw2 = null;
        jewelColor = null;
    }

    public void initTeleop(HardwareMap map)
    {
        hardwareMap  = map;

        initAll();

        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flipMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //slideMotor.setmode(DcMotor.RunMode.RUN_WITHOUT_)ENCODER);
    }

    public void initAuto(HardwareMap map, Telemetry telemetry, LinearOpMode opMode)
    {
        hardwareMap = map;

        initAll();

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flipMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        opMode.idle();

        // Set all motors to run with or without encoders.  Switch to use RUN_USING_ENCODERS when encoders are installed.
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flipMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d", leftMotor.getCurrentPosition(),rightMotor.getCurrentPosition());
        telemetry.update();

        telemetry.addData("Status", "Ready to Run");
        telemetry.update();

    }

    private void initAll ()
    {
        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor  = hardwareMap.dcMotor.get("right_drive");
        //slideMotor = hardwareMap.dcMotor.get("slide_motor");
        flipMotor = hardwareMap.dcMotor.get("flip_motor");

        jewelArm = hardwareMap.servo.get("jewel_arm");
        claw1 = hardwareMap.servo.get("claw1");
        claw2 = hardwareMap.servo.get("claw2");

        jewelArm.setPosition(0.75);
        claw1.setPosition(1.0);
        claw2.setPosition(0.0);

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        flipMotor.setDirection(DcMotor.Direction.FORWARD);
        //slideMotor.setDirection(DcMotor.Direction.FORWARD);
        //flipMotor.setDirection(DcMotor.Direction.FORWARD);

        leftMotor.setPower(0);
        rightMotor.setPower(0);
        flipMotor.setPower(0);
        //slideMotor.setPower(0);

        jewelColor = hardwareMap.colorSensor.get("jewel_color");
    }

    /*Getter Methods*/

    public DcMotor getLeftMotor() {return leftMotor; }

    public DcMotor getRightMotor() {return rightMotor; }

    public DcMotor getFlipMotor() {return flipMotor; }

    //public DcMotor getSlideMotor () {return slideMotor;}

    public Servo getJewelArm () {return jewelArm;}

    public Servo getClaw1 () {return claw1;}

    public Servo getClaw2 () {return claw2;}

    public ColorSensor getJewelColor() {return jewelColor;}

}
