<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.controller.*"
	import="de.randi2.utility.*" import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.STRATA_ANZEIGEN.toString());
			
			StrataBean aStrata = (StrataBean) request.getAttribute(StudieServlet.requestParameter.AKTUELLE_STRATA.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Strata</h1>
<fieldset>
<table style="text-align: left; width: 100%;" border="0" cellpadding="2"
	cellspacing="2">
	<tbody>
		<tr>
			<td style="width: 300px; text-align: left; vertical-align: top;">
			<table style="text-align: left; width: 100%;" border="0"
				cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3">
						<td>Name der Strata: &nbsp;</td>
						<td>Auspraegung</td>
					</tr>
					<tr class="tblrow1">
						<td>
						<h3><%=aStrata.getName()%></h3>
						</td>
						<td>
						<%
						Iterator<StrataAuspraegungBean> it = aStrata.getAuspraegungen().iterator();
						StrataAuspraegungBean temp = null;
						while(it.hasNext()){
							temp = it.next();
						%>
						<%=temp.getName()%><br>
						<%} %>
						</td>
					</tr>
				</tbody>
			</table>
			<br>
			</td>
		</tr>
		<tr>
			<td colspan="2" rowspan="1">
			<table style="text-align: left; width: 100%;" border="0"
				cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3" align="left">
						<td style="width: 50%; text-align: left;">Beschreibung</td>
					</tr>
					<tr align="left" class="tblrow1">
						<td><%=aStrata.getBeschreibung()%></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		</tbody>
		</table>
</fieldset>
<%@include file="include/inc_footer.jsp"%>
</div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>

