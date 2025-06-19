package com.yummy.mapper;

import com.github.pagehelper.Page;
import com.yummy.annotation.AutoFill;
import com.yummy.dto.CategoryPageQueryDTO;
import com.yummy.entity.Category;
import com.yummy.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @AutoFill(value =  OperationType.INSERT)
    @Insert("insert into category (id, type, name, sort, status, create_time, update_time, " +
            "create_user, update_user)"
            + "values"
            + "(#{id}, #{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, " +
            "#{createUser}, #{updateUser})")
    void insert(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    /**
     * delete by id
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    List<Category> selectByType(Integer type);
}
