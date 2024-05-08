package mygroup.questionnaire_star.service.Impl;

import mygroup.questionnaire_star.mapper.AnswerMapper;
import mygroup.questionnaire_star.mapper.QuestionMapper;
import mygroup.questionnaire_star.mapper.QuestionnaireMapper;
import mygroup.questionnaire_star.pojo.Answer;
import mygroup.questionnaire_star.pojo.Question;
import mygroup.questionnaire_star.pojo.Questionnaire;
import mygroup.questionnaire_star.service.AnswerService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    QuestionnaireMapper questionnaireMapper;
    @Autowired
    QuestionMapper questionMapper;

    @Override
    public Answer add(Answer answer) {
        answerMapper.add(answer);
        return answer;
    }

    @Override
    public List<Answer> listAllByQuestionId(Integer questionId) {
        List<Answer> answers = answerMapper.listAllByQuestionId(questionId);
        return answers;
    }

    @Override
    public List<Answer> listAllByFillerId(Integer fillerId) {
        List<Answer> answers = answerMapper.listAllByFillerIdId(fillerId);
        return answers;
    }

    @Override
    public List<Answer> deleteByFillerIdAndQustionnaireId(Integer fillerId, Integer questionnaireId) {
        List<Answer> answers = answerMapper.listAllByFillerIdId(fillerId);
        List<Answer> deletedAnswers = new ArrayList<>();
        for (Answer answer : answers) {
            Integer answerId = answer.getId();
            Integer questionId = answer.getQuestionId();
            Question question = questionMapper.findQuestionById(questionId);
            if (question != null) {
                if (question.getQuestionnaireId().equals(questionId)) {
                    deletedAnswers.add(answer);
                    answerMapper.deleteByAnswerId(answerId);
                }
            }
        }
        return deletedAnswers;
    }

    @Override
    public Answer update(Answer answer) {
        answerMapper.update(answer);
        return answer;
    }

    @Override
    public List<Answer> listAllByFillerIdAndQuestionnaireId(Integer fillerId, Integer questionnaireId) {
        List<Answer> answers = answerMapper.listAllByFillerIdId(fillerId);
        List<Answer> resAnswers = new ArrayList<>();
        for (Answer answer : answers) {
            Integer answerId = answer.getId();
            Integer questionId = answer.getQuestionId();
            Question question = questionMapper.findQuestionById(questionId);
            if (question != null) {
                if (question.getQuestionnaireId().equals(questionId)) {
                    resAnswers.add(answer);
                }
            }
        }
        return resAnswers;
    }
}
