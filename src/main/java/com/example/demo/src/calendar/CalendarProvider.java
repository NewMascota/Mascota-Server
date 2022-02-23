package com.example.demo.src.calendar;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.calendar.model.GetCalendarPetMoodRes;
import com.example.demo.src.calendar.model.GetCalendarUserMoodRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class CalendarProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CalendarDao calendarDao;

    @Autowired
    public CalendarProvider(CalendarDao calendarDao){this.calendarDao = calendarDao;}

    /**
     * 유저 기분 년도별 캘린더 조회
     * [GET] /calendar/user-moods/:userId
     * */
    public List<GetCalendarUserMoodRes> getUserMood(int userIdx, int diaryIdx) throws BaseException {
        try{
            List<GetCalendarUserMoodRes> getCalendarUserMoodRes = calendarDao.getUserMood(userIdx, diaryIdx);
            return getCalendarUserMoodRes;
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }



    /**
     * 반려동물 기분 년도별 캘린더 조회
     * [GET] /calendar/pet-moods/:userId/:diaryIdx/:petIdx
     * */
    public List<GetCalendarPetMoodRes> getPetMood(int userIdx, int diaryIdx, int petIdx) throws BaseException {
        try{
            List<GetCalendarPetMoodRes> getCalendarPetMoodRes = calendarDao.getPetMood(userIdx, diaryIdx, petIdx);
            return getCalendarPetMoodRes;
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }





}
