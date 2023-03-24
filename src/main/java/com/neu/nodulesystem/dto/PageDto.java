package com.neu.nodulesystem.dto;


import lombok.Data;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Data
public class PageDto<R> {
    private Long current;
    private Long total;
    private Long size;
    private List<R> data;

}
