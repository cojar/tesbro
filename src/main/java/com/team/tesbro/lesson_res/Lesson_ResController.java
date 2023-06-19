package com.team.tesbro.lesson_res;

import com.team.tesbro.Lesson.Lesson;
import com.team.tesbro.Lesson.LessonRepository;
import com.team.tesbro.User.SiteUser;
import com.team.tesbro.User.UserRepository;
import com.team.tesbro.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class Lesson_ResController {
    private final Lesson_ResService lesson_resService;
    private final LessonRepository lessonRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reserve/{id}")
    public String reserveLesson(@RequestParam String datePicker, @RequestParam String childSelectBox,
                                @PathVariable("id") Integer id, @Valid Lesson_ResDto lessonResDto,
                                BindingResult bindingResult, Principal principal) {
        // 유저 이름 필요
        //string => 날짜 시간 타입으로 변경
        LocalDate date = LocalDate.parse(datePicker, DateTimeFormatter.ISO_DATE);
        LocalTime time = LocalTime.parse(childSelectBox, DateTimeFormatter.ISO_TIME);
        // 콘솔 데이터 확인
        System.out.println(id);
        System.out.println(date);
        System.out.println(time);

        String currentUsername = principal.getName();
        Optional<SiteUser> currentUser = userRepository.findByusername(currentUsername);

        Integer currentUserId = currentUser.map(SiteUser::getId).orElse(null);
        lessonResDto.setBookedUsersId(Collections.singletonList(currentUserId));

        Optional<Lesson> lesson = lesson_resService.findLessonId(date, time, id);
        Integer lessonId = lesson.get().getId();
        Lesson_Res lessonRes = new Lesson_Res();
        lessonRes.setBookDate(LocalDateTime.now());
        System.out.println(lessonResDto);
        //레슨 테이블에 등록인원 추가 로직
        this.lesson_resService.reserve(lessonResDto, lessonId, currentUserId);
        System.out.println("예약됨");
        return "redirect:/reserve";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reserve")
    public String onlesson(Model model, Principal principal) {
        String currentUsername = principal.getName();
        Optional<SiteUser> currentUser = userRepository.findByusername(currentUsername);

        Integer currentUserId = currentUser.map(SiteUser::getId).orElse(null);
        List<Lesson_Res> lessonResList = lesson_resService.findUserRes(currentUserId);
        model.addAttribute("lessonResList", lessonResList);
        System.out.println(lessonResList);
        return "reserve";
    }
}