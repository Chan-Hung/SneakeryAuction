package com.hung.sneakery.controller;

import com.hung.sneakery.dto.CategoryDTO;
import com.hung.sneakery.dto.request.CategoryRequest;
import com.hung.sneakery.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(tags = "Category APIs")
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/", "https://aunction-react-js.vercel.app/"})
@RequestMapping("/categories")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryDTO getOne(@PathVariable final Long id) {
        return categoryService.getOne(id);
    }

    @GetMapping()
    public Page<CategoryDTO> getAll(final Pageable pageable) {
        return categoryService.getAll(pageable);
    }

    @PostMapping()
    public CategoryDTO create(@Valid @RequestBody final CategoryRequest request) {
        return categoryService.create(request);
    }

    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable final Long id, @Valid @RequestBody final CategoryRequest request) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public CategoryDTO delete(@PathVariable final Long id) {
        return categoryService.delete(id);
    }
}
