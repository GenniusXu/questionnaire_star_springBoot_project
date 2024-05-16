package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.pojo.Question;
import mygroup.questionnaire_star.pojo.Questionnaire;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.service.QuestionService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    //根据问卷id列出所有问题
    @GetMapping("/list_all")
    Result listAll(@RequestParam Integer questionnaireId){
        List<Question> questions = questionService.listAll(questionnaireId);
        return Result.success(questions);
    }

    //添加问题
    @PostMapping("/add")
    Result add(@RequestBody Question question){
        question = questionService.add(question);
        return Result.success(question);
    }

    //删除问题
    @DeleteMapping("/delete")
    Result delete(@RequestParam Integer id){
        questionService.delete(id);
        return Result.success();
    }

    //更新问题
    @PutMapping("/update")
    Result update(@RequestBody Question question) {
        question = questionService.update(question);
        return Result.success(question);
    }
}
