package com.example.blog.mapper;

import com.example.blog.domain.CategoryVO;

import java.util.Map;

public interface CategoryMapper {
    public CategoryVO getCategories();
    public int addCategory(String name);
    public int updateCategory(Map<String, Object> params);
    public int deleteCategory(int category_id);
}
