package mygroup.questionnaire_star.service;

import mygroup.questionnaire_star.pojo.Questionnaire;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionnaireService {
    void add(Questionnaire questionnaire);
    List<Questionnaire> listAll(Integer creatorId);
    void update(Questionnaire questionnaire);
    void delete(Integer id);
}
