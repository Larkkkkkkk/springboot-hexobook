package com.itheima.mapper;

import com.itheima.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //新增文章分类
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time) values(#{categoryName},#{categoryAlias},#{createUser},now(),now())")
    void add(Category category);

    //展示文章分类信息
    @Select("select *  from category where create_user=#{id}")
    List<Category> list(Integer id);

    //根据id查看文章分类详情
    @Select("select * from category where id=#{id}")
    Category findById(Integer id);

    //更新文章分类
    @Update("update category set category.category_name=#{categoryName},category_alias=#{categoryAlias},update_time=now() where id=#{id}")
    void update(Category category);

    //根据id删除文章分类
    @Delete("delete from category where id=#{id}")
    void delete(Integer id);

}
