package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        var result = year - other.year
        if (result == 0) result = month - other.month
        if (result == 0) result = dayOfMonth - other.dayOfMonth
        return result
    }

    infix operator fun plus(interval: TimeInterval): MyDate {
        return addTimeIntervals(interval, 1)
    }
    infix operator fun plus(interval: MultipleTimeInterval): MyDate {
        return addTimeIntervals(interval.timeInterval, interval.times)
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    infix operator fun times(i: Int): MultipleTimeInterval {
        return MultipleTimeInterval(this, i)
    }
}

class MultipleTimeInterval(val timeInterval: TimeInterval, val times: Int)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var currentDate: MyDate = start
            override fun hasNext(): Boolean {
                return currentDate in this@DateRange
            }

            override fun next(): MyDate {
                val result = currentDate
                currentDate = currentDate.nextDay()
                return result
            }
        }
    }
//    operator fun contains(d: MyDate): Boolean = start <= d && d <= endInclusive
}
