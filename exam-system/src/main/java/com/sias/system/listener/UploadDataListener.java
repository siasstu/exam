package com.sias.system.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.sias.commons.constant.Constant;
import com.sias.commons.model.SysUser;
import com.sias.commons.utils.StringUtil;
import com.sias.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
public class UploadDataListener implements ReadListener<SysUser> {


    @Resource
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<SysUser> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    @Resource
    private SysUserService sysUserService;

    public UploadDataListener() {
    }

    public UploadDataListener(SysUserService sysUserService,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.sysUserService = sysUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param sysUserService
     */
    /**
     * 这个每一条数据解析都会来调用
     *
     * @param sysUser    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(SysUser sysUser, AnalysisContext context) {
        Date now = new Date();
        sysUser.setCreateTime(now);
        sysUser.setPassword(bCryptPasswordEncoder.encode(Constant.DEFAULT_PASSWORD));
        log.info("解析到一条数据:{}", JSONUtil.parse(sysUser));
        String format = DateUtil.format(now, "yyyy-MM-dd HH:mm:ss");
        sysUser.setRemark(format+"批量导入");
        cachedDataList.add(sysUser);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        sysUserService.save(cachedDataList);
        log.info("存储数据库成功！");
    }
}