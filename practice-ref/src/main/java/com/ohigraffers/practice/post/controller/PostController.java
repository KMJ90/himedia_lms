package com.ohigraffers.practice.post.controller;
import com.ohigraffers.practice.post.dto.request.PostCreateRequest;
import com.ohigraffers.practice.post.dto.request.PostUpdateRequest;
import com.ohigraffers.practice.post.dto.response.PostResponse;
import com.ohigraffers.practice.post.dto.response.ResponseMessage;
import com.ohigraffers.practice.post.model.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static com.ohigraffers.practice.post.dto.response.PostResponse.from;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/* Swagger 문서화 시 Grouping 작성 */
@Tag(name = "post 관련 api")
@RestController
@RequestMapping("/posts")
public class PostController {

    private List<Post> posts;

    public PostController(){
        posts = new ArrayList<>();
        posts.add(new Post(1L, "제목1", "내용1", "홍길동"));
        posts.add(new Post(2L, "제목2", "내용2", "유관순"));
        posts.add(new Post(3L, "제목3", "내용3", "신사임당"));
        posts.add(new Post(4L, "제목4", "내용4", "이순신"));
        posts.add(new Post(5L, "제목5", "내용5", "장보고"));
    }

    /* 1. 전체 포스트 조회 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    @Operation(summary = "전체 게시물 조회", description = "전체 게시물 목록을 조회한다")
    @GetMapping("/select")
    public ResponseEntity<ResponseMessage> findAllPosts() {

        /* 응답 헤더 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", StandardCharsets.UTF_8));

        /* hateoas 적용 및 post 타입을 postResponse 타입으로 변환 후 반환 */
        List<EntityModel<PostResponse>> postResponse = posts.stream().map(
                post ->
                        EntityModel.of(
                                from(post),
                                linkTo(methodOn(PostController.class).findAllPosts()).withRel("posts"),
                                linkTo(methodOn(PostController.class).findPostByCode(post.getCode())).withSelfRel()
                        )
        ).toList();

        /* 응답 데이터 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("posts", posts);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        /* ResponseEntity 반환 */
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    /* 2. 특정 코드로 포스트 조회 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    @Operation(summary = "게시물 코드로 게시물 조회", description = "게시물 코드를 통해 해당하는 게시물 정보를 조회")
    @GetMapping("/select/{postCode}")
    public ResponseEntity<ResponseMessage> findPostByCode(@PathVariable Long postCode) {

        /* 응답 헤더 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json",StandardCharsets.UTF_8));

        /* Post 타입은 PostResponse 타입으로 변환해서 반환 */
        PostResponse foundPost = PostResponse.from(posts.stream().filter(post -> post.getCode() == postCode).toList().get(0));

        /* 응답 데이터 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("post", foundPost);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        /* ResponseEntity 반환 */
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(responseMessage);
    }

    /* 3. 신규 포스트 등록 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    @Operation(summary = "신규 게시물 등록", description = "신규 게시물 코드를 통해 포스트 등록")
    @PostMapping("/insert")
   public ResponseEntity<Void> registPost(@RequestBody PostCreateRequest newPost) {

       /* 리스트에 추가 */
        Long lastPostCode = posts.get(posts.size() - 1).getCode() + 1;
        posts.add(new Post(lastPostCode,newPost.getTitle(),newPost.getContent(), newPost.getWriter()));

       /* ResponseEntity 반환 */
       return ResponseEntity
               .created(URI.create("/swagger/posts" + posts.get(posts.size() - 1).getCode()))
               .build();
   }

   /* 4. 포스트 제목과 내용 수정 */
   /* Swagger 문서화 시 설명 어노테이션 작성 */
    @Operation(summary = "게시물 제목, 내용 수정")
    @PutMapping("/modify/{postCode}")
    public ResponseEntity<Void> modifyPost(@PathVariable Long postCode, @RequestBody PostUpdateRequest modifyInfo) {

        /* 리스트에서 찾아서 수정 */
        Post foundPost = posts.stream().filter(post -> post.getCode() == postCode).toList().get(0);
        /* 수정 메소드 활용 */
        foundPost.modifyTitleAndContent(modifyInfo.getTitle(), modifyInfo.getContent());

        /* ResponseEntity 반환 */
        return ResponseEntity
                .created(URI.create("/posts/modify/" + postCode))
                .build();
    }

    /* 5. 포스트 삭제 */
    @Operation(summary = "게시물 삭제")
    @DeleteMapping("/delete/{postCode}")
    public ResponseEntity<Void> removeUser(@PathVariable Long postCode) {

        /* 리스트에서 찾아서 삭제 */
        Post foundPost = posts.stream().filter(post -> post.getCode() == postCode).toList().get(0);
        posts.remove(foundPost);

        /* ResponseEntity 반환 */
        return ResponseEntity
                .noContent()
                .build();
    }

}
