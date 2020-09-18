package com.siddhiApi.util;

import com.siddhiApi.entity.Stream;

public interface StreamGenerator {
    String generateCodeInputStream(Stream stream);

    String generateCodeOutputStream(Stream stream);
}
