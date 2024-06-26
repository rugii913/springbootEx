package external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.List;
import java.util.Set;

@Slf4j
public class CommandLineV2 {

    public static void main(String[] args) {
        // args로 "--url=devdb --url=devdb2 --username=dev_user --password=dev_pw --activate mode=on"을 넘긴다고 가정
        for (String arg : args) {
            log.info("arg {}", arg); // String[]의 각 element 그대로 출력
        }

        ApplicationArguments appArgs = new DefaultApplicationArguments(args);
        log.info("SourceArgs = {}", List.of(appArgs.getSourceArgs())); // command line 인수 모두 출력
        log.info("NonOptionArgs = {}", appArgs.getNonOptionArgs()); // command line 인수 중 non option 인수 출력
        log.info("OptionNames = {}", appArgs.getOptionNames()); // command line 인수 중 option 인수의 key들을 출력

        Set<String> optionNames = appArgs.getOptionNames();
        for (String optionName : optionNames) {
            log.info("option arg {}={}", optionName, appArgs.getOptionValues(optionName)); // option 인수의 key, value 출력
        }

        List<String> url = appArgs.getOptionValues("url");
        List<String> username = appArgs.getOptionValues("username");
        List<String> password = appArgs.getOptionValues("password");
        List<String> activate = appArgs.getOptionValues("activate");
        List<String> mode = appArgs.getOptionValues("mode");
        log.info("url = {}", url); // url = [devdb, devdb2] → 인수로 넘긴 devdb, devdb2 모두 출력
        log.info("username = {}", username);
        log.info("password = {}", password);
        log.info("activate = {}", activate); // activate = [] → option 인수이지만 value를 주지 않아 빈 list
        log.info("mode = {}", mode); // mode = null → mode는 option 인수가 아니므로 null
    }
}
