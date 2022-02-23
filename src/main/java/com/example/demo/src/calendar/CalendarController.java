package com.example.demo.src.calendar;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.calendar.model.GetCalendarPetMoodRes;
import com.example.demo.src.calendar.model.GetCalendarUserMoodRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CalendarProvider calendarProvider;
    @Autowired
    private final CalendarService calendarService;
    @Autowired
    private final JwtService jwtService;

    public CalendarController(CalendarProvider calendarProvider, CalendarService calendarService, JwtService jwtService){
        this.calendarProvider = calendarProvider;
        this.calendarService = calendarService;
        this.jwtService = jwtService;
    }


    /**
     * 유저 기분 년도별 캘린더 조회
     * [GET] /calendar/user-moods/:userId/:diaryIdx
    * */
    @ResponseBody
    @GetMapping("/user-moods/{userIdx}/{diaryIdx}")
    public BaseResponse<List<GetCalendarUserMoodRes>> getUserMood(@PathVariable("userIdx") int userIdx, @PathVariable("diaryIdx") int diaryIdx){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            List<GetCalendarUserMoodRes> getCalendarUserMoodRes = calendarProvider.getUserMood(userIdx, diaryIdx);
            return new BaseResponse<>(getCalendarUserMoodRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 반려동물 기분 년도별 캘린더 조회
     * [GET] /calendar/pet-moods/:userId/:diaryIdx/:petIdx
     * */
    @ResponseBody
    @GetMapping("/pet-moods/{userIdx}/{diaryIdx}/{petIdx}")
    public BaseResponse<List<GetCalendarPetMoodRes>> getPetMood(@PathVariable("userIdx") int userIdx, @PathVariable("diaryIdx") int diaryIdx, @PathVariable("petIdx") int petIdx){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }  // 이 부분까지는 유저가 사용하는 기능 중 유저에 대한 보안이 철저히 필요한 api 에서 사용
            List<GetCalendarPetMoodRes> getCalendarPetMoodRes = calendarProvider.getPetMood(userIdx, diaryIdx, petIdx);
            return new BaseResponse<>(getCalendarPetMoodRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/log")
    public String getAll() {
        System.out.println("테스트");
//        trace, debug 레벨은 Console X, 파일 로깅 X
//        logger.trace("TRACE Level 테스트");
//        logger.debug("DEBUG Level 테스트");

//        info 레벨은 Console 로깅 O, 파일 로깅 X
        logger.info("INFO Level 테스트");
//        warn 레벨은 Console 로깅 O, 파일 로깅 O
        logger.warn("Warn Level 테스트");
//        error 레벨은 Console 로깅 O, 파일 로깅 O (app.log 뿐만 아니라 error.log 에도 로깅 됨)
//        app.log 와 error.log 는 날짜가 바뀌면 자동으로 *.gz 으로 압축 백업됨
        logger.error("ERROR Level 테스트");

        return "Success Test";
    }


}
