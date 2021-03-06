import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

class ImpLocalDateTime{
    public static void main(String[] args) {
        /*
        Important method signatures:

        public static LocalDateTime of(int year, int month,int dayOfMonth, int hour, int minute)
        
        public static LocalDateTime of(int year, int month,int dayOfMonth, int hour, int minute, int second)
        
        public static LocalDateTime of(int year, int month,int dayOfMonth, int hour, int minute, int second, int nanos)
        
        public static LocalDateTime of(int year, Month month,int dayOfMonth, int hour, int minute)
        
        public static LocalDateTime of(int year, Month month,int dayOfMonth, int hour, int minute, int second)
        
        public static LocalDateTime of(int year, Month month,int dayOfMonth, int hour, int minute, int second, int nanos)
        
        public static LocalDateTime of(LocalDate date, LocalTime time)
        */
        
        System.out.println(LocalDateTime.now()); // curr date time of the sys.

        // creating datetime object using date and time 
        LocalDate date1 = LocalDate.of(2015, Month.JANUARY, 15);
        LocalTime time1 = LocalTime.of(5, 50);

        LocalDateTime dateAnTime = LocalDateTime.of(date1, time1);
        System.out.println(dateAnTime);

        //other methods for creating datetime object
        System.out.println(LocalDateTime.of(2015, 2, 24, 12, 11));; //year, int month, dayOfMonth, hour, minute
        System.out.println(LocalDateTime.of(2015, Month.FEBRUARY, 24, 12, 11)); //year, Month month, dayOfMonth, hour, minute
        
        
    }
}