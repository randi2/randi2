<!-- 
 Logout page.

 Made by lplotni@users.sourceforge.org  

 This file is part of RANDI2.
 RANDI2 is free software: you can redistribute it and or modify it under the
 terms of the GNU General Public License as published by the Free Software
 Foundation, either version 3 of the License, or (at your option) any later
 version.
 RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with
 RANDI2. If not, see http://www.gnu.org/licenses/.
 -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%session.invalidate(); %>
<c:redirect url="/index.html"/>
