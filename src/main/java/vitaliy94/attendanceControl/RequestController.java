package vitaliy94.attendanceControl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitaliy94.attendanceControl.model.Lecturers;
import vitaliy94.attendanceControl.model.Schedule;
import vitaliy94.attendanceControl.util.AppUtil;
import vitaliy94.attendanceControl.util.HibernateUtil;

import java.util.Collection;
import java.util.List;

/**
 * Created by vitaliy on 01.02.2018.
 */

@RestController
public class RequestController
{
    @RequestMapping("/")
    public String helloWorld()
    {
        System.out.println("hello world");
        return "Hello world!";
    }

    @RequestMapping("/getLecturers")
    public ResponseEntity<String> testDb()
    {
        String responseBody="";
        Session session = HibernateUtil.getSession();
        try
        {
            Query query = session.createQuery("from Lecturers ");
            List<Lecturers> lect = query.list();
            responseBody = AppUtil.objToString(lect);

        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        } finally
        {
            session.close();
        }
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }



    @RequestMapping(value = "/post_test", method = RequestMethod.POST)
    public void postTest(@RequestBody String data)
    {
        System.out.println("POST" + data);
    }

    @RequestMapping("/getStats/lec/{id}")
    public String getLecturerStats(@PathVariable int id)
    {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from Lecturers ");
        List<Lecturers> lect = query.list();
        for(Lecturers l : lect)
        {
            System.out.println(l.getName());
            Collection<Schedule> c = l.getSchedulesById();
            for (Schedule s : c)
            {
                System.out.println(s.getRoom());
            }
        }
        return String.valueOf(id);
    }
}
