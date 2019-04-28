package com.mada.commons.enumeration;

/**
 * Created by madali on 2017/4/30.
 */

public enum ServiceEnum {

    //发往Router本身
    RouterService(1),

    //日结月结服务
    EodService(2),

    //Admin服务
    AdminService(3),

    //Email服务
    EmailService(4),

    //Customer服务
    CustomerService(5),

    //单点登录服务
    AuthoService(6),

    //快照服务
    SnapshotService(7),

    //FileService服务
    FileService(8),

    //BackupService服务
    BackupService(18);

    private final int value;

    private ServiceEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public String getZookeeperNodeName() {
        String name = null;

        switch (this) {
            case RouterService:
                name = "routerService";
                break;
            case EodService:
                name = "eodService";
                break;
            case AdminService:
                name = "adminService";
                break;
            case EmailService:
                name = "emailService";
                break;
            case CustomerService:
                name = "customerService";
                break;
            case AuthoService:
                name = "authoService";
                break;
            case SnapshotService:
                name = "snapshotService";
                break;
            case FileService:
                name = "fileService";
                break;
            case BackupService:
                name = "backupService";
                break;
        }

        return name;
    }

    public String description() {
        String description;

        switch (this) {
            case RouterService:
                description = "Router Service";
                break;
            case EodService:
                description = "Eod Service";
                break;
            case AdminService:
                description = "Admin Service";
                break;
            case EmailService:
                description = "Email Service";
                break;
            case CustomerService:
                description = "Customer Service";
                break;
            case AuthoService:
                description = "Autho Service";
                break;
            case SnapshotService:
                description = "Snapshot Service";
                break;
            case FileService:
                description = "File Service";
                break;
            case BackupService:
                description = "Backup Service";
                break;
            default:
                description = this.name();
                break;
        }

        return description;
    }
}
