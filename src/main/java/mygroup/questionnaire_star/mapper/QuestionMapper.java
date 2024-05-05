package mygroup.questionnaire_star.mapper;

import mygroup.questionnaire_star.pojo.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Select("select * from question where questionnaire_id=#{questionnaireId}")
    public List<Question> listAll(Integer questionnaireId);

    @Insert("insert into question (number,questionnaire_id,type,title,options) " +
            "values (#{number},#{questionnaireId},#{type},#{title},#{options})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void add(Question question);

    @Delete("delete from question where id=#{id}")
    public void delete(Integer id);

    @Update("update question set number=#{number},type=#{type},title=#{title}," +
            "options=#{options} where id=#{id}")
    public void update(Question question);
}
