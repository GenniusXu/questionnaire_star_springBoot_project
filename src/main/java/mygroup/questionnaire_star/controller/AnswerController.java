package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.pojo.Answer;
import mygroup.questionnaire_star.pojo.AnswerOption;
import mygroup.questionnaire_star.pojo.AnswerText;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.service.AnswerService;
import mygroup.questionnaire_star.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;
    @Autowired
    QuestionService questionService;

    //增加一条回答
    @PostMapping("/add")
    Result add(@RequestBody Answer answer) {
        System.out.println(answer);
        answer = answerService.add(answer);
        return Result.success(answer);
    }

    //列出某个问题的所有回答
    @GetMapping("/list_all_by_question_id")
    Result listAllByQuestionId(@RequestParam Integer questionId){
        List<Answer> answers = answerService.listAllByQuestionId(questionId);
        return Result.success(answers);
    }

    //列出某个用户的所有回答
    @GetMapping("/list_all_by_filler_id")
    Result listAllByFillerId(@RequestParam String fillerId){
        List<Answer> answers = answerService.listAllByFillerId(fillerId);
        return Result.success(answers);
    }

    //列出某个用户在某份问卷中的所有回答
    @GetMapping("/list_all_by_filler_id_and_qustionnaire_id")
    Result listAllByFillerIdAndQuestionnaireId(@RequestParam String fillerId, @RequestParam Integer questionnaireId){
        List<Answer> answers = answerService.listAllByFillerIdAndQuestionnaireId(fillerId, questionnaireId);
        return Result.success(answers);
    }

    //删除某个用户在某份问卷中的所有回答
    @DeleteMapping("/delete_by_filler_id_and_question_id")
    Result deleteByFillerId(@RequestParam String fillerId, @RequestParam Integer questionnaireId){
        List<Answer> answers = answerService.deleteByFillerIdAndQustionnaireId(fillerId, questionnaireId);
        return Result.success(answers);
    }

    //更新一条回答
    @PutMapping("/update")
    Result update(@RequestBody Answer answer){
        answer = answerService.update(answer);
        return Result.success(answer);
    }

    //获取选项计数
    @GetMapping("/calculateOptionStats")
    Result calculateOptionStats(Integer questionId){

        String type= questionService.getType(questionId);
        //if(!(type.equals("单选题")||type.equals("多选题")))return Result.error("本题不是选择题");
        List<AnswerOption> stats=answerService.calculateOptionStats(questionId);
        System.out.println(stats);
        return Result.success(stats);
    }

    //获取文本题答案
    @GetMapping("/listAllText")
    Result listAllText(Integer questionId){
        String type= questionService.getType(questionId);
        if(!type.equals("填空题"))return Result.error("本题不是填空题");
        List<AnswerText> texts=answerService.listAllText(questionId);
        return Result.success(texts);
    }
}
