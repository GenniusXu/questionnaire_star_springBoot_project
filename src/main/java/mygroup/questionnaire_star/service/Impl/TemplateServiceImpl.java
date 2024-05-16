package mygroup.questionnaire_star.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mygroup.questionnaire_star.mapper.TemplateMapper;
import mygroup.questionnaire_star.pojo.QTemplate;
import mygroup.questionnaire_star.pojo.Question;
import mygroup.questionnaire_star.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    TemplateMapper templateMapper;
    @Override
    public List<Integer> getTemplateIdsByName(String name) {
        List<Integer> ids= templateMapper.getTemplateIdsByName(name);
        return ids;
    }

    @Override
    public List<Question> getQuestionsByTemplateId(Integer templateId) {
        String jsonListOfQTemplate = templateMapper.getQuestionsByTemplateId(templateId);
        System.out.println(jsonListOfQTemplate);
        if (jsonListOfQTemplate == null) {

            return null;
        }
        List<Question> questions = null;
        // 使用 Jackson ObjectMapper 将 JSON 字符串转换为 List<Question>
        ObjectMapper mapper = new ObjectMapper();
        try {
            questions = mapper.readValue(jsonListOfQTemplate, new TypeReference<>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }
//    public List<QTemplate> getTemplatesByName(String name){
//        List<QTemplate> templates = templateMapper.getTemplatesByName(name);
//        return templates;
//    }
}
