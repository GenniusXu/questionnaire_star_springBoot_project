package mygroup.questionnaire_star.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Questionnaire {
    Integer id;//问卷的唯一标识符，自动递增
    String creatorId;//问卷创建者的Id
    String title;//问卷名称
    List<String> managersId;//管理员的Id（拥有编辑权限）
    String status;//问卷状态，“草稿”，“已发布”，“已结束”
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updateTime;//更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startTime;//开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endTime;//结束时间

}
