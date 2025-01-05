
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name="servoTest", group="TeleOp")

public class servoTest extends LinearOpMode {

    public Servo wrist = null; //the wrist serv

    // o
    final double WRIST_FOLDED_IN = 0.5;
    final double WRIST_FOLDED_OUT = 0.85;

    @Override
    public void runOpMode() {

        //intake = hardwareMap.get(CRServo.class, "intake");
        wrist  = hardwareMap.get(Servo.class, "wrist");

        //wrist.setDirection(Servo.Direction.REVERSE);

        wrist.setPosition(WRIST_FOLDED_IN);

        // When the driver presses start this telemtry will show
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addLine("V4");

            //ARM CODE begins

            telemetry.addData("Button Pressed", "NONE");
            //if y is pressed the arm should rise

            if(gamepad1.b) {
                telemetry.addData("Button Pressed", "WRIST OUT(B)");
                wrist.setPosition(WRIST_FOLDED_OUT);
            }
            else if(gamepad1.a) {
                telemetry.addData("Button Pressed", "WRIST IN(A)");
                wrist.setPosition(WRIST_FOLDED_IN);
            }

            telemetry.update();


        }
    }}

