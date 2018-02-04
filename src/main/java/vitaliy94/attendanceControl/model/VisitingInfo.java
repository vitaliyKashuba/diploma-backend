package vitaliy94.attendanceControl.model;

import javax.persistence.*;

@Entity
@Table(name = "visiting_info", schema = "public", catalog = "postgres")
public class VisitingInfo {
    private int id;
    private int scheduleId;
    private int studentId;
    private Schedule scheduleByScheduleId;
    private Students studentsByStudentId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "schedule_id", nullable = false)
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Basic
    @Column(name = "student_id", nullable = false)
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisitingInfo that = (VisitingInfo) o;

        if (id != that.id) return false;
        if (scheduleId != that.scheduleId) return false;
        if (studentId != that.studentId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + scheduleId;
        result = 31 * result + studentId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public Schedule getScheduleByScheduleId() {
        return scheduleByScheduleId;
    }

    public void setScheduleByScheduleId(Schedule scheduleByScheduleId) {
        this.scheduleByScheduleId = scheduleByScheduleId;
    }

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public Students getStudentsByStudentId() {
        return studentsByStudentId;
    }

    public void setStudentsByStudentId(Students studentsByStudentId) {
        this.studentsByStudentId = studentsByStudentId;
    }
}
