package mygroup.questionnaire_star.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    Integer id;//问题的唯一标识符，自动递增
    Integer number;//问题在问卷中的编号
    Integer questionnaireId;//所属问卷的Id
    String type;//问题类型，“单选题”，“多选题”，“填空题”
    String title;//题目
    List<String> options;//选项
}
