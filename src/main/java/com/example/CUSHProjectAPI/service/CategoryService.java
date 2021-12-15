package com.example.CUSHProjectAPI.service;

import com.example.CUSHProjectAPI.dto.BoardCategoryDto;
import com.example.CUSHProjectAPI.entity.BoardCategoryEntity;
import com.example.CUSHProjectAPI.repository.BoardCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final BoardCategoryRepository boardCategoryRepository;

    public List<BoardCategoryDto> getCategoryList() {
        List<BoardCategoryEntity> boardCategoryRepositoryAll = boardCategoryRepository.findAll();
        List<BoardCategoryDto> boardCategoryDtoList = new ArrayList<>();

        for(BoardCategoryEntity category: boardCategoryRepositoryAll){
            boardCategoryDtoList.add(category.toDto());
        }
        return boardCategoryDtoList;
    }
}
