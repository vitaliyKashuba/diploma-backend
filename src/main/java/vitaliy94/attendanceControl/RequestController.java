package vitaliy94.attendanceControl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
