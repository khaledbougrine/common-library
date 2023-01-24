package enums;

import com.peoplespheres.enums.EFunction;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class EFunctionTests {
    @Test
    void checkMainFolder(){
        assertThat(EFunction.MAIN.getFolder()).isEmpty();
    }

    @Test
    void checkDraftFolder(){
        assertThat(EFunction.DRAFT.getFolder()).isEqualTo("draft/");
    }

    @Test
    void checkReportFolder(){
        assertThat(EFunction.REPORT.getFolder()).isEqualTo("report/");
    }

    @Test
    void getListOfFunction(){
        String[] funcs = {"MAIN", "DRAFT", "REPORT"};
        String[] funcs_from_enum = EFunction.names();

        assertThat(funcs_from_enum).isEqualTo(funcs);
    }
}
