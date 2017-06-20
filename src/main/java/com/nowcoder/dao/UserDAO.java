package com.nowcoder.dao;

import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

/**
 * Created by Damon on 2017/6/20.
 */
@Mapper
public interface UserDAO {
    String TABLE_NAME = "people";
    String INSERT_FIELDS = "name,password,salt,head_url";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"INSERT INTO", TABLE_NAME ,"(", INSERT_FIELDS,") VALUES (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);


}
