/**
 * IT18012620
 * V.Y Samarasiri
 */

$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

$(document).on("click", "#btnSave", function(event) {
	debugger;
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "PaymentsAPI",
		type : type,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaymentSaveComplete(response.responseText, status);
		}
	});
});

function onPaymentSaveComplete(response, status) {
	debugger;
	var resultSet = JSON.parse(response);
	alert("Response Status" + resultSet.status);
	if (resultSet.status.trim() == "success") {
		$("#alertSuccess").text("Successfully saved.");
		$("#alertSuccess").show();
		$("#divPaymentsGrid").html(resultSet.data);
	} else if (resultSet.status.trim() == "error") {
		$("#alertError").text(resultSet.data);
		$("#alertError").show();
	}

	else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidPaymentIDSave").val("");
	 $("#formPayment")[0].reset();
	
}

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentsAPI",
		type : "DELETE",
		data : "paymentID=" + $(this).data("paymentid"),
		dataType : "text",
		complete : function(response, status) {
			onPaymentDeleteComplete(response.responseText, status);
		}
	});
});

function onPaymentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


//UPDATE================================================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidPaymentIDSave").val(
					$(this).closest("tr").find('#hidPaymentIDUpdate').val());
			$("#firstname").val(
					$(this).closest("tr").find('td:eq(1)').text());
			$("#lastname")
					.val($(this).closest("tr").find('td:eq(2)').text());
			$("#ammount").val(
					$(this).closest("tr").find('td:eq(3)').text());
			$("#cardnumber").val(
					$(this).closest("tr").find('td:eq(4)').text());
		//	$("#date").val(
			//		$(this).closest("tr").find('td:eq(5)').text());
			$("#cvv").val(
					$(this).closest("tr").find('td:eq(6)').text());
		});






function validatePaymentForm() {
	// FIRST NAME
	if ($("#firstname").val().trim() == "") {
		return "Insert first name.";
	}
	// LAST NAME
	if ($("#lastname").val().trim() == "") {
		return "Insert last Name.";
	}

	// AMMOUNT
	if ($("#ammount").val().trim() == "") {
		return "Insert Payment ammount.";
	}
	// is numerical value
	var tmpAmmount = $("#ammount").val().trim();
	if (!$.isNumeric(tmpAmmount)) {
		return "Insert a numerical value for payment ammount.";
	}
	// convert to decimal price
	$("#ammount").val(parseFloat(tmpAmmount).toFixed(2));

	// CARDNUMBER------------------------
	if ($("#cardnumber").val().trim() == "") {
		return "Insert Card Number.";
	}
	// DATE
	//if ($("#date").val().trim() == "") {
		//return "Insert Date.";
	//}

	// CVV
	if ($("#cvv").val().trim() == "") {
		return "Insert CVV.";
	}

	return true;
}