package com.epam.esm.extractor;

import java.util.Map;

public interface FieldExtractor<T> {

    Map<String, String> extract(T item);
}
