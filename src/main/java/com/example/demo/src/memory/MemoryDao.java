package com.example.demo.src.memory;

import com.example.demo.src.memory.model.*;
import com.example.demo.src.my.model.PatchPetStatusReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MemoryDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 추억하기 전체 질문 조회 (답변하기 탭)
     * [GET] /memories/all/notAnsweredMemories/:userIdx/:petIdx
     * @return BaseResponse<List<GetNotAnsweredMemoryRes>>
     * */
    public List<GetNotAnsweredMemoryRes> getNotAnsweredMemory(int userIdx, int petIdx){
        String Query = "SELECT mq.idx as questionNum, REPLACE(mq.context, '*', " +
                "(SELECT p.name FROM pet p WHERE p.idx = ? )) as question,\n" +
                "       case ma.context\n" +
                "        when (SELECT ma.context FROM memory_answer ma " +
                "WHERE ma.petIdx = ? AND mq.idx = ma.mqIdx ) then '답변을 입력하세요'\n" +
                "           else '답변을 입력하세요' end as answer\n" +
                "FROM memory_question mq\n" +
                "LEFT JOIN memory_answer ma on mq.idx = ma.mqIdx\n" +
                "where (SELECT ma.context FROM memory_answer ma " +
                "WHERE ma.petIdx = ? AND mq.idx = ma.mqIdx) is null or ma.status = 'N';";

        int Params = petIdx;

        return this.jdbcTemplate.query(Query,
                (rs, rowNum) -> new GetNotAnsweredMemoryRes(
                        rs.getInt("questionNum"),
                        rs.getString("question"),
                        rs.getString("answer")
                ),Params, Params, Params);
    }



    /**
     * 추억하기 전체 질문 조회 (모아보기 탭) (질문인덱스순, 최신순, 오래된순)
     * [GET] /memories/all/answeredMemories/:userIdx/:petIdx
     * @return BaseResponse<List<GetAnsweredMemoryRes>>
     * */
    public List<GetAnsweredMemoryRes> getAnsweredMemory(int petIdx, String order){
        // 기본 - 질문 인덱스 순
        String Query1 = "SELECT mq.idx AS questionNum,\n" +
                "       REPLACE(mq.context, '*', p.name) as question,\n" +
                "       ma.context AS answer\n" +
                "        , date_format(ma.updatedAt, '%Y년 %m월 %d일') as updatedAt\n" +
                "FROM pet p\n" +
                "    LEFT JOIN memory_answer ma on p.idx = ma.petIdx\n" +
                "         LEFT JOIN memory_question mq on mq.idx = ma.mqIdx\n" +
                "WHERE ma.petIdx = ? and ma.status = 'Y'\n" +
                "ORDER BY questionNum;";
        // 최신순
        String Query2 = "SELECT mq.idx AS questionNum,\n" +
                "       REPLACE(mq.context, '*', p.name) as question,\n" +
                "       ma.context AS answer\n" +
                "        , date_format(ma.updatedAt, '%Y년 %m월 %d일') as updatedAt\n" +
                "FROM pet p\n" +
                "         LEFT JOIN memory_answer ma on p.idx = ma.petIdx\n" +
                "         LEFT JOIN memory_question mq on mq.idx = ma.mqIdx\n" +
                "WHERE ma.petIdx = ? and ma.status = 'Y'\n" +
                "ORDER BY ma.updatedAt DESC;";

        // 오래된 순
        String Query3 = "SELECT mq.idx AS questionNum,\n" +
                "       REPLACE(mq.context, '*', p.name) as question,\n" +
                "       ma.context AS answer\n" +
                "        , date_format(ma.updatedAt, '%Y년 %m월 %d일') as updatedAt\n" +
                "FROM pet p\n" +
                "         LEFT JOIN memory_answer ma on p.idx = ma.petIdx\n" +
                "         LEFT JOIN memory_question mq on mq.idx = ma.mqIdx\n" +
                "WHERE ma.petIdx = ? and ma.status = 'Y'\n" +
                "ORDER BY ma.updatedAt ASC;";

        int Params = petIdx;

        if (order == null){
            return this.jdbcTemplate.query(Query2,
                    (rs, rowNum) -> new GetAnsweredMemoryRes(
                            rs.getInt("questionNum"),
                            rs.getString("question"),
                            rs.getString("answer"),
                            rs.getString("updatedAt")
                    ),Params);
        }
        if (order.equals("latest")){
            return this.jdbcTemplate.query(Query3,
                    (rs, rowNum) -> new GetAnsweredMemoryRes(
                            rs.getInt("questionNum"),
                            rs.getString("question"),
                            rs.getString("answer"),
                            rs.getString("updatedAt")
                    ),Params);
        }
        return this.jdbcTemplate.query(Query1,
                (rs, rowNum) -> new GetAnsweredMemoryRes(
                        rs.getInt("questionNum"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("updatedAt")
                ),Params);


    }



    /**
     * 추억하기 답변 조회
     * [GET] /memories/one/:userIdx/:memoryAnswerIdx
     * @return BaseResponse<List<GetOneMemoryRes>>
     * */
    public List<GetOneMemoryRes> getOneMemory(int userIdx, int memoryAnswerIdx){
        String Query = "SELECT mq.idx as questionNum, REPLACE(mq.context, '*', p.name) as question,\n" +
                "       ma.context AS answer\n" +
                "        , date_format(ma.updatedAt, '%Y년 %m월 %d일') as updatedAt\n" +
                "FROM pet p\n" +
                "         LEFT JOIN memory_answer ma on p.idx = ma.petIdx\n" +
                "         LEFT JOIN memory_question mq on mq.idx = ma.mqIdx\n" +
                "WHERE ma.idx = ? and ma.mqIdx = mq.idx and ma.context IS NOT NULL and ma.status = 'Y';";

        int Params = memoryAnswerIdx;

        return this.jdbcTemplate.query(Query,
                (rs, rowNum) -> new GetOneMemoryRes(
                        rs.getInt("questionNum"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("updatedAt")
                ),Params);
    }

    /**
     * 추억하기 답변 작성 API
     * [POST] /memories/one/answer/:userIdx/:petIdx/:memoryQuestionIdx
     * @return BaseResponse<String>
     */
    public int createMemoryAnswer(PostMemoryAnswerReq postMemoryAnswerReq){
        String createUserQuery = "insert into memory_answer (petIdx, mqIdx, context) VALUES (?,?,?)";
        Object[] createUserParams = new Object[]{postMemoryAnswerReq.getPetIdx(), postMemoryAnswerReq.getMemoryQuestionIdx(), postMemoryAnswerReq.getContext()};

        return this.jdbcTemplate.update(createUserQuery, createUserParams);
    }

    /**checkAnswerExist*/
    public int checkMemoryAnswerExist(int petIdx, int questionIdx){
        String Query = "select exists(select ma.mqIdx from memory_answer ma where petIdx = ? and mqIdx = ?)";
        int Params1 = petIdx;
        int Params2 = questionIdx;
        return this.jdbcTemplate.queryForObject(Query,
                int.class,
                Params1, Params2);
    }

    /**
     * 추억하기 답변 수정 API
     * [PATCH] /memories/one/answer/:userIdx/:memoryAnswerIdx
     * @return BaseResponse<String>
     */
    public int modifyMemoryAnswer(PatchMemoryAnswerReq patchMemoryAnswerReq){
        String Query = "update memory_answer ma set ma.context = ? where ma.idx = ? and status = 'Y'";
        Object[] Params = new Object[]{patchMemoryAnswerReq.getContext(), patchMemoryAnswerReq.getMemoryAnswerIdx()};

        return this.jdbcTemplate.update(Query,Params);
    }


    /**
     * 추억하기 답변 삭제 API
     * [PATCH] /memories/one/answer/:userIdx/:memoryAnswerIdx/status
     * @return BaseResponse<String>
     */
    public int deleteMemoryAnswer(PatchMemoryAnswerStatusReq patchMemoryAnswerStatusReq){
        String modifyOrderQuery = "UPDATE memory_answer set status = ? where idx = ? and status = 'Y';";
        Object[] modifyOrderParams = new Object[]{patchMemoryAnswerStatusReq.getStatus(), patchMemoryAnswerStatusReq.getMemoryAnswerIdx()};

        return this.jdbcTemplate.update(modifyOrderQuery,modifyOrderParams);
    }
    /**checkNotExist
     * memoryAnswerIdx가 데이터베이스에 존재하지 않을 때 예외처리
     */
    public int checkMANotExist(int memoryAnswerIdx){
        String checkQuery = "select exists(select idx from memory_answer where idx = ?)";
        int checkParams = memoryAnswerIdx;
        return this.jdbcTemplate.queryForObject(checkQuery,
                int.class,
                checkParams);
    }
    /**checkMAAlreadyDelete
     * status가 N일 때, 이미 삭제된 답변입니다.*/
    public int checkMAAlreadyDelete(int memoryAnswerIdx){
        String checkQuery = "select (case status when 'N' THEN 0\n" +
                "                    when 'Y' then 1\n" +
                "    end) as 'checkStatus'\n" +
                "    from memory_answer where idx = ?;";
        int checkParams = memoryAnswerIdx;
        return this.jdbcTemplate.queryForObject(checkQuery,
                int.class,
                checkParams);
    }


}
