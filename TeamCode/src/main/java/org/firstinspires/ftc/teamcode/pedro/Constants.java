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
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("stalker");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-9.589074202409881);
        c.yPodOffset.set(5.246203565222072);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.2344702500278425);
                Controller secondaryTranslationalForward = Controller.proportional(0.08663049222481994);
                Controller primaryTranslationalLateral = Controller.proportional(0.24491395541548833);
                Controller secondaryTranslationalLateral = Controller.proportional(0.09048916230460759);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.016897706480267883));
                c.brake.set(Controller.proportionalFeedforward(0.0143630505082277));

                c.headingFeedback.set(Controller.proportional(4.569060482240155));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.047505232036937364, 0.006389645200549565));

                c.linearBrakeCoefficients.set(Matrix.diag(0.09508324343973285, 0.0630520239839743));
                c.quadraticBrakeCoefficients.set(Matrix.diag(1.2890837818024277E-4, 0.0010912798831615826));

                c.maxAchievableForwardVelocity.set(61.63732000901363);
                c.maxAchievableStrafeVelocity.set(50.802280830713215);
                c.naturalForwardDeceleration.set(45.94640551100628);
                c.naturalStrafeDeceleration.set(75.17392224254081);
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

