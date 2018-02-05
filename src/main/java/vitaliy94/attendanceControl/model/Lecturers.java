package vitaliy94.attendanceControl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "lecturers", schema = "public", catalog = "postgres")
public class Lecturers {
    private int id;
    private String name;
    @JsonIgnore
    private Collection<Schedule> schedulesById;
    private String photo;

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

    @Basic
    @Column(name = "photo", nullable = true, length = 150)
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * class for collecting stats
     */
    public static class Stats
    {
        String subjName;
        int attendance = 0;
        int actualGroupSize;

        public Stats(String subjName, int attendance, int actualGroupSize)
        {
            this.subjName = subjName;
            this.attendance = attendance;
            this.actualGroupSize = actualGroupSize;
        }

        public String getSubjName() {
            return subjName;
        }

        public void setSubjName(String subjName) {
            this.subjName = subjName;
        }

        public int getAttendance() {
            return attendance;
        }

        public void setAttendance(int attendance) {
            this.attendance = attendance;
        }

        public int getActualGroupSize() {
            return actualGroupSize;
        }

        public void setActualGroupSize(int actualGroupSize) {
            this.actualGroupSize = actualGroupSize;
        }
    }
}
