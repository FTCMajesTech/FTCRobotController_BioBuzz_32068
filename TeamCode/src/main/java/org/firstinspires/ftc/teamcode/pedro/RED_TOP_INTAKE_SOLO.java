package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.api.Paths;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

@Autonomous(name="RED_TOP_INTAKE_SOLO", group="Robot")
public class RED_TOP_INTAKE_SOLO extends OpMode {
    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    // pose factory
    private final Pose rob = poseFactory.of(53.5, 10, 90);
    private final Pose path1 = poseFactory.of(15, 10, 180);
    private final Pose path1Control1 = poseFactory.of(46.75, 34.75, 0);
    private final Pose point2 = poseFactory.of(53, 10, 0);
    private final Pose point3Start = poseFactory.of(53, 10, 0);
    private final Pose point3 = poseFactory.of(14, 47, 180);
    private final Pose point4Start = poseFactory.of(14, 47, 180);
    private final Pose point4 = poseFactory.of(59, 113, 270);
    private final Pose point4Control1 = poseFactory.of(29, 142, 0);
    private final Pose point5Start = poseFactory.of(59, 113, 0);
    private final Pose point5 = poseFactory.of(47, 128, 90);
    private final Pose point6Start = poseFactory.of(47, 128, 0);
    private final Pose point6 = poseFactory.of(59, 113, 270);
    private final Pose point7Start = poseFactory.of(59, 113, 0);
    private final Pose point7 = poseFactory.of(23, 125, 150);
    private final Pose point8Start = poseFactory.of(23, 125, 0);
    private final Pose point8 = poseFactory.of(59, 113, 270);
    private final Pose point9Start = poseFactory.of(59, 113, 0);
    private final Pose point9 = poseFactory.of(11, 107, 180);

    // PATHS

    public Path path1() {
        return Paths.curve(rob, path1Control1, path1).linear(rob, path1);
    }

    public Path path2() {
        return Paths.line(path1, point2).tangent();
    }

    public Path path3() {
        return Paths.line(point3Start, point3).linear(point3Start, point3);
    }

    public Path path4() {
        return Paths.curve(point4Start, point4Control1, point4).linear(point4Start, point4);
    }

    public Path path5() {
        return Paths.line(point5Start, point5).linear(point5Start, point5);
    }

    public Path path6() {
        return Paths.line(point6Start, point6).linear(point6Start, point6);
    }

    public Path path7() {
        return Paths.line(point7Start, point7).linear(point7Start, point7);
    }

    public Path path8() {
        return Paths.line(point8Start, point8).linear(point8Start, point8);
    }

    public Path path9() {
        return Paths.line(point9Start, point9).linear(point9Start, point9);
    }

    private Command autoRoutine(){
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3()),
                follow(follower, path4()),
                follow(follower, path5()),
                follow(follower, path6()),
                follow(follower, path7()),
                follow(follower, path8()),
                follow(follower, path9()));

    }

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(rob);
    }

    @Override
    public void start() {
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();

        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", follower.pose().heading());
        telemetry.update();
    }
}
