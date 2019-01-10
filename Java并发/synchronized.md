# Synchronized实现原理与使用 #

synchronized最初是以重量级锁的形式出现，随着JDK1.6对其进行优化后,synchronized并不会显得那么重。

## 1.实现原理

Java中每一个对象都可以作为锁，这是synchronized实现同步的基础。其的作用域主要有：

1. 放在普通方法上，作用于调用这个方法的对象，内置锁作用域该实例对象，所谓的互斥锁即为独占锁。
2. 修饰静态方法时，内置锁作用域是当前Class字节码对象，作用于类的所有对象。
3. 修饰类：括号括起来的部分，作用于类所有对象。
4. 修饰**代码块**时，内置锁作用Synchronized作用域括号里配置的对象。

## 2.synchronized是如何实现所得获取与释放的？

同步代码块使用monitorenter和monitorexit指令实现，同步方法依靠方法修饰符上的ACC_SYNCHRONIZED实现。


- 同步代码块：monitorenter指令插入到同步代码块的开始位置，monitorexit指令插入到同步代码块的结束位置。JVM需要保证每一个monitorenter都有一个monitorexit与之对应。任何对象都有一个monitor与之关联，并且一个monitor被持有之后，它将处于锁定状态。线程执行到monitorenter指令时，将会尝试获取对象所对应的monitor所有权，及尝试获取对象锁。
- 同步方法：synchronized方法会被翻译成普通方法和返回指令：invokevirtual、areturn指令，在VM字节码层面并没有任何特别的指令来实现被synchronized修饰的方法，而是在Class文件方法表中将该方法的access_flags字段中synchronized标志位置1，表示该方法是同步方法并使用调用该方法的对象或该方法所属的Class在JVM的内部对象表示为锁对象。

## 3.Java对象头、monitor

### 1）对象头

synchronized使用的锁是在Java对象头里的，Hotspot虚拟机对象头主要包括两部分数据：Mark Word(标记字段),类型指针。其中类型指针是对象指向它类元数据的指针，虚拟机通过这个指针来确定这个对象是哪个实例，Mark Word用于存储对象自身的运行时数据，它是实现轻量级锁和偏向锁的关键。






