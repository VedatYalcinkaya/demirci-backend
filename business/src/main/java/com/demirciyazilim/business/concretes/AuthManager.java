package com.demirciyazilim.business.concretes;

import com.demirciyazilim.business.abstracts.AuthService;
import com.demirciyazilim.business.abstracts.RefreshTokenService;
import com.demirciyazilim.business.constants.Messages;
import com.demirciyazilim.business.dtos.auth.requests.LoginRequest;
import com.demirciyazilim.business.dtos.auth.requests.RefreshTokenRequest;
import com.demirciyazilim.business.dtos.auth.responses.JwtAuthResponse;
import com.demirciyazilim.business.dtos.user.requests.CreateUserRequest;
import com.demirciyazilim.business.rules.UserBusinessRules;
import com.demirciyazilim.core.security.JwtTokenProvider;
import com.demirciyazilim.core.utilities.exceptions.BusinessException;
import com.demirciyazilim.core.utilities.results.DataResult;
import com.demirciyazilim.core.utilities.results.ErrorDataResult;
import com.demirciyazilim.core.utilities.results.Result;
import com.demirciyazilim.core.utilities.results.SuccessDataResult;
import com.demirciyazilim.core.utilities.results.SuccessResult;
import com.demirciyazilim.entities.RefreshToken;
import com.demirciyazilim.entities.User;
import com.demirciyazilim.entities.enums.Role;
import com.demirciyazilim.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthManager implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final UserBusinessRules userBusinessRules;
    private final RefreshTokenService refreshTokenService;

    @Override
    public DataResult<JwtAuthResponse> login(LoginRequest loginRequest) {
        try {
            // Kimlik doğrulama işlemi gerçekleştirme
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Security context'e kimlik doğrulama bilgisini set etme
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Kullanıcıyı bulma
            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new BusinessException(Messages.USER_NOT_FOUND));
            
            // Son giriş zamanını güncelleme
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            // JWT ve refresh token oluşturma
            String token = tokenProvider.generateToken(authentication);
            String refreshToken = createRefreshTokenForUser(user);
            
            // Yanıt oluşturma
            JwtAuthResponse response = JwtAuthResponse.builder()
                    .accessToken(token)
                    .refreshToken(refreshToken)
                    .userId(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .role(user.getRole().name())
                    .build();

            return new SuccessDataResult<>(response, Messages.LOGIN_SUCCESS);
        } catch (Exception e) {
            return new ErrorDataResult<>(Messages.LOGIN_FAILED + ": " + e.getMessage());
        }
    }

    @Override
    public DataResult<JwtAuthResponse> register(CreateUserRequest registerRequest) {
        try {
            // Kullanıcı adı ve e-posta kontrolleri
            userBusinessRules.checkIfUsernameNotExists(registerRequest.getUsername());
            userBusinessRules.checkIfEmailNotExists(registerRequest.getEmail());
            userBusinessRules.checkIfUsernameIsValid(registerRequest.getUsername());
            userBusinessRules.checkIfEmailIsValid(registerRequest.getEmail());
            userBusinessRules.checkIfPasswordIsValid(registerRequest.getPassword());

            // Yeni kullanıcı oluşturma
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            
            // Ad-soyad 
            String fullName = (registerRequest.getFirstName() + " " + registerRequest.getLastName()).trim();
            user.setFullName(fullName);
            
            // Rol belirleme (varsayılan olarak USER)
            try {
                if (registerRequest.getRole() != null && !registerRequest.getRole().isEmpty()) {
                    user.setRole(Role.valueOf(registerRequest.getRole().toUpperCase()));
                } else {
                    user.setRole(Role.USER);
                }
            } catch (IllegalArgumentException e) {
                user.setRole(Role.USER);
            }
            
            user.setActive(registerRequest.isActive());
            user.setCreatedAt(LocalDateTime.now());
            
            // Kullanıcıyı kaydetme
            User savedUser = userRepository.save(user);

            // JWT ve refresh token oluşturma
            String token = tokenProvider.generateToken(savedUser.getUsername(), savedUser.getRole().name());
            String refreshToken = createRefreshTokenForUser(savedUser);
            
            // Yanıt oluşturma
            JwtAuthResponse response = JwtAuthResponse.builder()
                    .accessToken(token)
                    .refreshToken(refreshToken)
                    .userId(savedUser.getId())
                    .username(savedUser.getUsername())
                    .email(savedUser.getEmail())
                    .role(savedUser.getRole().name())
                    .build();

            return new SuccessDataResult<>(response, Messages.REGISTER_SUCCESS);
        } catch (BusinessException be) {
            return new ErrorDataResult<>(be.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>(Messages.REGISTER_FAILED + ": " + e.getMessage());
        }
    }
    
    @Override
    public Result validateToken(String token) {
        return tokenProvider.validateToken(token) 
                ? new SuccessDataResult<>("Token geçerli") 
                : new ErrorDataResult<>("Token geçersiz");
    }
    
    @Override
    public DataResult<JwtAuthResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            // Refresh token doğrulama
            DataResult<RefreshToken> tokenResult = refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken());
            if (!tokenResult.isSuccess()) {
                return new ErrorDataResult<>(tokenResult.getMessage());
            }
            
            RefreshToken refreshToken = tokenResult.getData();
            
            // Refresh token'ın geçerliliğini kontrol et
            DataResult<RefreshToken> verificationResult = refreshTokenService.verifyExpiration(refreshToken);
            if (!verificationResult.isSuccess()) {
                return new ErrorDataResult<>(verificationResult.getMessage());
            }
            
            // Kullanıcıyı al
            User user = refreshToken.getUser();
            
            // Yeni JWT token oluştur
            String accessToken = tokenProvider.generateToken(user.getUsername(), user.getRole().name());
            
            // Yanıt oluştur
            JwtAuthResponse response = JwtAuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getToken()) // Aynı refresh token'ı kullan
                    .userId(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .role(user.getRole().name())
                    .build();
            
            return new SuccessDataResult<>(response, "Token başarıyla yenilendi");
        } catch (Exception e) {
            return new ErrorDataResult<>("Token yenileme başarısız: " + e.getMessage());
        }
    }
    
    @Override
    public Result logout(String refreshToken) {
        return refreshTokenService.revokeToken(refreshToken);
    }
    
    // Kullanıcı için refresh token oluşturma yardımcı metodu
    private String createRefreshTokenForUser(User user) {
        DataResult<RefreshToken> refreshTokenResult = refreshTokenService.createRefreshToken(user);
        return refreshTokenResult.getData().getToken();
    }
} 