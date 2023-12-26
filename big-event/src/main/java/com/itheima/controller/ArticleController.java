package com.itheima.controller;

import com.itheima.pojo.Article;
import com.itheima.pojo.Category;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.ArticleService;
import com.itheima.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")  //共同前缀 /article
public class ArticleController {

    @Autowired
    private ArticleService articleService;  //调用service层对象


    //新增文章
    @PostMapping   //请求方式是Post  路径是/article
    public Result add(@RequestBody @Validated(Category.Add.class) Article article){
        //调用service对象方法
        articleService.add(article);
        //返回正确状态
        return Result.success();
    }


    //文章列表(条件查询)
    @GetMapping  //请求方式是Get  路径是/article
    public Result<PageBean<Article>> list(Integer pageNum,Integer pageSize,@RequestParam(required = false)String categoryId,@RequestParam(required = false)String state){  //前两个参数必备，后两个参数不是必备
        //调用service对象方法
        //调用service对象方法
        PageBean<Article> pb=articleService.list(pageNum,pageSize,categoryId,state);  //返回需要的数据结构
        //返回正确状态和需要的数据pb
        return Result.success(pb);
    }


    //获取文章详情
    @GetMapping("/detail") //请求方式是Get  路径是/article/detail
    public Result<Article> detail(Integer id){
        //调用service对象方法
        Article article=articleService.detail(id);
        //返回正确状态和需要的数据article
        return Result.success(article);
    }


    //更新文章
    @PutMapping  //请求方式是Put  路径是/article
    public Result update(@RequestBody @Validated(Category.Update.class) Article article){
        //调用service对象方法
        articleService.update(article);
        //返回正确状态
        return Result.success();
    }


    //删除文章
    @DeleteMapping  //请求方式是Delete  路径是/article
    public Result delete(Integer id){
        //调用service对象方法
        articleService.delete(id);
        //返回正确状态
        return Result.success();
    }


}
