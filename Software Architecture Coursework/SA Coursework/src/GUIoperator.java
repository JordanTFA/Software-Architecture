import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class GUIoperator {
	
	static Boolean choice;
	static String NHSno;
	static String name;
	static String address;
	static String condition;
	static int location;

	// Initial Screen
	public static void loadupScreenGUI(){
		
		JFrame frame = new JFrame("Kwik Medical");
		frame.setBounds(0, 0, 300, 100);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();	
		frame.getContentPane().setLayout(null);
		panel.setBounds(0, 0, 300, 100);
		panel.setLayout(null);	
		frame.getContentPane().add(panel);
		
		
		JRadioButton operator = new JRadioButton("Telephone Operator");
			operator.setSelected(true);
			operator.setBounds(10, 10, 150, 20);
		JRadioButton pda = new JRadioButton("PDA");
			pda.setBounds(10, 30, 150, 20);
			
		ButtonGroup group = new ButtonGroup();
		group.add(operator);
		group.add(pda);
			
		JButton submit = new JButton("Submit");
		submit.setBounds(200, 25, 75, 20);
		
		panel.add(submit);		
		panel.add(operator);
		panel.add(pda);
			
		submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{	
				// Operator selected
				if(operator.isSelected()){
					operatorGUI();
				// PDA selected
				} else if(pda.isSelected()){
					GUIpda.pdaGUI();
				}
				frame.setVisible(false);
			}
		});
	}
	
	// Operator has been selected
	public static void operatorGUI(){
		
		JFrame frame = new JFrame("Kwik Medical");
		frame.setBounds(0, 0, 300, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();	
		frame.getContentPane().setLayout(null);
		panel.setBounds(0, 0, 300, 400);
		panel.setLayout(null);	
		frame.getContentPane().add(panel);
		
		JLabel lblDetails = new JLabel("Please enter the following details:");
		lblDetails.setBounds(60,0,300,50);
		JLabel lblNHSno = new JLabel("NHS Number:");
		JLabel lblName = new JLabel("Name:");
		JLabel lblAddress = new JLabel("Address:");
		JLabel lblCondition = new JLabel("Medical Condition:");
		JLabel lblLocation = new JLabel("Location (1-100):");
		
		lblNHSno.setBounds(60,40,300,50);
		lblName.setBounds(60,100,300,50);
		lblAddress.setBounds(60,160,300,50);
		lblCondition.setBounds(60,220,300,50);
		lblLocation.setBounds(60,280,300,50);
		
		panel.add(lblDetails);
		panel.add(lblNHSno);
		panel.add(lblName);
		panel.add(lblAddress);
		panel.add(lblCondition);
		panel.add(lblLocation);

		
		JTextField tfNHSno = new JTextField();
		JTextField tfName = new JTextField();
		JTextField tfAddress = new JTextField();
		JTextField tfCondition = new JTextField();
		JTextField tfLocation = new JTextField();
		tfNHSno.setBounds(60, 80, 150, 20);
		tfName.setBounds(60, 140, 150, 20);
		tfAddress.setBounds(60, 200, 150, 20);
		tfCondition.setBounds(60, 260, 150, 20);
		tfLocation.setBounds(60, 320, 150, 20);
		panel.add(tfNHSno);
		panel.add(tfName);
		panel.add(tfAddress);
		panel.add(tfCondition);
		panel.add(tfLocation);
		
		JButton submit = new JButton("Submit");
		submit.setBounds(100,340,75,20);
		panel.add(submit);
		
		// Submit has been clicked
		submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				// Get details
				setName(tfName.getText());
				setNHSno(tfNHSno.getText());
				setAddress(tfAddress.getText());
				setCondition(tfCondition.getText());
				setLocation(Integer.parseInt(tfLocation.getText()));

				frame.setVisible(false);
				
				// Send to Operator
				Operator.operator(getNHSno(), getName(), getAddress(), getCondition(), getLocation());
			}	
		});
	}
	
	// Get choice of what to do with the patient
	public static void operatorNext(Boolean exists){
		
		JFrame frame = new JFrame("Kwik Medical");
		frame.setBounds(0, 0, 300, 130);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();	
		frame.getContentPane().setLayout(null);
		panel.setBounds(0, 0, 300, 130);
		panel.setLayout(null);	
		frame.getContentPane().add(panel);
		
		JLabel lblMessage = new JLabel();
		lblMessage.setBounds(50,10,300,30);
		
		JButton yes = new JButton("Yes");
		yes.setBounds(50, 50, 75, 20);
		JButton no = new JButton("No");
		no.setBounds(150, 50, 75, 20);
		
		// Patient exists in database
		if(exists){
			lblMessage.setText("<html>Patient Found<br>Would you like to update?</html>");
		}
		// Patient does not exist in database
		else if(!exists){
			lblMessage.setText("<html>Record does not exist<br>Would you like to add patient?</html>");
		}
		
		// Yes is clicked
		yes.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				// Patient exists... Update
				if(exists){
					dbUpdate.update(getNHSno(), getName(), getAddress(), getCondition() );
						updated("Patient Updated");
					Hospital.allocateHospital(getLocation());
					frame.setVisible(false);
				}else if(!exists){
					// Patient doesn't exist... Add
					dbInsertPatients.insert(getNHSno(), getName(), getAddress(), getCondition() );
						updated("Patient Added");
					Hospital.allocateHospital(getLocation());
					frame.setVisible(false);
				}
			}
		});
		
		// No is clicked
		no.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				// Patient exists... Don't update
				if(exists){
					Hospital.allocateHospital(getLocation());
					
				}else if(!exists){
				// Patient doesn't exist... Close Program
					
					System.exit(-1);
					
				}
			}
		});

		
		panel.add(lblMessage);
		panel.add(yes);
		panel.add(no);
		
	}
	
	// Tell user what happened to the patient
	public static void updated(String message){
		JFrame frame = new JFrame("Kwik Medical");
		frame.setBounds(0, 0, 300, 100);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();	
		frame.getContentPane().setLayout(null);
		panel.setBounds(0, 0, 300, 100);
		panel.setLayout(null);	
		frame.getContentPane().add(panel);
		
		JLabel label = new JLabel(message);
		label.setBounds(10,10,150,30);
		
		JButton ok = new JButton("OK");
		ok.setBounds(120, 50, 75, 20);
		
		panel.add(label);
		panel.add(ok);
		
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				System.exit(-1);
				// End of operator
			}
		});
	}
	
	public static String getNHSno() {
		return NHSno;
	}

	public static void setNHSno(String nHSno) {
		GUIoperator.NHSno = nHSno;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		GUIoperator.name = name;
	}

	public static String getAddress() {
		return address;
	}

	public static void setAddress(String address) {
		GUIoperator.address = address;
	}

	public static String getCondition() {
		return condition;
	}

	public static void setCondition(String condition) {
		GUIoperator.condition = condition;
	}

	public static int getLocation() {
		return location;
	}

	public static void setLocation(int location) {
		GUIoperator.location = location;
	}
	
	public static Boolean getChoice() {
		return choice;
	}

	public static void setChoice(Boolean choice) {
		GUIoperator.choice = choice;
	}
	

}
