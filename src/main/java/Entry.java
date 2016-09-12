import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Entry")
public class Entry {

    private String content;
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
}
