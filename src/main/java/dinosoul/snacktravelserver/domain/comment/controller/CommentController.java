package dinosoul.snacktravelserver.domain.comment.controller;

import dinosoul.snacktravelserver.domain.comment.dto.RequestCommentDto;
import dinosoul.snacktravelserver.domain.comment.service.CommentService;
import dinosoul.snacktravelserver.global.dto.ResponseDataDto;
import dinosoul.snacktravelserver.global.dto.ResponseStatusDto;
import dinosoul.snacktravelserver.global.jwt.JwtUserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/comment/snack/{snackId}")
    public ResponseDataDto<?> searchComment(@PathVariable Long snackId) {
        return commentService.searchComment(snackId);
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
