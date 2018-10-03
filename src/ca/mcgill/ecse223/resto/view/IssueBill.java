package ca.mcgill.ecse223.resto.view;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import java.awt.Color;
import java.awt.Dimension;
import java.util.*;

import ca.mcgill.ecse223.resto.controller.RestoController;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class IssueBill extends JFrame {

	private static final long serialVersionUID = -6426310869335015542L;
	
	private JLabel seat;
	private JComboBox<String> seatList;
	
	private JButton cancel;
	private JButton addSeat;
	private JButton issueBill;
	
	private JLabel errorMessage;
	private String error = null;
	
	private Integer selectedSeat = -1;
	private HashMap<Integer, Seat> seats;
	
	List<Seat> seatsForBill;
	
	public IssueBill(){
		
		initComponents();
		refreshData();
		
	}
	
	protected void initComponents() {
		
		seat = new JLabel();
		
		cancel = new JButton();
		addSeat = new JButton();
		issueBill = new JButton();
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		seatsForBill = new ArrayList<Seat>();
		
		setTitle("Issue Bill");
		setPreferredSize(new Dimension(500, 190));
		setResizable(true);
		
		seat.setText("Seat: ");
		
		seatList = new JComboBox<String>(new String[0]);
		
		seatList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedSeat = cb.getSelectedIndex();
			}
		});
		
		issueBill.setText("Issue Bill");
		issueBill.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				issueBillActionPerformed(evt);
			}
		});
		
		cancel.setText("Cancel");
		cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});
		
		addSeat.setText("Add Seat");
		addSeat.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addSeatActionPerformed(evt);
			}
		});
		
		JSeparator horizontalLineMiddleOne = new JSeparator();
		JSeparator horizontalLineMiddleTwo = new JSeparator();
		
		//Layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addComponent(seat,100,100,100)
								.addComponent(seatList,100,100,450))
						
						.addGroup(layout.createSequentialGroup()
								.addComponent(issueBill)
								.addComponent(horizontalLineMiddleOne)
								.addComponent(addSeat)
								.addComponent(horizontalLineMiddleTwo)
								.addComponent(cancel)))
							     
		);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
				         .addComponent(seat,100,100,100)
				         .addComponent(seatList,100,100,450))
		       
		        .addGroup(layout.createParallelGroup()
						.addComponent(issueBill)
						.addComponent(horizontalLineMiddleOne)
						.addComponent(addSeat)
						.addComponent(horizontalLineMiddleTwo,100,100,350)
						.addComponent(cancel))
		        
						
		);	
		
		pack();
		
	}
	
	protected void refreshData() {
		
		errorMessage.setText(error);
		
		if (error == null || error.length() == 0) {
			
			seats = new HashMap<Integer, Seat>();
			seatsForBill = new ArrayList<Seat>();
			seatList.removeAllItems();
			Integer index = 0;
			
			for (Table table : RestoController.getCurrentTables()) {
				for (int i = 0; i < table.getSeats().size(); i++) {
					
					Seat seat = table.getSeat(i);
					seats.put(index, seat);
					seatList.addItem("Table: #" + table.getNumber() + " " + "Seat: #" + i);
					index++;
					
				}
			}
			
			selectedSeat = -1;
			seatList.setSelectedIndex(selectedSeat);

		}

    }
	
	private void issueBillActionPerformed(java.awt.event.ActionEvent evt) {
    	
		error = "";	
			
		if (seatsForBill.size() <= 0) {
				
			error = "Must select seats to be billed";
				
		}
			
		error = error.trim();
			
		if (error.length() == 0) {
				
				try {
					
					RestoController.issueBill(seatsForBill);
					
				} catch (InvalidInputException e) {
					
					error = e.getMessage();
					
				}
				
			}
	
		
		refreshData();
	
	}


	private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
	
		error = ""; 
		errorMessage.setText(error);
		refreshData();	
		pack(); 
		this.setVisible(false);
	
	}
	
	private void addSeatActionPerformed(java.awt.event.ActionEvent evt) {
		
		error = "";
		
		if (selectedSeat < 0) {
			
			error = "A seat must be selected";
			
		}
		
		error = error.trim();
		
		if (error.length() == 0) {

			Seat seat = seats.get(selectedSeat);
			seatsForBill.add(seat);
			
		}

	}

}
