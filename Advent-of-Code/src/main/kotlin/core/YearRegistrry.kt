package core

data class YearInfo(
    val year: Int,
    val days: List<Day>,
)

object YearRegistry {
    private val years = mutableListOf<YearInfo>()

    fun register(
        year: Int,
        days: List<Day>,
    ) {
        years.add(YearInfo(year, days.sortedBy { it.dayNumber }))
    }

    fun getAllYears(): List<YearInfo> = years.sortedByDescending { it.year }

    fun getYearData(year: Int): YearInfo? = years.find { it.year == year }

    fun getDay(
        year: Int,
        dayNumber: Int,
    ): Day? {
        return years.find { it.year == year }?.days?.find { it.dayNumber == dayNumber }
    }
}
