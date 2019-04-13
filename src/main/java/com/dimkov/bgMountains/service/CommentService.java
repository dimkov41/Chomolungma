package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.CommentAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.CommentServiceModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    boolean saveComment(CommentAddServiceModel commentAddServiceModel);

    List<CommentServiceModel> findAll(String freelancerId);
}
