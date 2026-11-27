package org.firstinspires.ftc.teamcode.pedro.procedures;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private static final String SHOOTER_NAME = "shooter";

    public static final double TARGET_VELOCITY = 2800;   // ticks per second
    public static final double READY_VELOCITY = 2700;

    private final DcMotorEx shooterMotor;

    public Shooter(HardwareMap hardwareMap) {
        shooterMotor = hardwareMap.get(DcMotorEx.class, SHOOTER_NAME);


        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    /** Spin the flywheel at a target velocity (ticks per second). */
    public void setShooterVelocity(double ticksPerSecond) {
        shooterMotor.setVelocity(ticksPerSecond);
    }

    /** Spin the flywheel at the default target velocity. */
    public void spinUp() {
        setShooterVelocity(TARGET_VELOCITY);
    }

    public double getShooterVelocity() {
        return shooterMotor.getVelocity();
    }

    public boolean isReady() {
        return getShooterVelocity() >= READY_VELOCITY;
    }

    public void stop() {
        shooterMotor.setVelocity(0);
        shooterMotor.setPower(0);
    }
}