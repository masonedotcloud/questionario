<%@ include file="../../statics/header.jsp" %>

<%@ include file="../../statics/header-bar.jsp" %>
<div class="container-fluid">
    <div class="d-flex justify-content-center">
        <div class="max-500-px w-100 p-3">
            <h1 class="mb-3 text-center">Registrati</h1>
            <form action="process" method="POST">
                <div class="form-floating mb-3">
                    <input autocomplete="off" type="text" class="form-control" id="nome" name="nome"
                           aria-describedby="Nome" required>
                    <label for="nome" class="form-label">Nome</label>
                </div>
                <div class="form-floating mb-3">
                    <input autocomplete="off" type="text" class="form-control" id="cognome" name="cognome"
                           aria-describedby="Cognome" required>
                    <label for="cognome" class="form-label">Cognome</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" autocomplete="off" name="password" class="form-control" id="password"
                           required>
                    <label for="password" class="form-label">Password</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" autocomplete="off" name="password-check" class="form-control"
                           id="password-check" required>
                    <label for="password-check" class="form-label">Password confirm</label>
                </div>
                <button type="submit" class="btn btn-primary">Registrati</button>
            </form>
        </div>
    </div>
</div>

<%@ include file="../../statics/footer.jsp" %>