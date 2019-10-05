package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name="Pushbot: Auto Drive By Encoder")
public class autonomous_test extends LinearOpMode {

    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    private ElapsedTime     runtime = new ElapsedTime();

    DcMotor  FR   = null; //declaration of motors
    DcMotor  FL  = null;
    DcMotor  BR   = null;
    DcMotor  BL  = null;

    DcMotor catapult = null;

    Servo in = null;
    Servo out = null;



    double inVal = 0, outVal = 1; //max and min values of the servo


    @Override
    public void runOpMode() throws InterruptedException
    {

        /*FR  = hardwareMap.get(DcMotor.class, "FR"); //initilization
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        catapult = hardwareMap.get(DcMotor.class, "catapult");

        in = hardwareMap.get(Servo.class, "in");
        out = hardwareMap.get(Servo.class, "out");

        FL.setDirection(DcMotorSimple.Direction.REVERSE); //set left side motors to opposite so that the robot moves
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        catapult.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //used fo encoders
        catapult.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/

        waitForStart(); //when the driver clicks play



        catapult.setTargetPosition(600); //sets the distance of the motor
        catapult.setMode(DcMotor.RunMode.RUN_TO_POSITION); //tells the motor to go the distance of the encoder
        runtime.reset();
        catapult.setPower(0.3); //speed of the motor

        while (opModeIsActive() && //use this method to prevent skipping of encoders
                catapult.isBusy()) {


        }

        // Stop all motion;
        catapult.setPower(0);

        // Turn off RUN_TO_POSITION
        catapult.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        catapult.setTargetPosition(-800);
        catapult.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        runtime.reset();
        catapult.setPower(0.3);

        while (opModeIsActive() &&
                catapult.isBusy()) {


        }

        // Stop all motion;
        catapult.setPower(0);

        // Turn off RUN_TO_POSITION
        catapult.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }
    //public void movement(DcMotor motor, )
}


