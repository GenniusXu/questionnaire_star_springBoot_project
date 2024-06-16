package mygroup.questionnaire_star.service.Impl;

import mygroup.questionnaire_star.mapper.AnswerMapper;
import mygroup.questionnaire_star.mapper.QuestionMapper;
import mygroup.questionnaire_star.mapper.QuestionnaireMapper;
import mygroup.questionnaire_star.pojo.Answer;
import mygroup.questionnaire_star.pojo.Question;
import mygroup.questionnaire_star.pojo.Questionnaire;
import mygroup.questionnaire_star.service.AnswerService;
import mygroup.questionnaire_star.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Autowired
    QuestionnaireMapper questionnaireMapper;
    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    QuestionMapper questionMapper;

    @Override
    public Questionnaire add(Questionnaire questionnaire) {
        questionnaire.setCreateTime(LocalDateTime.now());
        questionnaire.setUpdateTime(LocalDateTime.now());
        questionnaireMapper.add(questionnaire);
        return questionnaire;
    }

    @Override
    public List<Questionnaire> listAllByCreatorId(String creatorId) {
        return questionnaireMapper.listAll(creatorId);
    }

    @Override
    public Questionnaire update(Questionnaire questionnaire) {
        questionnaire.setUpdateTime(LocalDateTime.now());
        questionnaire.setCreateTime(questionnaireMapper.getQuestionnaireById(questionnaire.getId()).getCreateTime());
        questionnaireMapper.update(questionnaire);
        return questionnaire;
    }

    @Override
    public void delete(Integer id) {
        //在删除问卷前，应当把问卷中的问题也删掉
        List<Question> questions = questionMapper.listAll(id);
        for (Question question : questions) {
            questionMapper.delete(question.getId());
        }
        //删除问卷
        questionnaireMapper.delete(id);
    }

    @Override
    public List<Questionnaire> listAllByFillerId(String fillerId) {
        List<Questionnaire> questionnaires = new ArrayList<>();
        List<Answer> answers = answerMapper.listAllByFillerId(fillerId);
        Set<Integer> questionnaireIds = new HashSet<>();
        for (Answer answer : answers) {
            Integer questionId = answer.getQuestionId();
            Question question = questionMapper.findQuestionById(questionId);
            if (question != null) {
                Integer questionnaireId = question.getQuestionnaireId();
                questionnaireIds.add(questionnaireId);
            }
        }
        for (Integer questionnaireId : questionnaireIds) {
            Questionnaire questionnaire = questionnaireMapper.getQuestionnaireById(questionnaireId);
            questionnaires.add(questionnaire);
        }
        return questionnaires;
    }

    @Override
    public Questionnaire getById(Integer id) {
        return questionnaireMapper.getQuestionnaireById(id);
    }

    @Override
    public Questionnaire getQuestionnaireById(Integer questionnaireid) {
        Questionnaire questionnaire = questionnaireMapper.getQuestionnaireById(questionnaireid);
        return questionnaire;
    }

    @Override
    public List<Questionnaire> listAllByManagerId(String managerId) {
        List<Questionnaire> questionnairesWithManagers = questionnaireMapper.ListAllQuestionnairesWithManagers();
        List<Questionnaire> resQuestionnaires = new ArrayList<>();
        for (Questionnaire questionnaire : questionnairesWithManagers) {
            List<String> managersId = questionnaire.getManagersId();
            if (managersId.contains(managerId)) {
                resQuestionnaires.add(questionnaire);
            }
        }
        return resQuestionnaires;
    }

    //
    @Override
    public Boolean user_enter(String requesterId,Integer questionnaireId) {
        String editingId = questionnaireMapper.getQuestionnaireById(questionnaireId).getEditingId();
        if(editingId==null||editingId.equals(requesterId)){
            questionnaireMapper.setEditingId(requesterId,questionnaireId);
            questionnaireMapper.setLastEntryTime(questionnaireId,LocalDateTime.now());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Boolean user_exit(String requesterId,Integer questionnaireId) {
        String editingId = questionnaireMapper.getQuestionnaireById(questionnaireId).getEditingId();
        if(editingId==null){
            return false;
        }
        else {
            questionnaireMapper.setEditingId(null,questionnaireId);
            return true;
        }
    }

    @Override
    public void checkEditor() {
        {
            // 定义半小时的分钟数
            long twoHoursInMinutes = 30;

            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            List<Questionnaire>questionnaireList = questionnaireMapper.getAllQuestionnaire();
            for (Questionnaire questionnaire : questionnaireList) {
                // 检查编辑时间是否超过半小时
                if (questionnaire.getLastEntryTime() != null &&
                        questionnaire.getEditingId()!=null&&!questionnaire.getEditingId().isEmpty() && !questionnaire.getEditingId().equals("!")) {
                    if (ChronoUnit.MINUTES.between(questionnaire.getLastEntryTime(), now) >= twoHoursInMinutes) {
                        // 更新正在编辑用户为空
                        questionnaireMapper.setEditingId(null,questionnaire.getId());
                    }
                }
            }
        }
    }


}
