<jsp:useBean id="user" class="login.ParseUser" scope="session"/>
<jsp:setProperty name="user" property="*"/> 
<div id="header"><img src="images/dkfz_logo.gif" width="337"
	height="63" title="" alt=""></div>

<div id="breadcrumb">
<table width="100%" height="25" border="0" cellSpacing="2"
	class="breadcrumb_tbl">
	<tr>
		<td><span id="studie_highlight">Aktuelle Studie $NAME</span> &gt;
		$AKTUELLE_ANSICHT</td>
		<%-- Name nur zu Demozwecken --%>
		<td align="right">Herr <%= user.getUsername() %>&nbsp;<% if (!user.getUsername().equals("sa")) { %>(<font color="red">$rolle</font>)<% }%>
		:: <a href="index.html" id="logout_link">Logout</a>&nbsp;&nbsp;&nbsp;</td>
		<td><a href="nachrichtendienst.jsp"><img  src="images/message.gif" border="0"
			alt="Nachricht senden" title="Nachricht senden" width="22"
			height="22"></a>&nbsp;<a href="hilfe.jsp"><img src="images/help.gif" border="0"
			alt="Hilfe" title="Hilfe" width="22" height="22"></a></td>
	</tr>
</table>
</div>