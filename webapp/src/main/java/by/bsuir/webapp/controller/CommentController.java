package by.bsuir.webapp.controller;

import by.bsuir.webapp.dto.CommentDto;
import by.bsuir.webapp.model.comment.Comment;
import by.bsuir.webapp.model.comment.Rating;
import by.bsuir.webapp.repository.AccountRepository;
import by.bsuir.webapp.repository.comment.CommentRepository;
import by.bsuir.webapp.service.CommentService;
import by.bsuir.webapp.service.security.PersitedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/comments/{tutorId}/list")
    public String getComments(@PathVariable Long tutorId,
                              @RequestParam Optional<Integer> page,
                              @RequestParam(required = false) List<Integer> rating,
                              Model model) {
        Integer pageNum = page.orElse(1);
        PageRequest request = PageRequest.of(pageNum-1, 10, Sort.by(Sort.Direction.DESC,"createdAt"));
        Page<Comment> commentPage;
        if(rating != null && !rating.isEmpty()) {
            List<Rating> list = rating.stream().map(Rating::fromOrder).toList();
            commentPage = commentRepository.findAll(CommentRepository.Specs.byTargetId(tutorId).and(CommentRepository.Specs.byRatingIn(list)), request);
        } else {
            commentPage = commentRepository.findAll(CommentRepository.Specs.byTargetId(tutorId), request);
        }
        model.addAttribute("comments", commentPage.getContent());
        model.addAttribute("totalPages", commentPage.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("addCommentUrl", "/comments/"+tutorId+"/add");
        return "comments/comments";
    }

    @PreAuthorize("isAuthenticated() and !@securityService.isCurrentUserAccountId(#tutorId)")
    @GetMapping("/comments/{tutorId}/add")
    public String getAddCommentForm(@PathVariable Long tutorId, Model model) {
        model.addAttribute("comment", new CommentDto());
        model.addAttribute("tutorId", tutorId);
        return "comments/comment";
    }

    @PreAuthorize("isAuthenticated() and !@securityService.isCurrentUserAccountId(#tutorId)")
    @PostMapping("/comments/{tutorId}/add")
    public String addComment(@ModelAttribute("comment") @Valid CommentDto commentDto,
                                                         BindingResult bindingResult,
                                                         @AuthenticationPrincipal PersitedUser user,
                                                         @PathVariable Long tutorId) {
        if (bindingResult.hasErrors())
            return "comments/comment";

        commentService.createComment(commentDto, user.getAccountId(), tutorId);
        return "redirect:/comments/" + tutorId + "/list";
    }
}
