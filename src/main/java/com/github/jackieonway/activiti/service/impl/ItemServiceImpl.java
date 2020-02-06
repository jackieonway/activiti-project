package com.github.jackieonway.activiti.service.impl;

import com.github.jackieonway.activiti.mapper.ItemMapper;
import com.github.jackieonway.activiti.model.Item;
import com.github.jackieonway.activiti.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Item selectByPrimaryKey(int id) throws Exception {
        return itemMapper.selectByPrimaryKey(id);
    }
}
