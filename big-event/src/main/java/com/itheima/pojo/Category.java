package com.itheima.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
//lombok 在编译阶段，为实体类自动生成set/get方法和toString方法等等
//使用步骤: 1.在pom文件中引入依赖  2.实体类上添加注解
@Data
public class Category {

    @NotNull(groups = Update.class)
    private Integer id;//主键ID

    //@NotEmpty(groups = {Add.class,Update.class})
    @NotEmpty()
    private String categoryName;//分类名称

    //@NotEmpty(groups = {Add.class,Update.class})
    @NotEmpty()
    private String categoryAlias;//分类别名

    private Integer createUser;//创建人ID

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")    //前后端传输的时候输出我设定的格式
    private LocalDateTime createTime;//创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")   //前后端传输的时候输出我设定的格式
    private LocalDateTime updateTime;//更新时间

    //两个规则:
    //如果某个校验项没有指定分组，默认属于Default分组
    //分组之间可以继承  如果Add extends Update 那么Add拥有Update中所有的校验项

    public interface Add extends Default {    //默认属于Default分组

    }

    public interface Update extends Default {      //默认属于Default分组

    }

}
