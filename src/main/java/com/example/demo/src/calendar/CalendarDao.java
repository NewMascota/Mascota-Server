package com.example.demo.src.calendar;

import com.example.demo.src.calendar.model.GetCalendarPetMoodRes;
import com.example.demo.src.calendar.model.GetCalendarUserMoodRes;
import com.example.demo.src.memory.model.GetNotAnsweredMemoryRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CalendarDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){this.jdbcTemplate = new JdbcTemplate(dataSource);}

    /**
     * 유저 기분 년도별 캘린더 조회
     * [GET] /calendar/user-moods/:userId
     * */
    public List<GetCalendarUserMoodRes> getUserMood(int userIdx, int diaryIdx){
        String Query = "";

        int Params1 = userIdx;
        int Params2 = diaryIdx;

        return this.jdbcTemplate.query(Query,
                (rs, rowNum) -> new GetCalendarUserMoodRes(
                        rs.getDate("calendarDate"),
                        rs.getString("userMood")
                ),Params1, Params2);
    }

    /**
     * 반려동물 기분 년도별 캘린더 조회
     * [GET] /calendar/pet-moods/:userId/:diaryIdx/:petIdx
     * */
    public List<GetCalendarPetMoodRes> getPetMood(int userIdx, int diaryIdx, int petIdx){
        String Query = "";

        int Params1 = userIdx;
        int Params2 = diaryIdx;
        int Params3 = petIdx;

        return this.jdbcTemplate.query(Query,
                (rs, rowNum) -> new GetCalendarPetMoodRes(
                        rs.getDate("calendarDate"),
                        rs.getString("petMood")
                ),Params1, Params2, Params3);
    }
}
