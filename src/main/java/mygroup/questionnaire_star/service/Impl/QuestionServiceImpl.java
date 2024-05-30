package mygroup.questionnaire_star.service.Impl;

import mygroup.questionnaire_star.mapper.QuestionMapper;
import mygroup.questionnaire_star.mapper.QuestionnaireMapper;
import mygroup.questionnaire_star.pojo.Question;
import mygroup.questionnaire_star.pojo.Questionnaire;
import mygroup.questionnaire_star.service.QuestionService;
import mygroup.questionnaire_star.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionnaireMapper questionnaireMapper;

    @Override
    public List<Question> listAll(Integer questionnaireId) {
        return questionMapper.listAll(questionnaireId);
    }

    @Override
    public Question add(Question question) {
        questionMapper.add(question);
        return question;
    }

    @Override
    public void delete(Integer id) {
        questionMapper.delete(id);
    }

    @Override
    public Question update(Question question) {

        questionMapper.update(question);
        return question;
    }

    //获取类型
    @Override
    public String getType(Integer questionId) {
        return questionMapper.getType(questionId);
    }

    @Override
    public Questionnaire getQuestionnaire(Question question) {
        return questionnaireMapper.getQuestionnaireById(question.getQuestionnaireId());
    }

    @Override
    public Question getQuestionById(Integer questionId) {
        Question question = questionMapper.findQuestionById(questionId);
        return question;
    }
}
