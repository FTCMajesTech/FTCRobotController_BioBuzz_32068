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

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

@Autonomous(name="test auto", group="Robot")
public class testauto extends OpMode {
    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start1 = poseFactory.of(53.5, 10, 90);
    private final Pose path1 = poseFactory.of(15, 10, 180);
    private final Pose path1Control1 = poseFactory.of(46.75, 34.75, 0);
    private final Pose path2 = poseFactory.of(59, 113, 270);
    private final Pose path2Control1 = poseFactory.of(29, 142, 0);


    public Path curve1() {
        return Paths.curve(start1, path1Control1, path1).linear(start1, path1);
    }
    public Path curve2() {
        return Paths.curve(path1, path2Control1, path2).linear(path1, path2);
    }

    private Command autoRoutine(){
        return sequential(
                follow(follower, curve1()),
                follow(follower, curve2()));
    }





    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(start1);
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
