package com.ohigraffers.practice.post.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "게시물 등록")
@Getter
@Setter
public class PostCreateRequest {

    @Schema(description = "게시물 제목")
    @NotNull(message = "제목은 반드시 입력되어야 합니다.")
    @NotBlank(message = "제목은 공백일 수 없습니다")
    private String title;

    @Schema(description = "게시물 내용")
    @NotNull(message = "내용은 반드시 입력되어야 합니다.")
    @NotBlank(message = "내용은 공백일 수 없습니다")
    private String content;

    @Schema(description = "게시물 작성자")
    @NotNull(message = "작성자는 반드시 입력되어야 합니다.")
    @NotBlank(message = "작성자는 공백일 수 없습니다")
    private String writer;

}

