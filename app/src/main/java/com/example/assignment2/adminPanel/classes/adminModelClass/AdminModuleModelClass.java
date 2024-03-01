package com.example.assignment2.adminPanel.classes.adminModelClass;

public class AdminModuleModelClass {
    private String moduleName;
    private int moduleImg, modulePosition;

    public AdminModuleModelClass(String moduleName, int moduleImg, int modulePosition) {
        this.moduleName = moduleName;
        this.moduleImg = moduleImg;
        this.modulePosition = modulePosition;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getModuleImg() {
        return moduleImg;
    }

    public void setModuleImg(int moduleImg) {
        this.moduleImg = moduleImg;
    }

    public int getModulePosition() {
        return modulePosition;
    }

    public void setModulePosition(int modulePosition) {
        this.modulePosition = modulePosition;
    }
}
