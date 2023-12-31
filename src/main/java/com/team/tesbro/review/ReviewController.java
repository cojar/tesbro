package com.team.tesbro.review;

import com.team.tesbro.academy.Academy;
import com.team.tesbro.academy.AcademyService;
import com.team.tesbro.user.SiteUser;
import com.team.tesbro.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/review")
@RequiredArgsConstructor
@Controller
public class ReviewController {
    private final AcademyService academyService;
    private final ReviewService reviewService;
    private final UserService userService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String reviewCreate(Model model, @PathVariable("id") Integer id, @Valid ReviewForm reviewForm, BindingResult bindingResult, Principal principal){
        Academy academy = this.academyService.getAcademy(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("academy", academy);
            return "academy_detail";
        }
        Review review = this.reviewService.create(academy, reviewForm.getContent(), reviewForm.getStar_rating(), siteUser);
        return String.format("redirect:/academy/detail/%d#review_%d", review.getAcademy().getId(), review.getId());
    }




    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String reviewDelete(Principal principal, @PathVariable("id") Integer id) {
        Review review = this.reviewService.getReview(id);
        if (!review.getUserId().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.reviewService.delete(review);
        return String.format("redirect:/academy/detail/%d", review.getAcademy().getId());
    }



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String reviewModify(ReviewForm reviewForm, @PathVariable("id") Integer id, Principal principal) {
        Review review = this.reviewService.getReview(id);
        if (!review.getUserId().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        reviewForm.setContent(review.getContent());
        reviewForm.setStar_rating(review.getStar_rating());
        return "review_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String reviewModify(@Valid ReviewForm reviewForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "review_form";
        }
        Review review = this.reviewService.getReview(id);
        if (!review.getUserId().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.reviewService.modify(review, reviewForm.getContent(), reviewForm.getStar_rating());
        return String.format("redirect:/academy/detail/%d#review_%d", review.getAcademy().getId(), review.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String reviewVote(Principal principal, @PathVariable("id") Integer id) {
        Review review = this.reviewService.getReview(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.reviewService.vote(review, siteUser);
        return String.format("redirect:/academy/detail/%d#review_%d", review.getAcademy().getId(), review.getId());
    }
}
