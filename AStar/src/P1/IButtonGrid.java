package P1;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout; //imports GridLayout library
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton; //imports JButton library
import javax.swing.JComponent;
import javax.swing.JFrame; //imports JFrame library
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;

/*
 * Here we will build all the graphic interface
 */
public class IButtonGrid {

	JFrame frame = new JFrame(); 
	public IButtonGrid( int filas,int columnas) { 
		
		@SuppressWarnings("unused")
		IBoard board = new IBoard( filas,columnas);
		GridLayout grid = new GridLayout( filas, columnas);
		final JPanel panelA = new JPanel();
		panelA.setLayout(grid);
	
		frame.setBounds(500, 300, 300, 200);
		frame.setLayout(new GridLayout( filas, columnas)); 

		IBoardButtonListener boardButtonListener = new IBoardButtonListener();
		// adds button to grid	
		for (int x = 0; x < filas; x++) {
				for (int y = 0; y < columnas; y++) {
					JButton b = new JButton(x+","+ y);
					b.setName(x + "," + y);
					b.setPreferredSize(new Dimension(50, 50));
					b.addActionListener(boardButtonListener);
					panelA.add(b); 
				}
			}

		final JRadioButton startButton = new JRadioButton("Start");
		startButton.setActionCommand("Start");
		startButton.setSelected(true);

		final JRadioButton exitButton = new JRadioButton("Exit");
		exitButton.setActionCommand("Exit");

		JRadioButton obstacleButton = new JRadioButton("Obstacle");
		obstacleButton.setActionCommand("Obstacle");

		JRadioButton dangerousAreaButton = new JRadioButton("Dangerous Area");
		dangerousAreaButton.setActionCommand("Dangerous Area");

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(startButton);
		group.add(exitButton);
		group.add(obstacleButton);
		group.add(dangerousAreaButton);

		JPanel panel = new JPanel();
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);

		// radiobutton positions

		layout.putConstraint(SpringLayout.WEST, startButton, 50,SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, startButton, 75,SpringLayout.NORTH, panel);

		layout.putConstraint(SpringLayout.WEST, exitButton, 50,SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, exitButton, 125,SpringLayout.NORTH, panel);
		
		layout.putConstraint(SpringLayout.WEST, obstacleButton, 50,SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, obstacleButton, 175,SpringLayout.NORTH, panel);

		layout.putConstraint(SpringLayout.WEST, dangerousAreaButton, 50,SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, dangerousAreaButton, 225,SpringLayout.NORTH, panel);

		panel.add(startButton);
		panel.add(exitButton);
		panel.add(obstacleButton);
		panel.add(dangerousAreaButton);

		group.add(exitButton);
		group.add(obstacleButton);

		JLabel jl = new JLabel("Select the type of box and click on the left board to set it:");
		jl.setForeground(Color.WHITE);
		layout.putConstraint(SpringLayout.NORTH, jl, 30, SpringLayout.NORTH,panel);
		layout.putConstraint(SpringLayout.WEST, jl, 10, SpringLayout.WEST,panel);
		panel.add(jl);

		JButton buttonStartFinal = new JButton("START!");
		layout.putConstraint(SpringLayout.SOUTH, buttonStartFinal, 0,SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, buttonStartFinal, 0,SpringLayout.EAST, panel);
		panel.add(buttonStartFinal);
		
		frame.setLayout(new GridLayout(1, 1));
		panel.setBackground(Color.LIGHT_GRAY);
		Container contentPane = frame.getContentPane();
		contentPane.add(panelA, BorderLayout.CENTER);
		contentPane.add(panel);
		panel.setLayout(layout);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); // sets appropriate size for frame
		frame.setVisible(true); // makes frame visible

	
		// LISTENERS

		IRadioListener myListener = new IRadioListener();
		startButton.addActionListener(myListener);
		exitButton.addActionListener(myListener);
		obstacleButton.addActionListener(myListener);
		dangerousAreaButton.addActionListener(myListener);
		buttonStartFinal.addActionListener(new ActionListener() {

			//Method to draw the final route
			public void actionPerformed(ActionEvent e) {
				IBoard.printGrid();
				AStar.inicio();
				for (int i = 0; i < AStar.getListaCerradaTotal().size(); i++) {
					for (Component b : panelA.getComponents()) {
						if (AStar.matriz[AStar.getListaCerradaTotal().get(i).getX()][AStar.getListaCerradaTotal().get(i).getY()] != 3) {
							if (b instanceof JButton) {
								String sName = AStar.getListaCerradaTotal().get(i).getX() + "," + AStar.getListaCerradaTotal().get(i).getY();
								for (int j = 0; j < IBoard.listaSalidas.size(); j++) {
									if (IBoard.listaSalidas.get(j).getCoordentaAsStringAlContario().equals(b.getName())) {
										((AbstractButton) b).getModel().setPressed(true);
										b.setBackground(Color.BLUE);
										((JComponent) b).setOpaque(true);
										((AbstractButton) b).setText("E"+ (j + 1));
									} else {
										 if(b.getName().equals(IBoard.start.getX()+","+IBoard.start.getY()) ) {
										   	((AbstractButton) b).getModel().setPressed(true);
										   	b.setBackground(Color.CYAN);
											  ((JComponent) b).setOpaque(true);
											  ((AbstractButton) b).setText("Start");
										 }
										 else {
										  if (b.getName().equals(sName)) {
											  ((AbstractButton) b).getModel().setPressed(true);
											  b.setBackground(Color.GREEN);
											  ((JComponent) b).setOpaque(true);
											  ((AbstractButton) b).setText("*");
										  }
										}
									}
								}
							}
						}
					}
				}
			}			
		});
	}
}
