<%@ include file="statics/header.jsp" %>

<%@ include file="statics/header-bar.jsp" %>
<div class="container-fluid">
    <div class="d-flex justify-content-center">
        <div class="max-500-px w-100 p-3">
            <%= request.getAttribute("msg_stato") %>
        </div>
    </div>
</div>
<%@ include file="statics/footer.jsp" %>