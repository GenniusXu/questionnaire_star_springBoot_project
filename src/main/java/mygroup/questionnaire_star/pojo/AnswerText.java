package mygroup.questionnaire_star.pojo;

import lombok.Data;

@Data
public class AnswerText {
    private Integer questionId;  // 问题ID
    private String type = "填空题";   //为了给前端判断而加的，直接写死就行
    private String title = "";  //题目（指总的题目，不是选项的内容）
    private String text = "";   // 填空题目内容

    public AnswerText(Integer questionId, String title, String text) {
        this.questionId = questionId;
        if (title != null) {
            this.title = title;
        }
        if (text != null) {
            this.text = text;
        }
    }
}
