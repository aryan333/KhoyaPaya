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
				
				
				
				$('#sendButton').on('click', function(e) {
					
					
				    var file_data = $('#qrMapForm').prop('files'); 
				    var fileName=file_data.value;
				    var ext = fileName.split('.').pop();
				   
				    if(ext!='csv'){
				    	alert('Please chooes csv file');
				    	return;
				    }
				   var form_data = new FormData();                  
				    form_data.append('file', file_data.files[0]);
					
				    $.ajax({
				        url: './mapQrCode', // point to server-side PHP script 
				        dataType: 'text',  // what to expect back from the PHP script, if anything
				        cache: false,
				        contentType: false,
				        processData: false,
				        data: form_data,                         
				        type: 'POST',
				        success: function(php_script_response){
				        	setTableData(); 
				        	alert("Please check below table for mapped users");
							// display response from the PHP script, if any
				        },
						
						error: function(request, status, error){
				            // display response from the PHP script, if any
				        }
						
				     });
				});


				
			});
function setTableData(){	
	
	var table = $('#mapQrCodesDetail').DataTable({
		responsive : true,
		"destroy" : true,
		
		"ajax" : "./getQrMappedUsers",
		"bSort" : false,			
		"columns" : [
			
			{
				"data" : "name"
			},
			{
				"data" : "mobileNumber"
			},
			{
				"data" : "qrSeries"
			},
			{
				"data" : "qrMappedDate"
			},
			
			]
		
	});	
	
	
	
	
	
}
