package enumeration;

/**
 * Created by madl on 2017/5/1.
 */
public enum ServerStateEnum {

    //准备阶段，LoadingManager尚未启动或者处于启动初始化阶段
    Preparing(0),

    //加载阶段
    Loading(1),

    //运行中
    Running(2),

    //退出清理阶段
    ClearPreExit(3);

    private final int value;

    private ServerStateEnum(int value) {
        this.value = value;
    }

    public int value() {

        return this.value;
    }

    public String description() {

        String description;

        switch (this) {

            case ClearPreExit:
                description = "Clear Pre Exit";
                break;
            default:
                description = this.name();
                break;
        }

        return description;
    }
}
