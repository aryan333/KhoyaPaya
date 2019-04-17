jQuery(document).ready(function() {

	var userId=0;

	var select = $('#select').change(function(){	
	
		userId = select.val();
   
		
		setDataTableBodyForMerchant("../admin/getMerchantInfo/?userId="
				+ userId);

	});
	

	
	// for all merchant location by indivual salesperson through mobile app
	
   $("#allMerchantsBySPMapBtn").click(function(){
		
		window.open('allmerchantLocationbyindiviualspmap/?userId='+userId+'');
	});
	
	
	
	// view merchant shop pic
	
	$('.merchantInfoBySP').on('click' ,function(){
		
		
		var src = $(this).attr('src');
		
		   $('#merchantShopPic').attr('src',src);
		
		
		
	});
	
	
$("#merchantInfo").on('click','.merchantInfoBySP',function(){
		
		var src = $(this).attr('src');
		
		   $('#merchantShopPic').attr('src',src);
		
		
		
	});
	
	
	
		function setDataTableBodyForMerchant(url) {
			

			var table = $('#merchantInfo').DataTable({
				responsive : true,
				"pageLength": 100,
				"destroy" : true,
				dom : 'lBfrtip',							
					  buttons: [
					       {
					           extend: 'pdf',
					           footer: true,
					           exportOptions: {
					                columns: [1,2,3,4,5,6,7,8,9,10,11,12,13,14]
					            }
					       },
					       {
					           extend: 'csv',
					           footer: false,
					           exportOptions: {
					                columns: [1,2,3,4,5,6,7,8,9,10,11,12,13,14]
					            }
					          
					       },
					       {
					           extend: 'excel',
					           footer: false,
					           exportOptions: {
					                columns: [1,2,3,4,5,6,7,8,9,10,11,12,13,14]
					            }
					       }         
					    ]  
					     ,
				"order" : [ [ 4, "desc" ] ],
				"ajax" : {
					"url":url,
					
					error: function (xhr, httpStatusMessage, customErrorMessage) {
		                if (xhr.status === 400) {
		                	document.location.href="sessionout";
		                  
		                }
		               }
				},
				
				"aoColumnDefs": [
					{ "sClass":"p-3 mb-2 bg-success text-white", "aTargets": [ 13 ] },
					{ "sClass":"p-3 mb-2 bg-success text-white", "aTargets": [ 12 ] }
					],
				/* "bSort" : false, */
				async : true,
				"columns" : [ {
					"data" : null
				},{
					"data" : "name"	
				},{
					"data" : "mobileNumber"
						
				},{
					"data" : "merchant"		
				},{
					"data" : "createdOn"		
				},{
					"data" : "enterprise"
			    },{
					"data" : "natureOfBusiness"
						
				},{
					"data" : "installationMedium"
						
				},{
					"data" : "installationStatus"		
						
			    },{
					"data" : "shopArea"
						
				},{
					"data" : "city"
						
				},{
					"data" : "state"
						
				},{
					data : "shopBlobId",
					render :function(data,type,row){
						var shopid = row.shopBlobId;									
						if (shopid == null) {
							data = "Not Available";
						} else {
							
						data='<button type="button" class="btn btn-link merchantInfoBySP" src='+row.shopBlobId+' data-toggle="modal" data-target="#onMerchantPic">View Photo</button>';
						} 
						return data;
					}
					
				}, {
					data : "salesPersonName",
				
					render :function(data,type,row){
					var salespersonName=row.salesPersonName;
					
					data='<h5>'+salespersonName+'<h5>';
					
					return data;
						
					}
					
				},{
					data : "salesPersonMobileNumber",
					
					render :function(data,type,row){
						var salespersonMobile=row.salesPersonMobileNumber;
						
						data='<h5 class="p-3 mb-2 bg-success text-white" >'+salespersonMobile+'<h5>';
						
						return data;
							
						}
				
				}
				/*{
					"data":"address"
				}*/]

			});
			table.on('order.dt search.dt', function() {
				table.column(0, {
					search : 'applied',
					order : 'applied'
				}).nodes().each(function(cell, i) {
					cell.innerHTML = i + 1;
				});
			}).draw();
		};

	
					
});