package org.bell.domain.bell.entity;


import org.bell.domain.bell.dto.BellRequestDto;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String keyword;

    @Column
    private String content;
    @Column(columnDefinition = "Long default 0", nullable = false)
    private Long hit;



    public void update(BellRequestDto requestDto){
        this.keyword = requestDto.getKeyword();
        this.content = requestDto.getContent();
    }

}
