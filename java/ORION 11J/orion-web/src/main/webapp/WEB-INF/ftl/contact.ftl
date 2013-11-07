<#import "/spring.ftl" as spring />
<html>
<head>
<title>Spring 3 MVC Series - Contact Manager</title>
<link rel="stylesheet" href="<@spring.theme code="css"/>" type="text/css" />

</head>
<body>

	<span style="float: right"> <a href="?lang=en">en</a> | <a
		href="?lang=de">de</a> </span>


	<span style="float: left"> <a href="?theme=default">def</a> | <a
		href="?theme=black">blk</a> | <a href="?theme=blue">blu</a> </span>
	<br />
	<h3>Contacts</h3>
		<table class="data" cellpadding="0" cellspacing="0"
			style="border: 1px solid #ccc;">
			<tr>
				<th>Name</th>
				<th>Email</th>
				<th>Telephone</th>
				<th>&nbsp;</th>
			</tr>
			<#list contactList as contact>
				<tr>
					<td>${contact.lastname}, ${contact.firstname}</td>
					<td>${contact.email}</td>
					<td>${contact.telephone}</td>
					<td><a href="contact/${contact.id}?_method=delete">delete</a>
					</td>
				</tr>
			</#list>
		</table>
</body>
</html>
