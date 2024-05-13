<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Management</title>
<jsp:include page="../common/head.jsp" />
<link rel="stylesheet" href="<c:url value='/css/product.css'/>">
</head>
<body>
<header>
	<div class="container">
		<div class="d-flex  justify-content-between ">
			<div class="left">
				<div class="main-logo">Pilot Project</div>
				<a class="ml-5 nav-link active" href="/product">Product</a>
				<a class="nav-link " href="/brand">Brand</a>
			</div>
			<div class="right">
				<a class="nav-link" href="/logout">Log Out</a>
			</div>
		</div>
	</div>
</header>
<div class="container">
	<div class="sub-header">
		<div class="float-left sub-title">Product Management</div>	
		<div class="float-right"><a class="btn btn-success add-btn" id="addProduct"><i class="fa fa-plus-circle"></i> Add Product</a></div>
	</div>
	<div class="search">
		<div class="search-form">
			<div class="main-search">
				<input class="search-product" id="search-product" type="text" placeholder="Search..."/>
			</div>
			<div class="main-search-price">
				<label for="priceFrom">Price From:</label>
				 <select class="search-price" id="priceFrom" name="priceFrom" >
				 	<option value="">Lowest Price</option>
					<option value="1000000">1.000.000 ₫</option>
					<option value="2000000">2.000.000 ₫</option>
					<option value="3000000">3.000.000 ₫</option>
					<option value="4000000">4.000.000 ₫</option>
					<option value="5000000">5.000.000 ₫</option>
					<option value="6000000">6.000.000 ₫</option>
					<option value="7000000">7.000.000 ₫</option>
					<option value="8000000">8.000.000 ₫</option>
				</select>
				<label for="priceTo">Price To:</label>
				 <select class="search-price" id="priceTo" name="priceTo" >
				 <option value="">Highest Price</option>
					<option value="5000000">  5.000.000 ₫</option>
					<option value="10000000">10.000.000 ₫</option>
					<option value="15000000">15.000.000 ₫</option>
					<option value="20000000">20.000.000 ₫</option>
					<option value="25000000">25.000.000 ₫</option>
					<option value="30000000">30.000.000 ₫</option>
					<option value="35000000">35.000.000 ₫</option>
					<option value="500000000">50.000.000 ₫</option>
				</select>
			</div>	
			<div class="search-btn">
				<a class="btn btn-primary searchPrice-btn" id="searchPrice">Search</a>
				<a class="btn btn-dark reset-btn" id="resetPage">Reset</a>
			</div>
		</div>
			<div class="brand-item">
					<ul class="sb_dropdown"  >
	    				<c:forEach items="${brandList}" var="brand">
		    				<li class="logo-item" id="check-img">
			    				<input class="check" type="checkbox" id="${brand.brandId}" value="${brand.brandId}" name="brand.brandName" >
		    					<label for="${brand.brandId}"  class="label-logo">
		    						<img id="img-brand" src="${brand.logo}">
		    					</label>
		    				</li>	
	    				</c:forEach>  
	    				               
					</ul>
					<div id="output">
						<a class="out" href="#"></a>
					</div>
			</div>
	</div>
	
		<table class="table table-bordered" id="productInfoTable">
			<thead>
				<tr class="text-center">
					<th scope="col">No</th>
					<th scope="col">Name</th>
					<th scope="col">Quantity</th>
					<th scope="col">Price</th>
					<th scope="col" style="width: 120px">Sale Date</th>
					<th scope="col" style="width: 120px">Brand Name</th>
					<th scope="col">Image</th>
					<th scope="col">Description</th>
					<th scope="col">Action</th>
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
	<div class="modal fade" id="infoProductModal">
		<div class="modal-dialog modal-dialog-centered" role= "document">
			<div class="modal-content">
				<form id="productInfoForm" role="form" enctype="multipart/form-data">
					<div class="modal-header">
						<h4 class="modal-title">Add Product</h4>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group d-none">
							<label for="productId">Product ID </label>
							<input type="text" class="form-control" id="productId" name="productId" placeholder="Product Id" readonly/>
						</div>
						<div class="form-group">
							<label>Product Name <span class="required-mask">(*)</span></label>
							<input type="text" class="form-control" id="productName" name="productName" placeholder="Product name"/>
						</div>
						<div class="form-row">
							<div class="form-group col-md-6">
								<label>Quantity <span class="required-mask">(*)</span></label>
								<input type="text" class="form-control" id="quantity" name="quantity" placeholder="Quantity"/>
							</div>
							<div class="form-group col-md-6">
								<label>Price <span class="required-mask">(*)</span></label>
								<input type="text" class="form-control" id="price" name="price" placeholder="Price"/>
							</div>
						</div>
						<div class="form-row">
						<div class="form-group col-md-6">
							<label>Sale Date <span class="required-mask">(*)</span></label>
							<input type="date" class="form-control" id="saleDate" name="saleDate" value="2020-09-10" placeholder="saleDate"/>
						</div>
						<div class="form-group col-md-6">
							<label>Brand Name</label>
							<select class="form-control" id="brandId" name="brandEntity.brandId">
								<c:forEach items="${brandList}" var="brand">
									<option value="${brand.brandId}">${brand.brandName}</option>
								</c:forEach>
							</select>
						</div>
						</div>
						<div class="form-row">
						<div class="form-group col-md-6">
							<label>Image <span class="required-mask">(*)</span></label>
							<div class="preview-image-upload" id="logoImg">
								<img src="<c:url value='/images/image-demo.png'/>" alt="image">
							</div>
							<input type="file" class="form-control upload-image" name="imageFiles" accept="image/*"/>
							<input type="hidden" class="old-img" id="image" name="image" />
						</div>
						<div class="form-group col-md-6">
							<label for="description">Description</label>
							<textarea name="description" id="description" cols="30" rows="3" class="form-control" placeholder="Description"></textarea>
						</div>
						</div>
					</div>
					<div class="modal-footer modal-add">
						<button type="button" class="btn btn-secondary" data-dismiss="modal"> Cancel</button>
						<button type="button" class="btn btn-primary" id="saveProduct" >Save</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!--  modal delete -->
		<div class="modal fade" id="deleteModal" >
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Delete Brand</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>Do you want to delete <b class="product-name"></b>?</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary" id="deleteBtn">Delete</button>
				</div>
			</div>
		</div>
	</div>

<jsp:include page="../common/footer.jsp" />
<script src="<c:url value='/js/base.js'/>"></script>
<script src="<c:url value='/js/product.js'/>"></script>
</body>
</body>
</html>