package mygroup.questionnaire_star.service;

import mygroup.questionnaire_star.pojo.Answer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {

    Answer add(Answer answer);

    List<Answer> listAllByQuestionId(Integer questionId);

    List<Answer> listAllByFillerId(Integer fillerId);

    List<Answer> deleteByFillerIdAndQustionnaireId(Integer fillerId, Integer questionnaireId);

    Answer update(Answer answer);

    List<Answer> listAllByFillerIdAndQuestionnaireId(Integer fillerId, Integer questionnaireId);
}
