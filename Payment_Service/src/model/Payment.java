//IT18012620
//Samarasiri V.Y

package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import util.DBconnection;
import java.sql.Connection;

public class Payment {

	public String insertPayment(String firstname, String lastname, String ammount, String cardnumber, String date,
			String cvv) {

		String output = "";
		try {
			Connection con = DBconnection.connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payments (`paymentID`,`firstname`,`lastname`,`ammount`,`cardnumber`,`date`,`cvv`)"
					+ " values (?, ?, ?, ?, ?, ?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, firstname);
			preparedStmt.setString(3, lastname);
			preparedStmt.setDouble(4, Double.parseDouble(ammount));
			preparedStmt.setString(5, cardnumber);
			preparedStmt.setString(6, date);
			preparedStmt.setString(7, cvv);

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;

	}

	public String readPayments() {

		String output = "";
		try {
			Connection con = DBconnection.connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed

			output = "<table border=\\\"1\\\"><tr><th></th><th>First Name</th><th>Last Name</th><th>Ammount</th><th>Card Number</th><th>Date</th><th>Cvv</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from payments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String paymentID = Integer.toString(rs.getInt("paymentID"));
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String ammount = Double.toString(rs.getDouble("ammount"));
				String cardnumber = rs.getString("cardnumber");
				String date = rs.getString("date");
				String cvv = rs.getString("cvv");

				// Add into the html table

				//output += "<tr><td><input id=\\\"hidPaymentIDUpdate\\\" name=\\\"hidPaymentIDUpdate\\\" type=\\\"hidden\\\" value=\\\""
				//		+ paymentID + "\\\">"+ "</td>";
				output +=  "<tr><td><input id='hidPaymentIDUpdate' name='hidPaymentIDUpdate' type='hidden' value='" + paymentID + "'></td>"; 
				//output += "<tr><td>" + paymentID + "</td>";
				output += "<td>" + firstname + "</td>";
				output += "<td>" + lastname + "</td>";
				output += "<td>" + ammount + "</td>";
				output += "<td>" + cardnumber + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + cvv + "</td>";

				// buttons

				output += "<td><input name='btnUpdate' type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove'class='btnRemove btn btn-danger' data-paymentid='"
						+ paymentID + "'>" + "</td></tr>";

			}
			con.close();
			// Complete the html table
			output += "</table>";

		}

		catch (Exception e) {
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
		}
		return output;

	};

	public String deletePayment(String paymentID) {
		{
			String output = "";
			try {
				Connection con = DBconnection.connect();
				if (con == null) {
					return "Error while connecting to the database for deleting.";
				}
				// create a prepared statement
				String query = "delete from payments where paymentID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(paymentID));
				// execute the statement
				preparedStmt.execute();
				con.close();
				String newPayments = readPayments();
				output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
			} catch (Exception e) {
				output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
				System.err.println(e.getMessage());
			}
			return output;

		}

	}

	public String updatePayment(String paymentid, String firstname, String lastname, String ammount, String cardnumber,
			String date, String cvv) {
		String output = "";
		try {
			Connection con = DBconnection.connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE payments SET firstname=?,lastname=?,ammount=?,cardnumber=?,date=?,cvv=? WHERE paymentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setString(1, firstname);
			preparedStmt.setString(2, lastname);
			preparedStmt.setDouble(3, Double.parseDouble(ammount));
			preparedStmt.setString(4, cardnumber);
			preparedStmt.setString(5, date);
			preparedStmt.setString(6, cvv);
			preparedStmt.setInt(7, Integer.parseInt(paymentid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the payments. Error\"" + e.getMessage() + "\"}";
			System.err.println(e.getMessage());
		}
		return output;

	}
}
