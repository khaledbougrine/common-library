package dto;

import com.peoplespheres.dto.download.BucketDto;
import org.junit.jupiter.api.Test;
import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BucketDtoTests {
    private final String TITLE = "bucketName";
    private final ZonedDateTime DATE = ZonedDateTime.now();

    void checkBucketDto(BucketDto bucketTested){
        assertThat(bucketTested.getTitle()).isEqualTo(TITLE);
        assertThat(bucketTested.getCreationDate()).isEqualTo(DATE);
    }

    public BucketDto constructFilledBucketDto (){
        return new BucketDto(TITLE, DATE);
    }

    @Test
    void checkAttributes() {
        BucketDto bucketTested = constructFilledBucketDto();
        checkBucketDto(bucketTested);
    }

    @Test
    void checkEmptyConstructorAndSetter() {
        BucketDto bucketTested = new BucketDto();

        assertThat(bucketTested.getTitle()).isNull();
        assertThat(bucketTested.getCreationDate()).isNull();

        bucketTested.setTitle(TITLE);
        bucketTested.setCreationDate(DATE);

        checkBucketDto(bucketTested);
    }

    @Test
    void testBuilder() {
        BucketDto bucketTested = BucketDto.builder()
                .title(TITLE)
                .creationDate(DATE)
                .build();

        checkBucketDto(bucketTested);
    }
}
