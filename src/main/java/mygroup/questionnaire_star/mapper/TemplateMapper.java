package mygroup.questionnaire_star.mapper;

import mygroup.questionnaire_star.pojo.QTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TemplateMapper {

    @Select("SELECT * from template WHERE id=#{templateId};")
    QTemplate getTemplateById(Integer templateId);

    @Select("SELECT * from template WHERE name=#{name};")
    List<QTemplate> getTemplatesByName(String name);
}
