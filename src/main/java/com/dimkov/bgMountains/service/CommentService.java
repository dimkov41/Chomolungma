package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.CommentServiceModel;

public interface CommentService {
    boolean saveComment(CommentServiceModel commentServiceModel);

}
