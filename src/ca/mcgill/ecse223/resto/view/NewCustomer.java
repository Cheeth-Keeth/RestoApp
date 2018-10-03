package ca.mcgill.ecse223.resto.view;

import javax.swing.JFrame;
import java.util.HashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoController;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

public class NewCustomer extends JFrame
{
	private static final long serialVersionUID = -96310869335015654L;
	
	private JLabel errorMessage; 
	private String error = null;
	
	private JLabel custNameLabel; 
	private JTextField custNameField;
	
	private JLabel custPhoneLabel; 
	private JTextField custPhoneField;
	
	private JLabel orderNumberLabel; 
	private JTextField orderNumberField;
	
	private JButton createButton;
	private JButton cancelButton;
	
	
	public NewCustomer()
	{	
		initComponents();
		refreshData();	
	}
	
	protected void initComponents()
	{
		setTitle("New Customer");
		setPreferredSize(new Dimension (500,200));
		setResizable(true);
		
		// Elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		//Elements for text box
		custNameLabel = new JLabel();
		custNameField = new JTextField();
		
		custPhoneLabel = new JLabel();
		custPhoneField = new JTextField();
		
		orderNumberLabel = new JLabel();
		orderNumberField = new JTextField();
		
		
		//Buttons
		createButton = new JButton();
        cancelButton = new JButton();
        
        
        custNameLabel.setText("Customer Name:");
        custPhoneLabel.setText("Phone #:");
        orderNumberLabel.setText("OrderNumber");
        
        
        createButton.setText("Create");
		createButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createActionPerformed(evt);
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
										.addComponent(custNameLabel)
										.addComponent(custNameField,20,20,450))
								.addGroup(layout.createSequentialGroup()
										.addComponent(custPhoneLabel)
										.addComponent(custPhoneField,20,20,450))
								.addGroup(layout.createSequentialGroup()
										.addComponent(orderNumberLabel)
										.addComponent(orderNumberField,20,20,450))
								.addGroup(layout.createSequentialGroup()
										.addComponent(cancelButton)
										.addComponent(horizontalLineMiddle)
										.addComponent(createButton)))
									     
				);
				
				layout.setVerticalGroup
				(
						layout.createSequentialGroup()
						.addComponent(errorMessage)
							.addGroup(layout.createParallelGroup()
									.addComponent(custNameLabel)
									.addComponent(custNameField,20,20,450))
							.addGroup(layout.createParallelGroup()
									.addComponent(custPhoneLabel)
									.addComponent(custPhoneField,20,20,450))
							.addGroup(layout.createParallelGroup()
									.addComponent(orderNumberLabel)
									.addComponent(orderNumberField,20,20,450))
							.addGroup(layout.createParallelGroup()
									.addComponent(cancelButton)
									.addComponent(horizontalLineMiddle)
									.addComponent(createButton))
								
				);
				
				pack();
		
		
	}
	
	protected void refreshData()
	{
		errorMessage.setText(error);
		
		if (error == null || error.length() == 0)
		{
			custNameField.setText("");
			custPhoneField.setText("");
			orderNumberField.setText("");
		}
	}
	
	
	private void createActionPerformed(java.awt.event.ActionEvent evt)
	{
		error = "";
		
		if (custNameField.getText().trim().equals(""))
		{
			error += "Need to enter a name. ";
		}
		if (custPhoneField.getText().trim().equals(""))
		{
			error += "Need to enter a phone number. ";
		}
		
		if (error.length() == 0)
		{
			try
			{
				RestoController.createTakeOutCustomer(custNameField.getText(), custPhoneField.getText(), orderNumberField.getText());
			}
			catch (InvalidInputException e)
			{
				error=e.getMessage();
			}
		}
		
		refreshData();
		
	}
	
	private void cancelActionPerformed(java.awt.event.ActionEvent evt)
	{
        //Refresh data and exit page
       	error = ""; 
        errorMessage.setText(error);
        refreshData();
        pack(); 
        this.setVisible(false);
    }
	
	
	
	
	
	
	
	
	
}
