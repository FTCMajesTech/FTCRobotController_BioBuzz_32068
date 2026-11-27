package org.firstinspires.ftc.teamcode.pedro.procedures;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private static final String INTAKE_NAME = "intake";

    public static final double IN_POWER = 1.0;
    public static final double OUT_POWER = -1.0;

    private final DcMotor intakeMotor;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, INTAKE_NAME);

        // Flip if the intake spins the wrong way
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double power) {
        intakeMotor.setPower(power);
    }

    /** Pull balls in. */
    public void in() {
        setPower(IN_POWER);
    }

    /** Spit balls out (clears jams). */
    public void out() {
        setPower(OUT_POWER);
    }

    public void stop() {
        setPower(0);
    }
}