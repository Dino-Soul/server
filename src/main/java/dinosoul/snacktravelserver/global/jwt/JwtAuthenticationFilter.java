package dinosoul.snacktravelserver.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import dinosoul.snacktravelserver.domain.member.dto.request.RequestLoginDto;
import dinosoul.snacktravelserver.domain.member.entity.Member;
import dinosoul.snacktravelserver.domain.member.repository.MemberRepository;
import dinosoul.snacktravelserver.global.dto.ResponseDataDto;
import dinosoul.snacktravelserver.global.dto.ResponseStatusDto;
import dinosoul.snacktravelserver.global.globalenum.HttpStatusEnum;
import dinosoul.snacktravelserver.global.util.JwtProvider;
import dinosoul.snacktravelserver.global.util.ResponseHttpStatusUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static dinosoul.snacktravelserver.global.globalenum.HttpStatusEnum.*;
import static org.springframework.http.MediaType.*;
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestLoginDto requestLoginDto = objectMapper.readValue(request.getInputStream(), RequestLoginDto.class);
            return authenticationRequest(requestLoginDto);
        } catch (IOException e) {
            throw new RuntimeException("JSON 형식의 데이터인지, nickname, password가 모두 입력되었는지 확인해주세요.");
        }
    }

    private Authentication authenticationRequest(RequestLoginDto requestLoginDto) {
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestLoginDto.getLoginId(),
                        requestLoginDto.getPassword(),
                        null
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        ResponseHttpStatusUtil instance = ResponseHttpStatusUtil.getInstance();
        String nickname = getJwtUserDetailsImpl(authResult).getUsername();
        Member member = memberRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("해당 유저는 없는 유저입니다."));
        String accessToken = jwtProvider.createAccessToken(member);

        ResponseDataDto<ResponseStatusDto> responseDataDto = instance.generatedResponseDto(SUCCESS_LOGIN);

        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(responseDataDto));

        jwtProvider.addJwtToHeader(accessToken, response);
    }

    private JwtUserDetailsImpl getJwtUserDetailsImpl(Authentication authResult) {
        return (JwtUserDetailsImpl) authResult.getPrincipal();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseHttpStatusUtil instance = ResponseHttpStatusUtil.getInstance();
        HttpStatusEnum httpStatus = FAIL_LOGIN;
        ResponseDataDto<ResponseStatusDto> responseDataDto = instance.generatedResponseDto(httpStatus);

        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(responseDataDto));
        response.setStatus(httpStatus.getStatusCode());
    }
}
