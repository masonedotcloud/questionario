# Questionario

Benvenuto nel repository del progetto **Questionario**. Questo sistema consente agli utenti di compilare questionari online e agli amministratori di gestire utenti e questionari. 


## Caratteristiche principali
- **Compilazione dei questionari**: Gli utenti possono rispondere a domande di vario tipo (checkbox, textarea, testo, radio, select) sia in modo anonimo che registrandosi.
- **Gestione degli utenti**: Possibilità di visualizzare profili e storici dei questionari.
- **Dark Mode**: Per un'esperienza utente ottimizzata in condizioni di scarsa luminosità.
- **Compatibilità multi-dispositivo**: Design responsivo per accesso ottimale su desktop, tablet e smartphone.

## Requisiti di sistema
- **Browser supportati**:
  - Google Chrome (v80+)
  - Mozilla Firefox (v72+)
  - Safari (v13+)
  - Microsoft Edge (v80+)
- **Sistema operativo**:
  - Windows 10
  - macOS 10.13+
  - Ubuntu 18.04+
- **Connessione Internet**: 1 Mbps (download), 0.5 Mbps (upload)
- **Server**:
  - Tomcat con supporto per file WAR
  - MySQL o MariaDB per il database

## Installazione
### Database
1. Accedi a `phpMyAdmin` o un altro gestore di database.
2. Crea un nuovo database e importa il file `.sql` per popolare le tabelle richieste.

### Deploy dell'applicazione
1. Scarica il file `Questionario.war`.
2. Carica il file WAR nel server Tomcat tramite la console di gestione.
3. Modifica il file `web.xml` per configurare i parametri:
   - **DB_URL**: URL del database.
   - **DB_NAME**: Nome del database.
   - **USER/PASS**: Credenziali del database.
   - **ADMIN_USER/ADMIN_PASS**: Credenziali amministrative.
4. Avvia l'applicazione e verifica che sia accessibile all'URL configurato.

## Utilizzo
### Per gli utenti
1. **Registrazione e accesso**:
   - Compila i campi richiesti e accedi al tuo profilo.
2. **Compilazione questionari**:
   - Visualizza i questionari disponibili e rispondi alle domande.

### Per gli amministratori
1. **Gestione utenti**:
   - Elimina o aggiorna gli utenti tramite la dashboard.
2. **Creazione questionari**:
   - Configura domande personalizzate e imposta opzioni obbligatorie o facoltative.
3. **Configurazione applicazione**:
   - Personalizza `web.xml` per cambiare parametri di connessione o preferenze.

## Documentazione
Per maggiori dettagli, consulta i seguenti manuali inclusi nel repository:
- [Manuale Utente](./Manuale%20utente%20Questionario.pdf)
- [Manuale Amministrazione](./Manuale%20amministrazione%20Questionario.pdf)
- [Relazione Tecnica](./Relazione.pdf)

## Licenza

Questo progetto è distribuito sotto la Licenza MIT - vedi il file [LICENSE](LICENSE) per ulteriori dettagli.


## Autore

Questo progetto è stato creato da [alessandromasone](https://github.com/alessandromasone).