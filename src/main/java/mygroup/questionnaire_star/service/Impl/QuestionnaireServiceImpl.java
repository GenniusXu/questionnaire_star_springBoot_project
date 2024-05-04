package mygroup.questionnaire_star.service.Impl;

import mygroup.questionnaire_star.mapper.QuestionnaireMapper;
import mygroup.questionnaire_star.pojo.Questionnaire;
import mygroup.questionnaire_star.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Autowired
    QuestionnaireMapper questionnaireMapper;

    @Override
    public void add(Questionnaire questionnaire) {
        questionnaireMapper.add(questionnaire);
    }

    @Override
    public List<Questionnaire> listAll(Integer creatorId) {
        return questionnaireMapper.listAll(creatorId);
    }

    @Override
    public void update(Questionnaire questionnaire) {
        questionnaireMapper.update(questionnaire);
    }

    @Override
    public void delete(Integer id) {
        questionnaireMapper.delete(id);
    }
}
