package com.sias.admin.domain;

import com.sias.admin.domain.enums.QuestionTypeEnum;
import com.sias.commons.utils.ExamUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName question
 */
@Data
public class Question implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 问题类型
     */
    private Integer questionType;

    /**
     * 所属学科
     */
    private Integer courseId;

    /**
     * 选择题答案
     */
    private String correct;

    /**
     * 问答题答案
     */
    private Integer infoTextContentId;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    private Boolean deleted;

    public void setCorrectFromVM(String correct, List<String> correctArray) {
        int qType = this.getQuestionType();
        if (qType == QuestionTypeEnum.MultipleChoice.getCode()) {
            String correctJoin = ExamUtil.contentToString(correctArray);
            this.setCorrect(correctJoin);
        } else {
            this.setCorrect(correct);
        }
    }

    private static final long serialVersionUID = 1L;
}