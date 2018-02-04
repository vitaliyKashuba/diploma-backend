package vitaliy94.attendanceControl.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "schedule", schema = "public", catalog = "postgres")
public class Schedule {
    private int id;
    private Timestamp time;
    private int subjectId;
    private int lecturerId;
    private String room;
    private Subjects subjectsBySubjectId;
    private Lecturers lecturersByLecturerId;
    private Collection<VisitingInfo> visitingInfosById;
    private Integer groupId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "time", nullable = true)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "subject_id", nullable = false)
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Basic
    @Column(name = "lecturer_id", nullable = false)
    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    @Basic
    @Column(name = "room", nullable = true, length = 10)
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (id != schedule.id) return false;
        if (subjectId != schedule.subjectId) return false;
        if (lecturerId != schedule.lecturerId) return false;
        if (time != null ? !time.equals(schedule.time) : schedule.time != null) return false;
        if (room != null ? !room.equals(schedule.room) : schedule.room != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + subjectId;
        result = 31 * result + lecturerId;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public Subjects getSubjectsBySubjectId() {
        return subjectsBySubjectId;
    }

    public void setSubjectsBySubjectId(Subjects subjectsBySubjectId) {
        this.subjectsBySubjectId = subjectsBySubjectId;
    }

    @ManyToOne
    @JoinColumn(name = "lecturer_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public Lecturers getLecturersByLecturerId() {
        return lecturersByLecturerId;
    }

    public void setLecturersByLecturerId(Lecturers lecturersByLecturerId) {
        this.lecturersByLecturerId = lecturersByLecturerId;
    }

    @OneToMany(mappedBy = "scheduleByScheduleId")
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
}
