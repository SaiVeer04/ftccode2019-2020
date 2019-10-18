/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

/**
 * This 2019-2020 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Skystone game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "Concept: TensorFlow Object Detection Webcam2")
public class ConceptTensorFlowObjectDetectionWebcam2 extends LinearOpMode {
    components c = new components();
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);


    static final double wheel_diameter = 4.0;
    static final double gear_ratio = .5;
    //for encoders
    static final double ticks = 1120;
    static final double ticks_per_inch = (ticks * gear_ratio) / (wheel_diameter * Math.PI);

    DcMotor FR = null; //declaration of motors
    DcMotor FL = null;
    DcMotor BR = null;
    DcMotor BL = null;
    //Servo paddle;
    Servo drag;
    public ElapsedTime runtime = new ElapsedTime();


    //Servo test;

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AVF+IPr/////AAABmSIHgAGLI0ODn254a6Sw3UQK0VqDJcWazHjdrhyfBcJdjHXFe3pv6C0EYG8QGSLhOfCTPGxj3GgfzXF/ndSARshwvj7P3SrpPLgvKVZyl5tPjFYSCIU6r3CzQTmFXGut7tgCZjTS59auWpsAZJSLeO76pI2oqQ5aga+MMDlaQ6i2IM3TbaqrcamwoPfElmTc/kb6qMqibv98MGhAflk0Rv1fHEoTjmBw6WzMI5pWn5QEPtjwW2JaS5JsLZu0jQWu9qn6Wz35u9yLrs8rA8ChOIvQemWFUuTzlteADKNPnogFOWZQv4iur/22GphGP+Cu/65iAV6r+RkBnQ3oiRspOi3J4QliYBnbrSokwkBHiyhW";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        FR = hardwareMap.get(DcMotor.class, "FR"); //initilization
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");

        //paddle = hardwareMap.get(Servo.class, "paddle");
        drag = hardwareMap.get(Servo.class, "drag");
        //test =hardwareMap.get(Servo.class, "test"); //used to grab the servo


        FL.setDirection(DcMotorSimple.Direction.REVERSE); //set left side motors to opposite so that the robot moves
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        //required to tell using encoder
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //get the foundation
                move(-30,30,30,-30); //move to foundation

                drag.setPosition(1); //moves drag servo down, to grab foundation
               //c.straferight(29.5, -0.5); // move back to start pos
                move(30,-30,-30,30);
                drag.setPosition(0); //release foundation
                c.forward(126, -0.5); //move to the first stone
                c.strafeleft(10, .5); //used to move to first stone
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());

                            if (recognition.getLabel() == "Skystone") {
                                int firstforward = 126;


                                //pick it up
                                pickup(1); //pick up skystone
                                sleep(100);
                                c.forward(firstforward, 0.5); //move stone to the end
                                sleep(100);
                                pickup(0); //release the servo
                                sleep(100);
                                c.forward(firstforward - 24, -0.5); //move to second one
                                sleep(100);
                                //pick it up
                                pickup(1); //pick up second skystone
                                sleep(100);
                                c.forward(firstforward - 24, 0.5); //move to the end
                                sleep(100);
                                pickup(0); //release the servo
                                sleep(100);
                                break;

                            } else {
                                c.forward(8, -0.5);
                            }


                            if (recognition.getLabel() == "Skystone") {
                                int firstforward = 118;

                                //pick it up
                                pickup(1); //pick up skystone
                                sleep(100);
                                c.forward(firstforward, 0.5); //move stone to the end
                                sleep(100);
                                pickup(0); //release the servo
                                sleep(100);
                                c.forward(firstforward - 24, -0.5); //move to second one
                                sleep(100);
                                //pick it up
                                pickup(1); //pick up second skystone
                                sleep(100);
                                c.forward(firstforward - 24, 0.5); //move to the end
                                sleep(100);
                                pickup(0); //release the servo
                                sleep(100);
                                break;
                            } else {
                                c.forward(8, -0.5);
                            }


                            if (recognition.getLabel() == "Skystone") {
                                int firstforward = 110;

                                //pick it up
                                pickup(1); //pick up skystone
                                sleep(100);
                                c.forward(firstforward, 0.5); //move stone to the end
                                sleep(100);
                                pickup(0); //release the servo
                                sleep(100);
                                c.forward(firstforward - 24, -0.5); //move to second one
                                sleep(100);
                                //pick it up
                                pickup(1); //pick up second skystone
                                sleep(100);
                                c.forward(firstforward - 24, 0.5); //move to the end
                                sleep(100);
                                pickup(0); //release the servo
                                sleep(100);
                                break;
                            }
                        }
                        telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    public void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    public void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    public void pickup(double position) {
        drag.setPosition(position);
    }


    public void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (Exception e) {

        }
    }

    public void move(int fl, int bl, int fr, int br) {
        int newFLTarget = FL.getCurrentPosition() + (int)(fl * COUNTS_PER_INCH);
        int newBLTarget = BL.getCurrentPosition() + (int)(bl * COUNTS_PER_INCH);
        int newBRTarget = BR.getCurrentPosition() + (int)(br * COUNTS_PER_INCH);
        int newFRTarget = FR.getCurrentPosition() + (int)(fr * COUNTS_PER_INCH);


        FL.setTargetPosition(newFLTarget); //sets the distance of the motor
        FR.setTargetPosition(newFRTarget); //sets the distance of the motor
        BR.setTargetPosition(newBRTarget); //sets the distance of the motor
        BL.setTargetPosition(newBLTarget); //sets the distance of the motor

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION); //tells the motor to go the distance of the encoder
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION); //tells the motor to go the distance of the encoder
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION); //tells the motor to go the distance of the encoder
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION); //tells the motor to go the distance of the encoder

        runtime.reset();
        // Stop all motion;
        FL.setPower(0.3);
        FR.setPower(0.3);
        BR.setPower(0.3);
        BL.setPower(0.3); //speed of the motor

        while (opModeIsActive() && //use this method to prevent skipping of encoders
                FL.isBusy()) {


        }

        // Stop all motion;
        FL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
        BL.setPower(0);

        // Turn off RUN_TO_POSITION
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }
}