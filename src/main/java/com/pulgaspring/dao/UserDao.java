package com.pulgaspring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.pulgaspring.domain.User;

/**
 * Created by pulgawang on 11/05/2017.
 */
@Repository // 定义一个Dao
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    private final static String MATCH_COUNT_SQL = "SELECT count(*) FROM t_user " +
            "WHERE user_name =? and password =? ";
    private final static String FIND_USER_SQL = "SELECT user_id, user_name, credits FROM t_user " +
            "WHERE user_name =?" ;
    private final static String UPDATE_LOGIN_INFO_SQL = "UPDATE t_user SET " +
            "last_visit =?, last_ip =?, credits =? WHERE user_id =?";

    @Autowired // 自动注入JdbcTemplate的Bean
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // 用户匹配数量
    public int getMatchCount(String userName, String password){
        Object[] args = { userName, password };
        return jdbcTemplate.queryForObject(MATCH_COUNT_SQL, args, Integer.class);
    }

    // 按照用户名查找用户信息
    public User findUserByUserName(final String userName){
        final User user = new User();
        Object[] args = { userName };
        jdbcTemplate.query(FIND_USER_SQL, args,
                new RowCallbackHandler() {
                    public void processRow(ResultSet rs) throws SQLException {
                        user.setUserId(rs.getInt("user_id"));
                        user.setUserName(userName);
                        user.setCredits(rs.getInt("credits"));
                    }
                }
        );
        return user;
    }

    // 更新用户系统信息
    public void updateLoginInfo(User user){
        Object[] args = { user.getLastVisit(), user.getLastIp(), user.getCredits(), user.getUserId() };
        jdbcTemplate.update(UPDATE_LOGIN_INFO_SQL, args);
    }
}
