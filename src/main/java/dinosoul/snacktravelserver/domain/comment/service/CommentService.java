package dinosoul.snacktravelserver.domain.comment.service;

import dinosoul.snacktravelserver.domain.comment.dto.RequestCommentDto;
import dinosoul.snacktravelserver.domain.comment.dto.RequestSearchCommentDto;
import dinosoul.snacktravelserver.domain.comment.dto.ResponseCommentDto;
import dinosoul.snacktravelserver.domain.comment.dto.ResponseCommentInformationDto;
import dinosoul.snacktravelserver.domain.comment.entity.Comment;
import dinosoul.snacktravelserver.domain.comment.repository.CommentRepository;
import dinosoul.snacktravelserver.domain.member.entity.Member;
import dinosoul.snacktravelserver.domain.snack.entity.Snack;
import dinosoul.snacktravelserver.domain.snack.repository.SnackRepository;
import dinosoul.snacktravelserver.global.dto.ResponseDataDto;
import dinosoul.snacktravelserver.global.dto.ResponseDatasDto;
import dinosoul.snacktravelserver.global.dto.ResponseStatusDto;
import dinosoul.snacktravelserver.global.globalenum.HttpStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static dinosoul.snacktravelserver.global.globalenum.HttpStatusEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final SnackRepository snackRepository;
    private final CommentRepository commentRepository;

    public ResponseStatusDto createComment(Long snackId, RequestCommentDto requestCommentDto, Member member) {
        Snack findSnack = snackRepository.findById(snackId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 스낵 입니다."));

        Comment comment = Comment.builder()
                .nickname(member.getNickname())
                .content(requestCommentDto.getContent())
                .snackId(snackId)
                .member(member)
                .build();

        commentRepository.save(comment);

        return ResponseStatusDto.builder()
                .message(CREATED.getMessage())
                .statusCode(CREATED.getStatusCode())
                .build();
    }

    public ResponseDatasDto<?> searchComment(RequestSearchCommentDto requestSearchCommentDto) {
        Snack findSnack = snackRepository.findById(requestSearchCommentDto.getSnackId()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 스낵입니다."));
        List<Comment> commentList = commentRepository.findAllBySnackId(requestSearchCommentDto.getSnackId());
        List<ResponseCommentDto> responseCommentDtoList = commentList.stream().map(ResponseCommentDto::new).toList();
        ResponseCommentInformationDto responseCommentInformationDto = ResponseCommentInformationDto.builder()
                .snackId(findSnack.getId())
                .content(findSnack.getContent())
                .videoUrl(findSnack.getVideoUrl())
                .nickname(findSnack.getMember().getNickname())
                .latitude(findSnack.getLatitude())
                .longitude(findSnack.getLongitude())
                .profileImageUrl(findSnack.getProfileImageUrl())
                .location(findSnack.getLocation())
                .build();
        return new ResponseDatasDto<>(responseCommentDtoList, responseCommentInformationDto);
    }

    @Transactional
    public ResponseStatusDto deleteComment(Long commentId, Member member) {

        commentRepository.delete(validateComment(commentId, member));

        return ResponseStatusDto.builder()
                .message(OK.getMessage())
                .statusCode(OK.getStatusCode())
                .build();
    }

    @Transactional
    public ResponseStatusDto updateComment(Long commentId, RequestCommentDto requestCommentDto, Member member) {

        validateComment(commentId, member).update(requestCommentDto);

        return ResponseStatusDto.builder()
                .message(OK.getMessage())
                .statusCode(OK.getStatusCode())
                .build();
    }

    public Comment validateComment(Long commentId, Member member) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 댓글 입니다."));

        if (!member.getId().equals(findComment.getMember().getId())) {
            throw new IllegalArgumentException("댓글 삭제 권한이 없습니다.");
        }
        return findComment;
    }
}
