package com.example.demo;

import com.example.demo.config.EnableCasClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCasClient
public class DemoApplication {

    /*测试：
    先访问:http://leannain.com:8081/index.html,
    系统会跳转到CAS服务器的登录地址。成功后返回到index.html页面。
    此时,如果再请求http://leannain.com:8081/index也会成功。
    同理,先测试index地址,系统也会跳转到CAS服务器的登录地址。
    * */

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
