package jvm;

/**
 * 内存堆Heap：VisualVM 通过检测 JVM 中加载的类和对象信息等帮助我们分析内存使用情况，我们可以通过 VisualVM 的监视标签对应用程序进行内存分析。
 *
 * @Auther: madali
 * @Date: 2018/8/20 17:39
 */
public class JavaHeapDemo {

    public final static int OUTOFMEMORY = 200000000;

    private String oom;

    private int length;

    // StringBuffer类型的 全局变量 tempOOM 占用内存特别大，注意局部变量是无法通过堆dump来得到分析结果的。
    // 另外，对于堆dump来说，在远程监控jvm的时候，VisualVM是没有这个功能的，只有本地监控的时候才有。
    StringBuffer tempOOM = new StringBuffer();

    public JavaHeapDemo(int length) {
        this.length = length;

        int i = 0;
        while (i < length) {
            i++;
            try {
                tempOOM.append("a");
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                break;
            }
        }
        this.oom = tempOOM.toString();
    }

    public String getOom() {
        return oom;
    }

    public int getLength() {
        return length;
    }

    public static void main(String[] args) {
        JavaHeapDemo demo = new JavaHeapDemo(OUTOFMEMORY);
        System.out.println(demo.getOom().length());
    }

}
