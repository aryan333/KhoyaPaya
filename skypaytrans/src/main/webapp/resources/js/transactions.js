bluPay = new function() {
	var $instance = this;
	this.init = function(flag) {
		var payeeName;
		var payerName;
    	$('#dataTables-example tfoot th').each( function () {
            var title = $(this).text();
            $(this).html( '<input style="width:100%;font-size:8px; line-height:2.5;font-weight: bolder;" type="text" placeholder="'+title+'" />' );
        } );
    	
      var table=  $('#dataTables-example').DataTable( {
        	 responsive: true,
        	 dom: 'lBfrtip',
             buttons: [
            	 'copyHtml5',
                 'excelHtml5',
                 'csvHtml5',
                 'pdfHtml5'
             ],
             "order": [[ 0, "desc" ]],
             paging : true,
            "processing": true,
            "serverSide": true,
            "scrollY":  "400px",
            "bSort" : false,
            "scrollCollapse": true,
            "ajax": {
                "url": '../admin/transactionsList?txnType='+flag+'',
                "type": "GET",
                error: function (xhr, httpStatusMessage, customErrorMessage) {
                    if (xhr.status === 400) {
                    	document.location.href="sessionout";
                      
                    }
                   }
            },
            "columns": [
                { "data": "transactionId" },
                { "data": "payerName",
                	render : function(data, type, row, meta) {
						payerName = data;
						
						return data;
					}
                
                },
                { "data": "payeeName",
                	render : function(data, type, row, meta) {
						payeeName = data;
						
						return data;
					}
                
                
                
                },
                { "data": "paymentStatus",
                		
                	render : function(data, type, row, meta) {
						var paymentStatus = data;
						if (paymentStatus.includes('P')
								) {
							data ='Pending' ;
						} else if (paymentStatus.includes('A')) {
							data = 'Accepted';
						}else if(paymentStatus.includes('R')){
							data='Rejected';
						}else{
							data='Closed';
						}

						return data;
					}
                },
                { "data": "cashReceived" },
                { "data": "skyCredit",
                	render : function(data, type, row, meta) {
						var skyCredit = data;
						if (skyCredit.includes('-')
								) {
							data ='<span title="'+payerName+' get from '+payeeName+'" style="color:red;">' +skyCredit.replace('-','')+'</span>'
						}else{
							data='<span title="'+payerName+' owe to '+payeeName+'" style="color:#2ad02a;">' +skyCredit+'</span>'
						}

						return data;
					}   
                
                
                
                
                
                
                },
                { "data": "billAmount" },
                
                { "data": "gstEnabled",
            		
                	render : function(data, type, row, meta) {
						var gstEnabled = data;
						console.log(gstEnabled);
						if (gstEnabled==true) {
							data ='YES' ;
						
						}else{
							data='NO';
						}

						return data;
					}
                },
                { "data": "userType",
                	
                	render : function(data, type, row, meta) {
						var userType = data;
						if (userType.includes('P')
								) {
							data ='Registered User' ;
						}else{
							data='Guest User';
						}

						return data;
					}   
                
                },
                
                { "data": "createdOn" },
                { "data": "modifiedOn" }
            ]
        } );
    
       
        table.columns().every( function () {
            var that = this;
     
           var input= $( 'input', this.footer() ).keyup(function (e) {
            	
                if ( that.search() !== this.value ) {
                    that
                        .search( this.value )
                       
                }
                if(e.which==13 && input.val()!=''){
                that.draw();
                }
            } );
        } );
        $('.dataTables_filter').hide();
  
    
}
}