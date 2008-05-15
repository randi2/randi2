
<%@page import="de.randi2test.utility.Config"%>
<%@page import="de.randi2test.utility.Parameter"%>
<%@page import="de.randi2.controller.DispatcherServlet"%>
<div id="header"><img
	src="<%=Config.getProperty(Config.Felder.RELEASE_BILD_LOGO) %>"
	width="337" height="63" title="" alt=""></div>

<div id="breadcrumb">
<table class="breadcrumb_tbl" width="100%" summary="Impressum">
	<tr>
		<td align="right" width="100%"><a href="DispatcherServlet"
			id="logout_link"><img src="images/home.gif" width="22"
			height="22" border="0" title="Zurueck zum Login"
			alt="Zurueck zum Login"></a></td>
		<td align="right" valign="middle">
		<form action="DispatcherServlet" method="POST" name="impressum_form"
			id="impressum_form"><input type="hidden"
			name="<%=Parameter.anfrage_id %>" value=""></form>
		<span id="logout_link" style="cursor:pointer"
			onClick="document.forms['impressum_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_HEADER_IMPRESSUM.name() %>';document.forms['impressum_form'].submit();">
		Impressum</span></td>
	</tr>
</table>
</div>
