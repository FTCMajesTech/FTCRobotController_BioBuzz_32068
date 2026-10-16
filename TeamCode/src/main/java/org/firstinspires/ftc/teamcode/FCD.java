package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name="FCD", group="Robot")
public class FCD extends OpMode {
    private Follower follower;

    private DcMotor intake, shooter;
    private Servo transfer;

    //IMU for field-centric driving
    private IMU imu;


    @Override
    public void init() {
        // initialize the constants for the follower
        follower = Constants.create(hardwareMap);

        //initialize hardware motors and servos

        intake = hardwareMap.get(DcMotor.class, "intake");
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        //transfer = hardwareMap.get(Servo.class, "transfer");

        //set motor directions

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
        // drive controls
       /* double forward = -gamepad1.left_stick_y;
        double lateral = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x;

        follower.manual(forward, lateral, turn);
        follower.update();*/

       /* DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_x,
                -gamepad1.left_stick_y,
                -gamepad1.right_stick_x,
                follower.pose().heading()
        );
        ManualDrive.driveOrHold(follower, powers);
        follower.update();*/

        if (gamepad1.yWasPressed()) {
            DrivePowers powers = ManualDrive.fieldCentric(
                    -gamepad1.left_stick_x,
                    -gamepad1.left_stick_y,
                    -gamepad1.right_stick_x,
                    follower.pose().heading()
            );
            ManualDrive.driveOrHold(follower, powers);
            follower.update();
        } else {
            double forward = -gamepad1.left_stick_y;
            double lateral = -gamepad1.left_stick_x;
            double turn = -gamepad1.right_stick_x;

            follower.manual(forward, lateral, turn);
            follower.update();
        }



        if (gamepad1.a) {
            intake.setPower(1.0);
        } else {
            intake.setPower(0.0);
        }

       if (gamepad1.x) {
            shooter.setPower(1.0);
            //  transfer.setPosition(1.0);
        } else {
            shooter.setPower(0.0);
            //transfer.setPosition(0.0);
        }

       if (gamepad1.b) {
           transfer.setPosition(1);
       } else {
           transfer.setPosition(0);
       }
        telemetry.addData("Shooter Power", shooter.getPower());
        telemetry.addData("B Button Pressed", gamepad1.b);
        telemetry.update();
    }


}
