package com.lizi.fast_fragment.model;

import com.lizi.fast_fragment.sample.SampleListActivity;

/**
 * 类名: Module
 * 此类用途: ---
 *
 * @Author: GuXiao
 * @Date: 2017-06-21 11:21
 * @Email: sage.lu6@gmail.com
 * @FileName: com.lizi.fast_fragment.model.Module.java
 */
public class Module {
    private String moduleName;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Module(String s, Class<SampleListActivity> aClass) {

    }
}
