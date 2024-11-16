<%@ page import="functionality.WebXmlParams" %>
<header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
    <a href="/<%=WebXmlParams.getNameServlet()%>/admin/"
       class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
        <i style="font-size: 1.7rem;" class=" bi bi-bezier2 ms-3 me-2"></i>
        <span class="fs-4">Questionario admin</span>
    </a>
    <ul class="nav nav-pills">
        <li class="nav-item"><a href="/<%=WebXmlParams.getNameServlet()%>/admin/" class="nav-link">Home</a></li>
        <% if (session != null && session.getAttribute("ADMIN") != null) { %>
        <li class="nav-item"><a href="/<%=WebXmlParams.getNameServlet()%>/admin/dashboard"
                                class="nav-link">Dashboard</a></li>
        <li class="nav-item"><a href="/<%=WebXmlParams.getNameServlet()%>/admin/questionnaire/create" class="nav-link">Crea</a>
        </li>
        <li class="nav-item"><a href="/<%=WebXmlParams.getNameServlet()%>/admin/questionnaire/" class="nav-link">Questionari</a>
        </li>
        <li class="nav-item">
            <form action="/<%=WebXmlParams.getNameServlet()%>/admin/logout" method="post" id="logout-form">
                <button type="submit" class="nav-link cursor-pointer">Logout</button>
            </form>
        </li>
        <% } else { %>
        <li class="nav-item"><a href="/<%=WebXmlParams.getNameServlet()%>/admin/login/view" class="nav-link">Accedi</a>
        </li>
        <% } %>
    </ul>
</header>