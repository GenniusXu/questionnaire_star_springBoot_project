package mygroup.questionnaire_star.service.Impl;

import mygroup.questionnaire_star.mapper.TemplateMapper;
import mygroup.questionnaire_star.pojo.QTemplate;
import mygroup.questionnaire_star.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    TemplateMapper templateMapper;
    @Override
    public QTemplate getTemplateById(Integer templateId) {
        QTemplate template = templateMapper.getTemplateById(templateId);
        return template;
    }

    @Override
    public List<QTemplate> getTemplatesByName(String name) {
        List<QTemplate> templates = templateMapper.getTemplatesByName(name);
        return templates;
    }
}
