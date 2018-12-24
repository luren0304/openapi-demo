	$("#btn_DBatchAdd").click(function() {
		$.getJSON('json/Loan.json', function(data) {
			var saveDataAry = [];
			for (var i = 0; i < data.length; i++) {
				console.log(data[i]);
				saveDataAry.push(data[i]);
			}
			console.log(saveDataAry);
			console.log(JSON.stringify(saveDataAry));
			$.ajax({
				type : "POST",
				url : "deposits/createObjList",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(saveDataAry),
				// data : $.toJSON(saveDataAry),
				success : function(data) {

				}
			});
		});
	});
	
	$("#btn_DAddObj").click(function() {
		var saveData = {
				"prod_id" :"D4",
				"product" :"Deposits",
				"type" :"Statement Savings Account",
				"subtype" :"Saving",
				"currency" :"HKD",
				"interestRate" :"2.00",
				"min_amount" :"5000",
				"fee" :"50",
				"remark" :"No monthly service fee if you maintain a balance of more than HKD5,000 (rolling average of previous 3 months, if not you'll pay a fee of HKD50)"
				};
		console.log(saveData);
		$.ajax({
			type : "POST",
			url : "deposits/createObj",
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(saveData),
			success : function(data) {

			}
		});
	});

