<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Spring 3 MVC Series - Contact Manager</title>
<link rel="stylesheet" href="<spring:theme code="css"/>" type="text/css" />

</head>
<body>

	<span style="float: right"> <a href="?lang=en">en</a> | <a
		href="?lang=de">de</a> </span>


	<span style="float: left"> <a href="?theme=default">def</a> | <a
		href="?theme=black">blk</a> | <a href="?theme=blue">blu</a> </span>
	<br />

	<form:form method="post" action="add.html" commandName="contact">

		<table>
			<tr>
				<td><form:label path="firstname">
						<spring:message code="label.firstname" />
					</form:label>
				</td>
				<td><form:input path="firstname" />
				</td>
			</tr>
			<tr>
				<td><form:label path="lastname">
						<spring:message code="label.lastname" />
					</form:label>
				</td>
				<td><form:input path="lastname" />
				</td>
			</tr>
			<tr>
				<td><form:label path="email">
						<spring:message code="label.email" />
					</form:label>
				</td>
				<td><form:input path="email" />
				</td>
			</tr>
			<tr>
				<td><form:label path="telephone">
						<spring:message code="label.telephone" />
					</form:label>
				</td>
				<td><form:input path="telephone" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="<spring:message code="label.addcontact"/>" /></td>
			</tr>
		</table>
	</form:form>

	<h3>Contacts</h3>
	<c:if test="${!empty contactList}">
		<table class="data" cellpadding="0" cellspacing="0"
			style="border: 1px solid #ccc;">
			<tr>
				<th>Name</th>
				<th>Email</th>
				<th>Telephone</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach items="${contactList}" var="contact">
				<tr>
					<td>${contact.lastname}, ${contact.firstname}</td>
					<td>${contact.email}</td>
					<td>${contact.telephone}</td>
					<td><a href="delete/${contact.id}">delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>


        <h1>Please upload a file</h1>
        <form method="post" action="upload.form" enctype="multipart/form-data">
            <input type="file" name="file"/>
            <input type="submit"/>
        </form>
</body>
</html>
