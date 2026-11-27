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

@Autonomous(name="RED_TOP_SOLO", group="Robot")
public class RED_TOP_SOLO extends OpMode {
    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    // ---------------- POSES ----------------
    // Start
    private final Pose start = poseFactory.of(53.5, 10, 90);

    // 1) start -> GARDEN (near corner)
    private final Pose gardenControl = poseFactory.of(46.75, 34.75, 0);
    private final Pose garden = poseFactory.of(15, 10, 180);

    // 2) GARDEN -> LAUNCH spot
    private final Pose gardenStart = poseFactory.of(15, 10, 180);
    private final Pose launchControl = poseFactory.of(46.75, 34.75, 0);
    private final Pose launch = poseFactory.of(56.046, 17.9656, 90);

    // 3) LAUNCH spot -> far wall
    private final Pose launchStart = poseFactory.of(56.046, 17.9656, 270);
    private final Pose farWallControl = poseFactory.of(66, 43, 0);
    private final Pose farWall = poseFactory.of(58.75, 124.75, 270);

    // 4) far wall sweep (move along the wall)
    private final Pose farWallSweepStart = poseFactory.of(58.75, 124.75, 270);
    private final Pose farWallSweepEnd = poseFactory.of(46.7791, 124.75, 90);

    // 5) far wall return (sweep back)
    private final Pose farWallReturnStart = poseFactory.of(46.7791, 124.75, 90);
    private final Pose farWallReturnEnd = poseFactory.of(58.75, 124.75, 270);

    // 6) far wall -> far corner
    private final Pose farCornerStart = poseFactory.of(58.75, 124.75, 270);
    private final Pose farCorner = poseFactory.of(23, 125, 150);

    // 7) far corner -> back to LAUNCH/start spot
    private final Pose returnStart = poseFactory.of(23, 125, 150);
    private final Pose returnControl = poseFactory.of(14.8758, 48.727, 0);
    private final Pose returnEnd = poseFactory.of(53.5, 10, 90);

    // 8) start spot -> left wall
    private final Pose leftWallStart = poseFactory.of(53.5, 10, 90);
    private final Pose leftWallControl = poseFactory.of(18, 43, 0);
    private final Pose leftWall = poseFactory.of(18, 107, 90);

    // ---------------- PATHS ----------------

    public Path toGarden() {
        return Paths.curve(start, gardenControl, garden).linear(start, garden);
    }

    public Path toLaunch() {
        return Paths.curve(gardenStart, launchControl, launch).linear(gardenStart, launch);
    }

    public Path toFarWall() {
        return Paths.curve(launchStart, farWallControl, farWall).linear(launchStart, farWall);
    }

    public Path sweep() {
        return Paths.line(farWallSweepStart, farWallSweepEnd).linear(farWallSweepStart, farWallSweepEnd);
    }

    public Path sweepBack() {
        return Paths.line(farWallReturnStart, farWallReturnEnd).linear(farWallReturnStart, farWallReturnEnd);
    }

    public Path toCorner() {
        return Paths.line(farCornerStart, farCorner).linear(farCornerStart, farCorner);
    }

    public Path toHome() {
        return Paths.curve(returnStart, returnControl, returnEnd).linear(returnStart, returnEnd);
    }

    public Path toLeftWall() {
        return Paths.curve(leftWallStart, leftWallControl, leftWall).linear(leftWallStart, leftWall);
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
                        follow(follower, toGarden()),
                        follow(follower, toLaunch()),
                        waitMs(3000),   // wait for shooting
                        follow(follower, toFarWall()),
                        //follow(follower, sweep()),
                        follow(follower, sweepBack()),
                        waitMs(3000),   // wait for shooting
                        follow(follower, toCorner()),
                        follow(follower, toHome()),
                        waitMs(3000),   // wait for shooting
                        follow(follower, toLeftWall())
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