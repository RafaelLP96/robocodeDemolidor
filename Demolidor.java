// 01/04/2024 version
package sample;

import robocode.*;
import robocode.HitRobotEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import java.awt.*;
import java.lang.Math;

public class Demolidor extends AdvancedRobot {
    boolean movingForward;
	boolean lockForward = false;
    public void run() {
	
        // Cores
        setBodyColor(java.awt.Color.blue);
        setGunColor(java.awt.Color.blue);
        setRadarColor(java.awt.Color.blue);
        setBulletColor(java.awt.Color.blue);
        setScanColor(java.awt.Color.blue);

        // Loop 
        while (true) {
            // Movimentar
			if (lockForward=true){
			movingForward = true;
            setAhead(100);
            setTurnRight(95);
			}
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {

            setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());
            setTurnRight(2);
			
			double distance = e.getDistance();
	        double power = 3 /distance*200; // Quanto maior a distância, menor a força do tiro
	        
	        
	        if (power <1) {
	            power = 1;
	        } else if (power > 3) {
	            power = 3;
	        }

        setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());
        
        // Atirar com a força calculada e nao atirar se estiver longe
		if (distance<800){
	        fire(power);
			out.println(Math.round(power));
		}
		if (distance<300){
			setBack(90);
			lockForward=true;
		}else{
			lockForward=false;
		}
    }
	
	public void onHitByBullet(HitByBulletEvent e){
		reverseDirection();
		
	}
	
	public void reverseDirection() {
		if (movingForward) {
			setBack(40000);
			movingForward = false;
		} else {
			setAhead(40000);
			movingForward = true;
		}
	}
	
	public void onHitRobot(HitRobotEvent e) {
		
		if (e.isMyFault()) {
			reverseDirection();
			setTurnRight(60);
			back(80);
		}
	}
	
	public void onHitWall(HitWallEvent e) {
		
		reverseDirection();
	}
	

}
