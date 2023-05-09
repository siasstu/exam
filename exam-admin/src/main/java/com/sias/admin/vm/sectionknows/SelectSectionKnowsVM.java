package com.sias.admin.vm.sectionknows;

import lombok.Data;

import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-13 12:27:08
 */
@Data
public class SelectSectionKnowsVM {
    private Long subjectId;
    private List<Long> parentIds;
}
