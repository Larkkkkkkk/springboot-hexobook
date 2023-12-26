package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ArticleMapper;
import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.service.ArticleService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;  //调用mapper层对象

    @Override
    public void add(Article article) {
        //补充属性值
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userid= (Integer) map.get("id");
        //补充进去
        article.setCreateUser(userid);
        //调用mapper对象方法
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state) {
        //1.创建pagebean(获取最终结果)
        PageBean<Article> pb=new PageBean<>();
        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);
        //2.1 获取补充id(自己只能看到自己的文章)
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        //3.调用mapper
        //查询出来的结果是一个list集合 里面存放的是Article
        List<Article> as =articleMapper.list(userId,categoryId,state);  //自己只能看到自己的文章
        //Page中提供了方法，可以获取pagehelper分页查询后得到的总记录条数和当前页数据
        Page<Article> p= (Page<Article>) as;
        //4.将数据填充到PageBean对象中
        pb.setTotal(p.getTotal());  //将总记录条数放进去
        pb.setItems(p.getResult()); //将当前页数据放进去
        return pb;
    }

    @Override
    public Article detail(Integer id) {
        //调用mapper对象方法
        Article article=articleMapper.detail(id);
        return article;
    }

    @Override
    public void update(Article article) {
        //2.1 获取补充id
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id= (Integer) map.get("id");
        //补充id
        article.setCreateUser(id);
        //调用mapper对象方法
        articleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        //调用mapper对象方法
        articleMapper.delete(id);
    }


}
