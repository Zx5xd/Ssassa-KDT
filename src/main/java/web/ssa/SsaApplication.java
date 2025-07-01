package web.ssa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;  // 추가된 부분

@SpringBootApplication
@EnableScheduling  // 스케줄링 활성화
public class SsaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsaApplication.class, args);
    }

}
