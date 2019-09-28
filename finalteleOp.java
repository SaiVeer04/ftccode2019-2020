package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp2")
public class finalteleOp extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        /*DcMotor FR   = null; //declaration of motors
        DcMotor  FL  = null;
        DcMotor  BR   = null;
        DcMotor  BL  = null;*/
        DcMotor intakeLeft = null;
        DcMotor intakeRight = null;

        /*FR  = hardwareMap.get(DcMotor.class, "FR"); //initilization
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");*/
        intakeLeft = hardwareMap.get(DcMotor.class, "intakeLeft");
        intakeRight = hardwareMap.get(DcMotor.class, "intakeRight");

        //FL.setDirection(DcMotorSimple.Direction.REVERSE); //set left side motors to opposite so that the robot moves
       // BL.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                /*double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
                double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
                double rightX = gamepad1.right_stick_x;
                final double v1 = r * Math.cos(robotAngle) + rightX;
                final double v2 = r * Math.sin(robotAngle) - rightX;
                final double v3 = r * Math.sin(robotAngle) + rightX;
                final double v4 = r * Math.cos(robotAngle) - rightX;

                FL.setPower(v1);
                FR.setPower(v2);
                BL.setPower(v3);
                BR.setPower(v4);*/

                intakeLeft.setPower(gamepad1.right_trigger*2);
                intakeRight.setPower(gamepad1.right_trigger*2);

                intakeLeft.setPower(-gamepad1.left_trigger*2);
                intakeRight.setPower(-gamepad1.left_trigger*2);

            }
            }

    }

}
