package mygroup.questionnaire_star.pojo;

import lombok.Data;

@Data
public class AnswerOption {

    private Integer questionId;  // 问题ID
    private String type = "选择题";   //为了给前端判断而加的，直接写死就行
    private String optionText;   // 选项内容
    private Integer count;       // 被选择的次数
    private Double rate;         // 选择比例

    public AnswerOption(Integer questionId, String optionText, Integer count, Double rate) {
        this.questionId = questionId;
        this.optionText = optionText;
        this.count = count;
        this.rate = rate;
    }
}
