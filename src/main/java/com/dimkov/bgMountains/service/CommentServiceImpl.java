package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Comment;
import com.dimkov.bgMountains.domain.entities.Freelancer;
import com.dimkov.bgMountains.domain.entities.User;
import com.dimkov.bgMountains.domain.models.service.CommentServiceModel;
import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import com.dimkov.bgMountains.repository.CommentRepository;
import com.dimkov.bgMountains.util.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CommentServiceImpl implements CommentService {
    private static final String FREELANCER_NOT_FOUND_MESSAGE = "Freelancer with such id does not found";

    private static final int MAX_COMMENT_LENGTH = 255;

    private final FreelancerService freelancerService;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(
            FreelancerService freelancerService,
            CommentRepository commentRepository,
            ModelMapper modelMapper) {
        this.freelancerService = freelancerService;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean saveComment(CommentServiceModel commentServiceModel){
        FreelancerServiceModel freelancerServiceModel =
                this.freelancerService.findById(commentServiceModel.getFreelancerId())
                .orElseThrow(() -> new NoSuchElementException(FREELANCER_NOT_FOUND_MESSAGE));

        String comment = commentServiceModel.getComment();
        if(!comment.equals("") && comment.length() <= MAX_COMMENT_LENGTH){

            Comment c = this.modelMapper.map(commentServiceModel,Comment.class);
            c.setFreelancer(this.modelMapper.map(freelancerServiceModel, Freelancer.class));

            try {
                this.commentRepository.save(c);
            } catch (Exception e){
                return false;
            }

            return true;
        }

        return false;
    }
}
