package raven.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Raven
 */
public class DateCalculator {

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public DateCalculator(Date dateStart, Date dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public DateCalculator() {
    }

    private Date dateStart;
    private Date dateEnd;

    public String getTextSearch() {
        ModelDate start = new ModelDate(dateStart);
        ModelDate end = new ModelDate(dateEnd);
        String date;
        if (start.year != end.year) {
            date = start.toString() + " - " + end.toString();
        } else if (start.month != end.month) {
            date = start.toStringNoYear() + " - " + end.toString();
        } else if (start.getDay() != end.getDay()) {
            date = start.toStringNoYear() + " - " + end.toStringNoMonth();
        } else {
            date = start.toString();
        }
        return date;
    }

    public long getDifferenceDays() {
        long diff = dateEnd.getTime() - dateStart.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    private class ModelDate {

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public ModelDate(Date date) {
            this.date = date;
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            day = cal.get(Calendar.DATE);
            month = cal.get(Calendar.MONTH) + 1;
            year = cal.get(Calendar.YEAR);
        }

        private Date date;
        private int day;
        private int month;
        private int year;

        @Override
        public String toString() {
            SimpleDateFormat df = new SimpleDateFormat("dd MMM, yyyy");
            return df.format(date);
        }

        public String toStringNoYear() {
            SimpleDateFormat df = new SimpleDateFormat("dd MMM");
            return df.format(date);
        }

        public String toStringNoMonth() {
            SimpleDateFormat df = new SimpleDateFormat("dd, yyyy");
            return df.format(date);
        }
    }
}
