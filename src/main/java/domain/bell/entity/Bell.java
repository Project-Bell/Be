package domain.bell.entity;


import domain.bell.dto.BellRequestDto;
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



    public void update(BellRequestDto requestDto){
        this.keyword = requestDto.getKeyword();
        this.content = requestDto.getContent();
    }

}
