// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.*;
import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import edu.wpi.first.wpilibj.Encoder;

import edu.wpi.first.wpilibj.XboxController;
/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private XboxController gamepad1;
  private XboxController gamepad2;

  private final WPI_TalonFX leftmotorA = new WPI_TalonFX(1);
  private final WPI_TalonFX leftmotorB = new WPI_TalonFX(2);
  private final WPI_TalonFX rightmotorA = new WPI_TalonFX(3);
  private final WPI_TalonFX rightmotorB = new WPI_TalonFX(4);
  
  private final Encoder leftEncoder = new Encoder(0, 1);
  private final Encoder rightEncoder = new Encoder(2, 3);
  private final Encoder TESTencoder = new Encoder (8,9);


  private DifferentialDrive drive = new DifferentialDrive(leftmotorA, rightmotorA);


  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    rightmotorA.setInverted(TalonFXInvertType.Clockwise); 
    leftmotorA.setInverted(TalonFXInvertType.CounterClockwise);

    rightmotorB.follow(rightmotorA);
    leftmotorB.follow(leftmotorA);

    rightmotorB.setInverted(InvertType.FollowMaster);
    leftmotorB.setInverted(InvertType.FollowMaster);

    gamepad1 = new XboxController(0);
    gamepad2 = new XboxController(1);

    rightEncoder.setDistancePerPulse((6*3.14159/2048));
    leftEncoder.setDistancePerPulse((6*3.14159)/2048.);
    TESTencoder.setDistancePerPulse(2000);

    SmartDashboard.putNumber("rightEncoder", rightEncoder.getDistance());
    SmartDashboard.putNumber("rightEncoder DPP", rightEncoder.getDistancePerPulse());
    SmartDashboard.putNumber("leftEncoder", leftEncoder.getDistance());
    SmartDashboard.putNumber("leftEncoder DPP", leftEncoder.getDistancePerPulse());
    SmartDashboard.putNumber("TESTencoder", TESTencoder.getDistance());
    SmartDashboard.putNumber("TESTencoder DISTANCE per pulse", TESTencoder.getDistancePerPulse());
    SmartDashboard.putNumber("TESTencoder RAW", TESTencoder.get());
  
  }

  @Override
  public void teleopPeriodic() {
    drive.tankDrive(gamepad1.getLeftY(), gamepad1.getRightY());
  }

  @Override
  public void autonomousInit(){
    rightEncoder.reset();
    leftEncoder.reset();
    TESTencoder.reset();

    rightEncoder.setMaxPeriod(1);
    leftEncoder.setMaxPeriod(1);

    rightEncoder.setMinRate(10);
    leftEncoder.setMinRate(10);

    rightEncoder.setReverseDirection(true);
    leftEncoder.setReverseDirection(false);

    rightEncoder.setSamplesToAverage(5);
    leftEncoder.setSamplesToAverage(5);

  }

  @Override
  public void autonomousPeriodic() {
    
    SmartDashboard.putNumber("rightEncoder", rightEncoder.getDistance());
    SmartDashboard.putNumber("rightEncoder DPP", rightEncoder.getDistancePerPulse());
    SmartDashboard.putNumber("leftEncoder", leftEncoder.getDistance());
    SmartDashboard.putNumber("leftEncoder DPP", leftEncoder.getDistancePerPulse());
    SmartDashboard.putNumber("TESTencoder", TESTencoder.getDistance());
    SmartDashboard.putNumber("TESTencoder DISTANCE per pulse", TESTencoder.getDistancePerPulse());
    SmartDashboard.putNumber("TESTencoder RAW", TESTencoder.get());

    // while (rightEncoder.getDistance() < 19 || leftEncoder.getDistance() < 19){
    //   drive.tankDrive(.4075, .4);
    // }
    // leftEncoder.reset();
    // rightEncoder.reset();
    // drive.tankDrive(0, 0);

    // while (rightEncoder.getDistance() < 82.6 || leftEncoder.getDistance() > -82.6){
    //   drive.tankDrive(-.4075, .4);
    // }
    // leftEncoder.reset();
    // rightEncoder.reset();
    // drive.tankDrive(0, 0);
    // return;

    if(rightEncoder.getDistance() < 19) {
      drive.tankDrive(.4075, .4);
    } 
    else if(rightEncoder.getDistance() < 101.6) {
      drive.tankDrive(-.4075, .4);
    } 
    else if(leftEncoder.getDistance() > -73){
      drive.tankDrive(-.4075, -.4);
    }
    else {
      drive.tankDrive(0, 0);
    }

  //   if(rightEncoder.getDistance() < 19 || leftEncoder.getDistance() < 19) {
  //     drive.tankDrive(.4075, .4);
  //   } 
  //   else {
  //     drive.tankDrive(0, 0);
  //   }
  //   leftEncoder.reset();
  //   rightEncoder.reset();

  }
}