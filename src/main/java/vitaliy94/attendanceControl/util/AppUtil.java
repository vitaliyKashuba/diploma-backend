package vitaliy94.attendanceControl.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AppUtil
{
    static String objToString(Object o) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }

    private static HttpHeaders getCORSHeader()
    {
        HttpHeaders h = new HttpHeaders();  //ad-hocked because of cross-domain request. fix it later
        h.add("Access-Control-Allow-Origin", "*");
        return h;
    }

    public static ResponseEntity responseWithCORSHeader(String responseBody)
    {
        return new ResponseEntity(responseBody, getCORSHeader(), HttpStatus.OK);
    }

    /**
     * dont know how is possible to catch JsonProcessingException
     * extract this to simplify code and make it DRY
     */
    public static String GenerateJSONResponse(Object o)
    {
        String responseBody="";
        try
        {
            responseBody = objToString(o);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return responseBody;
    }

    /**
     * return number of pair
     * 8.15 +- p mins -> 1
     * 9.50 +- p mins -> 2
     *
     * @param range range in minutes
     * @link schedule.sumdu.edu.ua
     * @return lesson number or 0 if time is invalid
     */
    public static int getPairNumber(OffsetTime time, int range)
    {
        ArrayList<OffsetTime> lessons = new ArrayList<>();

        lessons.add(timeCreator(8, 15));
        lessons.add(timeCreator(9,50));
        lessons.add(timeCreator(11,25));
        lessons.add(timeCreator(13,25));
        lessons.add(timeCreator(15,0));

        for(int i = 0; i < lessons.size(); i++)
        {
            if (inRange(time, lessons.get(i), range))
            {
                return i++;
            }
        }

        return 0;
    }

    /**
     * use it to simplify time creating and avoid UTC param bugs when creating time like ZoneOffset.now()
     */
    public static OffsetTime timeCreator(int hours, int min)
    {
        return OffsetTime.of(hours, min, 0, 0, ZoneOffset.UTC);
    }

    /**
     * @return true if attTime is in range of range minutes from lessonTime
     */
    static boolean inRange(OffsetTime attTime, OffsetTime lessonTime, int range)
    {
        return attTime.isBefore(lessonTime.plusMinutes(range)) && attTime.isAfter(lessonTime.minusMinutes(range));
    }

    /**
     * remove '\r' and '\n' from arduino message
     */
    public static String removeControlCharacters(String s)
    {
        return s.replace("\r", "").replace("\n", "");
    }
}
