package mygroup.questionnaire_star.mapper;

import mygroup.questionnaire_star.pojo.QTemplate;
import org.apache.ibatis.annotations.Mapper;
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
}
