package mygroup.questionnaire_star.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    //@JsonProperty("id")
    Integer id; // 问题的唯一标识符，自动递增

    //@JsonProperty("number")
    Integer number; // 问题在问卷中的编号

    //@JsonProperty("questionnaireId")
    Integer questionnaireId; // 所属问卷的Id

    //@JsonProperty("type")
    String type; // 问题类型，“单选题”，“多选题”，“填空题”

    //@JsonProperty("title")
    String title; // 题目

    //@JsonProperty("options")
    List<String> options; // 选项
}
