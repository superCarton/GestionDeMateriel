package borrowmanager.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import borrowmanager.model.Manager;
import borrowmanager.model.booking.DateInterval;

public abstract class TextInterfacePage {
	private BufferedReader br;
	
	private static final String separator = "\n===========================\n";
	
	private static final String dateFormat = "dd/MM/yyyy";
	protected static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
	
	public TextInterfacePage() {
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	protected void openChildPage(TextInterfacePage page) {
		while (page != null) {
			System.out.println(separator);
			page = page.display();
		}
		System.out.println();
		//System.out.println("Exiting loop from : "+this);
	}
	
	protected void enterToContinue() {
		System.out.println();
		System.out.println("Press enter to continue");
		input();
	}
	
	protected String input() {
		String s = null;
	    try {
			s = br.readLine();
		} catch (IOException e) {
			// nothing
		}
	    return s;
	}
	
	protected boolean question(String message) {
		while (true) {
			System.out.println(message + " (y/n)");
			String i = input().toLowerCase();
			if (i.equals("y")) return true;
			if (i.equals("n")) return false;
			System.out.println("Answer '"+i+"' not recognized. Please answer by 'y' or 'n'");
		}
	}
	
	protected Integer inputQuantity() {
		String input = null;
		Boolean valid = false;
		while (!valid) {
			System.out.println("Please enter a quantity :");
			try {
				input = br.readLine();
				Boolean isInteger;
				try {
					return Integer.parseInt(input);
				} catch (NumberFormatException e) {
					// do nothing
				}
			} catch (IOException e) {
				// do nothing
			}
		}
		return -1;
	}
	
	private Date readFutureDate() {
		Date date ;
		Date now = Manager.now;
		
		while (true) {
			date = readDate();
			if (date.compareTo(now) <= 0) {
				System.out.println("You must enter a future date !");
			} else {
				return date;
			}
		}
	}
	
	protected Date readDate() {
		String input = null;
		while (true) {
			System.out.println("Enter date ("+dateFormat+") : ");
			input = input();
			try {
				Date d = simpleDateFormat.parse(input);
				System.out.println("Parsed date: "+simpleDateFormat.format(d));
				return d;
			} catch (ParseException e) {
				System.out.println("Bad date format !");
			} 
			System.out.println("Invalid date. Please enter a correct date.");
		}
	}
	
	protected DateInterval inputDateInterval() {
		Date
			startDate,
			endDate;
		System.out.println("Please enter a start date : ");
		startDate = readFutureDate();
		System.out.println("Please enter a end date : ");
		endDate = readFutureDate();
		
		return new DateInterval(startDate, endDate);
	}
	
	public abstract TextInterfacePage display();
	
	
}
