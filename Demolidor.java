/*
 * Copyright (c) 2001-2023 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package sample;
import robocode.*;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;


/**
 * MyFirstRobot - a sample robot by Mathew Nelson.
 * <p>
 * Moves in a seesaw motion, and spins the gun around at each end.
 *
 * @author Mathew A. Nelson (original)
 */
public class Demolidor extends Robot {
	
	// Declarar variaveis
	int count = 0; // Valor da distancia ao alvo
	
	double gunTurnAmt; // Quanto girar a mira enquanto escaneia
	
	String trackName; //Guarda nome do robo alvo para perseguir

	public void run() {

		///Preparar arma
		trackName = null; //Setar que ainda achou ninguem
		setAdjustGunForRobotTurn(true); //Mantem a mira enquanto giramos
		gunTurnAmt = 10; // Inicia o giro da mira em 10
		
		while (true) {
			turnGunRight(360);
			// Girar a mira e procurar o alvo
			//turnGunRight(gunTurnAmt);
			//Conta quanto tempo esta procurando
			count++;
			// Gira no sentido contrario depois de 2 giros se não achar o alvo
			if (count > 2) {
				//gunTurnAmt = -10;
			}

			// Se ainda não achar o alvo em 5 giros. Vira pra direita
			if (count > 5) {
				//gunTurnAmt = 10;
			}

			//Se AINDA, não achou o alvo em 11 giros, "esqueça-o" e procure outro

			if (count > 11) {
				trackName = null;
			}
		}
	}

	// Inimigo localizado!!!
	public void onScannedRobot(ScannedRobotEvent e) {
		//Ignora quem não for "A CAÇA"
		//if (trackName != null && !e.getName().equals(trackName)) {
		//	return;
		//}
		fire(3);
		// Se não tivermos um alvo, agora teremos
		if (trackName == null) {

			trackName = e.getName();
			out.println("Tracking" + trackName);
		}

		//Reseta a contagem quanto tiver um alvo
		count = 0;

		// Se nosso alvo estiver longe, persiga-o.
		if (e.getDistance() > 20){

			gunTurnAmt = normalRelativeAngleDegrees(e.getBearing()+(getHeading()-getRadarHeading()));

			//turnGunRight(gunTurnAmt);
			turnRight(e.getBearing());

			ahead(e.getDistance() - 10);
			return;
		}

	
		// Nosso alvo esta perto...
		/*
		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmt);
		*/
		fire(3);
	
		// Nosso alvo esta MUITO perto
		if (e.getDistance()  < 1)	{
			if (e.getBearing() > -10 && e.getBearing() <= 10)	{
				back(100);
			}
			else	{
				ahead(60);
			}
			scan();
		}
	}
		
	

	// caso acerte alguem, este será o alvo
	public void onHitRobot(HitRobotEvent e)	{

		if (trackName != null && !(trackName.equals(e.getName())))	{
			out.println("Seguindo" + e.getName() + "porque me bateu");
		}
		// Define o alvo
		trackName = e.getName();

		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmt);
		fire(3);
		back(70);
	}
	
	public void onHitByBullet(HitByBulletEvent e) {
        turnLeft(90 - e.getBearing());
    }
	
}
			


