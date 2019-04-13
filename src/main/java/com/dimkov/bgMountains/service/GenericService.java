package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.util.Constants;
import org.springframework.data.domain.Page;

import java.util.NoSuchElementException;

public abstract class GenericService {
    public static <T> void checkPages(int page, Page<T> pageObj) {
        if (page > pageObj.getTotalPages()) {
            throw new NoSuchElementException(Constants.PAGE_ERROR_MESSAGE);
        }
    }
}
