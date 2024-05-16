package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.pojo.Questionnaire;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Autowired
    QuestionnaireService questionnaireService;

    //新增问卷
    @PostMapping("/add")
    public Result add(@RequestBody Questionnaire questionnaire) {
        questionnaire = questionnaireService.add(questionnaire);
        return Result.success(questionnaire);
    }

    //列出某一用户创建的所有问卷
    @GetMapping("/listAllByCreatorId")
    public Result listAll(@RequestParam String creatorId) {
        List<Questionnaire> questionnaires = questionnaireService.listAllByCreatorId(creatorId);
        return Result.success(questionnaires);
    }

    //更新问卷
    @PutMapping("/update")
    public Result update(@RequestBody Questionnaire questionnaire) {
        questionnaire = questionnaireService.update(questionnaire);
        return Result.success(questionnaire);
    }

    //删除问卷
    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        questionnaireService.delete(id);
        return Result.success();
    }

    //列出填写者填写过的所有问卷
    @GetMapping("listAllByFillerId")
    public Result listAllByFillerId(String fillerId){
        List<Questionnaire> questionnaires = questionnaireService.listAllByFillerId(fillerId);
        return Result.success(questionnaires);
    }
}
