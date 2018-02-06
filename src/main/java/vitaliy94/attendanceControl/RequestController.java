package vitaliy94.attendanceControl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitaliy94.attendanceControl.model.Lecturers;
import vitaliy94.attendanceControl.model.Schedule;
import vitaliy94.attendanceControl.model.Students;
import vitaliy94.attendanceControl.util.AppUtil;
import vitaliy94.attendanceControl.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class RequestController
{
    @RequestMapping("/")
    public String helloWorld()
    {
        System.out.println("hello world");
        return "Hello world!";
    }

    /**
     * @return list of lecturers in JSON
     */
    @RequestMapping("/getLecturers")
    public ResponseEntity getLecturers()
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
        return AppUtil.responseWithCORSHeader(responseBody);
    }

    /**
     * query lecturer DB for stats
     * return it like [{subjName, attendance, actualGroupSize},...]
     *
     * @param id lecturer id
     * @return lecturer stats in JSON
     */
    @RequestMapping("/getStats/lec/{id}")
    public ResponseEntity getLecturerStats(@PathVariable int id)
    {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from Lecturers where id=" + id);
        Lecturers lect = (Lecturers) query.list().get(0);
        System.out.println(lect.getName());
        Collection<Schedule> schedule = lect.getSchedulesById();
        ArrayList<Lecturers.Stats> stats = new ArrayList<>();
        for(Schedule s : schedule)
        {
            String subjName = s.getSubjectsBySubjectId().getName();
            Query q = session.createQuery("from VisitingInfo where scheduleId = " + s.getId());
            int studCount = q.list().size();
            Query q2 = session.createQuery("from Students where groupId = " + s.getGroupId());
            int groupSize = q2.list().size();
            stats.add(new Lecturers.Stats(subjName, studCount, groupSize));
        }
        String responseBody="";
        try
        {
            responseBody = AppUtil.objToString(stats);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return AppUtil.responseWithCORSHeader(responseBody);
    }

    /**
     * @return list of groups in JSON
     */
    @RequestMapping("/getGroups")
    public ResponseEntity getGroups()
    {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from Groups");
        String responseBody="";
        try
        {
            responseBody = AppUtil.objToString(query.list());
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return AppUtil.responseWithCORSHeader(responseBody);
    }

    /**
     * @param id group id
     * @return list of group in JSON
     */
    @RequestMapping("/getStudents/{id}")
    public ResponseEntity getStudents(@PathVariable int id)
    {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from Students where groupId = " + id);
        String responseBody="";
        try
        {
            responseBody = AppUtil.objToString(query.list());
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return AppUtil.responseWithCORSHeader(responseBody);
    }

    @RequestMapping("/getStats/stud/{id}")
    public ResponseEntity<String> getStudentStats(@PathVariable int id)
    {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from Students where id=" + id);
        Students stud = (Students) query.list().get(0);



        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping(value = "/post_test", method = RequestMethod.POST)
    public void postTest(@RequestBody String data)
    {
        System.out.println("POST" + data);
    }

}
