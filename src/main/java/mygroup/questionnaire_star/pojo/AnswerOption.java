package mygroup.questionnaire_star.pojo;

import lombok.Data;

@Data
public class AnswerOption {

    private Integer questionId;  // 问题ID
    private String type = "选择题";   //为了给前端判断而加的，直接写死就行
    private String title = "";  //题目（指总的题目，不是选项的内容）
    private String optionText = "";   // 选项内容
    private Integer count;       // 被选择的次数
    private Double rate;         // 选择比例

    public AnswerOption(Integer questionId, String title, String optionText, Integer count, Double rate) {
        this.questionId = questionId;
        if (title != null) {
            this.title = title;
        }
        if (optionText != null) {
            this.optionText = optionText;
        }
        this.count = count;
        this.rate = rate;
    }
}
