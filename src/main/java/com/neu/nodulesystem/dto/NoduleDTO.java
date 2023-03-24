package com.neu.nodulesystem.dto;

import com.neu.nodulesystem.entity.Nodule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoduleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;
    private Long scanId;

    private List<Nodule> noduleList;
}
