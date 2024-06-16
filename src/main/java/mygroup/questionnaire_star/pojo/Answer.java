package mygroup.questionnaire_star.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Answer {
    Integer id;//回答的唯一标识符
    Integer questionId;//所属问题的id
    String fillerId;//填写者的id
    List<String> selectedOptions;//选择题（包括单选和多选）专属，被选了的选项
    String fillInAnswer = "";//填空题专属，问卷填写者填写的内容
    String fileURL = "";//文件URL
}
