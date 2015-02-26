<%@ page import="domain.Visit" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="util.HibernateUtil" %>
<%@ page import="domain.Waiter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
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
    <script src="//cdn.datatables.net/plug-ins/380cb78f450/integration/bootstrap/3/dataTables.bootstrap.js"></script>
    <link rel="stylesheet" href="//cdn.datatables.net/plug-ins/380cb78f450/integration/bootstrap/3/dataTables.bootstrap.css">
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
            <div>
                <table id="example" class="table table-striped table-bordered dataTable no-footer" role="grid">
                    <thead>
                    <tr role="row">
                        <th>Waiters name</th>
                        <th>Average rate</th>
                    </tr>
                    </thead>
                    <tbody>
    <%
        Session mSession = HibernateUtil.getSessionFactory().openSession();
        List<Visit> visits;
        List<Waiter> waiters;
        try {
            mSession.beginTransaction();
            visits = mSession.createCriteria(Visit.class).list();
            waiters = mSession.createCriteria(Waiter.class).list();
            mSession.getTransaction().commit();
        } catch (Exception e) {
            mSession.getTransaction().rollback();
            throw e;
        } finally {
            mSession.close();
        }
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
<tr role="row">
    <td>
        <%= waiter.getName() %>
        <% for (String s : comments) {
            out.println("");
            out.println(s);
        }%>

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
