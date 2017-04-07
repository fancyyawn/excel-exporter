package top.zhacker.exporter.showcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import top.zhacker.exporter.config.ExporterConfiguration;

/**
 * DATE: 2017/4/7 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@Import(ExporterConfiguration.class)
@SpringBootApplication
public class ShowcaseApplication {

    public static void main(String[] args){
        SpringApplication.run(ShowcaseApplication.class, args);
    }
}
