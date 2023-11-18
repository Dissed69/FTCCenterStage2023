import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@TeleOp(name = "teletest")
public class teletest extends OpMode {
    protected DcMotor frontLeft;
    protected DcMotor backLeft;
    protected DcMotor frontRight;
    protected DcMotor backRight;
    protected DcMotor arm;
    protected Servo airplaneServo;
    protected Servo clawServo;
    protected boolean isLowered;
    protected boolean isOpenedClaw;
    static final double tick_count = 400;

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double ARM_RAISE_INCHES = 4.6;
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    public static final double MID_SERVO       =  0.5 ;
    public static final double HAND_SPEED      =  0.02 ;  // sets rate to move servo
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;


    public void raiseArm(double speed){
        raiseArm(speed, ARM_RAISE_INCHES);
    }
    public void raiseArm(double speed,
                         double armInches) {
        int newArmTarget;

        // Determine new target position, and pass to motor controller
        newArmTarget = arm.getCurrentPosition() + (int) (armInches * COUNTS_PER_INCH);
        arm.setTargetPosition(newArmTarget);

        // Turn On RUN_TO_POSITION
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        arm.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.
        while (arm.isBusy()) {
//
//                 Display it for the driver.
//                telemetry.addData("Running to", " %7d :%7d", newLeftTarget, newRightTarget);
//                  telemetry.addData("Currently at", " at %7d :%7d",
//                           leftDrive.getCurrentPosition(), rightDrive.getCurrentPosition());
            //telemetry.update();
        }

        // Stop all motion;
        arm.setPower(0);
        // Turn off RUN_TO_POSITION
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        isLowered = false;
    }

    public void lowerArm(double speed){
        lowerArm(speed, ARM_RAISE_INCHES);
    }
    public void lowerArm(double speed,
                         double armInches) {
        int newArmTarget;

        newArmTarget = arm.getCurrentPosition() - (int) (armInches * COUNTS_PER_INCH);  //plus or minus?
        arm.setTargetPosition(newArmTarget);

        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm.setPower(Math.abs(speed));

        while (arm.isBusy()) {}

        arm.setPower(0);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        isLowered = true;
    }

    public void encoderDrive(double speed,
                             double driveInches) {
        int driveFrontLeftTarget, driveFrontRightTarget, driveBackLeftTarget,driveBackRightTarget;

        driveFrontLeftTarget = frontLeft.getCurrentPosition() + (int) (driveInches * COUNTS_PER_INCH);
        frontLeft.setTargetPosition(driveFrontLeftTarget);
        driveFrontRightTarget = frontRight.getCurrentPosition() + (int) (driveInches * COUNTS_PER_INCH);
        frontRight.setTargetPosition(driveFrontRightTarget);
        driveBackLeftTarget = backLeft.getCurrentPosition() + (int) (driveInches * COUNTS_PER_INCH);
        backLeft.setTargetPosition(driveBackLeftTarget);
        driveBackRightTarget = backRight.getCurrentPosition() + (int) (driveInches * COUNTS_PER_INCH);
        backRight.setTargetPosition(driveBackRightTarget);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(Math.abs(speed));
        frontRight.setPower(Math.abs(speed));
        backLeft.setPower(Math.abs(speed));
        backRight.setPower(Math.abs(speed));

        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
//                 Display it for the driver.
//                telemetry.addData("Running to", " %7d :%7d", newLeftTarget, newRightTarget);
//                  telemetry.addData("Currently at", " at %7d :%7d",
//                           leftDrive.getCurrentPosition(), rightDrive.getCurrentPosition());
            //telemetry.update();
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void releasePlane(){
        airplaneServo.setPosition(1);
    }

    public void closeClaw(){
        clawServo.setPosition(1);
    }

    public void openClaw(){
        clawServo.setPosition(0);
    }

    @Override
    public void init() {
        frontLeft  = hardwareMap.get(DcMotor.class, "frontL");
        backLeft  = hardwareMap.get(DcMotor.class, "backL");
        frontRight = hardwareMap.get(DcMotor.class, "frontR");
        backRight = hardwareMap.get(DcMotor.class, "backR");
        arm = hardwareMap.get(DcMotor.class, "arm");
        airplaneServo = hardwareMap.get(Servo.class, "plane");
//        clawServo = hardwareMap.get(Servo.class, "right_hand");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.REVERSE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        airplaneServo.setPosition(0.5);
//        clawServo.setPosition(0);

        isLowered = true;
        isOpenedClaw = true;

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    @Override
    public void loop() {
        if(gamepad2.a){
            raiseArm(0.22, 4.65);
        }

        if(gamepad2.dpad_left){
            airplaneServo.setPosition(0.6);
            airplaneServo.setPosition(0.7);
            airplaneServo.setPosition(0.8);
            airplaneServo.setPosition(0.9);
            airplaneServo.setPosition(1);
        }
        if(gamepad2.dpad_right){
            airplaneServo.setPosition(0);
        }
        if(gamepad2.dpad_up){
            airplaneServo.setPosition(0.5);
        }
        if(gamepad2.x){
            if(isOpenedClaw) closeClaw();
            else openClaw();
        }

        //arm goes up
        if (gamepad2.right_bumper){
            arm.setPower(0.30);
        }
        //arm goes down
        else if (gamepad2.left_bumper){
            arm.setPower(-0.25);
        }
        else{
            arm.setPower(0.0);
        }

        double x = -gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double z = gamepad1.right_stick_x;

        double multiplier = 0.55;
        if (gamepad1.left_trigger > 0.1) {
            multiplier *= 0.33;
        }
        if (gamepad1.right_trigger > 0.1) {
            multiplier *= 0.33;
        }
        double v1 = Range.clip(y - x + z, -multiplier, multiplier);
        double v2 = Range.clip(y + x - z, -multiplier, multiplier);
        double v3 = Range.clip(y + x + z, -multiplier, multiplier);
        double v4 = Range.clip(y - x - z, -multiplier, multiplier);

        frontLeft.setPower(v1);
        frontRight.setPower(v2);
        backLeft.setPower(v3);
        backRight.setPower(v4);
    }
}