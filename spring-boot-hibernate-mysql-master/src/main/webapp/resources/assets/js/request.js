function getRequest(url,handleData){
	$.ajax({
		
		type:"GET",
		url:url,
		
		success:function(result){
			
			console.log(result);
			handleData(result);
		},
		error:function(result){
			
			console.log(result);
		}
	    
	
	})
}

function postRequest(url,data,handleData){
	var result;
	$.ajax({
		
		type:"POST",
		url:url,
		data:data,
		
		success:function(result){
			handleData(result);
		},
		error:function(result){
			
			return result;
			console.log(result);
		}
		
	})
}