package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="FCD", group="Robot")
public class FCD extends OpMode {
    public DcMotor FRdrive, BRdrive, BLdrive, FLdrive;
    public DcMotor intake, shooter;
    public Servo transfer;

    //IMU for field-centric driving
    public IMU imu;


    @Override
    public void init() {
        //initialize hardware motors and servos
        FLdrive = hardwareMap.get(DcMotor.class, "FLdrive");
        BLdrive = hardwareMap.get(DcMotor.class, "BLdrive");
        FRdrive = hardwareMap.get(DcMotor.class, "FRdrive");
        BRdrive = hardwareMap.get(DcMotor.class, "BRdrive");

        intake = hardwareMap.get(DcMotor.class, "intake");
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        //transfer = hardwareMap.get(Servo.class, "transfer");

        //set motor directions
        FLdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        BLdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        FRdrive.setDirection(DcMotorSimple.Direction.FORWARD);
        BRdrive.setDirection(DcMotorSimple.Direction.FORWARD);


        //transfer.setPosition(0);
        intake.setDirection(FORWARD);
        intake.setPower(0);
        shooter.setDirection(FORWARD);
        shooter.setPower(0);

        //initialize the IMU
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
        imu.initialize(parameters);

        telemetry.addData("Status", "Initialized. Press Start.");
        telemetry.update();
    }




    @Override
    public void start() {
        imu.resetYaw();

    }

    @Override
    public void loop() {
        double y  = -gamepad1.left_stick_y;
        double x  =  gamepad1.left_stick_x;
        double rx =  gamepad1.right_stick_x;


        if (gamepad1.a) {
            intake.setPower(1.0);
        } else {
            intake.setPower(0.0);
        }

       if (gamepad1.b) {
            shooter.setPower(1.0);
            //  transfer.setPosition(1.0);
        } else {
            shooter.setPower(0.0);
            //transfer.setPosition(0.0);
        }

        telemetry.addData("Shooter Power", shooter.getPower());
        telemetry.addData("B Button Pressed", gamepad1.b);
        telemetry.update();
    }


}
