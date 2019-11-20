package com.example.sideproject.Dao;

import com.example.sideproject.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {
    @Select("SELECT * FROM user WHERE name = #{name}")
    User findUserByName(@Param("name")String name);
    @Select("SELECT * FROM user")
    List<User> findAllUser();
    @Insert("INSERT INTO user VALUES(#{name}, #{password})")
    Integer insertUser(@Param("name")String name, @Param("password")String password);
}
