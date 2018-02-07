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
    /**
     * @return list of lecturers in JSON
     */
    @RequestMapping("/getLecturers")
    public ResponseEntity getLecturers()
    {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from Lecturers ");
        return AppUtil.responseWithCORSHeader(AppUtil.GenerateJSONResponse(query.list()));
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
        Collection<Schedule> schedule = lect.getSchedulesById();
        ArrayList<Lecturers.Stats> stats = new ArrayList<>();
        for(Schedule s : schedule)
        {
            String subjName = s.getSubjectsBySubjectId().getName();
            query = session.createQuery("from VisitingInfo where scheduleId = " + s.getId());
            int studCount = query.list().size();
            query = session.createQuery("from Students where groupId = " + s.getGroupId());
            int groupSize = query.list().size();
            stats.add(new Lecturers.Stats(subjName, studCount, groupSize));
        }

        return AppUtil.responseWithCORSHeader(AppUtil.GenerateJSONResponse(stats));
    }

    /**
     * @return list of groups in JSON
     */
    @RequestMapping("/getGroups")
    public ResponseEntity getGroups()
    {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from Groups");
        return AppUtil.responseWithCORSHeader(AppUtil.GenerateJSONResponse(query.list()));
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
        return AppUtil.responseWithCORSHeader(AppUtil.GenerateJSONResponse(query.list()));
    }

    /**
     * return attendance stats
     * contains lesson, lecture name, date, pair number and fact of attendance
     * @param id student id
     * @return students stats in JSON
     */
    @RequestMapping("/getStats/stud/{id}")
    public ResponseEntity getStudentStats(@PathVariable int id)
    {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from Schedule where groupId =" +
                                                "(select groupId from Students where id = " +id + ") ");
        List<Schedule> schedule = query.list();

        query = session.createQuery("select scheduleId from VisitingInfo where studentId = " + id);
        List<Integer> attendances = query.list();

        ArrayList<Students.Stats> stats = new ArrayList<>();
        for(Schedule s : schedule)
        {
            stats.add(new Students.Stats(s.getSubjectsBySubjectId().getName(),      // subj name
                                         s.getLecturersByLecturerId().getName(),    // lecture name
                                         s.getTime().toString().substring(0, 10),   // date 'converted' to string
                                         s.getLessonNumber(),                       // number of lesson in day schedule
                                         attendances.contains(s.getId())));         // is attended
        }

        return AppUtil.responseWithCORSHeader(AppUtil.GenerateJSONResponse(stats));
    }

    public void addAttendance()
    {}

    @RequestMapping(value = "/post_test", method = RequestMethod.POST)
    public void postTest(@RequestBody String data)
    {
        System.out.println("POST" + data);
    }

}
