package me.parkmookeun.schedule_develop.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    /**
     *
     * @param rawPassword 요청DTO의 비밀번호
     * @return BCrypt로 암호화된 패스워드
     */
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    /**
     *
     * @param rawPassword 요청DTO의 비밀번호
     * @param encodedPassword 암호화된 패스워드
     * @return 입력된 패스워드와 암호화된 패스워드가 서로 일치하는지 여부 반환
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}