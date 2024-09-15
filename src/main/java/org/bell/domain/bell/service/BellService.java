package org.bell.domain.bell.service;


import org.bell.domain.bell.dto.BellDto;
import org.bell.domain.bell.dto.BellRequestDto;
import org.bell.domain.bell.dto.BellResponseDto;
import org.bell.domain.bell.entity.Bell;
import org.bell.domain.bell.entity.SearchKeyword;
import org.bell.domain.bell.repository.BellRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bell.domain.bell.repository.SearchKeywordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.bell.returnMessage.Message;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BellService {

    private final BellRepository bellRepository;
    private final SearchKeywordRepository searchKeywordRepository;


    //벨 단일조회
    @Transactional
    public ResponseEntity<Message> readOne(long bellId) {
        Bell bell = findById(bellId);
        if (bell == null) {
            return ResponseEntity.notFound().build();
        }

        // 조회수 증가
        bell.setHit(bell.getHit() + 1);
        bellRepository.save(bell);

        BellResponseDto bellResponseDto = new BellResponseDto(bell);

        return new ResponseEntity<>(new Message("Bell 단일조회 성공", bellResponseDto), HttpStatus.OK);
    }





    //메인페이지조회
    @Transactional
    public ResponseEntity<Message> mainPage(int pageNumber) {
        // 페이지 요청 생성
        Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 전체 벨을 페이지 단위로 가져오기
        Page<Bell> bellPage = bellRepository.findAll(pageable);
        List<BellResponseDto> bellResponseDtos = bellPage.getContent().stream()
                .map(BellResponseDto::new)
                .toList();

        // 총 페이지 수와 현재 페이지 정보 추가
        Map<String, Object> response = new HashMap<>();
        response.put("bells", bellResponseDtos);
        response.put("totalPages", bellPage.getTotalPages());
        response.put("currentPage", pageNumber);

        // 최근 5개 벨 가져오기
        Pageable newBellsPageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Bell> newBellsPage = bellRepository.findAllByOrderByCreatedAtDesc(newBellsPageable);
        List<BellResponseDto> newBells = newBellsPage.getContent().stream()
                .map(BellResponseDto::new)
                .toList();

        response.put("newBells", newBells);

        return new ResponseEntity<>(new Message("Bell 메인페이지 조회 성공", response), HttpStatus.OK);
    }
    //핫이슈 벨(4개)-가장 조회수가 높고 채팅수, 참여자수
    @Transactional
    public ResponseEntity<Message> getTop4Bells() {
        Pageable pageable = PageRequest.of(0, 4);
        List<Bell> topBells = bellRepository.findTop4ByOrderByHitDesc(pageable);

        List<BellResponseDto> topBellsResponse = topBells.stream()
                .map(BellResponseDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(new Message("Top 4 Bells 조회 성공", topBellsResponse), HttpStatus.OK);
    }
    //실시간 top10 키워드
    public List<Map<String, Object>> getTopKeywords() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        Pageable pageable = PageRequest.of(0, 10);

        List<Object[]> results = bellRepository.findTopKeywords(tenMinutesAgo, pageable);

        return results.stream()
                .map(result -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("keyword", result[0]);
                    map.put("viewCount", result[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }

    //검색기능
    @Transactional
    public List<BellResponseDto> searchByKeyword(String keyword) {
        // 검색어 기록 업데이트
        recordSearchKeyword(keyword);

        // Bell 엔티티 검색 로직
        List<Bell> bells = bellRepository.findByKeywordContaining(keyword);
        return bells.stream().map(BellResponseDto::new).toList();
    }

    //인기검색
    public void recordSearchKeyword(String keyword) {
        SearchKeyword searchKeyword = searchKeywordRepository.findByKeyword(keyword)
                .orElse(new SearchKeyword(null, keyword, 0L));
        searchKeyword.incrementCount();
        searchKeywordRepository.save(searchKeyword);
    }

    public List<SearchKeyword> getTopSearchKeywords() {
        return searchKeywordRepository.findTop10ByOrderByCountDesc();
    }


    @Transactional
    public ResponseEntity<Message> createBell(BellDto bellDto) {

        Bell bell = Bell.builder()
                .keyword(bellDto.getKeyword())
                .content(bellDto.getContent())
                .hit(0L)  // hit 필드를 명시적으로 0으로 설정
                .build();

        bellRepository.save(bell);

        BellResponseDto bellResponseDto = new BellResponseDto(bell);

        return new ResponseEntity<>(new Message("Bell 등록 성공",bellResponseDto), HttpStatus.OK);
    }

    //Bell 수정
    @Transactional
    public ResponseEntity<Message> updateBell(Long bellId, Long userId, BellRequestDto requestDto) {

        //user 검증
        //Bell 검증
        Bell bell = findById(bellId);
        bell.update(requestDto);

        bellRepository.save(bell);
        BellResponseDto bellResponseDto = new BellResponseDto(bell);

        return new ResponseEntity<>(new Message("Bell 수정 성공",bellResponseDto), HttpStatus.OK);

    }
    //Bell 삭제
    @Transactional
    public String delete(Long id) {
        Bell bell = findById(id);
        bellRepository.deleteById(bell.getId());
        return String.format(" ID %d번째 게시글이 삭제되었습니다.", bell.getId());
    }

    //벨 찾기
    private Bell findById(Long id) {
        return bellRepository.findById(id).orElseThrow(()->new EntityNotFoundException("해당 Bell 을 찾을 수 없습니다."));
    }


}
