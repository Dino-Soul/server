package dinosoul.snacktravelserver.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.MediaType.*;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtProvider.getToken(request);

        if (StringUtils.hasText(accessToken)) {
            accessToken = jwtProvider.extractToken(accessToken);

            if (isNotTimeoutToken(accessToken)) {
                ResponseHttpStatusUtil instance = ResponseHttpStatusUtil.getInstance();
                HttpStatusEnum httpStatus = HttpStatusEnum.TOKEN_TIMEOUT;
                ResponseDataDto<ResponseStatusDto> responseDataDto = instance.generatedResponseDto(httpStatus);

                response.setStatus(httpStatus.getStatusCode());
                response.setContentType(APPLICATION_JSON_VALUE);
                response.getWriter().write(objectMapper.writeValueAsString(responseDataDto));
            }

            try {
                String subject = jwtProvider.getUserInfo(accessToken).getSubject();
                setAuthentication(subject);
            } catch (Exception e) {
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isNotTimeoutToken(String extractToken) {
        return !jwtProvider.validateToken(extractToken);
    }

    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) {
        UserDetails jwtUserDetails = jwtUserDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
    }

}
