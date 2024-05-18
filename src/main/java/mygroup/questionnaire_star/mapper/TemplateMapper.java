package mygroup.questionnaire_star.mapper;

import mygroup.questionnaire_star.pojo.QTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TemplateMapper {

    @Select("SELECT id from template WHERE name=#{name};")
    List<Integer> getTemplateIdsByName(String name);

//    @Select("SELECT * from template WHERE name=#{name};")
//    List<QTemplate> getTemplatesByName(String name);

    @Select("SELECT questions from template WHERE id=#{templateId};")
    String getQuestionsByTemplateId(Integer templateId);


    //因为现在有两个TypeHandler，所以需要用@Result注解来指定为某个字段使用哪个TypeHandler，否则会报错
    @Results({
            @Result(property = "questions", column = "questions", typeHandler = mygroup.questionnaire_star.config.QuestionListTypeHandler.class)
    })
    @Select("SELECT * from template WHERE name=#{name};")
    List<QTemplate> getTemplatesByName(String name);

    //因为现在有两个TypeHandler，所以需要用@Result注解来指定为某个字段使用哪个TypeHandler，否则会报错
    @Results({
            @Result(property = "questions", column = "questions", typeHandler = mygroup.questionnaire_star.config.QuestionListTypeHandler.class)
    })
    @Select("SELECT * from template WHERE id=#{templateId};")
    QTemplate getTemplateById(Integer templateId);
}
