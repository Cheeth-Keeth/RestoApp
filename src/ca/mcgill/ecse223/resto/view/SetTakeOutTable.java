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

import ca.mcgill.ecse223.resto.controller.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

public class SetTakeOutTable extends JFrame
{
	private static final long serialVersionUID = -2311086933501631L;
	
	private JLabel errorMessage; 
	private String error = null;
	
	private JLabel tableNumLabel; 
	private JTextField tableNumField;
	
	private JButton setButton;
	private JButton cancelButton;
	
	
	
	public SetTakeOutTable()
	{	
		initComponents();
		refreshData();	
	}
	
	
	protected void initComponents()
	{
		setTitle("Set Takeout Table");
		setPreferredSize(new Dimension (300,170));
		setResizable(true);
		
		// Elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		//Elements for text box
		tableNumLabel = new JLabel();
		tableNumField = new JTextField();
		
		//Buttons
		setButton = new JButton();
        cancelButton = new JButton();
        
        
        tableNumLabel.setText("Table:");
        
        
        setButton.setText("Set");
		setButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setActionPerformed(evt);
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
								.addComponent(tableNumLabel)
								.addComponent(tableNumField,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(cancelButton)
								.addComponent(horizontalLineMiddle)
								.addComponent(setButton)))
							     
		);
		
		layout.setVerticalGroup
		(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
					.addGroup(layout.createParallelGroup()
							.addComponent(tableNumLabel)
							.addComponent(tableNumField,20,20,450))
					.addGroup(layout.createParallelGroup()
							.addComponent(cancelButton)
							.addComponent(horizontalLineMiddle)
							.addComponent(setButton))
						
		);
		
		pack();
        
		
		
	}
	
	protected void refreshData()
	{
		errorMessage.setText(error);
		
		if (error == null || error.length() == 0)
		{
			tableNumField.setText("");
		}
	}
	
	
	
	
	private void setActionPerformed(java.awt.event.ActionEvent evt)
	{
		error = "";
		
		if (tableNumField.getText().trim().equals(""))
		{
			error += "Null Field! ";
		}
		
		if (error.length() == 0)
		{
			try
			{
				RestoController.setTakeOutOrderTable(tableNumField.getText());
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
