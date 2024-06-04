package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.pojo.Questionnaire;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/getQuestionnaireById")
    public Result getQuestionnaireById(@RequestParam Integer questionnaireid) {
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(questionnaireid);
        return Result.success(questionnaire);
    }

    //列出某一用户创建的所有问卷
    @GetMapping("/listAllByCreatorId")
    public Result listAll(@RequestParam String creatorId) {
        List<Questionnaire> questionnaires = questionnaireService.listAllByCreatorId(creatorId);
        return Result.success(questionnaires);
    }

    //列出该用户管理的所有问卷（不包括它创建的）
    @GetMapping("/listAllByManagerId")
    public Result listAllByManagerId(String managerId){
        List<Questionnaire> questionnaires = questionnaireService.listAllByManagerId(managerId);
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
    @GetMapping("/listAllByFillerId")
    public Result listAllByFillerId(String fillerId){
        List<Questionnaire> questionnaires = questionnaireService.listAllByFillerId(fillerId);
        return Result.success(questionnaires);
    }

    @GetMapping("/getTimeStatus")
    public Result getStatus(@RequestParam Integer id){
        Questionnaire questionnaire=questionnaireService.getById(id);
        LocalDateTime now=LocalDateTime.now();
        //如果超出最末时间
        if (questionnaire.getEndTime().isBefore(now)) {
            return Result.error("问卷已经于" + questionnaire.getEndTime() + "结束");
        }
        //如果还未开始
        if (questionnaire.getStartTime().isAfter(now)) {
            return Result.error("问卷还没开始,将于" + questionnaire.getStartTime() + "开始");
        }
        if(!questionnaire.getStatus().equals("已发布")){
            return Result.error("问卷已经停止收集");
        }
        return Result.success("问卷可填写");
    }
}
