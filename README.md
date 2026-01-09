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

- YouTube-demo:
- APK: https://github.com/Tuukkaleksi/Week1/releases/tag/week1
