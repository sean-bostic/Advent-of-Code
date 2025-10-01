package days

object DayRegistry {
    private val days: Map<Int, Day> = mapOf(
        1 to Day01(),
        2 to Day02(),
        3 to Day03(),
    )

    fun getDay(dayNumber: Int): Day? = days[dayNumber]

    fun getImplementedDays(): List<Int> = days.keys.sorted()

    fun isDayImplemented(dayNumber: Int): Boolean = dayNumber in days

    fun getAllDayNumbers(): List<Int> = (1..25).toList()
}