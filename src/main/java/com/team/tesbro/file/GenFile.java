package com.team.tesbro.file;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class GenFile {
    private String relTypeCode;
    private int relId;
    private String typeCode;
    private String type2Code;
    private String fileExtTypeCode;
    private String fileExtType2Code;
    private int fileSize;
    private int fileNo;
    private String fileExt;
    private String fileDir;
    private String originFileName;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;
    @JsonIgnore
    public String getFilePath() {
        return GenFileService.GEN_FILE_DIR_PATH + getBaseFileUri();
    }

    @JsonIgnore
    public String getBaseFileUri() {
        //img 안들어가서 추가함
        // 이 파일 불러오면 사진 보여주기 됨
        return "/" + "img" + "/" + relTypeCode + "/" + fileDir + "/" + getFileName();
    }

    public String getFileName() {
        return getId() + "." + fileExt;
    }
}