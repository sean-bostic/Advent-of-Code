package days

object DayRegistry {
    private val days: Map<Int, Day> = mapOf(
        1 to Day01(),
        2 to Day02(),
        3 to Day03(),
        4 to Day04(),
        5 to Day05(),
        6 to Day06(),
        7 to Day07(),
        8 to Day08(),
    )

    fun getDay(dayNumber: Int): Day? = days[dayNumber]

//    fun getImplementedDays(): List<Int> = days.keys.sorted()

    fun isDayImplemented(dayNumber: Int): Boolean = dayNumber in days

    fun getAllDayNumbers(): List<Int> = (1..25).toList()

    fun getCompletedStars(): Int = days.size
}
