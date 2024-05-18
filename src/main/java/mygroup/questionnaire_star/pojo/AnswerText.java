package mygroup.questionnaire_star.pojo;

import lombok.Data;

@Data
public class AnswerText {
    private Integer questionId;  // 问题ID
    private String text;   // 选项内容
    public AnswerText(Integer questionId,String text){
        this.questionId=questionId;
        this.text=text;
    }
}
