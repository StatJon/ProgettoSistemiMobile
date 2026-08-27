# Relazione Progetto Sistemi Mobile

## Descrizione e scopo del progetto

Il progetto consiste nella creazione di un videogioco a turni vecchio stile.
Il gioco è composto da due schermate principali gestite da relativi viewmodel (MainMenu e GameScreen), ad essi si aggiungono due schermate semplici senza viewmodel (LoadingScreen e EndScreen).
Il gioco consiste nello svolgimento in sequenza di combattimenti tra il personaggio del giocatore ed un singolo nemico (recuperato tramite API, vedere dopo) fino al termine degli incontri previsti.
É prevista la persistenza dei dati del giocatore tramite Room per salvare il numero di vittorie totali (su cui si basa la lunghezza della partita e gli sblocchi dei personaggi) e dei dati della sessione di gioco attiva, in modo da poter uscire e riprendere la partita in un secondo momento.
Sono state utilizzate chiamate API tramite Retrofit assieme a Moshi verso <https://www.dnd5eapi.co/> per il fetching degli avversari e delle abilità utilizzate dal giocatore, sono stati usati mapper ad-hoc come convertitori tra i dati grezzi recuperati e le classi di dominio del gioco.

## Punti di forza

- Indipendenza tra i moduli: Domain è completamente agnostico dagli altri moduli, Data è agnostico a app e uicompose. Ciò permette totale separazione tra la logica di business, il recupero dati e la visualizzazione.
- Uso estensivo di UseCases per permettere migliore mantenibilità seguendo best practice.
- É stato rispettato rigorosamente il cablaggio tra Ui, ViewModel, ViewModelFactory, RepositoryProvider, UseCaseProvider, Repository(Interface + Implementation) e UseCases (Interface + Implementation).
- Mapper tra API e domain: Usati mapper ad-hoc per Dto per permettere la maggiore compatibilità possibile dei dati recuperati dalla API.
- GameLoop a macchina a stati: Simula un gameloop tradizionale ma senza refresh periodico nel tempo, utilizzando i cambi di stati per attendere e continuare la logica e ciclare il loop di gioco.

## Possibili migliorie

- Polish generale UI.
- Implementazione di più feature di gioco secondarie.
