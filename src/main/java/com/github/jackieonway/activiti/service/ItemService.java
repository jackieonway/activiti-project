package com.github.jackieonway.activiti.service;

import com.github.jackieonway.activiti.model.Item;

public interface ItemService {
    public Item selectByPrimaryKey(int id) throws Exception;
}
