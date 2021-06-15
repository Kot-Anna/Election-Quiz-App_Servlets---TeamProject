<% 
	boolean isLoggedIn = false;
	if (session.getAttribute("isLoggedIn")!=null){
		isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
	}
	 
	if(isLoggedIn){
	
	}
	else{
		response.sendRedirect("/index.jsp");
	}
%>