package com.example.sideproject.config;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class NumbersTypeHandler implements TypeHandler<int[]> {
    @Override
    public void setParameter(PreparedStatement ps, int i, int[] parameter, JdbcType jdbcType)
            throws SQLException {
        try {
            ps.setString(i, Arrays.toString(parameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int[] getResult(ResultSet rs, String columnName) throws SQLException {
        String[] numbersString = rs.getString(columnName).replaceAll("\\[|\\]|\\s", "")
                .trim().split(",");
        int[] numbers = Arrays.stream(numbersString).mapToInt(Integer::parseInt).toArray();
        return numbers;
    }

    @Override
    public int[] getResult(ResultSet rs, int columnIndex) throws SQLException {
        String[] numbersString = rs.getString(columnIndex).replaceAll("\\[|\\]|\\s", "")
                .trim().split(",");
        int[] numbers = Arrays.stream(numbersString).mapToInt(Integer::parseInt).toArray();
        return numbers;
    }

    @Override
    public int[] getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String[] numbersString = cs.getString(columnIndex).replaceAll("\\[|\\]|\\s", "")
                .trim().split(",");
        int[] numbers = Arrays.stream(numbersString).mapToInt(Integer::parseInt).toArray();
        return numbers;
    }
}
