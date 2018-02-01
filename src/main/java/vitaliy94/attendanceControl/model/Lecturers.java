package vitaliy94.attendanceControl.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by vitaliy on 01.02.2018.
 */
@Entity
@Table(name = "lecturers", schema = "public", catalog = "postgres")
public class Lecturers {
    private int id;
    private String name;
    private Collection<Schedule> schedulesById;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lecturers lecturers = (Lecturers) o;

        if (id != lecturers.id) return false;
        if (name != null ? !name.equals(lecturers.name) : lecturers.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "lecturersByLecturerId")
    public Collection<Schedule> getSchedulesById() {
        return schedulesById;
    }

    public void setSchedulesById(Collection<Schedule> schedulesById) {
        this.schedulesById = schedulesById;
    }
}
