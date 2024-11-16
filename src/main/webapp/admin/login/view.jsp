<%@ include file="../../statics/header.jsp" %>
<%@ include file="../../statics/header-bar-admin.jsp" %>
<div class="container d-flex justify-content-center">
    <div class="rounded border p-3" style="width: 400px;">
        <h1 class="mb-3 text-center">Accedi</h1>
        <form action="process" method="POST">
            <div class="form-floating mb-3">
                <input autocomplete="off" type="text" class="form-control" id="username" name="username"
                       aria-describedby="Username" required>
                <label for="username" class="form-label">Username</label>
            </div>
            <div class="form-floating mb-3">
                <input type="password" autocomplete="off" name="password" class="form-control" id="password"
                       required>
                <label for="password" class="form-label">Password</label>
            </div>
            <button type="submit" class="btn btn-primary">Accedi</button>
        </form>

    </div>


</div>

<%@ include file="../../statics/footer.jsp" %>
