package mygroup.questionnaire_star.pojo;

import lombok.Data;

@Data
public class AnswerText {
    private Integer questionId;  // 问题ID
    private String type = "填空题";   //为了给前端判断而加的，直接写死就行
    private String text;   // 填空题目内容
    public AnswerText(Integer questionId,String text){
        this.questionId=questionId;
        this.text=text;
    }
}
