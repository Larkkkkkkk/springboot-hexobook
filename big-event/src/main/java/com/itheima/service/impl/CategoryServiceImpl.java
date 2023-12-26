package com.itheima.service.impl;

import com.itheima.mapper.CategoryMapper;
import com.itheima.pojo.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        //传进来的只有分类名称和分类别名  --还需要创建人id(通过ThreadLocal)和创建时间和修改时间(这两个sql里面补now()就行)
        //找到id
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        //添加id
        category.setCreateUser(id);
        //调用mapper对象方法
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        //没传进来值  --所以需要创建人id(通过ThreadLocal)
        //找到id
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        //调用mapper对象方法
        return categoryMapper.list(id);
    }

    @Override
    public Category findById(Integer id) {
        //调用mapper对象方法
        return categoryMapper.findById(id);
    }

    @Override
    public void update(Category category) {
        //调用mapper对象方法
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        //调用mapper对象方法
        categoryMapper.delete(id);
    }

}
