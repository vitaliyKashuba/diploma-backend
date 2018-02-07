package vitaliy94.attendanceControl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Table(name = "students", schema = "public", catalog = "postgres")
public class Students {
    private int id;
    private String name;
    private String rfidcode;
    @JsonIgnore
    private Collection<VisitingInfo> visitingInfosById;
    private Integer groupId;

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

    @Basic
    @Column(name = "rfidcode", nullable = true, length = 50)
    public String getRfidcode() {
        return rfidcode;
    }

    public void setRfidcode(String rfidcode) {
        this.rfidcode = rfidcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Students students = (Students) o;

        if (id != students.id) return false;
        if (name != null ? !name.equals(students.name) : students.name != null) return false;
        if (groupId != null ? !groupId.equals(students.groupId) : students.groupId != null) return false;
        if (rfidcode != null ? !rfidcode.equals(students.rfidcode) : students.rfidcode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (rfidcode != null ? rfidcode.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "studentsByStudentId")
    public Collection<VisitingInfo> getVisitingInfosById() {
        return visitingInfosById;
    }

    public void setVisitingInfosById(Collection<VisitingInfo> visitingInfosById) {
        this.visitingInfosById = visitingInfosById;
    }

    @Basic
    @Column(name = "group_id", nullable = true)
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * attendance stats for serializing
     * getters and setters need for jackson serializer
     */
    public static class Stats
    {
        String subject, lecturer, date;
        int lessonNumber;
        boolean attendance;

        public Stats(String subject, String lecturer, String date, int lessonNumber, boolean attendance) {
            this.subject = subject;
            this.lecturer = lecturer;
            this.date = date;
            this.lessonNumber = lessonNumber;
            this.attendance = attendance;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getLecturer() {
            return lecturer;
        }

        public void setLecturer(String lecturer) {
            this.lecturer = lecturer;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getLessonNumber() {
            return lessonNumber;
        }

        public void setLessonNumber(int lessonNumber) {
            this.lessonNumber = lessonNumber;
        }

        public boolean isAttendance() {
            return attendance;
        }

        public void setAttendance(boolean attendance) {
            this.attendance = attendance;
        }

        @Override
        public String toString() {
            return "Stats{" +
                    "subject='" + subject + '\'' +
                    ", lecturer='" + lecturer + '\'' +
                    ", date='" + date + '\'' +
                    ", lessonNumber=" + lessonNumber +
                    ", attendance=" + attendance +
                    '}';
        }
    }
}
