package repository.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Entry")
@Entity
@Table(name = "entry")
public class Entry {

    @Id
    @GeneratedValue
    @Column
    private long id;
    @Column
    private String content;
    @Column(name = "creation_date")
    private String creationDate;


    public String getContent() {
        return content;
    }

    @XmlElement
    public void setContent(String content) {
        this.content = content;
    }


    public String getCreationDate() {
        return creationDate;
    }

    @XmlElement
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
