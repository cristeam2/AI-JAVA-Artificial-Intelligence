package P1;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/*
 * Class with the listeners for the board
 */
class IBoardButtonListener implements ActionListener{  
	private int x;
	private int y;
	static boolean flagS = false;
	static boolean flagE = false;

	/*
	 * Here we take board's rows and columns 
	 */
	public void setParameters(String str){
		String[] results = str.split(",");
		x =	Integer.parseInt(results[0]);
		y = Integer.parseInt(results[1]);
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 *
	 *Class to give order to the board and know when there is select one cell option 
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			setParameters(e.getActionCommand());
		}
		catch( Exception ex ) {}
		JButton b = (JButton)e.getSource();
		String text = e.getActionCommand();

		if(IRadioListener.typeBox == 1 && flagS) 
			JOptionPane.showMessageDialog(null, "You are only allowed to set one Start box.");
		else if (b.getBackground() == new JButton().getBackground()){
			IBoard.board[x][y] = IRadioListener.typeBox;
			switch (IRadioListener.typeBox) {
			case 0: 
				break;
				//Start cell
			case 1: 
				flagS = true;
				text = "S";
				b.getModel().setPressed(true);
				b.setBackground(Color.GREEN);
				b.setOpaque(true);
				IBoard.start = new Coordenate(x,y);
				break;
				
			case 4: 
				flagS = true;
				text = "";
				b.getModel().setPressed(true);
				b.setBackground(Color.GRAY);
				b.setOpaque(true);
				break;
				//finish cell
			case 2:
				b.getModel().setPressed(true);
				b.setBackground(Color.RED);
				b.setOpaque(true);
				IBoard.listaSalidas.add(new Coordenate(x,y));
				System.out.print("salidas: ");
				for (Coordenate co: IBoard.listaSalidas)
					System.out.print( co.getX()+","+co.getY()+" ");
				System.out.println();
				IBoard.exit= IBoard.listaSalidas.get(0);
				if(!flagE)
					JOptionPane.showMessageDialog(null, "You can set multiple checkpoints");
				flagE = true;
				text = "E"+IBoard.listaSalidas.size();
				break;
				//Forbidden cell
			case 3: text = "X";
			b.getModel().setPressed(true);
			b.setBackground(Color.BLACK);
			b.setOpaque(true);
			break;
			}
			b.setText(text);
		}

	}
}