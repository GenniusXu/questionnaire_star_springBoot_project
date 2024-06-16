package mygroup.questionnaire_star.pojo;

import lombok.Data;

@Data
public class AnswerFile {
    private Integer questionId;  // 问题ID
    private String type = "文件上传题";   //为了给前端判断而加的，直接写死就行
    private String title = "";  //题目（指总的题目，不是选项的内容）
    private String fileURL = "";   // 填空题目内容

    public AnswerFile(Integer questionId, String title, String fileURL) {
        this.questionId = questionId;
        if (title != null) {
            this.title = title;
        }
        if (fileURL != null) {
            this.fileURL = fileURL;
        }
    }
}
