package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Comment;
import com.dimkov.bgMountains.domain.entities.Freelancer;
import com.dimkov.bgMountains.domain.entities.User;
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

    private final UserService userService;
    private final FreelancerService freelancerService;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(
            UserService userService,
            FreelancerService freelancerService,
            CommentRepository commentRepository,
            ModelMapper modelMapper) {
        this.userService = userService;
        this.freelancerService = freelancerService;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean saveComment(String comment, String user, String freelancerId){
        UserServiceModel userServiceModel = this.userService.findByUsername(user)
                .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        FreelancerServiceModel freelancerServiceModel =
                this.freelancerService.findById(freelancerId)
                .orElseThrow(() -> new NoSuchElementException(FREELANCER_NOT_FOUND_MESSAGE));

        if(!comment.equals("") && comment.length() <= MAX_COMMENT_LENGTH){

            Comment c = new Comment();
            c.setAuthor(this.modelMapper.map(userServiceModel, User.class));
            c.setFreelancer(this.modelMapper.map(freelancerServiceModel, Freelancer.class));
            c.setComment(comment);

            try {
                this.commentRepository.save(c);
                return true;
            } catch (Exception e){
                return false;
            }
        }

        return false;
    }
}
