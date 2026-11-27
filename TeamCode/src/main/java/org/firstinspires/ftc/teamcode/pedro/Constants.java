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
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("stalker");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(3.8582677165);
        c.yPodOffset.set(-5.688976378);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.3553427789990066);
                Controller secondaryTranslationalForward = Controller.proportional(0.13128966190620742);
                Controller primaryTranslationalLateral = Controller.proportional(0.4409910357781889);
                Controller secondaryTranslationalLateral = Controller.proportional(0.162934404222545);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.009496652474204875));
                c.brake.set(Controller.proportionalFeedforward(0.008072154603074142));

                c.headingFeedback.set(Controller.proportional(4.622644322984337));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.061816465963643666, 0.005435809921135567));

                c.linearBrakeCoefficients.set(Matrix.diag(0.11764651471786998, 0.05979976495057742));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.001025226829029855, 0.001800614129673901));

                c.maxAchievableForwardVelocity.set(93.92925124280632);
                c.maxAchievableStrafeVelocity.set(77.9478122275227);
                c.naturalForwardDeceleration.set(39.86923399343899);
                c.naturalStrafeDeceleration.set(71.23967924286603);
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

