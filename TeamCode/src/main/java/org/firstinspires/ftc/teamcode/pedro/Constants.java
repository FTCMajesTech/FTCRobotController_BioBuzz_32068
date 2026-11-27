package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("FLdrive");
        c.frontRightName.set("FRdrive");
        c.backLeftName.set("BLdrive");
        c.backRightName.set("BRdrive");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);

        c.manualBrakeMode.set(true);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("stalker");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-3.85677); //3.85677
        c.yPodOffset.set(5.68786); //5.68786
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.27384621291690203);
                Controller secondaryTranslationalForward = Controller.proportional(0.10117885836722142);
                Controller primaryTranslationalLateral = Controller.proportional(0.5696279304166455);
                Controller secondaryTranslationalLateral = Controller.proportional(0.2104622995503254);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.009509515442221164));
                c.brake.set(Controller.proportionalFeedforward(0.00808308812588799));

                c.headingFeedback.set(Controller.proportional(5.0540829881436835));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.06701489455900399, 0.004823465684407313));

                c.linearBrakeCoefficients.set(Matrix.diag(0.11759059055475761, 0.08037562603570503));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0010908735552383091, 0.0013023353922697266));

                c.maxAchievableForwardVelocity.set(94.39418458537111);
                c.maxAchievableStrafeVelocity.set(77.76979248012475);
                c.naturalForwardDeceleration.set(33.39428926366792);
                c.naturalStrafeDeceleration.set(75.9099256474097);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}

