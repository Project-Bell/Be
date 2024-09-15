package org.bell.domain.bell.controller;


import org.bell.domain.bell.dto.BellDto;
import org.bell.domain.bell.dto.BellRequestDto;
import org.bell.domain.bell.dto.BellResponseDto;
import org.bell.domain.bell.entity.SearchKeyword;
import org.bell.domain.bell.service.BellService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.bell.returnMessage.Message;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bell/")
@RequiredArgsConstructor
public class BellController {

    private final BellService bellService;
    @GetMapping()
    public String hello() {
        return "Hello World";
    }
    //메인페이지
    @GetMapping("main")
    public ResponseEntity<Message> mainPage(@RequestParam(defaultValue = "0") int pageNumber) {
        return bellService.mainPage(pageNumber);
    }
    //탑키워드조회
    @GetMapping("/top-keywords")
    public ResponseEntity<Message> getTopKeywords() {
        List<Map<String, Object>> topKeywords = bellService.getTopKeywords();
        return new ResponseEntity<>(new Message("Top Keywords 조회 성공", topKeywords), HttpStatus.OK);
    }
    //키워드검색
    @GetMapping("/search")
    public ResponseEntity<Message> search(@RequestParam String keyword) {
        List<BellResponseDto> result = bellService.searchByKeyword(keyword);
        return ResponseEntity.ok(new Message("검색 결과", result));
    }

    @GetMapping("/top-searches")
    public ResponseEntity<Message> getTopSearchKeywords() {
        List<SearchKeyword> topSearchKeywords = bellService.getTopSearchKeywords();
        return ResponseEntity.ok(new Message("인기 검색어", topSearchKeywords));
    }
    //벨 단일조회
    @GetMapping("{bellId}")
    public ResponseEntity<Message> readOne(@PathVariable long bellId) {
        return bellService.readOne(bellId);
    }
    //Bell 등록
    @PostMapping()
    public ResponseEntity<Message> createBell(@RequestBody BellDto bellDto) {
        return bellService.createBell(bellDto);
    }
    //Bell 수정
    @PutMapping("{bellId}")
    public ResponseEntity<Message> updateBell(@PathVariable Long bellId, @RequestBody BellRequestDto bellDto) {
        return bellService.updateBell(bellId,1L,bellDto);
    }
    //Bell 삭제
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return bellService.delete(id);
    }


}
