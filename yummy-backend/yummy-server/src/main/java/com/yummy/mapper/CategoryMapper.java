package com.yummy.mapper;

import com.github.pagehelper.Page;
import com.yummy.dto.CategoryPageQueryDTO;
import com.yummy.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    @Insert("insert into category (id, type, name, sort, status, create_time, update_time, " +
            "create_user, update_user)"
            + "values"
            + "(#{id}, #{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, " +
            "#{createUser}, #{updateUser})")
    void insert(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void update(Category category);
}
