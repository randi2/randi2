<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"%>

<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ZENTRUM_ANSEHEN.toString());
	long id = Long.parseLong((String) request
			.getAttribute(Parameter.zentrum.ZENTRUM_ID.toString()));
	ZentrumBean aZentrum = Zentrum.getZentrum(id);
	PersonBean aPerson = aZentrum.getAnsprechpartner();
	
	
	String aInstitut = aZentrum.getInstitution();
	String aAbteilung = aZentrum.getAbteilung();
	String aPLZ = aZentrum.getPlz();
	String aOrt = aZentrum.getOrt();
	String aStrasse = aZentrum.getStrasse();
	String aNummer = aZentrum.getHausnr();
	
	

	String aVorname = aPerson.getVorname();
	String aNachname = aPerson.getNachname();
	String aTitel = aPerson.getTitel().toString();
	String aTelefonnummer = aPerson.getTelefonnummer();
	String aHandynummer = aPerson.getHandynummer();
	String aFax = aPerson.getFax();
	String aEmail = aPerson.getEmail();
	

	if(aVorname==null) {
		
		aVorname = "";
		
	}
	if(aNachname==null) {
		
		aNachname = "";
		
	}

	if (aTitel == null || aTitel == "kein Titel") {

		aTitel = "";

	} 


	if (aTelefonnummer == null) {

		aTelefonnummer ="nicht vorhanden";

	} 

	if (aHandynummer == null) {

		aHandynummer = "nicht vorhanden";
	}

	if (aFax == null) {

		aFax = "nicht vorhanden";
	}

	

	if(aEmail==null){
		aEmail="nicht vorhanden";
	}
	
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>

<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Zentrum anzeigen</h1>

<table style="text-align: left; width: 70%;" border="0"
 cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td>
      <table style="text-align: left; width: 100%;" border="0"
 cellpadding="2" cellspacing="2">
        <tbody>
          <tr class="tblrow3">
            <td colspan="2" rowspan="1"><big><big>Institut</big></big></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td><big><big><%=aInstitut %></big></big></td>
          </tr>
          <tr class="tblrow3">
            <td colspan="2" rowspan="1"><big><big>Abteilung</big></big></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td><big><%=aAbteilung %></big></td>
          </tr>
        </tbody>
      </table>
      </td>
    </tr>
    <tr>
      <td style="width: 100%;vertical-align: top;"><fieldset><legend><b>Kontaktdaten</b></legend>
     <table style="text-align: left; width: 100%;" border="0"
 cellpadding="2" cellspacing="2">
        <tbody> <%
	if (aPerson != null) {
	%>
          <tr>
            <td style="width: 50%;">      
            
            <table style="text-align: left; width: 100%;"
 border="0" cellpadding="2" cellspacing="2">
              <tbody>
                
                <tr class="tblrow3">
                  <td colspan="2" rowspan="1"><big>&nbsp;Ansprechpartner</big><br>
                  </td>
                </tr>
                <tr>
                  <td></td>
                  <td style="text-align: center"><%=aTitel %>&nbsp; <%=aVorname %> &nbsp;<%=aNachname %></td>
                </tr>
                <tr class="tblrow3">
                  <td colspan="2" rowspan="1">eMail<br>
                  </td>
                </tr>
                <tr>
                  <td></td>
                  <td style="text-align: center"><%=aEmail %></td>
                </tr>
                <tr>
                  <td colspan="2" rowspan="1">
                  <table style="text-align: left; width: 100%;"
 border="0" cellpadding="2" cellspacing="2">
                    <tbody>
                      <tr class="tblrow3">
                        <td>&nbsp;Telefonnummer</td>
                        <td>&nbsp;Faxnummer</td>
                      </tr>
                      <tr>
                        <td><%=aTelefonnummer %></td>
                        <td><%=aFax %></td>
                      </tr>
                      <tr class="tblrow3">
                        <td>Handynummer</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td><%=aHandynummer %></td>
                        <td>&nbsp;</td>
                      </tr><%
	} else {
	%>
	<tr style="text-align: center;">
		<td>
		<h2>Kein Ansprechpartner vorhanden</h2>
		</td>
	</tr>
	<%
	}
	%>
                    </tbody>
                  </table>
                  </td>
                </tr>
              </tbody>
            </table>
            <br>
            </td>
            <td style="vertical-align: top;">
            <table style="text-align: left; width: 100%;"
 border="0" cellpadding="2" cellspacing="2">
              <tbody>
                <tr>
                  <td style="text-align: center;" colspan="2"
 rowspan="1"></td>
                </tr>
                <tr class="tblrow3">
                  <td colspan="2" rowspan="1"><big>&nbsp;Anschrift des Zentrums</big></td>
                </tr>
                <tr class="tblrow3">
                  <td colspan="2" rowspan="1">&nbsp;Stra&szlig;e</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><%=aStrasse %>&nbsp;<%=aNummer %></td>
                </tr>
                <tr class="tblrow3">
                  <td colspan="2" rowspan="1">&nbsp;PLZ und Ort </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><%=aPLZ %>&nbsp;<%=aOrt %></td>
                </tr>
              </tbody>
            </table>
            <br>
            </td>
          </tr>
        </tbody>
      </table></fieldset>
      </td>
    </tr>
  </tbody>
</table>





<br>
<br>


<%@include file="include/inc_footer.jsp"%></div>


<%@include file="include/inc_menue.jsp"%>

</body>
</html>
