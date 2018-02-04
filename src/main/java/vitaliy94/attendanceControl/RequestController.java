package vitaliy94.attendanceControl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.web.bind.annotation.*;
import vitaliy94.attendanceControl.model.Lecturers;
import vitaliy94.attendanceControl.util.HibernateUtil;

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

    @RequestMapping("/test_db")
    public String testDb()
    {
        Session session = HibernateUtil.getSession();
        StringBuilder sb = new StringBuilder();
        try
        {
            Query query = session.createQuery("from Lecturers ");
            List<Lecturers> lect = query.list();
            for(Lecturers l : lect)
            {
             sb.append(l.getName()).append("\n");
            }
        } finally
        {
            session.close();
        }
        return sb.toString();
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
        }
        return String.valueOf(id);
    }
}
