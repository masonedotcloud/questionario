<%@ include file="../statics/header.jsp" %>

<%@ include file="../statics/header-bar.jsp" %>
<div class="container-fluid">
    <div class="d-flex justify-content-center">
        <div class="max-500-px w-100 p-3">
            <h1 class="mb-3 text-center">Profilo</h1>
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="session" value="<%= session.getId() %>" readonly>
                <label for="session">ID Sessione</label>
            </div>
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="name" value="<%= session.getAttribute("nome") %>" readonly>
                <label for="name">Nome</label>
            </div>
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="cognome" value="<%= session.getAttribute("cognome") %>"
                       readonly>
                <label for="name">Cognome</label>
            </div>
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="access" value="<%= session.getAttribute("accesso") %>"
                       readonly>
                <label for="name">Ultimo accesso</label>
            </div>
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="access" value="<%= session.getAttribute("accessi") %>"
                       readonly>
                <label for="name">Accessi totali</label>
            </div>
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="questionnaire"
                       value="<%= session.getAttribute("questionari") %>" readonly>
                <label for="name">Questionari totali</label>
            </div>
            <a href="/<%= WebXmlParams.getNameServlet() %>/profile/questionnaire/list" type="button" class="mb-3 w-100 btn btn-primary">Archivio
                questionari</a>
            <div class="text-end">
                <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal"
                        data-bs-target="#deleteModal">
                    <i class="bi bi-trash-fill"></i>
                    Elimina il profilo
                </button>
            </div>

        </div>
    </div>
</div>

<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="deleteModalLabel">Sei sicuro?</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Elimina il profilo, un azione irreversibile, pensaci due volte, anzi tre. Paragonabile a un
                licenziamento in azienda, non si torna indietro!!!
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                <form action="delete" method="post">
                    <button type="submit" class="btn btn-danger">Conferma</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="../statics/footer.jsp" %>