bugReports = new function() {
	var $instance = this;
	this.init = function(flag) {
		$instance.setDataTableBody();
		$instance.initiateDatePicker();
		$instance.initiateDatePicker2();


		$('#excLogBtn').click(function() {
			var dateTxt = $('#datepicker').val();
			var url = './getExceptionLog/'+dateTxt;
			console.log(url);
			 $.get(url, function(data, status){
		            console.log("Data: " + data.exceptions + "\nStatus: " + status);
		            $('#exceptionLogHeading').text("Logs on "+dateTxt);
		            $('#excepLog').text(data.exceptions);
		        }); 
		});		
		
		$('#infoLogBtn').click(function() {
			console.log('btn clicked');
			var dateTxt = $('#datepicker2').val();
			var url = './getInfoLog/'+dateTxt;
			console.log(url);
			 $.get(url, function(data, status){
		            console.log("Data: " + data.exceptions + "\nStatus: " + status);
		            $('#infoLogHeading').text("Logs on "+dateTxt);
		            $('#infoLogs').text(data.info);
		        }); 
		});		
		
	};

	$instance.setDataTableBody = function() {		
		var table = $('#clientExceptions').DataTable({
			responsive : true,
			"destroy" : true,
			"order" : [ [ 0, "desc" ] ],
			"ajax" : "./getClientExceptions",
			"bSort" : false,			
			"columns" : [
				{
					"data" : null
				},
				{
					"data" : "userId"
				},
				{
					"data" : "userDevice"
				},
				{
					"data" : "exceptionType"
				},
				{
					"data" : "exceptionDescription"
				},
				{
					"data" : "clientType"
				},
				{
					"data" : "createdOn"
				}]
			
		});	
		table.on( 'order.dt search.dt', function () {
			table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
	            cell.innerHTML = i+1;
	        } );
	    } ).draw();
		
	};
	
	$instance.initiateDatePicker = function() {
		$("#datepicker").datepicker({
		    dateFormat: 'yy-mm-dd',
		    inline: true		   
		});

	};
	
	$instance.initiateDatePicker2 = function() {
		$("#datepicker2").datepicker({
		    dateFormat: 'yy-mm-dd',
		    inline: true
		   /* onSelect: function(dateText, inst) { 
		        var date = $(this).datepicker('getDate'),
		            day  = date.getDate(),  
		            month = date.getMonth() + 1,              
		            year =  date.getFullYear();		       
		        var url = './getExceptionLog/'+year-month-day;
		        console.log(url)
		        $.get(url, function(data, status){
		            alert("Data: " + data + "\nStatus: " + status);
		        });   
		        
		    }*/
		});
	};

};
