package com.itheima.service;

import com.itheima.pojo.Category;

import java.util.List;

public interface CategoryService {
    //新增分类
    void add(Category category);
    //列表查询
    List<Category> list();
    //根据id展示文章分类详情
    Category findById(Integer id);
    //更新文章分类
    void update(Category category);
    //删除文章分类
    void delete(Integer id);
}
