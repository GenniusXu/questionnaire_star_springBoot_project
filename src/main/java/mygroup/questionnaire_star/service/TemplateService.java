package mygroup.questionnaire_star.service;

import mygroup.questionnaire_star.pojo.QTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TemplateService {


    QTemplate getTemplateById(Integer templateId);

    List<QTemplate> getTemplatesByName(String name);
}
