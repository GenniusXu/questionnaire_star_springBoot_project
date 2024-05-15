package mygroup.questionnaire_star.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//PS：本来是想叫Template的，但很多多JDK包中都有叫Template的类，避免重名，所以改名叫QTemplate
@Data
public class QTemplate {
    Integer id; //模板id
    String name; //模板名称
    List<Question> questions = new ArrayList<>(); //模板中包含的问题列表
}
