package com.example.sideproject.dao;

import com.example.sideproject.config.NumbersTypeHandler;
import com.example.sideproject.entity.Order;
import com.example.sideproject.entity.WinningNumbers;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface OrderDao {
    @Results(value = {
            @Result(property = "numbers", column = "numbers", typeHandler = NumbersTypeHandler.class),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("SELECT id, numbers, create_time, stage, winning FROM orders WHERE user_id = (SELECT id FROM user WHERE email = #{email})")
    List<Order> findOrdersByEmail(String email);

    @Results(value = {
            @Result(property = "numbers", column = "numbers", typeHandler = NumbersTypeHandler.class),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "userId", column = "user_id"),
    })
    @Select("SELECT id, numbers, create_time, stage, winning, user_id FROM orders WHERE stage = #{stage} AND winning = 0")
    List<Order> findNotCheckedOrdersByStage(String stage);

    @Update({"<script>",
            "<foreach collection='updateBalanceByUserId' item='value' index='key'  separator=';' >",
            "UPDATE user SET balance = balance + #{value} WHERE id = #{key}",
            "</foreach>",
            "</script>"})
    int updateAddBalanceWithUserId(@Param("updateBalanceByUserId")Map<String, Integer> updateBalanceByUserId);

    @Insert("INSERT INTO orders(user_id, numbers, stage) " +
            "VALUES(SELECT id FROM user WHERE email = #{email}, " +
            "#{numbers,javaType=int[],typeHandler=com.example.sideproject.config.NumbersTypeHandler}," +
            "SELECT CEILING( CAST( CEILING( FORMATDATETIME( NOW(), 'yyyyMMddHHmm.ss')) AS DECIMAL) /5) * 5)")
    int insertOrder(@Param("email")String email, @Param("numbers")int[] numbers);

    @Delete("DELETE FROM orders WHERE id = #{id} AND user_id = ( SELECT id FROM user WHERE email = #{email}) AND numbers = #{numbers,javaType=int[],typeHandler=com.example.sideproject.config.NumbersTypeHandler}")
    int deleteOrder(@Param("id")int id, @Param("email")String email, @Param("numbers")int[] numbers);

    @Insert("INSERT INTO winning(stage, numbers) VALUES((SELECT FORMATDATETIME(NOW(), 'yyyyMMddHHmm')), #{numbers,javaType=int[],typeHandler=com.example.sideproject.config.NumbersTypeHandler})")
    int insertWinningNumbers(@Param("numbers")int[] numbers);

    @Results(value = {
            @Result(property = "numbers", column = "numbers", typeHandler = NumbersTypeHandler.class),
            @Result(property = "createTime", column = "create_time")
    })
    @SelectProvider(type = UserSqlBuilder.class, method = "selectWinningNumbers")
    List<WinningNumbers> findAllWinningNumbers(@Param("page")int page);

    @Results(value = {
            @Result(property = "numbers", column = "numbers", typeHandler = NumbersTypeHandler.class),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("SELECT stage, numbers, create_time FROM WINNING WHERE checked = 0")
    WinningNumbers findNotCheckedWinningNumbers();

    @Update("UPDATE winning SET checked = 1 WHERE stage = #{stage}")
    int updateWinningChecked(WinningNumbers winningNumbers);

    @Update({"<script>",
            "<foreach collection='list' item='order'  index='index'  separator=';' >",
            "UPDATE orders SET winning = #{order.winning} WHERE id = #{order.id}",
            "</foreach>",
            "</script>"})
    int updateOrder(List<Order> orders);

    class UserSqlBuilder {
        public static String selectWinningNumbers(final int page) {
            return new SQL(){{
                SELECT("stage", "numbers", "create_time");
                FROM("WINNING");
                ORDER_BY("create_time DESC");
                if(page > 0) {
                    LIMIT(10);
                    OFFSET((page-1)*10);
                }
            }}.toString();
        }
    }
}