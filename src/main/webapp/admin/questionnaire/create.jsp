<%@ include file="../../statics/header.jsp" %>
<%@ include file="../../statics/header-bar-admin.jsp" %>
<div class="container d-flex justify-content-center">
    <div class="rounded border p-3" style="width: 500px;">
        <h1 class="mb-3 text-center">Genera questionario</h1>

        <form id="form-total">
            <div class="mb-3">
                <label for="tipoDomanda" class="form-label">Tipo di domanda</label>
                <select id="tipoDomanda" class="form-select" name="tipoDomanda">
                    <option value="text">Testo</option>
                    <option value="select">Selezione</option>
                    <option value="radio">Scelta singola</option>
                    <option value="checkbox">Scelta multipla</option>
                    <option value="textarea">Area di testo</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="testoDomanda" class="form-label">Titolo domanda</label>
                <input type="text" name="testoDomanda" class="form-control" id="testoDomanda">
            </div>

            <div class="form-check mb-3">
                <input class="form-check-input" type="checkbox" name="obbligatorio" id="obbligatorio">
                <label class="form-check-label" for="obbligatorio">
                    Obbligatorio
                </label>
            </div>
            <div class="mb-3 d-flex">
                <button type="sumbmit" class="btn btn-primary me-1">Aggiungi domanda</button>
                <button type="button" id="aggiungiRisposta" class="btn btn-primary">Aggiungi risposta</button>

            </div>


            <div id="risposte">

            </div>


        </form>

        <form id="form-content" action="create" method="POST">
            <div class="mb-3">
                <label for="titolo" class="form-label">Titolo questionario</label>
                <input type="text" class="form-control" id="titolo" name="titolo">
            </div>
            <div class="mb-3">
                <label for="jsonOutput" class="form-label">Risultato JSON</label>
                <textarea name="jsonOutput" class="form-control" id="jsonOutput" rows="6" readonly></textarea>
            </div>
            </br>

        </form>

        <div class="mb-3">

            <button type="button" onclick="document.querySelector('#form-content').submit();" class="btn btn-primary">
                Invia
            </button>
            <button type="button" onclick="viewpreview()" class="btn btn-primary">Anteprima</button>

        </div>


        <script>

            const form = document.querySelector('#form-total');
            const tipoDomanda = document.querySelector('#tipoDomanda');
            const testoDomanda = document.querySelector('#testoDomanda');
            const jsonOutput = document.querySelector('#jsonOutput');
            const risposteContainer = document.querySelector('#risposte');
            const aggiungiRispostaButton = document.querySelector('#aggiungiRisposta');
            const checkbox = document.querySelector('#obbligatorio');

            let risposteCount = 0;

            function mostraRisposte() {
                const tipo = tipoDomanda.value;
                if (tipo === 'select' || tipo === 'radio' || tipo === 'checkbox') {
                    risposteContainer.style.display = 'block';
                    aggiungiRispostaButton.style.display = 'block';
                    risposteContainer.innerHTML = "";
                } else {
                    aggiungiRispostaButton.style.display = 'none';
                    risposteContainer.style.display = 'none';
                    risposteContainer.innerHTML = "";
                    risposteCount = 0;
                }
            }

            function aggiungiRisposta() {
                risposteCount++;

                const rispostaContainer = document.createElement('div');
                const label = document.createElement('label');
                const input = document.createElement('input');
                const rimuoviButton = document.createElement('button');

                label.setAttribute('for', `risposta${risposteCount}`);
                label.innerText = `Risposta ${risposteCount}`;
                input.setAttribute('type', 'text');
                input.setAttribute('name', `risposta${risposteCount}`);
                input.setAttribute('id', `risposta${risposteCount}`);
                input.setAttribute('class', 'form-control mb-1')
                rimuoviButton.setAttribute('type', 'button');
                rimuoviButton.setAttribute('class', 'btn btn-danger btn-sm');
                rimuoviButton.innerText = 'Rimuovi';
                rimuoviButton.addEventListener('click', () => {
                    rispostaContainer.remove();
                });

                rispostaContainer.appendChild(label);
                rispostaContainer.appendChild(input);
                rispostaContainer.appendChild(rimuoviButton);

                risposteContainer.appendChild(rispostaContainer);

            }


            function creaDomanda(event) {
                event.preventDefault();

                const tipo = tipoDomanda.value;
                const testo = testoDomanda.value.trim();
                const risposte = [];

                if (!testo) {
                    alert('Inserisci il testo della domanda.');
                    return;
                }

                if (tipo === 'select' || tipo === 'radio' || tipo === 'checkbox') {
                    const inputRisposte = risposteContainer.querySelectorAll('input');
                    for (let i = 0; i < inputRisposte.length; i++) {
                        const testoRisposta = inputRisposte[i].value.trim();
                        if (!testoRisposta) {
                            alert('Inserisci il testo di tutte le risposte.');
                            return;
                        }
                        risposte.push(testoRisposta);
                    }
                    if (tipo == 'select') {
                        if (risposte.length < 2) {
                            alert('Inserisci almeno due risposte.');
                            return;
                        }
                    } else {
                        if (risposte.length < 1) {
                            alert('Inserisci almeno una risposta.');
                            return;
                        }
                    }

                }

                if (tipo === 'select' || tipo === 'radio' || tipo === 'checkbox') {
                    const domanda = {
                        tipo,
                        testo,
                        risposte,
                        required: checkbox.checked,
                    };

                    if (jsonOutput.value == '') {
                        jsonOutput.value += JSON.stringify(domanda);
                    } else {
                        jsonOutput.value += ',' + JSON.stringify(domanda);
                    }

                } else {
                    const domanda = {
                        tipo,
                        testo,
                        required: checkbox.checked,
                    };

                    if (jsonOutput.value == '') {
                        jsonOutput.value += JSON.stringify(domanda);
                    } else {
                        jsonOutput.value += ',' + JSON.stringify(domanda);
                    }
                }


                testoDomanda.value = '';
                tipoDomanda.selectedIndex = 0;
                risposteCount = 0;
                checkbox.checked = false;
                mostraRisposte();

            }

            tipoDomanda.addEventListener('change', mostraRisposte);

            aggiungiRispostaButton.addEventListener('click', aggiungiRisposta);

            form.addEventListener('submit', creaDomanda);


            window.addEventListener('load', function () {
                mostraRisposte();
            });

        </script>


        <script>
            function viewpreview() {
                // Seleziona il form e l'elemento di input del titolo e il textarea per i dati JSON
                var form = document.querySelector('form');
                var titoloInput = document.querySelector('#titolo');
                var jsonOutputTextarea = document.querySelector('#jsonOutput');

                // Aggiungi un gestore per la sottomissione del modulo

                // Impedisci l'invio del modulo
                event.preventDefault();

                // Crea un nuovo oggetto XMLHttpRequest
                var xhr = new XMLHttpRequest();

                // Imposta il metodo della richiesta e l'URL di destinazione
                xhr.open('POST', 'preview');

                // Imposta l'intestazione della richiesta
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

                // Crea una stringa contenente i dati da inviare
                var data = 'titolo=' + encodeURIComponent(titoloInput.value) + '&jsonOutput=' + encodeURIComponent(jsonOutputTextarea.value);

                // Invia la richiesta POST con i dati come dati
                xhr.send(data);

                // Crea una nuova finestra popup e imposta la risposta come contenuto della finestra
                var popup = window.open('', 'popup', 'width=1000,height=800');
                popup.document.title = 'Titolo della finestra popup'
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        popup.document.write(xhr.responseText);
                    }
                };


            }
        </script>

    </div>
</div>


<%@ include file="../../statics/footer.jsp" %>