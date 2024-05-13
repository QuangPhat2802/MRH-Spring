/**
 * 
 */
$(document).ready(function(){
	findBrands(1);
	
	/**
	 *  Show pagination
	 */
	$('.pagination').on('click','.page-link', function(){
		var pagerNumber = $(this).attr("data-index");
		var keyword = $('.search-brand').val();
		if ( keyword != "" ) {
			findBrandByBrandName(keyword,pagerNumber);
		} else {
			findBrands(pagerNumber)
		}
	});

	
	// search
	$('.search-btn').on('click', function() {
		var keyword = $('.search-brand').val().toLowerCase();
		var pagerNumber = $(this).attr("data-index");
		findBrandByBrandName(keyword,1);
	
	});
	
	
	
	var $brandInfoForm = $('#brandInfoForm');
	var $brandInfoModal = $('#brandInfoModal');
	
	// show add modal
	$('#addModal').on('click', function() {
		resetForm($brandInfoForm);
		showModalWithCustomizedTitle($brandInfoModal,"Add Brand");
		$('#logoImg img').attr('src' , '/images/image-demo.png');
		$('#brandId').closest(".form-group").addClass("d-none");
	});
	
	
	// action save brand
	// Submit add and update brand
	$('#saveBrandBtn').on('click', function (event) {

		event.preventDefault();
		var formData = new FormData($brandInfoForm[0]);
		var brandId = formData.get("brandId");
		var isAddAction = brandId == undefined || brandId == "";
		//var isValidForm = formValidate($footballTeamForm, _self.getValidationInfo(isAddAction));
		$brandInfoForm.validate({
			ignone: [],
			rules: {
				brandName: {
					required: true,
					maxlength : 50
				},
				logoFiles: {
					required: isAddAction,
				}
			},
			messages: {
				brandName: {
					required: "Please input Brand Name",
					maxlength: "The Brand Name must be less than 50 characters",
				},
				logoFiles: {
					required: "Please upload Brand Logo",
				}
			},
			errorElement: "div",
			errorClass: "error-message-invalid"
		});
		
		
		if ($brandInfoForm.valid()) {

			// POST data to server-side by AJAX
			$.ajax({
				url: "/brand/api/" + (isAddAction ? "add" : "update"),
				type: 'POST',
				enctype: 'multipart/form-data',
				processData: false,
				contentType: false,
				cache: false,
				timeout: 10000,
				data: formData,
				success: function(responseData) {
					
					if (responseData.responseCode == 100) {
						$brandInfoModal.modal('hide');
						findBrands(1);
						showNotification(true, responseData.responseMsg);
					} else {
						showMsgOnField($brandInfoForm.find("#brandName"), responseData.responseMsg);
					}
				}
			});
		}
	});
	
	
	$("#brandInfoTable").on('click', '.delete-btn', function() {
		$("#deletedBrandName").text($(this).data("name"));
		$("#deleteSubmitBtn").attr("data-id", $(this).data("id"));
		$('#confirmDeleteModal').modal('show');
	});

	// Submit delete brand
	$("#deleteSubmitBtn").on('click' , function() {
		$.ajax({
			url : "/brand/api/delete/" + $(this).attr("data-id"),
			type : 'DELETE',
			dataType : 'json',
			contentType : 'application/json',
			success : function(responseData) {
				$('#confirmDeleteModal').modal('hide');
				showNotification(responseData.responseCode == 100, responseData.responseMsg);
				findBrands(1);
			}
		});
	});
	
	// Show update brand modal
	$("#brandInfoTable").on('click', '.edit-btn', function() {

		// Get brand info by brand ID
		$.ajax({
			url : "/brand/api/findAll?id=" + $(this).data("id"),
			type : 'GET',
			dataType : 'json',
			contentType : 'application/json',
			success : function(responseData) {
				if (responseData.responseCode == 100) {
					var brandInfo = responseData.data;
					resetForm($brandInfoForm);
					showModalWithCustomizedTitle($brandInfoModal, "Edit Brand");

					$('#brandId').val(brandInfo.brandId);
					$('#brandName').val(brandInfo.brandName);
					$('#description').val(brandInfo.description);

					var brandLogo = brandInfo.logo;
					if (brandLogo == null || brandLogo == "") {
						brandLogo = "/images/image-demo.png";
					}
					$("#logoImg img").attr("src", brandLogo);
					$("#logo").val(brandLogo);
					$('#brandId').closest(".form-group").removeClass("d-none");
				}
			}
		});
	});
	
});

function findBrandByBrandName(keyword,pageNumber) {
	$.ajax({
		url : "/brand/api/search/" + keyword+"/"+pageNumber,
		type : 'Get',
		dataType : 'json',
		contentType : "application/json",
		success : function(responseData) {
			if(responseData.responseCode == 100){
				renderBrandsTable(responseData.data.brandsList);
				renderPagination(responseData.data.paginationInfo);
				if(responseData.data.paginationInfo.pageNumberList.length < 2){
					$('.pagination').addClass("d-none");
				}else{
					$('.pagination').removeClass("d-none")
				}
				totalItem(responseData.data.totalItem);
			}
		}
		
	});
}


function findBrands(pagerNumber) {
	$.ajax({
		url : "/brand/api/findAll/" +pagerNumber,
		type : 'GET',
		dataType : 'json',
		contentType : "application/json",
		success : function(responseData) {
			if(responseData.responseCode == 100){
				renderBrandsTable(responseData.data.brandsList);
				renderPagination(responseData.data.paginationInfo);
				totalItem(responseData.data.totalItem);
			}
		}
	});
}

function totalItem(item) {
	$(".total").empty();
	$(".total").append(item);
}


function renderBrandsTable(brandList) {
	var rowHTML = "";
	$("#brandInfoTable tbody").empty();
	$.each(brandList, function(key, value) {
		rowHTML = "<tr>"
				+		"<td>" + value.brandId + "</td>"
				+ 		"<td>" + value.brandName +"</td>" 
				+		"<td class='text-center'><a href='" + value.logo + "' data-toggle='lightbox' data-max-width='1000'><img class='img-fluid' src='" + value.logo + "'></td>"
				+		"<td>" + value.description + "</td>"
				+		"<td class = 'action-btns'>"
				+			"<a class='edit-btn' data-id='" + value.brandId +"'><i class='fas fa-edit'></i></a> | <a class='delete-btn' data-name='" + value.brandName + "' data-id='" + value.brandId + "'><i class='fas fa-trash-alt'></i></a>"		
				+		"</td>" 
					"</tr>";
		$("#brandInfoTable tbody").append(rowHTML);
	});
}
function renderPagination(paginationInfo) {
	var paginationInnerHtml = "";
	if(paginationInfo.pageNumberList.length > 0){
		$("ul.pagination").empty();
		paginationInnerHtml += '<li class="page-item"><a class="page-link' + (paginationInfo.firstPage == 0 ? ' disabled' : '') + '" href="javascript:void(0)" data-index="'+ paginationInfo.firstPage + '">Firts Page</a></li>'
		paginationInnerHtml += '<li class="page-item"><a class="page-link' + (paginationInfo.previousPage == 0 ? ' disabled' : '') + '" href="javascript:void(0)" data-index="'+ paginationInfo.previousPage + '"> < </a></li>'
		$.each(paginationInfo.pageNumberList, function(key, value) {
			paginationInnerHtml += '<li class="page-item"><a class="page-link '+ (value == paginationInfo.currentPage ? 'active' : '') +'" href="javascript:void(0)" data-index="' + value +'">' + value + '</a></li>';
		});
		paginationInnerHtml += '<li class="page-item"><a class="page-link ' + (paginationInfo.nextPage == 0 ? ' disabled' : '') + '" href="javascript:void(0)" data-index="'+ paginationInfo.nextPage + '"> > </a></li>'
		paginationInnerHtml += '<li class="page-item"><a class="page-link ' + (paginationInfo.lastPage == 0 ? ' disabled' : '') + '" href="javascript:void(0)" data-index="'+ paginationInfo.lastPage + '">Last Page</a></li>'
		$("ul.pagination").append(paginationInnerHtml);
	}
}