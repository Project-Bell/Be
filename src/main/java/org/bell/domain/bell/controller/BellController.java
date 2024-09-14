package org.bell.domain.bell.controller;


import org.bell.domain.bell.dto.BellDto;
import org.bell.domain.bell.dto.BellRequestDto;
import org.bell.domain.bell.service.BellService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.bell.returnMessage.Message;

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
    public ResponseEntity<Message> mainPage() {
        return bellService.mainPage();
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
