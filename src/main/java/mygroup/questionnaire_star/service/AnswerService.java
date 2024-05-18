package mygroup.questionnaire_star.service;

import mygroup.questionnaire_star.pojo.Answer;
import mygroup.questionnaire_star.pojo.AnswerOption;
import mygroup.questionnaire_star.pojo.AnswerText;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {

    Answer add(Answer answer);

    List<Answer> listAllByQuestionId(Integer questionId);

    List<Answer> listAllByFillerId(String fillerId);

    List<Answer> deleteByFillerIdAndQustionnaireId(String fillerId, Integer questionnaireId);

    Answer update(Answer answer);

    List<Answer> listAllByFillerIdAndQuestionnaireId(String fillerId, Integer questionnaireId);

    List<AnswerOption>calculateOptionStats(Integer questionId);

    List<AnswerText>listAllText(Integer questionId);
}
