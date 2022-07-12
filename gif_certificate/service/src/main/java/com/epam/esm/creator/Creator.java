package com.epam.esm.creator;

import org.springframework.util.MultiValueMap;

import java.util.List;

public interface Creator<T> {

    T createObject(MultiValueMap<String, String> requestParams);

    List<T> createList(MultiValueMap<String, String> requestParams);
}
