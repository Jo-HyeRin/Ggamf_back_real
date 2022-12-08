package shop.ggamf.ggamf.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.ggamf.ggamf.config.auth.LoginUser;
import shop.ggamf.ggamf.config.exception.CustomApiException;
import shop.ggamf.ggamf.domain.user.User;
import shop.ggamf.ggamf.domain.user.UserRepository;
import shop.ggamf.ggamf.dto.UserReqDto.JoinReqDto;
import shop.ggamf.ggamf.dto.UserReqDto.UpdateIntroReqDto.UpdateReqDto;
import shop.ggamf.ggamf.dto.UserReqDto.UpdateIntroReqDto.UpdateStateReqDto;
import shop.ggamf.ggamf.dto.UserReqDto.UpdatePhotoReqDto;
import shop.ggamf.ggamf.dto.UserRespDto.JoinRespDto;
import shop.ggamf.ggamf.dto.UserRespDto.UpdateRespDto;
import shop.ggamf.ggamf.dto.UserRespDto.UpdateStateRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static LoginUser loginuser;

    @Transactional
    public JoinRespDto 회원가입(JoinReqDto joinReqDto) {

        // 1. 비밀번호 암호화
        String rawPassword = joinReqDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        joinReqDto.setPassword(encPassword);
        // 2. 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity());
        // 3. DTO 응답
        return new JoinRespDto(userPS);
    }

    @Transactional
    public UpdateRespDto 회원정보수정(UpdateReqDto updateReqDto) {
        //1. user가 본인인지 체크
        Optional<User> userOP = userRepository.findById(updateReqDto.getId());
        if (!userOP.isPresent()) {
            userRepository.findById(updateReqDto.getId())
                    .orElseThrow(() -> (new CustomApiException("해당유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST)));
        }
        //2. 수정
        User userPS = userOP.get();
        userPS.사진수정(updateReqDto.getPhoto());
        userPS.자기소개수정(updateReqDto.getIntro());
        userPS.닉네임수정(updateReqDto.getNickname());
        userPS.비밀번호수정(updateReqDto.getPassword());
        userPS.전화번호수정(updateReqDto.getPhone());
        userPS.이메일수정(updateReqDto.getEmail());

        return new UpdateRespDto(userPS);
    }

    @Transactional
    public void 사진수정(UpdatePhotoReqDto updatePhotoReqDto, Long id) {
        //1. user가 본인인지 체크
        Optional<User> userOP = userRepository.findById(updatePhotoReqDto.getId());
        if (!userOP.isPresent()) {
            userRepository.findById(updatePhotoReqDto.getId())
                    .orElseThrow(() -> (new CustomApiException("해당유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST)));
        }
        //2. 사진 수정
        //3. Dto리턴
    }

    @Transactional
    public UpdateStateRespDto 회원탈퇴(UpdateStateReqDto updateStateReqDto) {
        //1. user가 본인인지 체크
        Optional<User> userOP = userRepository.findById(updateStateReqDto.getId());
        if (!userOP.isPresent()) {
            userRepository.findById(updateStateReqDto.getId())
                    .orElseThrow(() -> (new CustomApiException("해당유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST)));
        }
        //2. user의 state를 '탈퇴' 로 바꾸기
        User userPS = userOP.get();
        userPS.회원탈퇴(updateStateReqDto.getState());
        return new UpdateStateRespDto(userPS);
    }


    @Transactional
    public void 회원영구삭제(Long id) { //관리자만 가능
        //1. ROLE이 admin이고
        //2. user의 state가 '탈퇴' 상태이면
        //3. 회원 영구삭제
        userRepository.deleteById(id);
    }
}
