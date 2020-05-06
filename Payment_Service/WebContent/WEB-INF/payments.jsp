 <!-- IT18012620 -->.
 <!-- V.Y Samarasiri -->.
<%
   //Initialize 
   
   session.setAttribute("statusMsg", "");
   System.out.println("Trying to process");

//Save---------------------------------
if (request.getParameter("firstname") != null)
{
	Payment payObj = new Payment();
	String stsMsg = "";


	//Insert--------------------------
	if (request.getParameter("hidPaymentIDSave") == "")
	{
		stsMsg = payObj.insertPayment(request.getParameter("firstname"),
				request.getParameter("lastname"),
				request.getParameter("ammount"),
				request.getParameter("cardnumber"),
				//request.getParameter("date"),
				request.getParameter("cvv"));
	}
	else//Update----------------------
	{
		stsMsg = payObj.updatePayment(request.getParameter("hidPaymentIDSave"),
		request.getParameter("firstname"),
		request.getParameter("lastname"),
		request.getParameter("ammount"),
		request.getParameter("cardnumber"),
		//request.getParameter("date"),
		request.getParameter("cvv"));
	}
	//Delete-----------------------------
	if (request.getParameter("hidPaymentIDDelete") != null)
	{
		//Payment payObj = new Payment();
	    stsMsg = payObj.deletePayment(request.getParameter("hidPaymentIDDelete"));
		session.setAttribute("statusMsg", stsMsg);
	}
}
%>

<%@page import="model.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Health Care System </title>
<script src="jquery-3.5.0.min.js"></script>
<script type="text/javascript" src="payments.js"/></script>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body>


	<div class="container">
		<div class="raw">
			<div class="col-6">




				<form id="formPayment" name="formPayment" method="post"
					action="payments.jsp">
					First Name: <input id="firstname" name="firstname" type="text"
						class="form-control form-control-sm"> <br> Last Name:
					<input id="lastname" name="lastname" type="text"
						class="form-control form-control-sm"> <br> Ammount: <input
						id="ammount" name="ammount" type="text"
						class="form-control form-control-sm"> <br> Card
					Number: <input id="cardnumber" name="cardnumber" type="text"
						class="form-control form-control-sm"> <br> <br>
					<!--Date: <input id="date" name="date" type="text"
						class="form-control form-control-sm"> <br> <br>-->
					CVV: <input id="cvv" name="cvv" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
				</form>


				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>

				<div id="divPaymentsGrid">
		<%
                  Payment payObj = new Payment();
                   out.print(payObj.readPayments());
         %>

				</div>

			</div>

		</div>
	</div>




</body>
</html>







