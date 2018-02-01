package vitaliy94.attendanceControl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
