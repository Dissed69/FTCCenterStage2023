
//package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous (name = "bluestraightauton")
public class bluestraightauton extends LinearOpMode{
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

        waitForStart();
        //forward(3);
//        left(0.3, 3);
//        sleep(1000);
//        right(0.3, 3);x
//        sleep(1000);
//        forward(0.3, 3);
//        sleep(1000);
//        back(0.3, 3);
//        sleep(1000);
//        newT(3);
        forward(0.43,1);
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
}