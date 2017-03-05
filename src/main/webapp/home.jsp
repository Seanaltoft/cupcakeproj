<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.buscupcake.Controller.userDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.mycompany.buscupcake.Modeller.Item"%>
<%@page import="com.mycompany.buscupcake.Controller.cupcakeDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Small Business - Start Bootstrap Template</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/small-business.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
    <%
//allow access only if session exists
String user = null;
if(session.getAttribute("user") == null){
	response.sendRedirect("login.html");
}else user = (String) session.getAttribute("user");
String userName = null;
String sessionID = null;
Cookie[] cookies = request.getCookies();
if(cookies !=null){
for(Cookie cookie : cookies){
	if(cookie.getName().equals("user")) userName = cookie.getValue();
	if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
}
}else{
    sessionID = session.getId();
}

%>
    <% 
    userDAO dao = new userDAO();
    Integer userid = (Integer) session.getAttribute("uid");
    int frog = dao.getUserCredit(userid);
    cupcakeDAO dao3 = new cupcakeDAO();
    List<Item> bottomslist = dao3.getBottoms();
    request.setAttribute("bottomslist", bottomslist);
    List<Item> toppingslist = dao3.getToppings();
    request.setAttribute("toppingslist", toppingslist);
    %>

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">
                    <img src="http://images.clipartpanda.com/cupcake-clip-art-eTMAA9XTn.jpeg" width="60" height="50" alt="">
                </a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="/buscupcake/home.jsp">Home</a>
                    </li>
                    <li>
                        <a href="/buscupcake/squirt">Cart</a>
                    </li>
                    <li>
                        <a href="/buscupcake/drip">Order History</a>
                    </li>
                    <li>
                        <a href="/buscupcake/balance.jsp">Get Credit!</a>
                    </li>
                    <li>
                        <a href="/buscupcake/contact.jsp">Contact</a>
                    </li>
                    <li>
                        <a> Account Balance: £<%=frog%></a>
                    </li>
                    <li>
                        <form action="LogoutServlet" method="post">
                            <input type="submit" value="Logout" >
                        </form>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Page Content -->
    <div class="container">

        <!-- Heading Row -->
        <div class="row">
            <div class="col-md-8">
                <img class="img-responsive img-rounded" src="http://heidicohen.com/wp-content/uploads/Big-Fat-Cat-.jpg" width="900" height="350" alt="">
            </div>
            <!-- /.col-md-8 -->
            <div class="col-md-4">
                
                <h1>Why is this cat more carefree than you?</h1>
                <p>He's an alleycat near our bakery, living on our premium trash and swears it's his secret to being over 100 cat years. Read our contact page to read other cats who have had similar success and to find out whether our cupcakes can you help you become a better you! </p>
                
                <a class="btn btn-primary btn-lg" href="#">Read more here!</a>
            </div>
            <!-- /.col-md-4 -->
        </div>
        <!-- /.row -->

        <hr>

        <!-- Call to Action Well -->
        <div class="row">
            <div class="col-lg-12">
                <div class="well text-center">

                    <form action="OrderServlet" method="post">
            Select Muffin Topping
            <select name="sTopping">
                <c:forEach var="item" items="${toppingslist}">
                    <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>
            Select Muffin Bottom
             <select name="sBottom">
                <c:forEach var="item" items="${bottomslist}">
                    <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>
            Amount
            <input type="number" name="sAmount" value="1">
            
        <input type="submit" value="Purchase!" >
        </form>
                </div>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        

        <!-- Content Row -->
        <div class="row">
            <div class="col-md-4">
                <h2>Available Toppings</h2>
                <table class="table table-bordered">
  <thead>
    <tr>
      <th>name</th>
      <th>price</th>
      <th>type</th>
      <th>purchase</th>
    </tr>
  </thead>
<c:forEach var="item" items="${toppingslist}">
		<tr>
			<td>${item.name}</td>
			<td>${item.price}</td>
			<td>${item.type}</td>
                        <td><form action="ViewServlet" method="post">
            <button type="submit" name= "seltopping" value="${item.id}"> Info
                            </form></td>
		</tr>
	</c:forEach></table>
                <a class="btn btn-default" href="#">More Info</a>
            </div>
            <div class="col-md-4">
                
            </div>
            <!-- /.col-md-4 -->
            <div class="col-md-4">
                <h2>Available Bottoms</h2>
                <p><table class="table table-bordered">
  <thead>
    <tr>
      <th>name</th>
      <th>price</th>
      <th>type</th>
      <th>purchase</th>
    </tr>
  </thead>
<c:forEach var="item" items="${bottomslist}">
		<tr>
			<td>${item.name}</td>
			<td>${item.price}</td>
			<td>${item.type}</td>
                        <td><form action="ViewServlet" method="post">
            <button type="submit" name= "seltopping" value="${item.id}"> Info
                            </form></td>
		</tr>
</c:forEach></table></p>
                <a class="btn btn-default" href="#">More Info</a>
            </div>
            <!-- /.col-md-4 -->
        </div>
        <!-- /.row -->

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; Sean's Website 2017</p>
                </div>
            </div>
        </footer>

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

</body>

</html>
