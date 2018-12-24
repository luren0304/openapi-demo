	$("#btn_LBatchAdd").click(function() {
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
				url : "loans/createObjList",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(saveDataAry),
				// data : $.toJSON(saveDataAry),
				success : function(data) {

				}
			});
		});
	});
	
	$("#btn_LAddObj").click(function() {
		var saveData = {"prod_id":"L3",
				"product":"Loans",
				"type":"Premier Mastercard",
				"subtype":"Mastercard",
				"interestRate":"18.9% APR",	
				"prd_info_1" :"Extra RewardCash: \u000d\u000a- Enjoy up to 6X RewardCash for spending in Rewards of Your Choice category. Rebate rate at 2.4% ",
				"prd_info_2" :"Dining: \u000d\u000a- Buy 1 get 1 freeBuy 1 get 1 free This link will open in a new window dining privileges at Michelin Restaurants",
				"prd_info_3" :"Travel: \u000d\u000a- Stay 2 get 2 nights free on Hotel bookings\u000d\u000a- Earn 15 British Airways Avios / KrisFlyer Miles with just 1 RewardCash",
				"fee" :"$200 P.A.",
				"remark" :null
				};
		console.log(saveData);
		$.ajax({
			type : "POST",
			url : "loans/createObj",
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(saveData),
			success : function(data) {

			}
		});
	});

