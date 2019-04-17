var editor;
$(document).ready(function() {

	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		var token = $('input[name="_csrf"]').attr('value');
		return jqXHR.setRequestHeader('X-CSRF-Token', token);
	});

	var select = $('#select').change(function() {

		if (select.val() == 'relations') {
			/* alert(select.val()); */
			createRelationsTable();
		}
		if (select.val() == 'role') {
			/* alert(select.val()); */
			createRolesTable();
		}
		if (select.val() == 'disputeReasons') {
			/* alert(select.val()); */
			createDisputeReasonsTable();
		}
		if (select.val() == 'itemCategory') {
			/* alert(select.val()); */
			createItemcategoryTable();
		}
		if (select.val() == 'fiscalYear') {
			createFiscalYearTable();
			/* alert(select.val()); */
		}
		if (select.val() == 'rewardPoints') {
			createRewardPointsTable();
			/* alert(select.val()); */
		}

	});

	$('#saveRewardBtn').on('click', function() {

		var rpName = $('#name').val();
		var rpValue = $('#value').val();
		var rpDesc = $('#desc').val();
		var rpActive = $("input[name='rpactive']:checked").val();
		var json = JSON.stringify({
			name : rpName,
			desc : rpDesc,
			value : rpValue,
			active : rpActive
		});
		var url = '../admin/mastertable/addRewardPoint';
		var rewardpoints = 'rewardpoints';
		var modelId = '#rewardPointsModal';
		addRecord(json, url,rewardpoints,modelId);

	});
	
	$('#saveRoleBtn').on('click', function() {
		var rName = $('#role').val();
		var rDesc = $('#roleDescription').val();		
		var rActive = $("input[name='roleactive']:checked").val();
		var json = JSON.stringify({
			role : rName,
			roleDescription : rDesc,			
			active : rActive
		});
		var url = '../admin/mastertable/addRole';
		var roles = 'roles';
		var modelId = '#rolesModal';
		addRecord(json, url,roles,modelId);

	});
	
	$('#saveDisputeBtn').on('click', function() {
		var dReason = $('#disputeReason').val();		
		var drDesc = $('#disputeReasDes').val();		
		var drActive = $("input[name='reasonactive']:checked").val();		
		var json = JSON.stringify({
			disputeReason : dReason,
			disputeReasDes : drDesc,			
			active : drActive
		});
		var url = '../admin/mastertable/addDisputeReason';
		var dispute = 'dispute';
		var modelId = '#disputeReasonsModal';
		addRecord(json, url,dispute,modelId);

	});
	
	$('#saveItemBtn').on('click', function() {
		var cName = $('#categoryName').val();
		var cType = $('#categoryType').val();		
		var json = JSON.stringify({
			categoryName : cName,
			categoryType : cType		
		});
		var url = '../admin/mastertable/addItemCategory';
		var itemcategory = 'itemcategory';
		var modelId = '#itemCategoryModal';
		addRecord(json, url,itemcategory,modelId);
	});
	
	$('#saveFYBtn').on('click', function() {
		var fyStartDate = $('#startDate').val();
		var fyEndDate = $('#endDate').val();		
		var fyActive = $("input[name='fyactive']:checked").val();
		var json = JSON.stringify({
			startDate : fyStartDate,
			endDate : fyEndDate,			
			active : fyActive
		});
		var url = '../admin/mastertable/addFiscalYear';
		var fiscalyear = 'fiscalYear';
		var modelId = '#fiscalYearModal';
		addRecord(json, url,fiscalyear,modelId);

	});
	
	$('#saveRelationBtn').on('click', function() {
		var relation = $('#relation').val();
		var desc = $('#description').val();		
		var active = $("input[name='relactive']:checked").val();
		var json = JSON.stringify({
			relation : relation,
			description : desc,			
			active : active
		});
		var url = '../admin/mastertable/addRelation';
		var relations = 'relation';
		var modelId = '#relationModal';
		addRecord(json, url,relations,modelId);

	});
});

function addRecord(json, ajaxUrl,tableName,modelId) {
	/*console.log(tableName);*/
	$.ajax({
		type : "POST",
		url : ajaxUrl,
		async : false,
		data : json,
		contentType : 'application/json',
		success : function(data) {
			var response = data.data;
			if (tableName == 'rewardpoints') {
				addRowInRewardPointsTableAfterAjaxCall(response)
			} else if (tableName == 'itemcategory') {
				addRowInItemCategotyTableAfterAjaxCall(response);
			} else if (tableName == 'dispute') {
				addRowInDisputeReasonsTableAfterAjaxCall(response);
			} else if (tableName == 'relation') {
				addRowInRelationTableAfterAjaxCall(response);
			} else if (tableName == 'roles') {
				addRowInRolesTableAfterAjaxCall(response);
			} else {
				addRowInFiscalYearTableAfterAjaxCall(response);
			}
			alert('record added sucessfully');
			$(modelId).modal('toggle');
			
			

		},
		error: function (xhr, httpStatusMessage, customErrorMessage) {
            if (xhr.status === 400) {
            	document.location.href="sessionout";
              
            }
           }

	});

}

function addRowInRewardPointsTableAfterAjaxCall(response) { 
	/*console.log('add row in reward');*/
	//var status = checkActiveStatus(response);
	//console.log(response.id)
	$('#rewardPointsTable')
			.append(
					'<tr><td>'
							+ response.id
							+ '</td><td>'
							+ response.name
							+ '</td><td>'
							+ response.desc
							+ '</td><td>'
							+ response.value
							+ '</td><td>'
							+ response.active
							+ '</td><td>'
							+ response.createdOn
							+ '</td><td>'
							+ response.createdBy
							+ '</td><td>'
							+ response.modifiedOn
							+ '</td><td>'
							+ response.modifiedBy
							/*+ '</td><td><button class="btn btn-link" data-toggle="modal" data-target="">Edit</button>/<button class="btn btn-link" data-toggle="modal"	data-target="">Delete</button></td></tr>'*/);
}

function addRowInRolesTableAfterAjaxCall(response) {
	/*console.log('add row in roles');*/
	//var status = checkActiveStatus(response);
	$('#rolesTable')
			.append(
					'<tr><td>'
							+ response.id
							+ '</td><td>'
							+ response.active
							+ '</td><td>'
							+ response.role
							+ '</td><td>'
							+ response.roleDescription							
							+ '</td><td>'
							+ response.createdOn
							+ '</td><td>'
							+ response.createdBy
							+ '</td><td>'
							+ response.modifiedOn
							+ '</td><td>'
							+ response.modifiedBy
							/*+ '</td><td><button class="btn btn-link" data-toggle="modal" data-target="">Edit</button>/<button class="btn btn-link" data-toggle="modal"	data-target="">Delete</button></td></tr>'*/);
}

function addRowInRelationTableAfterAjaxCall(response) {
	/*console.log('add row in relation');*/
	//var status = checkActiveStatus(response);
	$('#relationsTable')
			.append(
					'<tr><td>'
							+ response.id
							+ '</td><td>'
							+ response.relation
							+ '</td><td>'
							+ response.description
							+ '</td><td>'
							+ response.active							
							+ '</td><td>'
							+ response.createdOn
							+ '</td><td>'
							+ response.createdBy
							+ '</td><td>'
							+ response.modifiedOn
							+ '</td><td>'
							+ response.modifiedBy
							/*+ '</td><td><button class="btn btn-link" data-toggle="modal" data-target="">Edit</button>/<button class="btn btn-link" data-toggle="modal"	data-target="">Delete</button></td></tr>'*/);
}

function addRowInItemCategotyTableAfterAjaxCall(response) {
	/*console.log('add row in item category');*/
	$('#itemCategoryTable')
			.append(
					'<tr><td>'
							+ response.id
							+ '</td><td>'
							+ response.categoryName
							+ '</td><td>'
							+ response.categoryType												
							+ '</td><td>'
							+ response.createdOn
							+ '</td><td>'
							+ response.createdBy
							+ '</td><td>'
							+ response.modifiedOn
							+ '</td><td>'
							+ response.modifiedBy
							/*+ '</td><td><button class="btn btn-link" data-toggle="modal" data-target="">Edit</button>/<button class="btn btn-link" data-toggle="modal"	data-target="">Delete</button></td></tr>'*/);
}

function addRowInDisputeReasonsTableAfterAjaxCall(response) {
	/*console.log('add row in dispute');*/
	//var status = checkActiveStatus(response);
	$('#disputeReasonsTable')
			.append(
					'<tr><td>'
							+ response.disputeReasonId
							+ '</td><td>'
							+ response.disputeReason
							+ '</td><td>'
							+ response.disputeReasDes												
							+ '</td><td>'							
							+ response.active
							+ '</td><td>'
							+ response.createdOn
							+ '</td><td>'
							+ response.createdBy
							+ '</td><td>'
							+ response.modifiedOn
							+ '</td><td>'
							+ response.modifiedBy
							/*+ '</td><td><button class="btn btn-link" data-toggle="modal" data-target="">Edit</button>/<button class="btn btn-link" data-toggle="modal"	data-target="">Delete</button></td></tr>'*/);
}

function addRowInFiscalYearTableAfterAjaxCall(response) { 
	/*console.log('add row in fy');*/
	//var status = checkActiveStatus(response);
	$('#fiscalYearTable')
			.append(
					'<tr><td>'
							+ response.id
							+ '</td><td>'
							+ response.startDate
							+ '</td><td>'
							+ response.endDate												
							+ '</td><td>'
							+ response.userId												
							+ '</td><td>'
							+ response.active
							+ '</td><td>'
							+ response.createdOn
							+ '</td><td>'
							+ response.createdBy
							+ '</td><td>'
							+ response.modifiedOn
							+ '</td><td>'
							+ response.modifiedBy
							/*+ '</td><td><button class="btn btn-link" data-toggle="modal" data-target="">Edit</button>/<button class="btn btn-link" data-toggle="modal" data-target="">Delete</button></td></tr>'*/);
}


function checkActiveStatus(response) {
	if (response.active == true) {
		return 'Yes';
	} else {
		return 'No';
	}
}

function createRelationsTable() {

	$('#tableTxnArea').text('');
	$('#tableArea').text('');

	$
			.get(
					'./mastertable/relations/',
					function(data) {

						$("#tablePanelHeading").html("<b>Relation Table </b>");

						// Crate table html tag
						var table = $(
								"<table id='relationsTable' class='table table-striped table-bordered table-hover'></table>")
								.appendTo("#tableArea");
						// Create table header row
						var colNames = $("<thead></thead>").appendTo(table);
						var rowHeader = $("<tr></tr>").appendTo(colNames);
						$("<th></th>").text("Relation Id").appendTo(rowHeader);
						$("<th></th").text("Relation").appendTo(rowHeader);
						$("<th></th>").text("Description").appendTo(rowHeader)
						$("<th></th>").text("IsActive").appendTo(rowHeader)
						$("<th></th>").text("CreatedOn").appendTo(rowHeader);
						$("<th></th>").text("CreatedBy").appendTo(rowHeader);
						$("<th></th>").text("ModifiedOn").appendTo(rowHeader);
						$("<th></th>").text("ModifiedBy").appendTo(rowHeader);
						/*$("<th></th>").text("Edit/Delete").appendTo(rowHeader);*/

						// Create new row

						var relationinfo = data.data;
						// alert(relationinfo);

						var tabBody = $("<tbody></tbody>").appendTo(table);
						$
								.each(
										relationinfo,
										function(index) {
 
											var row = $("<tr></tr>").appendTo(
													tabBody);
											$("<td></td>").text(
													relationinfo[index].id)
													.appendTo(row);
											$("<td></td>")
													.text(
															relationinfo[index].relation)
													.appendTo(row);

											$("<td></td>")
													.text(
															relationinfo[index].description)
													.appendTo(row);
											$("<td></td>").text(
													relationinfo[index].active)
													.appendTo(row);

											$("<td></td>")
													.text(
															relationinfo[index].createdOn)
													.appendTo(row);
											$("<td></td>")
													.text(
															relationinfo[index].createdBy)
													.appendTo(row);

											$("<td></td>")
													.text(
															relationinfo[index].modifiedOn)
													.appendTo(row);
											$("<td></td>")
													.text(
															relationinfo[index].modifiedBy)
													.appendTo(row);
											/*$("<td></td>")
													.html(
															'<button class="btn btn-link" data-toggle="modal"	data-target="" id="editRelationModal">Edit</button>/<button class="btn btn-link" data-toggle="modal"	data-target="">Delete</button>')
													.appendTo(row);*/

										});

					});
	setModalToAddRecord('#relationModal');

}

function createRolesTable() {

	$('#tableTxnArea').text('');
	$('#tableArea').text('');

	$
			.get(
					'./mastertable/roles/',
					function(data) {

						$("#tablePanelHeading").html("<b>Roles Table </b>");

						// Crate table html tag
						var table = $(
								"<table id='rolesTable' class='table table-striped table-bordered table-hover'></table>")
								.appendTo("#tableArea");
						// Create table header row
						var colNames = $("<thead></thead>").appendTo(table);
						var rowHeader = $("<tr></tr>").appendTo(colNames);
						$("<th></th>").text("Role Id").appendTo(rowHeader);
						$("<th></th").text("IsActive").appendTo(rowHeader);
						$("<th></th>").text("Role").appendTo(rowHeader)
						$("<th></th>").text("RoleDescription").appendTo(
								rowHeader)
						$("<th></th>").text("CreatedOn").appendTo(rowHeader);
						$("<th></th>").text("CreatedBy").appendTo(rowHeader);
						$("<th></th>").text("ModifiedOn").appendTo(rowHeader);
						$("<th></th>").text("ModifiedBy").appendTo(rowHeader);
						/*$("<th></th>").text("Edit/Delete").appendTo(rowHeader);*/

						// Create new row

						var rolesinfo = data.data;
						// alert(rolesinfo);

						var tabBody = $("<tbody></tbody>").appendTo(table);
						$
								.each(
										rolesinfo,
										function(index) {

											var row = $("<tr></tr>").appendTo(
													tabBody);
											$("<td></td>").text(
													rolesinfo[index].id)
													.appendTo(row);
											$("<td></td>").text(
													rolesinfo[index].active)
													.appendTo(row);

											$("<td></td>").text(
													rolesinfo[index].role)
													.appendTo(row);
											$("<td></td>")
													.text(
															rolesinfo[index].roleDescription)
													.appendTo(row);

											$("<td></td>").text(
													rolesinfo[index].createdOn)
													.appendTo(row);
											$("<td></td>").text(
													rolesinfo[index].createdBy)
													.appendTo(row);

											$("<td></td>")
													.text(
															rolesinfo[index].modifiedOn)
													.appendTo(row);
											$("<td></td>")
													.text(
															rolesinfo[index].modifiedBy)
													.appendTo(row);
											/*$("<td></td>")
													.html(
															'<button class="btn btn-link" data-toggle="modal"	data-target="">Edit</button>/<button class="btn btn-link" data-toggle="modal" data-target="">Delete</button>')
													.appendTo(row);*/

										});

					});
	setModalToAddRecord('#rolesModal');
}

function createDisputeReasonsTable() {

	$('#tableTxnArea').text('');
	$('#tableArea').text('');

	$
			.get(
					'./mastertable/disputeReasons/',
					function(data) {

						$("#tablePanelHeading").html(
								"<b>Dispute Reasons Table </b>");

						// Crate table html tag
						var table = $(
								"<table id='disputeReasonsTable' class='table table-striped table-bordered table-hover'></table>")
								.appendTo("#tableArea");
						// Create table header row
						var colNames = $("<thead></thead>").appendTo(table);
						var rowHeader = $("<tr></tr>").appendTo(colNames);
						$("<th></th>").text("Dispute Reason Id").appendTo(
								rowHeader);
						$("<th></th").text("DisputeReason").appendTo(rowHeader);
						$("<th></th>").text("DisputeReasDes").appendTo(
								rowHeader)
						$("<th></th>").text("IsActive").appendTo(rowHeader)
						$("<th></th>").text("CreatedOn").appendTo(rowHeader);
						$("<th></th>").text("CreatedBy").appendTo(rowHeader);
						$("<th></th>").text("ModifiedOn").appendTo(rowHeader);
						$("<th></th>").text("ModifiedBy").appendTo(rowHeader);
						/*$("<th></th>").text("Edit/Delete").appendTo(rowHeader);*/

						// Create new row

						var disputeReasonsinfo = data.data;
						// alert(disputeReasonsinfo);

						var tabBody = $("<tbody></tbody>").appendTo(table);
						$
								.each(
										disputeReasonsinfo,
										function(index) {

											var row = $("<tr></tr>").appendTo(
													tabBody);
											$("<td></td>")
													.text(
															disputeReasonsinfo[index].disputeReasonId)
													.appendTo(row);
											$("<td></td>")
													.text(
															disputeReasonsinfo[index].disputeReason)
													.appendTo(row);

											$("<td></td>")
													.text(
															disputeReasonsinfo[index].disputeReasDes)
													.appendTo(row);
											$("<td></td>")
													.text(
															disputeReasonsinfo[index].active)
													.appendTo(row);

											$("<td></td>")
													.text(
															disputeReasonsinfo[index].createdOn)
													.appendTo(row);
											$("<td></td>")
													.text(
															disputeReasonsinfo[index].createdBy)
													.appendTo(row);

											$("<td></td>")
													.text(
															disputeReasonsinfo[index].modifiedOn)
													.appendTo(row);
											$("<td></td>")
													.text(
															disputeReasonsinfo[index].modifiedBy)
													.appendTo(row);
											/*$("<td></td>")
													.html(
															'<button class="btn btn-link" data-toggle="modal"	data-target="">Edit</button>/<button class="btn btn-link" data-toggle="modal"	data-target="">Delete</button>')
													.appendTo(row);*/

										});

					});
	setModalToAddRecord('#disputeReasonsModal');
}

function createItemcategoryTable() {

	$('#tableTxnArea').text('');
	$('#tableArea').text('');

	$
			.get(
					'./mastertable/itemCategory/',
					function(data) {

						$("#tablePanelHeading").html(
								"<b>Item Category Table </b>");

						// Crate table html tag
						var table = $(
								"<table id='itemCategoryTable' class='table table-striped table-bordered table-hover'></table>")
								.appendTo("#tableArea");
						// Create table header row
						var colNames = $("<thead></thead>").appendTo(table);
						var rowHeader = $("<tr></tr>").appendTo(colNames);
						$("<th></th>").text("Item Category Id").appendTo(
								rowHeader);
						$("<th></th").text("CategoryName").appendTo(rowHeader);
						$("<th></th>").text("CategoryType").appendTo(rowHeader)

						$("<th></th>").text("CreatedOn").appendTo(rowHeader);
						$("<th></th>").text("CreatedBy").appendTo(rowHeader);
						$("<th></th>").text("ModifiedOn").appendTo(rowHeader);
						$("<th></th>").text("ModifiedBy").appendTo(rowHeader);
						/*$("<th></th>").text("Edit/Delete").appendTo(rowHeader);*/
						// Create new row

						var itemCategoryinfo = data.data;
						// alert(itemCategoryinfo);

						var tabBody = $("<tbody></tbody>").appendTo(table);
						$
								.each(
										itemCategoryinfo,
										function(index) {

											var row = $("<tr></tr>").appendTo(
													tabBody);
											$("<td></td>").text(
													itemCategoryinfo[index].id)
													.appendTo(row);
											$("<td></td>")
													.text(
															itemCategoryinfo[index].categoryName)
													.appendTo(row);

											$("<td></td>")
													.text(
															itemCategoryinfo[index].categoryType)
													.appendTo(row);

											$("<td></td>")
													.text(
															itemCategoryinfo[index].createdOn)
													.appendTo(row);
											$("<td></td>")
													.text(
															itemCategoryinfo[index].createdBy)
													.appendTo(row);

											$("<td></td>")
													.text(
															itemCategoryinfo[index].modifiedOn)
													.appendTo(row);
											$("<td></td>")
													.text(
															itemCategoryinfo[index].modifiedBy)
													.appendTo(row);
											/*$("<td></td>")
													.html(
															'<button class="btn btn-link" data-toggle="modal"	data-target="">Edit</button>/<button class="btn btn-link" data-toggle="modal"	data-target="">Delete</button>')
													.appendTo(row);*/

										});

					});
	setModalToAddRecord('#itemCategoryModal');
}

function createFiscalYearTable() {

	$('#tableTxnArea').text('');
	$('#tableArea').text('');

	$
			.get(
					'./mastertable/fiscalYear/',
					function(data) {

						$("#tablePanelHeading").html(
								"<b>Fiscal Year Table </b>");

						// Crate table html tag
						var table = $(
								"<table id='fiscalYearTable' class='table table-striped table-bordered table-hover'></table>")
								.appendTo("#tableArea");
						// Create table header row
						var colNames = $("<thead></thead>").appendTo(table);
						var rowHeader = $("<tr></tr>").appendTo(colNames);
						$("<th></th>").text("Fiscal Year Id").appendTo(
								rowHeader);
						$("<th></th").text("StartDate").appendTo(rowHeader);
						$("<th></th>").text("EndDate").appendTo(rowHeader)
						$("<th></th>").text("UserId").appendTo(rowHeader)
						$("<th></th>").text("IsActive").appendTo(rowHeader)

						$("<th></th>").text("CreatedOn").appendTo(rowHeader);
						$("<th></th>").text("CreatedBy").appendTo(rowHeader);
						$("<th></th>").text("ModifiedOn").appendTo(rowHeader);
						$("<th></th>").text("ModifiedBy").appendTo(rowHeader);

						/*$("<th></th>").text("Edit").appendTo(rowHeader);*/

						// Create new row

						var fiscalYearinfo = data.data;
						// alert(ficalYearinfo);

						var tabBody = $("<tbody id='fiscalYear'></tbody>")
								.appendTo(table);
						$
								.each(
										fiscalYearinfo,
										function(index) {

											var row = $("<tr ></tr>").appendTo(
													tabBody);
											$("<td></td>").text(
													fiscalYearinfo[index].id)
													.appendTo(row);
											$("<td></td>")
													.text(
															fiscalYearinfo[index].startDate)
													.appendTo(row);

											$("<td></td>")
													.text(
															fiscalYearinfo[index].endDate)
													.appendTo(row);
											$("<td></td>")
													.text(
															fiscalYearinfo[index].userId)
													.appendTo(row);
											$("<td></td>")
													.text(
															fiscalYearinfo[index].active)
													.appendTo(row);

											$("<td></td>")
													.text(
															fiscalYearinfo[index].createdOn)
													.appendTo(row);
											$("<td></td>")
													.text(
															fiscalYearinfo[index].createdBy)
													.appendTo(row);

											$("<td></td>")
													.text(
															fiscalYearinfo[index].modifiedOn)
													.appendTo(row);
											$("<td></td>")
													.text(
															fiscalYearinfo[index].modifiedBy)
													.appendTo(row);

											/*$("<td></td>")
													.html(
															'<button class="btn btn-link" data-toggle="modal" data-target="">Edit</button>/<button class="btn btn-link" data-toggle="modal"	data-target="">Delete</button>')
													.appendTo(row);*/

										});

					});
	setModalToAddRecord('#fiscalYearModal');

}
function createRewardPointsTable() {

	$('#tableTxnArea').text('');
	$('#tableArea').text('');

	$
			.get(
					'./mastertable/rewardPoints/',
					function(data) {

						$("#tablePanelHeading").html(
								"<b>Reward Points Table </b>");

						// Crate table html tag
						var table = $(
								"<table id='rewardPointsTable' class='table table-striped table-bordered table-hover'></table>")
								.appendTo("#tableArea");
						// Create table header row
						var colNames = $("<thead></thead>").appendTo(table);
						var rowHeader = $("<tr></tr>").appendTo(colNames);
						$("<th></th>").text("Rewars Point Id").appendTo(
								rowHeader);
						$("<th></th").text("Reward Point Name").appendTo(
								rowHeader);
						$("<th></th>").text("Reward Point Description")
								.appendTo(rowHeader)
						$("<th></th>").text("Reward Point Value").appendTo(
								rowHeader)
						$("<th></th>").text("IsActive").appendTo(rowHeader)
						$("<th></th>").text("CreatedOn").appendTo(rowHeader);
						$("<th></th>").text("CreatedBy").appendTo(rowHeader);
						$("<th></th>").text("ModifiedOn").appendTo(rowHeader);
						$("<th></th>").text("ModifiedBy").appendTo(rowHeader);
						/*$("<th></th>").text("Edit/Delete").appendTo(rowHeader);*/

						// Create new row

						var rewardPointsInfo = data.data;

						var tabBody = $("<tbody></tbody>").appendTo(table);
						$
								.each(
										rewardPointsInfo,
										function(index) {

											var row = $("<tr></tr>").appendTo(
													tabBody);
											$("<td></td>").text(
													rewardPointsInfo[index].id)
													.appendTo(row);
											$("<td></td>")
													.text(
															rewardPointsInfo[index].name)
													.appendTo(row);

											$("<td></td>")
													.text(
															rewardPointsInfo[index].desc)
													.appendTo(row);
											$("<td></td>")
													.text(
															rewardPointsInfo[index].value)
													.appendTo(row);
											$("<td></td>")
													.text(rewardPointsInfo[index].active).appendTo(row);

											$("<td></td>")
													.text(
															rewardPointsInfo[index].createdOn)
													.appendTo(row);
											$("<td></td>")
													.text(
															rewardPointsInfo[index].createdBy)
													.appendTo(row);

											$("<td></td>")
													.text(
															rewardPointsInfo[index].modifiedOn)
													.appendTo(row);
											$("<td></td>")
													.text(
															rewardPointsInfo[index].modifiedBy)
													.appendTo(row);
											/*$("<td></td>")
													.html(
															'<button class="btn btn-link" data-toggle="modal"	data-target="">Edit</button>/<button class="btn btn-link" data-toggle="modal"	data-target="">Delete</button>')
													.appendTo(row);*/
										});

					});
	setModalToAddRecord('#rewardPointsModal');
}

function setModalToAddRecord(modalId) {
	$('#addRecordBtn').attr('data-target', modalId);
}