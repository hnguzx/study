package main.java;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class SwitchTest {

    @Test
    public void handleFileType() {
        String fileType = "IDCard";
        String realFileType = null;
        if (fileType != null) {
            switch (fileType) {
                case "IDCard":
                case "Passport":
                    realFileType = "ID_CARD_PASSPORT";
                    break;
                case "Adobe_Final_Sign":
                case "Adobe_Open_Sign":
                    realFileType = "AC_OPENING_FORM";
                    break;
                case "CRR_RISK_LEVEL":
                    realFileType = "NAME_SCREENING_CDD";
                    break;
                default:
                    realFileType = fileType;
                    break;
            }
        }
        log.info(realFileType);
    }
}
