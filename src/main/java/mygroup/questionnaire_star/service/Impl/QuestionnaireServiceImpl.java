package mygroup.questionnaire_star.service.Impl;

import mygroup.questionnaire_star.mapper.QuestionnaireMapper;
import mygroup.questionnaire_star.pojo.Questionnaire;
import mygroup.questionnaire_star.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Autowired
    QuestionnaireMapper questionnaireMapper;

    @Override
    public Questionnaire add(Questionnaire questionnaire) {
        questionnaire.setCreateTime(LocalDateTime.now());
        questionnaire.setUpdateTime(LocalDateTime.now());
        questionnaireMapper.add(questionnaire);
        return questionnaire;
    }

    @Override
    public List<Questionnaire> listAll(String creatorId) {
        return questionnaireMapper.listAll(creatorId);
    }

    @Override
    public Questionnaire update(Questionnaire questionnaire) {
        questionnaire.setUpdateTime(LocalDateTime.now());
        questionnaireMapper.update(questionnaire);
        return questionnaire;
    }

    @Override
    public void delete(Integer id) {
        questionnaireMapper.delete(id);
    }
}
