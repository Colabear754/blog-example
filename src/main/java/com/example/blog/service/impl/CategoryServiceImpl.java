package com.example.blog.service.impl;

import com.example.blog.domain.CategoryVO;
import com.example.blog.mapper.CategoryMapper;
import com.example.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper mapper;

    @Override
    public List<CategoryVO> getCategories() {
        return mapper.getCategories();
    }

    @Override
    public CategoryVO getCategory(int category_id) {
        return mapper.getCategory(category_id);
    }

    @Override
    public int addCategory(CategoryVO category) {
        return mapper.addCategory(category);
    }

    @Override
    public int updateCategory(Map<String, Object> params) {
        return mapper.updateCategory(params);
    }

    @Override
    public int deleteCategory(int category_id) {
        return mapper.deleteCategory(category_id);
    }
}
