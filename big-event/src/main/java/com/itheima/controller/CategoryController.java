package com.itheima.controller;

import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController   //Controller层必备
@RequestMapping("/category")  //共同前缀路径
public class CategoryController {

    @Autowired
    private CategoryService categoryService; //引入一个service层对象

    //新增文章分类
    @PostMapping  //请求方式是post  路径是/category
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){   //一定要写@RequestBody注解  不然前端传不进来
        //调用service对象方法
        categoryService.add(category);
        //返回正确状态
        return Result.success();
    }

    //文章分类列表
    @GetMapping
    public Result<List<Category>>list(){
        //调用service对象方法
        List<Category> cs=categoryService.list();
        //返回正确状态和要的数据cs
        return Result.success(cs);
    }

    //获取文章分类详情
    @GetMapping("/detail")  //请求方式是get  路径是/category/detail
    public Result<Category> detail(Integer id){
        //调用service对象方法
        Category category=categoryService.findById(id);
        //返回正确状态和要的数据
        return Result.success(category);
    }

    //更新文章分类
    @PutMapping  //请求方式是put  路径是/category
    public Result Update(@RequestBody @Validated({Category.Update.class}) Category category) { //一定要写@RequestBody注解  不然前端传不进来
        //调用service对象方法
        categoryService.update(category);
        //返回正确状态
        return Result.success();
    }

    //删除文章分类
    @DeleteMapping  //请求方式是Delete  路径是/category
    public Result delete(Integer id){
        //调用service对象方法
        categoryService.delete(id);
        //返回正确状态
        return Result.success();
    }

}
