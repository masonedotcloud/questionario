<%@ page import="functionality.WebXmlParams" %>
<%@ include file="../../statics/header.jsp" %>

<%@ include file="../statics/header-bar-admin.jsp" %>

<div class="container-fluid">
    <div class="d-flex justify-content-center">
        <div class="max-500-px w-100 rounded p-3">

            <style>
                #status-radio {
                    width: 18px;
                    height: 18px;
                    border-radius: 15px;
                    position: relative;
                    content: '';
                    display: inline-block;
                    visibility: visible;
                }

                .status-radio-on {
                    background-color: #00E34B;
                }

                .status-radio-off {
                    background-color: red;
                }
            </style>
            <div class="d-flex align-items-center">
                <div id="insert-status" class="d-flex align-items-center"></div>
                <label for="status-radio" class="ms-2">Stato dei server</label>
            </div>

            <script>
                fetch('/<%= WebXmlParams.getNameServlet() %>/status', {
                    method: 'POST',
                })
                    .then(response => response.text())
                    .then(result => status(result));

                function status(string) {
                    try {
                        status = "off";
                        if (string === 'true') {
                            status = "on";
                        }
                        html = "<div id=\"status-radio\" class=\"status-radio-" + status + "\"></div>";
                        document.getElementById("insert-status").innerHTML = html;
                    } catch (error) {
                    }
                }
            </script>

            <h1>
                Benvenuti nell'area di amministrazione di Questionario!
            </h1>
            <p>
                Su questa piattaforma gli amministratori del sito possono gestire le informazioni dei
                questionari e degli utenti. Qui potrete accedere alle statistiche dei questionari completati,
                visualizzare e modificare i dati degli utenti registrati e aggiungere nuovi questionari.
            </p>
            <p>
                Grazie a questa pagina potrete controllare l'andamento dei questionari svolti dagli utenti e
                monitorare eventuali problemi tecnici.
            </p>
            <p>
                La nostra piattaforma, progettata per fornirvi uno strumento intuitivo e facile da
                utilizzare, in grado di soddisfare tutte le esigenze. Potrete accedere alla pagina di
                amministrazione inserendo le credenziali di accesso che vi sono state fornite in fase di
                registrazione come amministratori.
            </p>
            <p>
                Grazie per aver scelto Questionario come strumento per la raccolta di informazioni e dati sui
                diversi argomenti.
            </p>

        </div>
    </div>
</div>

<%@ include file="../../statics/footer.jsp" %>