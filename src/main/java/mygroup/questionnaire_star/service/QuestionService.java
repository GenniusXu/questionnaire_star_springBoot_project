package mygroup.questionnaire_star.service;

import mygroup.questionnaire_star.pojo.Question;
import mygroup.questionnaire_star.pojo.Questionnaire;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    List<Question> listAll(Integer questionnaireId);

    Question add(Question question);

    void delete(Integer questionId);

    Question update(Question question);

    String getType(Integer questionId);

    Questionnaire getQuestionnaire(Question question);
}
