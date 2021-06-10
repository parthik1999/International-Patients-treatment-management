<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>International Patients Management System</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Pinyon+Script&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="/css/style-table.css">
<link rel="stylesheet" href="/css/style-admin.css">
</head>
<body>
	<div class="main-container-register">
		<%@ include file="fragments/header.jsp"%>
		<div class="section grid">
			<%@ include file="admin-fragments/admin-sidebar.jsp"%>
			<div class="content list-container">
				<h1>Our In-patient Services</h1>
				<div class="container">
					<form:form action="/portal/addSpecialist" method="GET"
						modelAttribute="specialistDetails">
					<!-- 	<div class="form-group">
							<form:label path="specialistId">Your Id:</form:label>
							<form:input path="specialistId" class="form-control" id="specialistId"
								 required="required" pattern="[0-9]"
								data-error="Please enter a valid Id." />
						</div>     -->
						<div class="form-group">
							<form:label path="name">Your Name:</form:label>
							<form:input path="name" class="form-control" id="name"
								type="text" required="required" pattern="[a-zA-Z]+([\s][a-zA-Z]*)*"
								data-error="Please enter a valid Name." />
						</div>
						<div class="form-group">
							<form:label path="areaOfExpertise">Area of Expertise:</form:label>
							<form:select path="areaOfExpertise" class="form-control" id="areaOfExpertise"
								items="${ailmentList}" required="required" />
						</div>
						<div class="form-group">
							<form:label path="experienceInYears">Your Experience in Years:</form:label>
							<form:input path="experienceInYears" class="form-control"
								id="experienceInYears" max="75" min="1" data-error="Please enter a valid Name."
								required="required" />
								<form:errors path="experienceInYears" />
						</div>
						<div class="form-group">
							<form:label path="contactNumber">Contact Number:</form:label>
							<form:input path="contactNumber" class="form-control"
								id="contactNumber" pattern="[0-9]{10}"
								required="required" 
								data-error="Contact Number should be of 10 digits."/>
						</div>
						<form:button class="btn">Register</form:button>
						<input type="reset" class="btn btn-reset" value="Reset"/> 
					 
						
					</form:form>
				</div>
				<div class="container result-container center border">
					<h3>Registration result</h3>
					<div id="resultBox">
						<c:choose>
							<c:when test="${not empty error}">
								<div class="error">${error}</div>
							</c:when>
							<c:otherwise>

									<c:choose>
										
									<c:when test ="${not empty specialistDetails and specialDetails.contactNumber>10}">
									
									<table class="table table-striped">
											<thead>
												<tr>
												
													<th>Name</th>
													<th>Area of Expertise</th>
													<th>experience (year)</th>
													<th>Contact Number</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													
													<td>${specialistDetails.name}</td>
													<td>${specialistDetails.areaOfExpertise}</td>
													<td>${specialistDetails.experienceInYears}</td>
													<td>${specialistDetails.contactNumber}</td>
												</tr>
											</tbody>
										</table>
									
												
									</c:when>
									</c:choose>
						
						</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="fragments/footer.jsp"%>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="/js/script.js"></script>
</body>
</html>