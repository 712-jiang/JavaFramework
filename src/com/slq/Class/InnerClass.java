package com.slq.Class;

/**
 * @author qingqing
 * @function:
 * @create 2021-03-23-11:49
 */
public class InnerClass {
    public static void main(String[] args) {
        //静态成员内部类
        Person.brain brain = new Person.brain();
        brain.show();

        //创建非静态成员内部类
        //非静态成员不能通过类名调用，只能通过实例调用
        Person person = new Person();
        Person.head head = person.new head();
        head.display("display");
    }
}
class Person{
    public String name = "person-head";
    public void eat(){
    }
    //作为外部类的成员：>调用外部类的成员，静态内部类不能调用外部方法
    //                >可以被static修饰
    //                >可以被四种不同权限修饰：private、protected等

    //作为类：>类内可以定义属性、方法、构造器
    //       >可以被final修饰，不能被继承
    //       >可以被abstract修饰

    //成员内部类--静态
    static class brain{
        String name;
        public void show(){
            System.out.println("clever");
//            eat();   静态类不能调用外部方法
        }


    }

    //成员内部类--非静态
    class head{
        String name = "head-head";
        public void hat(){
            System.out.println("red hat");
            eat();  //同下
//            Person.this.eat();
        }
        public void display(String name){
            System.out.println(name);  //方法的形参
            System.out.println(this.name);  //内部类的属性
            System.out.println(Person.this.name);  //外部类的属性
        }
    }

    public void method(){
        //局部内部类--方法内
        class AA{

        }
    }

    //局部内部类--代码块内
    {
        class BB{

        }
    }

    public Person(){
        //局部内部类--构造函数内
        class CC{

        }
    }
}
