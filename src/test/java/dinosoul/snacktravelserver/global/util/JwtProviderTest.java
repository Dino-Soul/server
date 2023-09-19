//package dinosoul.snacktravelserver.global.util;
//
//import dinosoul.snacktravelserver.domain.member.entity.Member;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class JwtProviderTest {
//
//    private Member member;
//    private String TOKEN_TYPE = "Bearer ";
//
//    @Autowired
//    JwtProvider jwtProvider;
//
//    @BeforeEach
//    void beforeEach() {
//        member = Member.builder()
//                .id(1L)
//                .nickname("member1")
//                .profileUrl("url1")
//                .loginId("loginId1")
//                .build();
//    }
//
//    @Test
//    @DisplayName("JWT 생성 테스트")
//    void createAccessTokenTest() {
//        String accessToken = jwtProvider.createAccessToken(member);
//
//        assertThat(accessToken.startsWith(TOKEN_TYPE)).isTrue();
//    }
//
//    @Test
//    @DisplayName("생성된 토큰 앞에 token type 제거 테스트")
//    void extractTokenTest() {
//        String accessToken = jwtProvider.createAccessToken(member);
//        String extractToken = jwtProvider.extractToken(accessToken);
//
//        assertThat(accessToken.startsWith(TOKEN_TYPE)).isTrue();
//        assertThat(extractToken.startsWith(TOKEN_TYPE)).isFalse();
//        assertThat(accessToken.length() - extractToken.length()).isEqualTo(TOKEN_TYPE.length());
//    }
//
//    @Test
//    @DisplayName("잘못된 토큰 extract 테스트")
//    void extractTokenFailTest() {
//        String accessToken = jwtProvider.createAccessToken(member);
//        String wrongToken = accessToken.substring(2);
//
//        assertThatThrownBy(() -> jwtProvider.extractToken(wrongToken))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("유효하지 않은 토큰입니다.");
//    }
//
//    @Test
//    @DisplayName("토큰의 유저 정보 조회 테스트")
//    void getIdTest() {
//        String accessToken = jwtProvider.createAccessToken(member);
//        String extractToken = jwtProvider.extractToken(accessToken);
//
//        Long findId = jwtProvider.getId(extractToken);
//        String findNickname = jwtProvider.getNickname(extractToken);
//        String findProfileUrl = jwtProvider.getProfileUrl(extractToken);
//        String findLoginId = jwtProvider.getLoginId(extractToken);
//
//        assertThat(findId).isEqualTo(member.getId());
//        assertThat(findNickname).isEqualTo(member.getNickname());
//        assertThat(findProfileUrl).isEqualTo(member.getProfileUrl());
//        assertThat(findLoginId).isEqualTo(member.getLoginId());
//    }
//
//    @Test
//    @DisplayName("HttpServletRequest 내부의 토큰 정보 조회 테스트")
//    void getTokenTest() {
//        String accessToken = jwtProvider.createAccessToken(member);
//        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
//
//        when(mockHttpServletRequest.getHeader(any())).thenReturn(accessToken);
//
//        String token = jwtProvider.getToken(mockHttpServletRequest);
//
//        assertThat(token).isEqualTo(accessToken);
//    }
//
//}