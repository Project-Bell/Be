package org.bell.domain.bell.dto;


import org.bell.domain.bell.entity.Bell;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BellResponseDto extends BellDto{
    private Long id;
    private Long hit;
    public BellResponseDto(Bell bell) {
        super(
                bell.getKeyword(),
                bell.getContent()
        );
        this.id = bell.getId();
        this.hit = bell.getHit();
    }
}
