package com.example.sideproject.dao;

import com.example.sideproject.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {
    @Select("SELECT * FROM user WHERE name = #{name}")
    User findUserByName(@Param("name")String name);
    @Select("SELECT * FROM user WHERE email = #{email}")
    User findUserByEmail(@Param("email")String email);
    @Select("SELECT * FROM user")
    List<User> findAllUser();
    @Insert("INSERT INTO user(email, password) VALUES(#{email}, #{password})")
    Integer insertUser(@Param("email")String email, @Param("password")String password);
    @UpdateProvider(type = UserSqlBuilder.class, method = "buildUpdateUserInfo")
    Integer updateUser(@Param("email") String email, @Param("name") String name);


    class UserSqlBuilder {
        public static String buildUpdateUserInfo(final String email, final String name) {
            return new SQL(){{
                UPDATE("user");
                SET("name = #{name}");
                WHERE("email = #{email}");
            }}.toString();
        }
    }
}
