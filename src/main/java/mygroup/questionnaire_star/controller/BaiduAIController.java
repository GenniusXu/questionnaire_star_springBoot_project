package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.utils.BaiduAIUitil;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/BaiduAI")
public class BaiduAIController {

    @PostMapping("/analyzeResult")
    public String sendMessage(@RequestBody String result) {
        result = result.replace("\"", "\\\"");
        System.out.println(result);
        System.out.println(result.getClass());
        String prompt = "我有问卷收集的json格式结果如下：\n" +
                result +
                "\n请帮我对该结果进行分析，要求如下：" +
                "1、参考格式为：“从第几题（题目）收集结果来看，可以看出xxx现象/该结果呈现出xxx特点，可以得出xxx结论；”，" +
                "2、注意要深入地分析，尝试找到问题的本质原因，不要泛泛而谈，更不要仅仅是简单地重复我给你的数据；" +
                "3、可以多道题目的收集数据放到一起分析，尝试找出它们之间隐含的关联性；" +
                "4、若有填空题，过滤掉如“没有建议”、“随便”、“还行”这种敷衍、无效的回答，并对收集到的结果进行概括和总结，并对结果加以分析";

        try {
            String analysis = BaiduAIUitil.chat(prompt);
            return analysis;
        } catch (Exception e) {
            e.printStackTrace();
            return "请求失败，请稍后重试！.";
        }
    }
}
