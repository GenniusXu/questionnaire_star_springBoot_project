package mygroup.questionnaire_star.service;

import mygroup.questionnaire_star.pojo.Answer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {

    Answer add(Answer answer);

    List<Answer> listAllByQuestionId(Integer questionId);

    List<Answer> listAllByFillerIdId(Integer fillerId);

    List<Answer> deleteByFillerIdAndQustionnaireId(Integer fillerId, Integer questionnaireId);
}
