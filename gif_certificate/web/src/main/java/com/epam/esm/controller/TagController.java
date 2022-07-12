package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> allTags() throws DaoException {
        return tagService.getAll();
    }

    @GetMapping("/{id}")
    public Tag tagById(@PathVariable long id) throws DaoException, IncorrectParameterException {
        return tagService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTag(@PathVariable long id) throws DaoException, IncorrectParameterException {
        tagService.removeById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }

    @PostMapping
    public ResponseEntity createTag(@RequestBody Tag tag) throws DaoException, IncorrectParameterException {
        tagService.insert(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @GetMapping("/filter")
    public List<Tag> tagsByParameter(@RequestParam MultiValueMap<String, String> allRequestParams) throws DaoException {
        return tagService.doFilter(allRequestParams);
    }
}