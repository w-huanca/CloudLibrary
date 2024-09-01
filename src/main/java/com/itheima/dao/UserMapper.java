package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.domain.Book;
import com.itheima.domain.User;
import org.apache.ibatis.annotations.*;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-06 18:47
 */
public interface UserMapper {
    //登录功能
    @Select("select * from user where user_email = #{email} AND " +
            "user_password=#{password} AND user_status = '0'")
    @Results(id = "userResult", value = {
            @Result(id = true, property = "id", column = "user_id"),
            @Result(property = "name", column = "user_name"),
            @Result(property = "password", column = "user_password"),
            @Result(property = "email", column = "user_email"),
            @Result(property = "role", column = "user_role"),
            @Result(property = "status", column = "user_status")
    })
    User login(User user);

    //用户登录注册模块的注册
    Integer addUser(User user);
    //注册中的验证唯一功能
    @Select("select count(*) from user where user_email = #{email} or user_name = #{name}")
    Integer selectByRegister(User user);

    //管理员读者管理的新增用户
    @Insert("insert into user (user_name,user_password, user_email, user_role, user_status)\n" +
            "        values (#{name},#{password},#{email},#{role},#{status})")
    Integer addUser1(User user);

    //修改用户信息
    @Select("select count(*) from user where (user_email = #{email} or user_name = #{name})"+
            "and user_id != #{id}")
    Integer selectByEditProfile(User user);
    //修改个人密码
    @Update("update user set user_password = #{newPassword} where user_id = #{id}")
    Integer changePassword(@Param(value = "id") Integer id, @Param(value = "newPassword") String newPassword);
    //修改个人用户名和邮箱
    @Update("update user set user_name = #{name},user_email=#{email} where user_id = #{id}")
    @ResultMap("userResult")
    boolean editProfile(User user);

    @Select("select * from user where user_id !=#{id} and user_role != 'ADMIN'")
    @ResultMap("userResult")
    Page<User> selectUser(User user);

    @Select("SELECT * FROM user where user_id=#{id}")
    @ResultMap("userResult")
        //根据id查询用户信息
    User findByUserId(String id);


    Integer editUser(User user);

    @Select("        <script>\n" +
            "            select * from user\n"+
            "            where user_id != #{id}"+
            "                <if test=\"name != null and name.trim() != ''\">\n" +
            "                    AND user_name like concat('%',#{name},'%')\n" +
            "                </if>\n" +
            "                <if test=\"email != null and email.trim() != ''\">\n" +
            "                    AND user_email like concat('%',#{email},'%')\n" +
            "                </if>\n" +
            "                <if test=\"role != null and role.trim() != ''\">\n" +
            "                    AND user_role like concat('%',#{role},'%')\n" +
            "                </if>\n" +
            "            order by user_status\n" +
            "        </script>")
    @ResultMap("userResult")
    Page<User> searchUser(User user);

    //管理员删除用户
    @Delete("delete from user where user_id = #{id}")
    Integer deleteUserById(String id);

    //计算当前读者的总量
    @Select("SELECT COUNT(*) FROM user WHERE user_role = 'USER'")
    int countTotalReaders();
}
