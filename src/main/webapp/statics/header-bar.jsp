<%@ page import="functionality.WebXmlParams" %>
<header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
    <a href="/<%=WebXmlParams.getNameServlet()%>"
       class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
        <i style="font-size: 1.7rem;" class="text-primary bi bi-bezier2 ms-3 me-2"></i>
        <span class="fs-4 text-primary">Questionario</span>
    </a>
    <ul class="nav nav-pills">
        <li class="nav-item"><a href="/<%=WebXmlParams.getNameServlet()%>" class="nav-link"><span
                class="fs-6">Home</span></a></li>
        <% if (session != null && session.getAttribute("ID") != null) { %>
        <li class="nav-item"><a href="/<%=WebXmlParams.getNameServlet()%>/profile/view"
                                class="nav-link text-primary"><span class="fs-6">Profilo</span></a></li>
        <li class="nav-item"><a href="/<%=WebXmlParams.getNameServlet()%>/questionnaire/list" class="nav-link"><span
                class="fs-6">Compila</span></a></li>
        <li class="nav-item">
            <form action="/<%=WebXmlParams.getNameServlet()%>/profile/logout" method="post" id="logout-form">
                <button type="submit" class="nav-link cursor-pointer">Logout</button>
            </form>
        </li>
        <% } else { %>
        <li class="nav-item"><a href="/<%=WebXmlParams.getNameServlet()%>/form/login/view" class="nav-link"><span
                class="fs-6">Accedi</span></a></li>
        <li class="nav-item"><a href="/<%=WebXmlParams.getNameServlet()%>/form/register/view" class="nav-link"><span
                class="fs-6">Registrati</span></a></li>
        <li class="nav-item"><a href="/<%=WebXmlParams.getNameServlet()%>/questionnaire/list" class="nav-link"><span
                class="fs-6">Compila</span></a></li>
        <% } %>
    </ul>
</header>