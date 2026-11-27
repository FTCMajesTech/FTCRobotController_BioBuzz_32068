package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.api.Paths;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

@Autonomous(name="RED_TOP_HALF", group="Robot")
public class RED_TOP_HALF extends OpMode {
    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    // ---------------- POSES ----------------
    // Start
    private final Pose start = poseFactory.of(53.3, 10, 90);

    // start - grab balls from garden
    private final Pose path1 = poseFactory.of(32.0291, 10, 180);
    private final Pose path1Control1 = poseFactory.of(56.8942, 19.9608, 0);

    //collect balls
    private final Pose grabballsStart = poseFactory.of(32.0291, 7.5569, 180);
    private final Pose grabballs = poseFactory.of(10.75, 7.75, 180);

    //shoot balls from garden
    private final Pose shootballs2Start = poseFactory.of(10.75, 7.75, 180);
    private final Pose shootballs2 = poseFactory.of(55.75, 13.75, 90);
    private final Pose shootballs2Control1 = poseFactory.of(10.75, 37.75, 0);

    //collect balls from flower
    private final Pose collectflowerStart = poseFactory.of(55.75, 13.75, 90);
    private final Pose collectflower = poseFactory.of(13.75, 46.75, 180);

    //shoot balls from flower
    private final Pose point5Start = poseFactory.of(13.75, 46.75, 180);
    private final Pose point5 = poseFactory.of(55.75, 13.75, 90);


    // ---------------- PATHS ----------------
    public Path path1() {
        return Paths.curve(start, path1Control1, path1).linear(start, path1);
    }

    public Path grabballs() {
        return Paths.line(grabballsStart, grabballs).linear(grabballsStart, grabballs);
    }

    public Path shootballs2() {
        return Paths.curve(shootballs2Start, shootballs2Control1, shootballs2).linear(shootballs2Start, shootballs2);
    }

    public Path collectflower() {
        return Paths.line(collectflowerStart, collectflower).linear(collectflowerStart, collectflower);
    }

    public Path path5() {
        return Paths.line(point5Start, point5).linear(point5Start, point5);
    }



    // ---------------- COMMANDS ----------------

    // Does nothing for `ms` milliseconds, then moves on to the next command
    private Command waitMs(long ms) {
        final ElapsedTime timer = new ElapsedTime();
        return Command.build()
                .setStart(() -> timer.reset())
                .setExecute(() -> {})
                .setDone(() -> timer.milliseconds() >= ms)
                .setEnd(interrupted -> {})
                .requiring();
    }


    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(start);
    }

    @Override
    public void start() {
        schedule(
                sequential(
                        follow(follower, path1()),
                        follow(follower, grabballs()),
                        follow(follower, shootballs2())
                )
            );
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
