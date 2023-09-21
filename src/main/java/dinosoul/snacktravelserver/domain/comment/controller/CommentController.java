package dinosoul.snacktravelserver.domain.comment.controller;

import dinosoul.snacktravelserver.domain.comment.dto.RequestCommentDto;
import dinosoul.snacktravelserver.domain.comment.dto.RequestSearchCommentDto;
import dinosoul.snacktravelserver.domain.comment.service.CommentService;
import dinosoul.snacktravelserver.global.dto.ResponseDataDto;
import dinosoul.snacktravelserver.global.dto.ResponseDatasDto;
import dinosoul.snacktravelserver.global.dto.ResponseStatusDto;
import dinosoul.snacktravelserver.global.jwt.JwtUserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/snack/{snackId}")
    public ResponseStatusDto createComment(@PathVariable Long snackId,
                                           @RequestBody RequestCommentDto requestCommentDto,
                                           @AuthenticationPrincipal JwtUserDetailsImpl jwtUserDetails) {
        return commentService.createComment(snackId, requestCommentDto, jwtUserDetails.getMember());
    }

    @PostMapping("/comments")
    public ResponseDatasDto<?> searchComment(@RequestBody RequestSearchCommentDto requestSearchCommentDto) {
        return commentService.searchComment(requestSearchCommentDto);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseStatusDto deleteComment(@PathVariable Long commentId,
                                           @AuthenticationPrincipal JwtUserDetailsImpl jwtUserDetails) {
        return commentService.deleteComment(commentId, jwtUserDetails.getMember());
    }

    @PutMapping("/comment/{commentId}")
    public ResponseStatusDto updateComment(@PathVariable Long commentId,
                                           @RequestBody RequestCommentDto requestCommentDto,
                                           @AuthenticationPrincipal JwtUserDetailsImpl jwtUserDetails) {
        return commentService.updateComment(commentId, requestCommentDto, jwtUserDetails.getMember());
    }
}
