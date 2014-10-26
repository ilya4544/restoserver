<%@ page import="domain.Visit" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="util.HibernateUtil" %>
<%@ page import="domain.Waiter" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 19.10.2014
  Time: 3:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <title>Restomania</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.css">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.js"></script>
    <script src="http://bootstrap-ru.com/204/assets/js/bootstrap-collapse.js"></script>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <img href=""/>
            <a class="navbar-brand" href="#">Restomania</a> Welcome to control panel.
        </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="#">Dashboard</a></li>
                    <li><a href="#">Settings</a></li>
                    <li><a href="#">Profile</a></li>
                    <li><a href="#">Help</a></li>
                </ul>
            </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h2 class="sub-header">Section title</h2>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Waiters name</th>
                        <th>Average rate</th>
                    </tr>
                    </thead>
                    <tbody>
    <%
        Session hibsession = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        hibsession.beginTransaction();
        List<Visit> visits = hibsession.createCriteria(Visit.class).list();
        List<Waiter> waiters = hibsession.createCriteria(Waiter.class).list();
        hibsession.getTransaction().commit();

        for(Waiter waiter : waiters) {
            ArrayList<String> comments = new ArrayList<String>();
            double rating = 0;
            long count = 0;
            for(Visit vis : visits) {
                if(waiter.getId().equals(vis.getWaiterId())) {
                    count++;
                    rating += vis.getRating();
                    if (!vis.getComment().trim().isEmpty()) {
                        comments.add(vis.getComment());
                    }
                }
            }
            if(count != 0) {
                rating = rating / count;
            }


    %>
<tr>
    <td>
        <div class="accordion" id="accordion2">
        <div class="accordion-group">
            <div class="accordion-heading">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#<%=waiter.getId()%>">
                    <%= waiter.getName() %>
                </a>
            </div>
            <div id="<%=waiter.getId()%>" class="accordion-body collapse in">
                <div class="accordion-inner">
                    <% for (String comment : comments) { %>
                    <%= comment%> <br>
                    <% } %>
                </div>
            </div>
        </div>
        </div>
    </td>
    <td>
        <%= Math.round(rating * 100.0) / 100.0 %>
    </td>
</tr>
    <%
        }

    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
