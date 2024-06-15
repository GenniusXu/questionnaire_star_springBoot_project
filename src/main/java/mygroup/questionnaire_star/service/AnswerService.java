package mygroup.questionnaire_star.service;

import mygroup.questionnaire_star.pojo.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public List<AnswerFile> listAllFileURL(Integer questionId);

    Question getQuestion(Answer answer);

    Answer getAnswerById(Integer answerId);

    String uploadFile(MultipartFile file, String fileName) throws Exception;

    String updateFile(String originUrl, MultipartFile newFile, String NewFileName) throws Exception;
}
