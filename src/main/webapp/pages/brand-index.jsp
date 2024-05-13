<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<title>Brand Management</title>
<jsp:include page="../common/head.jsp" />
<link rel="stylesheet" href="<c:url value='/css/brand.css'/>">
</head>
<body>
	<header>
		<div class="container">
			<div class="d-flex">
				<div class="main-logo">Pilot Project</div>
				<a class="ml-5 nav-link " href="/product">Product</a>
				<a class="nav-link active " href="/brand">Brand</a>
			</div>
		</div>
	</header>
	<div class="container">
		<div class="sub-header">
			<div class="float-left sub-title">Brand Management</div>
				<div class="float-left main-search">
					<input class="search-brand" type="text" placeholder="Search..."/>
					<button type="submit" class="search-btn"><i class="fa fa-search"></i></button>
				</div>	
			<div class="float-right"><a class="btn btn-success add-btn" id="addModal"><i class="fas fa-plus-square"></i> Add Brand</a></div>
		</div>
		<table class="table table-bordered" id="brandInfoTable">
			<thead>
				<tr class="text-center">
					<th scope="col">#</th>
					<th scope="col">Name</th>
					<th scope="col">Logo</th>
					<th scope="col">Description</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody id="myTable">
			</tbody>
		</table>
		
		<div class="d-flex justify-content-center">
			<div class="total-items">
				<span>Total Items: </span>
			</div>&nbsp;&nbsp;
			<div class="total">
				
			</div>
			&nbsp;&nbsp;
			<ul class="pagination">
			</ul>
		</div>
	</div>
	<!-- Modal Add and Edit Brand -->
	<div class="modal fade" id="brandInfoModal">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<form id="brandInfoForm" role="form" enctype="multipart/form-data">
					<div class="modal-header">
						<h5 class="modal-title">Add Brand</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group d-none">
							<label>Brand ID</label>
							<input type="text" class="form-control" id="brandId" name="brandId" placeholder="Brand Id" readonly>
						</div>
						<div class="form-group">
							<label for="brandName">Brand Name <span class="required-mask">(*)</span></label>
							<input type="text" class="form-control" id="brandName" name="brandName" placeholder="Brand name">
						</div>
						<div class="form-group">
							<label for="logo">Logo <span class="required-mask">(*)</span></label>
							<div class="preview-image-upload" id="logoImg">
								<img src="<c:url value='/images/image-demo.png'/>" alt="image">
							</div>
							<input type="file" class="form-control upload-image" name="logoFiles" accept="image/*" />
							<input type="hidden" class="old-img" id="logo" name="logo">
						</div>
						<div class="form-group">
							<label for="description">Description</label>
							<textarea name="description" id="description" cols="30" rows="3" class="form-control" placeholder="Description"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
						<button type="button" class="btn btn-primary" id="saveBrandBtn">Save</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- Modal Confirm Deleting Brand -->
	<div class="modal fade" id="confirmDeleteModal" >
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Delete Brand</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>Do you want to delete <b class="brand-name"></b>?</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary" id="deleteSubmitBtn">Delete</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../common/footer.jsp" />
	<script src="<c:url value='/js/base.js'/>"></script>
	<script src="<c:url value='/js/brand.js'/>"></script>
</body>
</html>