<?xml version='1.0' encoding='UTF-8'?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">

	<jsp:output omit-xml-declaration="true" doctype-root-element="HTML"

		doctype-system="http://www.w3.org/TR/html4/loose.dtd"

		doctype-public="-W3CDTD HTML 4.01 Transitional//EN" />

	<jsp:directive.page contentType="text/html;charset=windows-1252" />

	<html>

	<head>

	<meta http-equiv="Content-Type"

		content="text/html;" />



	<title>Logging in</title>

	</head>

	<body onload="document.forms[0].submit()">

	<form method="post" action="j_spring_security_check">

		<input type="hidden" name="j_username" value="${loginPage.j_username}" /> 

		<input type="hidden" name="j_password" value="${loginPage.j_password}" />

	</form>

	</body>

	</html>

</jsp:root>