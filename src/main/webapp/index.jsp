<%@ page import="functionality.WebXmlParams" %>
<%@ include file="statics/header.jsp" %>

<%@ include file="../statics/header-bar.jsp" %>
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
                Benvenuti su Questionario!
            </h1>
            <p>
                Il nostro sito ti permette di compilare questionari su diversi argomenti e di visualizzare
                quelli completati. Puoi accedere al sito inserendo il tuo nome, il tuo cognome e la tua
                password, oppure puoi registrarti se sei un nuovo utente.
            </p>
            <p>
                Se preferisci rimanere anonimo, puoi compilare i questionari in modo anonimo senza dover
                effettuare l'accesso. I tuoi dati personali verranno protetti in ogni caso.
            </p>
            <p>
                Potrai scegliere tra diversi questionari su vari argomenti, come ad esempio la salute,
                l'ambiente o la tecnologia. In ogni questionario dovrai rispondere a una serie di
                domande, che ti aiuteranno a scoprire qualcosa sull'argomento in questione.
            </p>
            <p>
                Cosa aspetti? Registrati o accedi al sito per iniziare a compilare i tuoi questionari e a
                scoprire i temi che ti interessano!
            </p>

        </div>
    </div>
</div>
<%@ include file="statics/footer.jsp" %>