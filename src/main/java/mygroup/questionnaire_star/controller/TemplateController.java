package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.mapper.TemplateMapper;
import mygroup.questionnaire_star.pojo.QTemplate;
import mygroup.questionnaire_star.pojo.Question;
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

    //列出所有模板名
    @GetMapping("/listAllName")
    Result listAll() {
        List<String> names = templateService.listAllName();
        return Result.success(names);
    }

    //根据模板名获取模板id
    @GetMapping("/getTemplateIdsByName")
    Result getTemplateById(String name) {
        List<Integer> ids = templateService.getTemplateIdsByName(name);
        return Result.success(ids);
    }

    //根据模板id获取模板
    @GetMapping("/getQuestionsByTemplateId")
    Result getTemplatesByName(Integer templateId) {
        List<Question> questions = templateService.getQuestionsByTemplateId(templateId);
        return Result.success(questions);
    }

    //根据模板名字获取模板（精确搜索）
    @GetMapping("/getTemplatesByName")
    Result getTemplatesByName(String name) {
        List<QTemplate> templates = templateService.getTemplatesByName(name);
        return Result.success(templates);
    }

    //根据模板名字获取模板类似模板（模糊搜索）
    @GetMapping("/getRelativeTemplatesByName")
    Result getRelativeTemplatesByName(String name) {
        List<QTemplate> templates = templateService.getRelativeTemplatesByName(name);
        return Result.success(templates);
    }

    //根据模板id获取模板，
    @GetMapping("/getTemplateById")
    Result getTemplateById(Integer templateId) {
        QTemplate template = templateService.getTemplateById(templateId);
        return Result.success(template);
    }
}
