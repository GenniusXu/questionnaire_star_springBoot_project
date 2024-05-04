package mygroup.questionnaire_star.service.Impl;

import mygroup.questionnaire_star.mapper.QuestionMapper;
import mygroup.questionnaire_star.pojo.Question;
import mygroup.questionnaire_star.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    @Override
    public List<Question> listAll(Integer questionnaireId) {
        return questionMapper.listAll(questionnaireId);
    }

    @Override
    public void add(Question question) {
        questionMapper.add(question);
    }

    @Override
    public void delete(Integer questionId) {
        questionMapper.delete(questionId);
    }

    @Override
    public void update(Question question) {
        questionMapper.update(question);
    }
}
