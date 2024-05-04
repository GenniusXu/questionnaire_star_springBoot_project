package mygroup.questionnaire_star.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/*
* 自定义类型提供类型处理器
* 因为Questionnaire中的options属性需要作为参数传递给SQL语句，并且它是一个复杂类型（List<String>），
* 所以需要实现一个自定义的MyBatis类型处理器（TypeHandler）。将列表转换为数据库能理解的格式（如逗号分隔的字符串），反之亦然。
* */
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        // 将List转换为逗号分隔的字符串
        String joined = String.join(",", parameter);
        ps.setString(i, joined);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 从数据库读取字符串并转换为List
        String value = rs.getString(columnName);
        return value == null ? null : Arrays.asList(value.split(","));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : Arrays.asList(value.split(","));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : Arrays.asList(value.split(","));
    }
}
