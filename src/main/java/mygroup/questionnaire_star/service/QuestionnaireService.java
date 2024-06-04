package mygroup.questionnaire_star.service;

import mygroup.questionnaire_star.pojo.Questionnaire;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionnaireService {
    Questionnaire add(Questionnaire questionnaire);
    List<Questionnaire> listAllByCreatorId(String creatorId);
    Questionnaire update(Questionnaire questionnaire);
    void delete(Integer id);
    public List<Questionnaire> listAllByFillerId(String fillerId);

    Questionnaire getById(Integer id);

    Questionnaire getQuestionnaireById(Integer questionnaireid);

    List<Questionnaire> listAllByManagerId(String managerId);
}
