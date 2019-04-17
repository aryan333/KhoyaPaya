/**
 * 
 */
$(document)
	.ready(
			function() {

				$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
					var token = $('input[name="_csrf"]').attr('value');
					return jqXHR.setRequestHeader('X-CSRF-Token', token);
				});		
				setTableData();
				$("#sendButton").click(function() {
					var numbers=$("#numbersOfQRCodes").val();
					
					if(validate(numbers)){
						$("#sendButton").prop('disabled', true);
						$.ajax({
							type : "GET",
							url : "../admin/generateQRCodesInBulk/"+numbers,
							dataType : "text",
							async : false,
							success : function(data) {
								setTableData();
								alert("QR Codes successfully generated please download zip file");
								$("#sendButton").prop('disabled', false);
							},
							error: function (xhr, httpStatusMessage, customErrorMessage) {
			                    if (xhr.status === 400) {
			                    	document.location.href="sessionout";
			                      
			                    }else{
			                    	$("#sendButton").prop('disabled', false);
			                    }
			                   }

						});

								//window.location = "../admin/generateQRCodesInBulk/"+numbers;
					//	window.open("../admin/generateQRCodesInBulk/"+numbers);
					}
						
					
				});
			});
function setTableData(){	
	
	var table = $('#qrCodesDetail').DataTable({
		responsive : true,
		"destroy" : true,
		
		"ajax" : "./getQrFileDetails",
		"bSort" : false,			
		"columns" : [
			
			{
				"data" : "id"
			},
			{
				"data" : "folderName"
			},
			{
				"data" : "fileName"
			},
			{
				"data" : "createdOn"
			},
			{
				"data" : "fileName",
				render : function(data, type, row, meta) {
					data = '<a href="./downloadQRFile/'+data+'">Download</a>'
					return data;
				}
			},
			
			{
				"data" : "fileName",
				render : function(data, type, row, meta) {
					data = '<button class="btn btn-default" onclick="sendEmail(\''+data+'\')">Send Email</button>'
					return data;
				}
			},
			]
		
	});	
	
}
function validate(numbers){
		var numberRegex=/^[0-9]+$/;
		if(!numberRegex.test(numbers)){
			alert("enter a number");
			return false;
		}
		if(parseInt(numbers,10)>100){
			alert("You can't generate qr codes greater than 100. Please contact admin.");
			return false;
		}
		return true;
}


function sendEmail(fileName){
	
	$("#qrCodesDetail").find("button").prop('disabled', true);
	$.ajax({
		type : "GET",
		url : "../admin/sendQrViaMail",
		
		async : true,
		contentType : 'application/json',
		data : {
			'fileName' : fileName						
		},
		success : function(data) {
			$("#qrCodesDetail").find("button").prop('disabled', false);
			alert(data.response);
			
		},
		error: function (xhr, httpStatusMessage, customErrorMessage) {
            if (xhr.status === 400) {
            	document.location.href="sessionout";
              
            }else{
            	$("#qrCodesDetail").find("button").prop('disabled', false);
            	alert(xhr.responsetext);
            }
           }

	});

}