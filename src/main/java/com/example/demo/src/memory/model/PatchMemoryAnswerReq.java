package com.example.demo.src.memory.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchMemoryAnswerReq {
    private String context;
    private int memoryAnswerIdx;
}
