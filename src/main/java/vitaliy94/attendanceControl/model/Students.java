package vitaliy94.attendanceControl.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by vitaliy on 04.02.2018.
 */
@Entity
@Table(name = "students", schema = "public", catalog = "postgres")
public class Students {
    private int id;
    private String name;
    private String group;
    private String rfidcode;
    private Collection<VisitingInfo> visitingInfosById;

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
    @Column(name = "group", nullable = false, length = 10)
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
        if (group != null ? !group.equals(students.group) : students.group != null) return false;
        if (rfidcode != null ? !rfidcode.equals(students.rfidcode) : students.rfidcode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
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
}
