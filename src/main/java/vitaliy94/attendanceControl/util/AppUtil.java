package vitaliy94.attendanceControl.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppUtil
{
    public static String objToString(Object o) throws JsonProcessingException
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
}
