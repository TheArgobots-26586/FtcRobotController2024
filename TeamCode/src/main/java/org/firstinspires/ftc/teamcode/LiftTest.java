/*   MIT License
 *   Copyright (c) [2024] [Base 10 Assets, LLC]
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:

 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.

 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

/*
 * This is (mostly) the OpMode used in the goBILDA Robot in 3 Days for the 24-25 Into The Deep FTC Season.
 * https://youtube.com/playlist?list=PLpytbFEB5mLcWxf6rOHqbmYjDi9BbK00p&si=NyQLwyIkcZvZEirP (playlist of videos)
 * I've gone through and added comments for clarity. But most of the code remains the same.
 * This is very much based on the code for the Starter Kit Robot for the 24-25 season. Those resources can be found here:
 * https://www.gobilda.com/ftc-starter-bot-resource-guide-into-the-deep/
 *
 * There are three main additions to the starter kit bot code, mecanum drive, a linear slide for reaching
 * into the submersible, and a linear slide to hang (which we didn't end up using)
 *
 * the drive system is all 5203-2402-0019 (312 RPM Yellow Jacket Motors) and it is based on a Strafer chassis
 * The arm shoulder takes the design from the starter kit robot. So it uses the same 117rpm motor with an
 * external 5:1 reduction
 *
 * The drivetrain is set up as "field centric" with the internal control hub IMU. This means
 * when you push the stick forward, regardless of robot orientation, the robot drives away from you.
 * We "took inspiration" (copy-pasted) the drive code from this GM0 page
 * (PS GM0 is a world class resource, if you've got 5 mins and nothing to do, read some GM0!)
 * https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html#field-centric
 *
 */


@TeleOp(name="LiftTest", group="Robot")

//@Disabled
public class LiftTest extends LinearOpMode {
    public DcMotor  liftMotor        = null; //
    @Override
    public void runOpMode() {
        liftMotor = hardwareMap.dcMotor.get("extender");
        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("LIFT MOTOR ENCODER", liftMotor.getCurrentPosition());
            telemetry.update();
        }
    }

}
