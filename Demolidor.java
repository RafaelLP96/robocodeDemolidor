// New version 0.2 11-03-24
package sample;

import robocode.*;
import robocode.HitRobotEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import java.awt.*;
import java.lang.Math;

public class Demolidor extends AdvancedRobot {
    boolean movingForward;
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
			movingForward = true;
            setAhead(100);
            setTurnRight(95);
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {

            setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());
           
			double distance = e.getDistance();
	        double power = 3 /distance*200; // Quanto maior a distância, menor a força do tiro
	        
	        
	        if (power <1) {
	            power = 1;
	        } else if (power > 3) {
	            power = 3;
	        }

        setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());
        
        // Atirar com a força calculada
        fire(power);
		out.println(Math.round(power));
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
			setTurnRight(20);
			setBack(50);
		}
	}
	
	public void onHitWall(HitWallEvent e) {
		
		reverseDirection();
	}
	

}
