package com.mada.common.util.ketama;

import java.util.TreeMap;

/**
 * 一致性hash算法
 *
 * @param <T>
 */
public final class ConsistentHashing<T> {

    private final TreeMap<Long, T> nodes = new TreeMap<Long, T>();
    private final HashAlgorithm hashAlg = HashAlgorithm.KETAMA_HASH;
    private final int virtualNodeCount; //Ketama算法的虚拟节点数

    //单例
//    private volatile static ConsistentHashing singleton;
//
//    //双重校验锁
//    public static <T> ConsistentHashing<T> getInstance(HashSet<T> nodes, int virtualNodeCount) {
//
//        if (singleton == null) {
//            synchronized (ConsistentHashing.class) {
//                if (singleton == null) {
//                    singleton = new ConsistentHashing(nodes, virtualNodeCount);
//                }
//            }
//        }
//
//        return singleton;
//    }

    public ConsistentHashing(int virtualNodeCount) {
        this.virtualNodeCount = virtualNodeCount;
    }

    public ConsistentHashing() {
        this(160);
    }

//    private ConsistentHashing(HashSet<T> nodes, int virtualNodeCount) {
//        this.virtualNodeCount = virtualNodeCount;
//
//        for (T node : nodes) {
//            this.addNode(node);
//        }
//    }

    /**
     * 一致性hash算法获取对象
     *
     * @param hash
     * @return
     */
    private T getNodeForKey(long hash) {
        final T rv;
        Long key = hash;
        if (!this.nodes.isEmpty() && !this.nodes.containsKey(key)) {
            key = this.nodes.ceilingKey(key);
            if (key == null) {
                key = this.nodes.firstKey();
            }
        }
        rv = this.nodes.get(key);

        return rv;
    }

    /**
     * 添加节点
     *
     * @param node
     */
    public void addNode(T node) {
        for (int i = 0; i < this.virtualNodeCount / 4; i++) {
            byte[] digest = this.hashAlg.computeMd5(node.toString() + i);
            for (int h = 0; h < 4; h++) {
                long m = this.hashAlg.hash(digest, h);
                this.nodes.put(m, node);
            }
        }
    }

    /**
     * 删除节点
     *
     * @param node
     */
    public void removeNode(T node) {
        for (int i = 0; i < this.virtualNodeCount / 4; i++) {
            byte[] digest = this.hashAlg.computeMd5(node.toString() + i);
            for (int h = 0; h < 4; h++) {
                long m = this.hashAlg.hash(digest, h);
                this.nodes.remove(m);
            }
        }
    }

    /**
     * 一致性hash算法获取对象
     *
     * @param key
     * @return
     */
    public T getNode(final String key) {
        byte[] digest = this.hashAlg.computeMd5(key);
        T rv = this.getNodeForKey(this.hashAlg.hash(digest, 0));

        return rv;
    }
}
