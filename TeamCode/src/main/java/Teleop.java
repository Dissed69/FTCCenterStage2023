import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

    @TeleOp(name = "tele")
    public class Teleop extends OpMode {
        protected DcMotor frontLeft;
        protected DcMotor backLeft;
        protected DcMotor frontRight;
        protected DcMotor backRight;
        protected DcMotor arm;
        protected boolean isWorking;
        static final double tick_count = 400;


        @Override
        public void init() {
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
            arm.setDirection(DcMotor.Direction.REVERSE);
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            isWorking = false;
        }

        public void encoderDrive_Start(double speed,
                                       double arbitraryUnits) {

            int armTarget;

            // Determine new target position, and pass to motor controller
            armTarget = arm.getCurrentPosition() + (int) (arbitraryUnits);
            arm.setTargetPosition(armTarget);

            // Turn On RUN_TO_POSITION
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            arm.setPower(Math.abs(speed));
        }

        public boolean encoderDrive_IsDone() {
            return !arm.isBusy();
        }

        public void encoderDrive_Complete() {
            // Stop all motion;
            arm.setPower(0);

            // Turn off RUN_TO_POSITION
            arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        @Override
        public void loop() {
            double quart = 2000;
            //arm up preset
//            if (gamepad1.a){
//                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                int newTarget = arm.getTargetPosition() + (int)quart;
//                arm.setTargetPosition(newTarget);
//                arm.setPower(1);
//                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
            if (gamepad1.a && !isWorking) {
                encoderDrive_Start(0.25, 5);
                isWorking = true;
            }
            if  (isWorking)
            {
                if (encoderDrive_IsDone())
                {
                    isWorking = false;
                    encoderDrive_Complete();
                }
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
                arm.setPower(0);
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
