package by.bsuir.webapp.service;

import by.bsuir.webapp.dto.CommentDto;
import by.bsuir.webapp.model.comment.Comment;
import by.bsuir.webapp.model.comment.Rating;
import by.bsuir.webapp.repository.AccountRepository;
import by.bsuir.webapp.repository.comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    AccountRepository accountRepository;

    public List<Comment> findAll() {
        return null;
    }

    public void createComment(CommentDto commentDto, Long authorId, Long targetId) {
        Comment comment = new Comment();
        comment.setAuthor(accountRepository.getReferenceById(authorId));
        comment.setTarget(accountRepository.getReferenceById(targetId));
        comment.setCreatedAt(new Date());
        comment.setBody(commentDto.getBody());
        comment.setRating(Rating.fromOrder(commentDto.getRating()));
        commentRepository.save(comment);
    }

}
