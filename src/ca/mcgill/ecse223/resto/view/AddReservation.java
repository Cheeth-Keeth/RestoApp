package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoController;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;


public class AddReservation extends JFrame{

	private static final long serialVersionUID = -123456789L;
	
	private JLabel tableListLabel;
	private JTextField tableListField;
	
	private JLabel dateLabel;
	private JTextField dateField;
	
	private JLabel timeLabel;
	private JTextField timeField;
	
	private JLabel nameLabel;
	private JTextField nameField;
	
	private JLabel phoneLabel;
	private JTextField phoneField;
	
	private JLabel emailLabel;
	private JTextField emailField;
	
	private JLabel numInPartyLabel;
	private JTextField numInPartyField;
	
	
	
	private JLabel errorMessage; 
	private String error = null;
	
	
	private JButton createButton;
	private JButton cancelButton;
	
	
	//Current Tables
	private HashMap<Integer, Table> currentTables;
	
	private RestoPage restoPage; 
	
	
	public AddReservation(RestoPage restoPage){
		this.restoPage = restoPage; 
		initComponents();
		refreshData();
	}
	
	
	public void initComponents()
	{
		setTitle("Reserve A Table");
		setPreferredSize(new Dimension(550, 350));
		setResizable(true); 
		
		// Elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		//Elements for updating table
		tableListLabel = new JLabel();
		tableListField = new JTextField();
		
		dateLabel = new JLabel();
		dateField = new JTextField();
		
		timeLabel = new JLabel();
		timeField = new JTextField();
		
		nameLabel = new JLabel();
		nameField = new JTextField();
		
		phoneLabel = new JLabel();
		phoneField = new JTextField();
		
		emailLabel = new JLabel();
		emailField = new JTextField();
		
		numInPartyLabel = new JLabel();
		numInPartyField = new JTextField();
		
		
		
		createButton = new JButton();
        cancelButton = new JButton();
		
        tableListLabel.setText("Table Number(s) <#,##,#,###,>"); 
        
		/*
		
        tableList = new JComboBox<String>(new String[0]);
		tableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedTable = cb.getSelectedIndex();
			}
		});
		
		*/
		
		
		dateLabel.setText("Date <dd-MM-yyyy>:");
		timeLabel.setText("Time <hh:mm>:");
		nameLabel.setText("Name:");
		phoneLabel.setText("Phone:");
		emailLabel.setText("E-mail:");
		numInPartyLabel.setText("# People:");
		
		createButton.setText("Create");
		createButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createButtonActionPerformed(evt);
			}
		});
		
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});
		
		// horizontal line element
		JSeparator horizontalLineMiddle = new JSeparator();
		
		//Layout
				GroupLayout layout = new GroupLayout(getContentPane());
				getContentPane().setLayout(layout);
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);
				layout.setHorizontalGroup
				(
						layout.createParallelGroup()
						.addComponent(errorMessage)
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
										.addComponent(tableListLabel)
										.addComponent(tableListField,20,20,450))
								.addGroup(layout.createSequentialGroup()
										.addComponent(dateLabel)
										.addComponent(dateField,20,20,450))
								.addGroup(layout.createSequentialGroup()
										.addComponent(timeLabel)
										.addComponent(timeField,20,20,450))
								.addGroup(layout.createSequentialGroup()
										.addComponent(nameLabel)
										.addComponent(nameField,20,20,450))
								.addGroup(layout.createSequentialGroup()
										.addComponent(phoneLabel)
										.addComponent(phoneField,20,20,450))
								.addGroup(layout.createSequentialGroup()
										.addComponent(emailLabel)
										.addComponent(emailField,20,20,450))
								.addGroup(layout.createSequentialGroup()
										.addComponent(numInPartyLabel)
										.addComponent(numInPartyField,20,20,450))
								.addGroup(layout.createSequentialGroup()
										.addComponent(createButton)
										.addComponent(horizontalLineMiddle)
										.addComponent(cancelButton)))
									     
				);
				
				layout.setVerticalGroup
				(
						layout.createSequentialGroup()
						.addComponent(errorMessage)
							.addGroup(layout.createParallelGroup()
									.addComponent(tableListLabel)
									.addComponent(tableListField,20,20,450))
							.addGroup(layout.createParallelGroup()
									.addComponent(dateLabel)
									.addComponent(dateField,20,20,450))
							.addGroup(layout.createParallelGroup()
									.addComponent(timeLabel)
									.addComponent(timeField,20,20,450))
							.addGroup(layout.createParallelGroup()
									.addComponent(nameLabel)
									.addComponent(nameField,20,20,450))
							.addGroup(layout.createParallelGroup()
									.addComponent(phoneLabel)
									.addComponent(phoneField,20,20,450))
							.addGroup(layout.createParallelGroup()
									.addComponent(emailLabel)
									.addComponent(emailField,20,20,450))
							.addGroup(layout.createParallelGroup()
									.addComponent(numInPartyLabel)
									.addComponent(numInPartyField,20,20,450))
							.addGroup(layout.createParallelGroup()
									.addComponent(createButton)
									.addComponent(horizontalLineMiddle)
									.addComponent(cancelButton))
								
				);
				
				pack();
				
	}
	
	
	public void refreshData()
	{
		// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			
			// populate page with data
			tableListField.setText("");
			dateField.setText("");
			timeField.setText("");
			nameField.setText("");
			phoneField.setText("");
			emailField.setText("");
			numInPartyField.setText("");
			
			
			//All for combo box
			/*	currentTables = new HashMap<Integer, Table>();
				tableList.removeAllItems();
				Integer index = 0;
				for (Table table : RestoController.getCurrentTables()) //Fixed display currentTables.?????
				{
					currentTables.put(index, table);
					tableList.addItem("Table:" + table.getNumber() + " " + "Seats:" +  table.numberOfCurrentSeats());
					index++;
				};
				selectedTable = -1;
				tableList.setSelectedIndex(selectedTable);
			*/	
			//All for combo box^^^^^^

		}

    }
	
	
    private void createButtonActionPerformed(java.awt.event.ActionEvent evt){
    	// clear error message and basic input validation
    	error = "";	
		
    	if (tableListField.getText().trim().equals(""))
		{
			error += "tables, ";
		}
    			
    	if (dateField.getText().trim().equals(""))
		{
			error += "date, ";
		}
    	
    	if (timeField.getText().trim().equals(""))
		{
			error += "time, ";
		}
    	
    	if (nameField.getText().trim().equals(""))
		{
			error += "name, ";
		}
    	
    	if (phoneField.getText().trim().equals(""))
		{
			error += "phone, ";
		}
    	
    	if (emailField.getText().trim().equals(""))
		{
			error += "e-mail, ";
		}
    	
    	if (numInPartyField.getText().trim().equals(""))
		{
			error += "# people, ";
		}
    	
		//error = error.trim();
    	
    	if(error.length()>0) {
    		error = "Need to input: " +error;
    	}
		
    	System.out.println(error.length());
		
    			
    	if (error.length() == 0)
    	{
			// call the controller
			try
			{
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date date = sdf1.parse(dateField.getText());
				java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
				
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
				long ms = sdf.parse(timeField.getText()).getTime();
				Time t = new Time(ms);
				
				List<Table> tables = new ArrayList<>(); //The list that will be put into controller method
				String tablesListTrimmed = tableListField.getText().trim();
				System.out.println("tableslisttrimmed: "+tablesListTrimmed);
				
				String tableNumString = "";
				
				List<Table> allTables = RestoController.getCurrentTables();  //all tables in resto
				System.out.println("all tables: " +allTables.size());
				
				List<Integer> tableNums = new ArrayList<>();
				
				
				for (Table table: allTables)
				{
					int num = table.getNumber();
					tableNums.add(num);
				}
				System.out.println("tableNums size: "+tableNums.size());
				
				//This section iterates through a use inputted string with the goal of getting a list of tables
					//We update a string for the table number that can be several digits
						//if a comma is detected, we know the number is done
							//Then we check if that number is an actual table number
								//if it's not, throw an error
								//if it is, find the table that has that table number and add it to the list of tables that the controller method takes in
							// increment index to keep going through user input
						// if no comma, increment and keep going
				//Get to end of user input and then run controller method
				
				for (int i=0 ; i < tablesListTrimmed.length() ; i++)
				{
					System.out.println("in for loop to iterate through tablesListTrimmed");
					if (tablesListTrimmed.charAt(i) == ',' )
					{ 
						//Table currentTable = ;
						System.out.println("found a ,");
						
						int thisNum = Integer.parseInt(tableNumString);
						
						System.out.println("this num: " +thisNum);
						
						boolean contains = tableNums.contains(thisNum);
						
						System.out.println("contains: " +contains);
						
						if(contains)
						{
							for(Table table: allTables)
							{
								if(table.getNumber()==thisNum)
								{
									tables.add(table);
									System.out.println("added table "+thisNum);
								}
							}
						}
						else
						{
							error = "Table" +tableNumString +"does not exist";
							throw new InvalidInputException(error);
						}
						
						tableNumString = "";
					}
					else 
					{
						System.out.println("went into the ELSE");
						tableNumString += tablesListTrimmed.charAt(i);
					}
				}

				
			    int numInPartyInt = Integer.parseInt(numInPartyField.getText()); 
				
				System.out.println("calling controller in AddReservation");
				RestoController.reserveTable(sqlStartDate, t, numInPartyInt, nameField.getText(), emailField.getText(), phoneField.getText(), tables);
				restoPage.refreshReservation();
			}
			catch (InvalidInputException e)
			{
				error = e.getMessage();
			} 
			catch (ParseException e)
			{
				error = e.getMessage();
			}
    	}

		// update visuals
		refreshData();			
    }
    
    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
        //Refresh data and exit page
       	error = ""; 
        errorMessage.setText(error);
        refreshData();
        pack(); 
        this.setVisible(false);
    }
    
    
	
}
