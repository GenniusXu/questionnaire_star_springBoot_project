package mygroup.questionnaire_star.service;

import mygroup.questionnaire_star.pojo.QTemplate;
import mygroup.questionnaire_star.pojo.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TemplateService {


    List<Integer> getTemplateIdsByName(String name);

    List<Question> getQuestionsByTemplateId(Integer templateId);

    List<QTemplate> getTemplatesByName(String name);

    QTemplate getTemplateById(Integer templateId);
}
