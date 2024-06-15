package mygroup.questionnaire_star.component;

import mygroup.questionnaire_star.mapper.QuestionnaireMapper;
import mygroup.questionnaire_star.pojo.Questionnaire;
import org.jetbrains.annotations.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class TimeEventHandler {
    @Autowired
    QuestionnaireMapper questionnaireMapper;
    @Scheduled(cron = "0 */2 * * * ?") // 每2min执行一次
    public void checkEditor(){

        // 定义半小时的分钟数
        long twoHoursInMinutes = 30;

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        List<Questionnaire>questionnaireList = questionnaireMapper.getAllQuestionnaire();
        for (Questionnaire questionnaire : questionnaireList) {
            // 检查编辑时间是否超过半小时
            if (questionnaire.getLastEntryTime() != null &&
                    !questionnaire.getEditingId().isEmpty() && !questionnaire.getEditingId().equals("!")) {
                if (ChronoUnit.MINUTES.between(questionnaire.getLastEntryTime(), now) >= twoHoursInMinutes) {
                    // 更新正在编辑用户为空
                    questionnaireMapper.setEditingId(null,questionnaire.getId());
                }
            }
        }
    }
}
