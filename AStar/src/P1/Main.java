package P1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

/**
 * Class to launch the application.
 */

public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Main window = new Main();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame ( rows and columns).
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(500, 300, 300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Select the size of the board");;

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		Container contentPane = frame.getContentPane();
		contentPane.add(panel, BorderLayout.CENTER);
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);

		JLabel jl = new JLabel("Columns");
		panel.add(jl);

		JLabel jl2 = new JLabel("Rows");
		panel.add(jl2);

		SpinnerModel modeltau = new SpinnerNumberModel(10,1,99,1);
		final JSpinner spinner_height = new JSpinner(modeltau);
		((JSpinner.NumberEditor) spinner_height.getEditor()).getFormat().setMaximumFractionDigits(2);
		panel.add(spinner_height);

		SpinnerModel modeltau2 = new SpinnerNumberModel(10,1,99,1);
		final JSpinner spinner_width = new JSpinner(modeltau2);
		((JSpinner.NumberEditor) spinner_width.getEditor()).getFormat().setMaximumFractionDigits(2);
		panel.add(spinner_width);

		JButton button = new JButton("Next");
		button.setVerticalTextPosition(AbstractButton.BOTTOM);
		button.setHorizontalTextPosition(AbstractButton.CENTER);
		panel.add(button);

		layout.putConstraint(SpringLayout.WEST, spinner_width,135,SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, spinner_width,25,SpringLayout.NORTH, panel);

		layout.putConstraint(SpringLayout.WEST, spinner_height,135,SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, spinner_height,65,SpringLayout.NORTH, panel);

		layout.putConstraint(SpringLayout.WEST, jl,83,SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jl,25,SpringLayout.NORTH, panel);

		layout.putConstraint(SpringLayout.WEST, jl2,85,SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, jl2,65,SpringLayout.NORTH, panel);

		layout.putConstraint(SpringLayout.WEST, button,105,SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, button,125,SpringLayout.NORTH, panel);

		JComponent field = ((JSpinner.DefaultEditor) spinner_width.getEditor());
		Dimension prefSize = field.getPreferredSize();
		prefSize = new Dimension(40, prefSize.height);
		field.setPreferredSize(prefSize);

		JComponent field2 = ((JSpinner.DefaultEditor) spinner_height.getEditor());
		field2.setPreferredSize(prefSize);

		//LISTENERS
		
		button.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e)
			{
				frame.hide();
				Integer columnas = (Integer)spinner_width.getValue();
				Integer filas = (Integer)spinner_height.getValue();
				System.out.println("tablero de filas por columnas: "+filas +" x "+ columnas);
				@SuppressWarnings("unused")
				IButtonGrid bg = new IButtonGrid(filas, columnas);				
			}
		});
	}
}
