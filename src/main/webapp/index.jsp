<%@ page import="domain.Visit" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="util.HibernateUtil" %>
<%@ page import="domain.Waiter" %>
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
    <title>JSP Page</title>
</head>
<body>
<form>
    <table>
        <tbody>
    <%
        Session hibsession = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        hibsession.beginTransaction();
        List<Visit> visits = hibsession.createCriteria(Visit.class).list();
        List<Waiter> waiters = hibsession.createCriteria(Waiter.class).list();



        for(Waiter item : waiters) {
            double rating = 0;
            long count = 0;
            for(Visit vis: visits) {
                if(item.getId().equals(vis.getWaiterId())) {
                    count++;
                    rating += rating;
                }
            }
            if(count!=0) {
                rating = rating/count;
            }


    %>
<tr><td>rating %></tr></td>
    <%
        }

    %>
        </tbody>
        </table>
</form>
</body>
</html>
