package ca.mcgill.ecse223.resto.application;



import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.RestoPage;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;


public  class RestoApplication{
	
	private static RestoApp resto;
	private static String filename = "menu.resto";
	
	public static void main(String[] args) {
		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RestoPage().setVisible(true);
            }
        });
	}
	
	public static RestoApp getRestoApp() {
		if (resto == null) {
			// load model
			// TODO
			resto = load(); // need to change here to load() after add the persistence

		}
 		return resto;
	}
	
	public static void save() {
		PersistenceObjectStream.serialize(resto);
	}
	
	public static RestoApp load() {
		PersistenceObjectStream.setFilename(filename);
		resto = (RestoApp) PersistenceObjectStream.deserialize();
		
		if (resto == null) {
			resto = new RestoApp();
		}
		else {
			resto.reinitialize();
		}
		return resto;
	}
	
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}
}
