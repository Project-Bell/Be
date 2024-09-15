package org.bell.domain.bell.entity;


import lombok.experimental.SuperBuilder;
import org.bell.domain.bell.dto.BellRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.bell.util.timeStamped.Timestamped;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Bell extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String keyword;

    @Column
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long hit;



    public void update(BellRequestDto requestDto){
        this.keyword = requestDto.getKeyword();
        this.content = requestDto.getContent();
    }

}
