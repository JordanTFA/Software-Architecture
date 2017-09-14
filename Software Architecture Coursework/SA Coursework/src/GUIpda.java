import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIpda {
	
	public static String[] details;
	static String selected;

	// PDA is selected
	public static void pdaGUI() {

		JFrame frame = new JFrame("Kwik Medical");
		frame.setBounds(0, 0, 300, 180);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();	
		frame.getContentPane().setLayout(null);
		panel.setBounds(0, 0, 300, 180);
		panel.setLayout(null);	
		frame.getContentPane().add(panel);
		
		JLabel lbl = new JLabel("Please enter your Hospital Number");
		lbl.setBounds(50,10,300,30);
		
		JTextField tf = new JTextField();
		tf.setBounds(50, 50, 200, 20);
		
		JButton submit = new JButton("Submit");
		submit.setBounds(100, 80, 75, 20);
		
		submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				int hospNo = Integer.parseInt(tf.getText());
				
				PDA.receiveHospNo(hospNo);
				
				frame.setVisible(false);
			}
		});
		
		panel.add(lbl);
		panel.add(tf);
		panel.add(submit);

	}	
	
	// Choose patient from open requests
	public static void pdaNext(ArrayList<String> files, int hospNo){
		
		JFrame frame = new JFrame("Kwik Medical");
		frame.setBounds(0, 0, 600, 560);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();	
		frame.getContentPane().setLayout(null);
		panel.setBounds(0, 0, 600, 560);
		panel.setLayout(null);	
		frame.getContentPane().add(panel);
		
		JLabel lbl = new JLabel("Select a request");
		lbl.setBounds(10,10,150,30);
		panel.add(lbl);
		
		DefaultListModel<String> dlmFiles = new DefaultListModel<String>();
		
		JList<String> lstFiles = new JList<String>(dlmFiles);
		lstFiles.setBounds(10,60,150,360);
		panel.add(lstFiles);
		
		// Load requests
		for(int i = 0; i < files.size(); i++){
			dlmFiles.addElement(files.get(i));
		}
		
		lstFiles.setSelectedIndex(0);
		
		lstFiles.setModel(dlmFiles);	
		
		DefaultListModel<String> dlmDetails = new DefaultListModel<String>();
		
		JList<String> lstDetails = new JList<String>(dlmDetails);
		lstDetails.setBounds(170,60,420,360);
		panel.add(lstDetails);
		
		lstDetails.setModel(dlmDetails);
		
		JButton view = new JButton("View");
		view.setBounds(40, 440, 75, 25);
		panel.add(view);
		
		// View is clicked
		view.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				// Clear details
				dlmDetails.removeAllElements();
				
				// Load selected details
				setSelected(lstFiles.getSelectedValue());				
		        String line = "";
		        String cvsSplitBy = ",";
		        try (BufferedReader br = new BufferedReader(new FileReader("src\\req\\" + hospNo + "\\" + getSelected() ))) {
		            while ((line = br.readLine()) != null) {

		                // use comma as separator
		                setDetails(line.split(cvsSplitBy));
		                
		            }


		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        
		        // Display details
                dlmDetails.addElement("NHS No: " + details[0]); 
                dlmDetails.addElement("Name: " + details[1]);
                dlmDetails.addElement("Address: " + details[2]);
                dlmDetails.addElement("Condition: " + details[3]);	
			}
		});
		
		JButton submit = new JButton("Submit");
		submit.setBounds(200, 440, 75, 25);
		panel.add(submit);
		
		// Submit is clicked
		submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				// Send selected details
				callOutDetails(getDetails(), getSelected(), hospNo);
				frame.setVisible(false);
			}
		});
	}
	
	// Call Out Details form
	public static void callOutDetails(String[] details, String selected, int hospNo){
		
		JFrame frame = new JFrame("Kwik Medical");
		frame.setBounds(0, 0, 250, 630);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();	
		frame.getContentPane().setLayout(null);
		panel.setBounds(0, 0, 250, 630);
		panel.setLayout(null);	
		frame.getContentPane().add(panel);
		
		JLabel inst = new JLabel("Please enter the following details");
		inst.setBounds(10,10,200,40);
		panel.add(inst);
		
		JLabel lblCondition = new JLabel("Severity (Low/Medium/High):");
		lblCondition.setBounds(10,60,200,20);
		panel.add(lblCondition);
		
		JTextField tfCondition = new JTextField();
		tfCondition.setBounds(10, 90, 200, 20);
		panel.add(tfCondition);
		
		JLabel lblDate = new JLabel("Date (DD/MM/YYYY):");
		lblDate.setBounds(10,120,200,20);
		panel.add(lblDate);
		
		JTextField tfDate = new JTextField();
		tfDate.setBounds(10, 150, 70, 20);
		panel.add(tfDate);
		
		JButton currentDate = new JButton("Get Current Date");
		currentDate.setBounds(10,180,200,25);
		panel.add(currentDate);
		
		currentDate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				
				tfDate.setText(df.format(date));
			}
		});
		
		JLabel lblTime = new JLabel("Time (HH:MM):");
		lblTime.setBounds(10,210,200,20);
		panel.add(lblTime);
		
		JTextField tfTime = new JTextField();
		tfTime.setBounds(10, 240, 40, 20);
		panel.add(tfTime);
		
		JButton currentTime = new JButton("Get Current Time");
		currentTime.setBounds(10,270,200,25);
		panel.add(currentTime);
		
		currentTime.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				
				DateFormat df = new SimpleDateFormat("HH:mm");
				Date time = new Date();
				
				tfTime.setText(df.format(time));
			}
		});
		
		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setBounds(10,310,200,20);
		panel.add(lblLocation);
		
		JTextField tfLocation = new JTextField();
		tfLocation.setBounds(10, 340, 200, 20);
		panel.add(tfLocation);
		
		JLabel lblActionTaken = new JLabel("Action Taken:");
		lblActionTaken.setBounds(10,370,200,20);
		panel.add(lblActionTaken);
		
		JTextField tfActionTaken = new JTextField();
		tfActionTaken.setBounds(10, 400, 200, 60);
		panel.add(tfActionTaken);
		
		JLabel lblTimeSpent = new JLabel("Time spent on the call (minutes):");
		lblTimeSpent.setBounds(10, 470, 200, 20);
		panel.add(lblTimeSpent);
		
		JTextField tfTimeSpent = new JTextField();
		tfTimeSpent.setBounds(10, 500, 40, 20);
		panel.add(tfTimeSpent);
		
		JButton submit = new JButton("Submit");
		submit.setBounds(10,530,200,25);
		panel.add(submit);
		
		submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{	
				// Get details
				String NHSNo = details[0];
				String name = details[1];
				String condition = tfCondition.getText();
				String date = tfDate.getText();
				String time = tfTime.getText();
				String location = tfLocation.getText();
				String actionTaken = tfActionTaken.getText();
				String timeSpent = tfTimeSpent.getText();
				
				// Update callout records
				dbInsertCallOut.insert(NHSNo, name, condition, date, time, location, actionTaken, timeSpent);
				
				// Close request TODO: Not implemented fully
				Hospital.closeRequest(selected, hospNo);
				
				frame.setVisible(false);
			}
		});
		
	}
	
	public static String[] getDetails() {
		return details;
	}

	public static void setDetails(String[] details) {
		GUIpda.details = details;
	}
	
	public static String getSelected() {
		return selected;
	}

	public static void setSelected(String selected) {
		GUIpda.selected = selected;
	}

}
