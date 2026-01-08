package com.example.week1.domain

// Simulointi tietokantaa (mockData)
object TaskRepository {
    val mockTasks = listOf(
        Task(1, "Käy kaupassa", "Osta eväät viikolle", 1, "08-01-2026", true),
        Task(2, "Tee kotitehtävät", "Sunnuntaille!", 2, "11-01-2026", false),
        Task(3, "Siivoa", "Imuroi asunto", 3, "20-01-2026", false),

        Task(4, "Lenkki", "30 min juoksu", 4, "23-01-2026", false),
        Task(5, "Opiskele", "Description 5", 5, "24-01-2026", false)
    )
}