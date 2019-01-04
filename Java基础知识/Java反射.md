# Java反射 #

# 1.什么是反射？ #

在Java程序**运行状态**中，对于任意一个类都能够知道这个类的所有属性和方法；对于任意一个对象，都能调用它的任意一个方法和属性。通过反射，我们可以在运行时获取程序或程序集中每一个成员和成员信息。程序的一般对象类型都是在编译器确定下来，而Java反射机制可以动态的创建对象并调用其属性，这样的对象的类型在编译器是未知的。所以可以通过反射机制直接创建对象，即使这个对象的类型在编译器是未知的。

总得说来反射的核心是JVM在**运行时**才动态加载类或调用方法/访问属性，它不需要事先知道运行对象是谁。

# 2.Java反射主要功能 #

1. 在**运行**时判断任意一个对象所属的类。
2. 在**运行**时构造任意一个类的对象。
3. 在**运行**时判断任意一个类所具有的成员变量和方法(通过反射甚至调用private方法)。
4. 在**运行**时调用任意一个对象的方法。

# 3.反射的主要用途 #

1. Java开发中的IDE，输入对象或类访问其属性和方法。
2. 在各种框架(如Spring)中，通过XML文件配置Bean时，需要使用反射在运行时动态加载所需要的对象。

# 4.反射的基本运用 #

## 1）获取Class对象 ##

获取Class对象的方式有三种：

a.通过创建一个对象，并调用对象的getClass方法获取Class对象。

       //1.通过new产生一个Student对象和一个Class对象
        Student stu1 = new Student();
        Class stuClass = stu1.getClass();
        System.out.println(stuClass.getName());

b.直接获取Class对象

        //2.直接获取一个Class对象
        Class stuClass2 = Student.class;
        System.out.println(stuClass == stuClass2);

c.使用Class类的forName静态方法：

       //通过Class的静态方法forName获取Class的对象
        try {
            Class stuClass3 = Class.forName("zl.reflect.Student");
            System.out.println(stuClass2 == stuClass3);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

通过打印结果，我们可以发现，在运行期，一个类只有一个Class对象产生。

## 2）通过反射获取构造方法并使用 ##

a.所有的构造方法

    conArray =  clazz.getDeclaredConstructors();

b.所有公有的构造方法


    Constructor[] conArray = clazz.getConstructors();

c.获取单个公有方法，可以传入属性的.class

无参构造函数

    Constructor con = clazz.getConstructor();
    
可以传入变量

    Constructor con2 = clazz.getConstructor(String.class);

d.获取所有构造方法中的一个，具体有传入参数决定

    con = clazz.getDeclaredConstructor(char.class);

也可以通过调用Constructor类的newInstance方法调用构造方法。

        obj = con.newInstance('男');
        obj = con2.newInstance("zlcalma");

## 3）获取成员变量并调用 ##

a.获取所有公有字段：Field[] getFields()

       //获取字段
        System.out.println("*************获取所有公有的字段************");
        Field[] fieldArray = stuClass.getFields();
        for (Field f:fieldArray){
            System.out.println(f);
        }

b.获取所有字段：Field[] getDeclaredFields():获取所有字段

        System.out.println("*******获取所有的字段*************");
        fieldArray = stuClass.getDeclaredFields();
        for(Field f:fieldArray){
            System.out.println(f);
        }

c.获取某个公有字段

        System.out.println("**************获取某个公有字段并调用***********");
        Field f = stuClass.getField("name");
        System.out.println(f);

d.获取某个任意字段(可以私有也可以公有)

        System.out.println("************获取私有字段并调用**************");
        f = stuClass.getDeclaredField("phoneNum");
        System.out.println(f);

设置字段的值：

          Field --> public void set(Object obj,Object value):
                    参数说明：
                    1.obj:要设置的字段所在的对象；
                    2.value:要为字段设置的值；

        //为字段设置值
        f.set(obj,"刘德华");
        //验证
        Person stu = (Person) obj;
        System.out.println("验证姓名：" + stu.name);

## 4）获取成员方法并调用 ##

a.获取所有公有成员方法，包括父类的方法也包括Object的方法

       //获取所有的公有方法
        System.out.println("***********获取所有的公有方法************");
        stuClass.getMethods();
        Method[] methodArray = stuClass.getMethods();
        for(Method m:methodArray){
            System.out.println(m);
        }

b.获取所有成员的方法，包括私有的

      //获取所有方法，包括私有的
        System.out.println("************获取所有方法，包括私有的");
        methodArray = stuClass.getDeclaredMethods();
        for(Method m:methodArray){
            System.out.println(m);
        }

c.获取单个公有的方法

      System.out.println("************获取公有的show1()方法*************");
      Method m = stuClass.getMethod("show1", String.class);

d.获取单个方法，包括私有的

    System.out.println("****************获取私有的show4()方法*************");
    m = stuClass.getDeclaredMethod("show4", int.class);

调用方法：

         Method --> public Object invoke(Object obj,Object... args):
                    参数说明：
                    obj : 要调用方法的对象；                     
                    args:调用方式时所传递的实参；

        //实例化一个Student对象
        Object obj = stuClass.getConstructor().newInstance();
        m.invoke(obj,"刘德华");

## 5）.反射main方法 ##

    public class Main {
    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("zl.reflect.Student");

            //获取main方法,第一参数是方法名，第二个参数是方法形参的方法类型，第二个参数是String数组JDK1.4之前是数组，jdk1.5之后是可变参数
            Method methodMain = clazz.getMethod("main",String[].class);
            //调用main方法,第一个参数，对象类型，因为main方法是static所以可以使用null
            //methodMain.invoke(null,new String[]{"a","b","c"});

            //由于将new String[]{"a","b","c"}拆成三个对象，所以需要进行强制转化，主要有两种方法
            //方式1
            methodMain.invoke(null,new Object[]{new String[]{"a","b","c"}});
            //方式2
            methodMain.invoke(null,(Object)new String[]{"a","b","c"});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
## 6）.判断是否为某个类的实例 ##

一般的，我们使用instanceof关键字来判断是否为某个类的实例。在反射中，我们还可以使用Class对象的isInstance()方法来判断是否为某个类的实例，它是一个native方法：

    public native boolean isInstanceof(Object obj);

## 7）.通过反射创造实例 ##

使用Class对象的newInstance()方法来创建Class对象对应的实例。

前面例子中使用反射获取构造方法时，使用过该方法为构造函数赋值。

        obj = con.newInstance('男');
        obj = con2.newInstance("zlcalma");

## 8）.利用反射创建数组 ##

    import java.lang.reflect.Array;
    public class TestArray {
    public static void main(String[] args) throws Exception {

        Class<?> clazz = Class.forName("java.lang.String");
        Object array = Array.newInstance(clazz,25);

        //往数组中添加内容

        Array.set(array,0,"hello");
        Array.set(array,1,"Java");
        Array.set(array,2,"fuck");

        //获取某一项的内容
        System.out.println(Array.get(array,1));

    }
    }

**注意：**反射会额外消耗一定的系统资源，因此如果不需要动态创建一个对象，那么就不使用反射，另外反射调用方法时可以忽略权限检查，因此会破坏封装性而导致安全问题。

详细代码：https://github.com/zlweiyang/java-basis

参考博客：https://www.sczyh30.com/posts/Java/java-reflection-1/
