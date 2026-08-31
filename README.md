# Relazione Progetto Sistemi Mobile

## Descrizione e scopo del progetto

Il progetto consiste nella creazione di un videogioco a turni vecchio stile.

Il gioco è composto da due schermate principali gestite da relativi viewmodel (MainMenu e GameScreen) a cui si aggiungono due schermate semplici senza viewmodel (LoadingScreen e EndScreen).

Il gioco consiste nello svolgimento in sequenza di una serie di combattimenti tra il personaggio del giocatore ed un singolo nemico (recuperato tramite API, vedere dopo) fino al termine degli incontri previsti.

É prevista la persistenza dei dati del giocatore tramite Room per salvare il numero di vittorie totali (su cui si basa la lunghezza della partita e gli sblocchi dei personaggi) e dei dati della sessione di gioco attiva, in modo da poter uscire e riprendere la partita in un secondo momento.

Sono state utilizzate chiamate API tramite Retrofit assieme a Moshi verso <https://www.dnd5eapi.co/> per il fetching degli avversari e delle abilità utilizzate dal giocatore, sono stati usati mapper ad-hoc come convertitori tra i dati grezzi recuperati e le classi di dominio del gioco.

## Punti di forza

- Indipendenza tra i moduli: Domain è completamente agnostico dagli altri moduli, Data è agnostico a App e Uicompose. Ciò permette totale separazione tra la logica di business, il recupero dati e la visualizzazione.
- Uso estensivo di UseCases per tutti i casi di interazioni tra membri del Domain.
- La logica del gioco è strutturata seguendo i pattern init+loop e macchina a stati per simulare un loop di gioco senza un sistema di update tick.
- É stato rispettato rigorosamente il cablaggio tra Ui, ViewModel, ViewModelFactory, RepositoryProvider, UseCaseProvider, Repository(Interface + Implementation) e UseCases (Interface + Implementation).
- Mapper tra API e domain: Usati mapper ad-hoc per Dto per permettere la maggiore compatibilità e coerenza possibile tra i dati recuperati dalla API e il Domain di gioco.
- Logica di gioco a macchina a stati con init+loop pattern: Simula un gameloop ma senza usare un update tick, utilizzando i cambi di stati per attendere e continuare la logica e ciclare il loop di gioco affiancato dal pattern init + loop per la corretta inizializzazione e svolgimento dei loop..
- Channel per integrazione nei loop: Usato kotlinx.coroutines.Channel per permettere all'input utente di essere utilizzato all'interno del loop ed evitare di dover chiudere e reinizializzare il loop ad ogni azione dell'utente.

## Possibili migliorie

- Implementazione di più feature di gioco secondarie.
- Maggiore contenuto di base e più mapper ad-hoc per permettere una maggiore integrazione con spell e monsters reperibili sulla API dnd5e.
- Polish generale UI/UX.
- Animazioni Sprite.