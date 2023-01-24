package dto;

import com.peoplespheres.dto.download.FileDto;
import com.peoplespheres.enums.EFunction;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

 class FileDtoTests {
    private final String TITLE = "file.ext";
    private final String DESCRIPTION = "";
    private final String PATH = EFunction.MAIN.getFolder();
    private final String FILENAME = "file.ext";
    private final String CONTENT = "This is the content of the file";
    private final MultipartFile FILE = new MockMultipartFile(FILENAME, CONTENT.getBytes());


    void checkFileDto(FileDto fileTested){
        assertThat(fileTested.getFilename()).isEqualTo(FILENAME);
        assertThat(fileTested.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(fileTested.getTitle()).isEqualTo(TITLE);
        assertThat(fileTested.getPath()).isEqualTo(PATH);
        assertThat(fileTested.getFile()).isEqualTo(FILE);
    }

    public FileDto constructFilledFileDto() {
        return new FileDto(TITLE, DESCRIPTION, FILE, PATH, FILENAME);
    }

    @Test
    void checkAttributes() {
        checkFileDto(constructFilledFileDto());
    }

    @Test
    void checkFileContent() throws IOException {
        FileDto fileTested = constructFilledFileDto();
        assertThat(fileTested.getFile().getBytes()).isEqualTo(CONTENT.getBytes());
    }

    @Test
    void checkEmptyConstructorAndSetter() {
        FileDto fileTested = new FileDto();

        assertThat(fileTested.getFilename()).isNull();
        assertThat(fileTested.getDescription()).isNull();
        assertThat(fileTested.getTitle()).isNull();
        assertThat(fileTested.getPath()).isNull();
        assertThat(fileTested.getFile()).isNull();

        fileTested.setFile(FILE);
        fileTested.setPath(PATH);
        fileTested.setDescription(DESCRIPTION);
        fileTested.setTitle(TITLE);
        fileTested.setFilename(FILENAME);

        checkFileDto(fileTested);
    }

    @Test
    void testBuilder() {
        FileDto fileTested = FileDto.builder()
                .title(TITLE)
                .description(DESCRIPTION)
                .filename(FILENAME)
                .file(FILE)
                .path(PATH)
                .build();

        checkFileDto(fileTested);
    }
}