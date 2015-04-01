package P1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Class with the standard definition about possibilities on the board
class IRadioListener implements ActionListener{  

	static int typeBox = 1;

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Start") {
			typeBox = 1;
		} else if (e.getActionCommand() == "Obstacle") {
			typeBox = 3;
		}else if (e.getActionCommand() == "Exit") {
			typeBox = 2;
		}else if (e.getActionCommand() == "Dangerous Area") {
			typeBox = 4;
		}
	}
}