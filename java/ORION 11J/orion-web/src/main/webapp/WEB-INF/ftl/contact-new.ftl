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

	<form method="post" action="add.html" id="contact">
		<table>
			<tr>
				<td><label path="firstname">
						<@spring.message code="label.firstname" />
					</label>
				</td>
				<td><input id="firstname" name="firstname" type="text" value=""/>
				</td>
			</tr> 
			<tr>
				<td><label path="lastname">
						<@spring.message code="label.lastname" />
					</label>
				</td>
				<td><input id="lastname" name="lastname" type="text" value=""/>
				</td>
			</tr>
			<tr>
				<td><label path="email">
						<@spring.message code="label.email" />
					</label>
				</td>
				<td><input id="email" name="email" type="text" value=""/>
				</td>
			</tr>
			<tr>
				<td><label path="telephone">
						<@spring.message code="label.telephone" />
					</label>
				</td>
				<td><input id="telephone" name="telephone" type="text" value=""/>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="<@spring.message code="label.addcontact"/>" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
