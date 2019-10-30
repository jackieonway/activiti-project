package com.github.jackieonway.activiti.mapper;

import com.github.jackieonway.activiti.model.Item;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {
    public Item selectByPrimaryKey(int id) throws Exception;
}
