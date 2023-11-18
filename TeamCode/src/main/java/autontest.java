
//package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;
import org.openftc.easyopencv.OpenCvPipeline;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous (name = "forwardauton")
public class autontest extends LinearOpMode{
    protected DcMotor frontLeft;
    protected DcMotor backLeft;
    protected DcMotor frontRight;
    protected DcMotor backRight;
    protected DcMotor arm;
    public void runOpMode() throws InterruptedException{
        frontLeft  = hardwareMap.get(DcMotor.class, "frontL");
        backLeft  = hardwareMap.get(DcMotor.class, "backL");
        frontRight = hardwareMap.get(DcMotor.class, "frontR");
        backRight = hardwareMap.get(DcMotor.class, "backR");
        arm = hardwareMap.get(DcMotor.class, "arm");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        driveForwardEnc(0.3, 13);
        sleep(100);
        back(0.2, 1);
    }

    public void driveForwardEnc(double speed, int dist){
        dist *= 100;
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setTargetPosition(dist);
        frontRight.setTargetPosition(dist);
        backLeft.setTargetPosition(dist);
        backRight.setTargetPosition(dist);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        forward(speed);

        while(frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){

        }
        stopp();
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void forward(double speed){
        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
    }
    public void forward(double speed, int time){
        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
        sleep(time * 1000);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void left(double speed, int time){
        frontLeft.setPower(0);
        frontRight.setPower(speed);
        backLeft.setPower(0);
        backRight.setPower(speed);
        sleep(time * 1000);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    public void right(double speed, int time){
        frontLeft.setPower(speed);
        frontRight.setPower(0);
        backLeft.setPower(speed);
        backRight.setPower(0);
        sleep(time * 1000);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    public void back(double speed, int time){
        frontLeft.setPower(-speed);
        frontRight.setPower(-speed);
        backLeft.setPower(-speed);
        backRight.setPower(-speed);
        sleep(time * 1000);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void stopp(){
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}