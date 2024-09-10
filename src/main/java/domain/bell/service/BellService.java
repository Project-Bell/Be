package domain.bell.service;


import domain.bell.dto.BellDto;
import domain.bell.dto.BellRequestDto;
import domain.bell.dto.BellResponseDto;
import domain.bell.entity.Bell;
import domain.bell.repository.BellRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.returnMessage.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BellService {

    private final BellRepository bellRepository;

    //메인페이지조회
    @Transactional
    public ResponseEntity<Message> mainPage(){
        List<Bell> bells = bellRepository.findAll();
        //전체 벨
        List<BellResponseDto> bellResponseDtos = bells.stream()
                .map(BellResponseDto::new)
                .toList();
        //새로올라온 벨(5개씩)
        //핫이슈 벨(4개)-가장 조회수가 높고 채팅수, 참여자수
        //실시간 top10 키워드
        //인기검색
        return null;
    }


    @Transactional
    public ResponseEntity<Message> createBell(BellDto bellDto) {

        Bell bell = Bell.builder()
                .keyword(bellDto.getKeyword())
                .content(bellDto.getContent())
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
