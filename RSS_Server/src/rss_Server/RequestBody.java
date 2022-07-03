package rss_Server;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONArray;



@XmlRootElement
public class RequestBody {
    @XmlElement String login;
    @XmlElement JSONArray add;
    @XmlElement JSONArray deleted;
}