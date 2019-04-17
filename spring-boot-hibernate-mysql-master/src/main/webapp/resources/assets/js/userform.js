
function statemaster(data){
	console.log(data);
	
	var html="";
	data=data.statedata;
	
	for(var i=0; i<data.length;i++){
		
		console.log(data[i].stateid);
		html=html+"<option val="+data[i].stateid+">"+data[i].stateName+"</option>"
	}
	
	$("#masterstate1").html(html);
	$("#modalstatedata").html(html);
	
}

getRequest("/user/statedata",statemaster);

function manageResponse(result){
	
	console.log(result)
	  console.log(result.response)
	  
	  if(result.response == "OK"){
		  swal({
			  title: "Success!",
			  text: "Data saved successfully!",
			  icon: "success",
			  button: "ok",
			}).then((response) => {
				  if (response) {
					  setTimeout(function(){ 
						  createFoundTable();
					  }, 
					 1000);
					  
				  } else {
				    
				  }

			});
	  }
	  else {
		     toastr["error"](" something went wrong ");
	  }
	
}
$("#khoyaformSubmit").click(function(){
	
	var productType = $("#productTypeDrop").val();
	var stateId = $("#state").val();
	var productName = $("#productName").val();
	var date = $("#date").val();
	var country = $("#country").val();
	var locationName = $("#locationName").val();
	console.log(productType);
	console.log(stateId);
	console.log(productName);
	console.log(date);
	console.log(country);
	console.log(locationName);
	var data = {
			
			'productName' : productName,
			'productType.productTypeid' : productType,
			'country' : country,
			'state.stateid' : stateId,
			'locationName' : locationName,
			'productfindtime' : date
	}
	swal({
		  title: "Are you sure?",
		  icon: "warning",
		  buttons: true,
		  dangerMode: true,
		})
		.then((response) => {
		  if (response) {
			  postRequest("/user/productfound",data,manageResponse)
			  
		  } else {
		    
		  }
		});
	
	
})

function createFoundTable(){
	/*var html="";
	for(var i=0; i<datalist.length;i++){
		html = html + "<tr>"+
							"<td>"+datalist[i].productType +"</td>"+
							"<td>"+datalist[i].productName +"</td>"+
							"<td>"+datalist[i].productfindtime +"</td>"+
							"<td>"+datalist[i].locationName +"</td>"+
							"<td>"+datalist[i].state +"</td>"+
							"<td>"+datalist[i].country +"</td>"+
							"<td>"+datalist[i].user +"</td>"+
							"<td>"+datalist[i].user +"</td>"+
					 "</tr>";
		
	}
	$("#foundbodytable").html(html);*/
	$('#datafoundTable').DataTable( {
        processing: true,
	    "ajax": {"url":"/user/productfoundlist",dataSrc:""},
	    "columns": [
	        { "data": "productType.producttype" },
	        { "data": "productName" },
	        { "data": "productfindtime" },
	        { "data": "locationName" },
	        { "data": "state.stateName" },
	        { "data": "country" },
	        { "data": "user.name" },
	        { "data": "user.mobile" }
	    ]
	} );
	$("#foundtable").show();
	$("#foundformdata").hide();

	
}



