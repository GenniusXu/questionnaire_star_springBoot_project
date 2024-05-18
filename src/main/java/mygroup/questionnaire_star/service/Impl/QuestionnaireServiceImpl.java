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
        questionnaireMapper.delete(id);
    }

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
}
