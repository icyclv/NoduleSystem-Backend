package com.neu.nodulesystem.dto;

import com.neu.nodulesystem.entity.Scan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdScanDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    MultipartFile file;
    Scan scan;
}
