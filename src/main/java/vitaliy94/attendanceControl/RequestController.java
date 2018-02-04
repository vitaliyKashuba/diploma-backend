package vitaliy94.attendanceControl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitaliy94.attendanceControl.model.Lecturers;
import vitaliy94.attendanceControl.model.Schedule;
import vitaliy94.attendanceControl.model.VisitingInfo;
import vitaliy94.attendanceControl.util.AppUtil;
import vitaliy94.attendanceControl.util.HibernateUtil;

import java.util.Collection;
import java.util.HashMap;
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
    public ResponseEntity<String> getLecturers()
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

    @RequestMapping("/getStats/lec/{id}")
    public String getLecturerStats(@PathVariable int id)
    {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from Lecturers where id=" + id);
        Lecturers lect = (Lecturers) query.list().get(0);
        System.out.println(lect.getName());
        Collection<Schedule> schedule = lect.getSchedulesById();
        HashMap<String, Integer> stats = new HashMap<String, Integer>();
        for(Schedule s : schedule)
        {
            String subjName = s.getSubjectsBySubjectId().getName();
            Query q = session.createQuery("from VisitingInfo where scheduleId = " + s.getId());
            int studCount = q.list().size();
            if(stats.containsKey(subjName))
            {
                stats.replace(subjName, stats.get(subjName)+studCount);
            }
            else
            {
                stats.put(subjName, studCount);
            }
        }
        for(String k : stats.keySet())
        {
            System.out.println(k + " " + stats.get(k));
        }
        return String.valueOf(id);
    }

    @RequestMapping(value = "/post_test", method = RequestMethod.POST)
    public void postTest(@RequestBody String data)
    {
        System.out.println("POST" + data);
    }

}
