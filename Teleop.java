package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "test",group = "Test")
public class Teleop extends LinearOpMode {

    public DcMotor fr;
    //back right
    public DcMotor br;
    //front left
    public DcMotor fl;
    //back left
    public DcMotor bl;

    //components robot = new components();
    //@Override
    public void runOpMode() throws InterruptedException{

        fr = hardwareMap.dcMotor.get("fr");
        fl = hardwareMap.dcMotor.get("fl");
        br = hardwareMap.dcMotor.get("br");
        bl = hardwareMap.dcMotor.get("bl");

        waitForStart();
        while (opModeIsActive()){

            double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(-gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = (-gamepad1.right_stick_x);
            final double v1 = r * Math.cos(robotAngle) + rightX;
            final double v2 = r * Math.sin(robotAngle) - rightX;
            final double v3 = r * Math.sin(robotAngle) + rightX;
            final double v4 = r * Math.cos(robotAngle) - rightX;


            fl.setPower(v1);
            fr.setPower(v2);
            bl.setPower(v3);
            br.setPower(v4);

            fl.setPower(gamepad1.left_stick_y);

            idle();

        }
    }
}
