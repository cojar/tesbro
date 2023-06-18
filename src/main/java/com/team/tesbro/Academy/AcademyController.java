package com.team.tesbro.Academy;

import com.team.tesbro.Lesson.Lesson;
import com.team.tesbro.Lesson.LessonService;
import com.team.tesbro.Review.Review;
import com.team.tesbro.Review.ReviewService;
import com.team.tesbro.Teacher.Teacher;
import com.team.tesbro.Teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
@RequestMapping("/academy")
@RequiredArgsConstructor
@Controller
public class AcademyController {

    private final AcademyService academyService;
    private final ReviewService reviewService;
    private final LessonService lessonService;


    @RequestMapping("/list")
    public String academy(Model model, @Param("keyword") String keyword) {
        List<Academy> academyList = this.academyService.getList(keyword);
        model.addAttribute("academyList", academyList);
        model.addAttribute("keyword", keyword);

        return "list";
    }

//    @GetMapping("/detail/{id}")
//    public String detail(Model model, @PathVariable("id") Integer id, @RequestParam(value="page", defaultValue="0") int page) {
//        Academy academy = this.academyService.getAcademy(id);
//        model.addAttribute("academy", academy);
//        Page<Review> paging = this.reviewService.getList(page);
//        model.addAttribute("paging", paging);
//
//
//        List<Lesson> lessonList = lessonService.getAcLesson(id);
//        List<LocalDate> dateList = new ArrayList<>();
//        for (Lesson lesson : lessonList) {
//            LocalDate lessonDate = lesson.getLessonDate();
//            dateList.add(lessonDate);
//        }
//        Set<LocalDate> uniqueDates = new HashSet<>(dateList);
//        List<LocalDate> uniqueDateList = new ArrayList<>(uniqueDates);
//        Collections.sort(uniqueDateList);
//        model.addAttribute("uniqueDateList", uniqueDateList);
//        //학원의 날짜
//        //시간만 손보면 끝납니다잉
//
//        return "academy_detail";
//    }
}
