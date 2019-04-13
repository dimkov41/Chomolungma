package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Comment;
import com.dimkov.bgMountains.domain.entities.Freelancer;
import com.dimkov.bgMountains.domain.entities.Peak;
import com.dimkov.bgMountains.domain.models.service.CommentAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.CommentServiceModel;
import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import com.dimkov.bgMountains.domain.models.service.PeakServiceModel;
import com.dimkov.bgMountains.repository.CommentRepository;
import com.dimkov.bgMountains.util.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private static final String FREELANCER_NOT_FOUND_MESSAGE = "Freelancer with such id does not found";

    private static final int MAX_COMMENT_LENGTH = 255;

    private static final int MAX_ELEMENTS_PER_PAGE = 2;

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
    public boolean saveComment(CommentAddServiceModel commentAddServiceModel) {
        FreelancerServiceModel freelancerServiceModel =
                this.freelancerService.findById(commentAddServiceModel.getFreelancerId())
                        .orElseThrow(() -> new NoSuchElementException(FREELANCER_NOT_FOUND_MESSAGE));

        String comment = commentAddServiceModel.getComment();
        if (!comment.equals("") && comment.length() <= MAX_COMMENT_LENGTH) {

            Comment c = this.modelMapper.map(commentAddServiceModel, Comment.class);
            c.setFreelancer(this.modelMapper.map(freelancerServiceModel, Freelancer.class));

            try {
                this.commentRepository.save(c);
            } catch (Exception e) {
                return false;
            }

            return true;
        }

        return false;
    }

    @Override
    public List<CommentServiceModel> findAll(String freelancerId) {
        FreelancerServiceModel freelancerServiceModel =
                this.freelancerService.findById(freelancerId)
                        .orElseThrow(() -> new NoSuchElementException(Constants.USERNAME_NOT_FOUND_MESSAGE));

        Freelancer freelancer = this.modelMapper.map(freelancerServiceModel, Freelancer.class);

        List<Comment> comments = this.commentRepository.findAllByFreelancer(freelancer);

        return comments
                .stream()
                .map(p -> this.modelMapper.map(p, CommentServiceModel.class))
                .collect(Collectors.toList());
    }
}
