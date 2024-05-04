package mygroup.questionnaire_star.mapper;

import mygroup.questionnaire_star.pojo.Questionnaire;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionnaireMapper {
    @Insert("INSERT INTO questionnaire (creatorId, managersId, status, createDate, updateTime) VALUES (#{creatorId}, " +
            "#{managersId, jdbcType=VARCHAR, typeHandler=mygroup.questionnaire_star.config.StringListTypeHandler}, " +
            "#{status}, #{createDate}, #{updateTime})")
    void add(Questionnaire questionnaire);

    @Select("SELECT * FROM questionnaire WHERE id = #{id}")
    Questionnaire selectById(Integer id);

    @Select("SELECT * FROM questionnaire where creator_id=#{creatorId}")
    List<Questionnaire> listAll(Integer creatorId);

    @Update("UPDATE questionnaire SET creatorId = #{creatorId}, managersId = #{managersId}, status = #{status}, createDate = #{createDate}, updateTime = #{updateTime} WHERE id = #{id}")
    void update(Questionnaire questionnaire);

    @Delete("DELETE FROM questionnaire WHERE id = #{id}")
    void delete(Integer id);
}
