# Week 1

## Mitä tehtiin
- Luotiin Compose-template projekti.
- Tehtiin domain-malli Task (id, title, description, priority, dueDate, done).
- Lisättiin 5–10 mock-tehtävää listaan.
- Tehtiin puhtaat Kotlin-funktiot listan käsittelyyn:
  - addTask
  - toggleDone
  - filterByDone
  - sortByDueDate
- Tehtiin HomeScreen Compose-näkymä, joka näyttää otsikon ja mock-listan.

## Datamalli
Task kuvaa yhden tehtävän. dueDate on String muodossa.

## Kotlin funktiot lyhyesti
- addTask: palauttaa uuden listan, jossa uusi task on lisättynä.
- toggleDone: vaihtaa annetun id:n taskin done-tilaa (true/false) ja palauttaa uuden listan.
- filterByDone: palauttaa vain tehdyt tai tekemättömät tehtävät.
- sortByDueDate: järjestää tehtävät dueDate:n mukaan.

<hr></hr>

- YouTube-demo: https://www.youtube.com/watch?v=FU7NobRc-8c
- APK: https://github.com/Tuukkaleksi/Week1/releases/tag/week1

<hr></hr>

# Week 2
## Compose-tila
- Jetpack Compose on deklaratiivinen UI: ruutu piirretään tilan (state) perusteella.
- Kun state muuttuu, Compose tekee automaattisesti recompositionin ja päivittää UI:n ilman, että meidän tarvitsee käsin päivittää näkymää.

## Miksi ViewModel
- ViewModel myös mahdollistaa myöhemmin datalähteen vaihtamisen.
- remember säilyttää tilan vain Composablen elinkaaren ajan, ja tila voi hävitä.
- ViewModel säilyttää tilan paremmin elinkaaren yli, ja erottaa UI:n ja sovelluslogiikan.

<hr></hr>

- YouTube-demo: https://www.youtube.com/watch?v=QrSL9Pz6kcQ
- APK: https://github.com/Tuukkaleksi/Week1/releases/tag/week2

<hr></hr>

# Week 3
## MVVM
- Model on sovelluksen data
- View on UI
- ViewModel on UI:n logiikka

## Miksi MVVM
- Logiikkaa ei sekoiteta Composen sisään joka on helpompi ylläpitää

## Miten StateFlow toimii?
- Kun arvo muuttuu, se emittoi uuden arvon kaikille listenereille
- Compose muuntaa sen stateksi collectAsState() funktiolla

## Kerrosrakenne
- model/ -> dataluokat
- viewmodel/ -> tilanhallinta
- view/ -> UI

# Week 4
## Navigointi Jetpack Composessa
- Navigointi toteutetaan Single Activity mallilla: sovelluksessa on yksi Activity ja useita Composable ruutuja.
- Ruutujen välinen siirtyminen tapahtuu Navigation-Compose kirjaston avulla.

## NavController ja NavHost
- NavController vastaa navigoinnista ja back stackista (navigate, popBackStack).
- NavHost määrittelee sovelluksen reitit ja kertoo, mikä Composable näytetään kussakin reitissä.

## Navigaatiorakenne
- Sovelluksessa on kaksi ruutua:
    - HomeScreen (tehtävälista)
    - CalendarScreen (kalenterinäkymä)
- Siirtyminen Home -> Calendar tapahtuu painikkeella.
- Paluu tapahtuu joko back napilla tai popBackStack() kutsulla.

## MVVM + navigointi
- Sama TaskViewModel jaetaan HomeScreenin ja CalendarScreenin välillä.
- ViewModel luodaan NavHostin tasolla, joten sitä ei luoda uudelleen navigoinnin aikana.
- Molemmat ruudut lukevat saman StateFlow tilan collectAsState() funktion avulla.

## CalendarScreen
- Tehtävät ryhmitellään dueDate kentän perusteella.
- Jokainen päivämäärä näytetään otsikkona ja sen alle kuuluvat tehtävät listana.
- Näkymä havainnollistaa tehtävien sijoittumista eri päiville.

## AlertDialog
- Tehtävien lisäys ja muokkaus toteutetaan AlertDialogeilla.
- addTask ja editTask eivät ole omia navigaatiokohteitaan.
- Dialogit kutsuvat suoraan ViewModelin funktioita.
