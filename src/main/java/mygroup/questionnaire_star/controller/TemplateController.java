package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.mapper.TemplateMapper;
import mygroup.questionnaire_star.pojo.QTemplate;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/template")
public class TemplateController {
    @Autowired
    TemplateService templateService;

    @GetMapping("/getTemplateById")
    Result getTemplateById(Integer templateId) {
        QTemplate template = templateService.getTemplateById(templateId);
        return Result.success(template);
    }

    @GetMapping("/getTemplatesByName")
    Result getTemplatesByName(String name) {
        List<QTemplate> templates = templateService.getTemplatesByName(name);
        return Result.success(templates);
    }
}
