package Dao;

import java.util.Arrays;
import java.util.Scanner;

import entity.User;

//中国银行类
public class Bank {
    Scanner input = new Scanner(System.in);

    //保存已注册的用户数据
    User[] users = new User[3];

    int cardNo = 100000;//卡号
    int size = 3;//数组中用户下标

    //初始方法（如果存在一些程序起始时必须要执行的代码，则写入初始化方法initial中）
    public Bank() {

        User user = new User();
        user.setCardNo(1);
        user.setIdentity("131000199811111234");
        user.setUserName("Ziph");
        user.setPassWord("12345678");
        user.setPhone("12345670000");
        user.setBalance(1000.00);
        users[0] = user;

      
    }

    //登录
    public void login() {
        System.out.print("请输入卡号：");
        int no = input.nextInt();
        System.out.print("请输入密码（8~16位）：");
        String pwd = input.next();
        User check = loginCheck(no, pwd);

        if (check != null) {
            this.showMenu(check);
        } else {
            System.out.println("用户名或密码错误！请重新输入！");
            this.login();
        }
    }

    //开户
    public void register() {
        System.out.print("请输入姓名：");
        String userName = input.next();
        String identity;
        do {
            System.out.print("请输入身份证号码：");
            identity = input.next();
        } while (identity.length() != 18);
        String phone;
        do {
            System.out.print("请输入预存手机号码：");
            phone = input.next();
        } while (phone.length() != 11);
        String passWord;
        do {
            System.out.print("请输入密码：");
            passWord = input.next();
        } while (passWord.length()!=6);//密码8~16位
        double money;
        do {
            System.out.print("请输入预存金额（大于等于10元）：");
            money = input.nextInt();
        } while (money < 10);


        User user = new User();
        user.setCardNo(cardNo);
        user.setUserName(userName);
        user.setIdentity(identity);
        user.setPhone(phone);
        user.setPassWord(passWord);
        user.setBalance(money);
        System.out.println("开户成功！您的卡号为：" + cardNo);
        cardNo++;
        //将该对象存储到users数组中
        //扩容判断
        if (size > (users.length - 1)) {
            User[] newUsers = Arrays.copyOf(users, size + 1);
            users = newUsers;
            users[size] = user;
        }
        size++;
    }

    //登录验证
    public User loginCheck(int no, String pwd) {
        for (int i = 0; i < size; i++) {
            //验证卡号、密码
            if (users[i] != null) {//非空判断
                if ((no == users[i].getCardNo() && pwd.equals(users[i].getPassWord()))) {
                    return users[i];
                }
            }
        }
        return null;
    }

    //转账验证
    public User transferCheck(int no) {
        for (int i = 0; i < users.length; i++) {
            //校验卡号
            if (users[i] != null) {
                if (no == users[i].getCardNo()) {
                    return users[i];
                }
            }
        }
        return null;
    }

    //欢迎页面菜单
    public void welcomeMenu() {
        System.out.println("-------欢迎使用中国建设银行ATM自助服务-------");
        System.out.println("---      1.登录    2.开户    0.退出     ---");
        System.out.println("------------------------------------------");
        System.out.print("请输入操作编码：");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                this.login();
                break;
            case 2:
                this.register();
                this.welcomeMenu();
                break;
            case 0:
                exit();
            default:
                System.out.println("输入有误！请重新输入！");
        }
    }

    //退出
    public void exit() {
        System.exit(0);
    }

    //显示菜单
    public void showMenu(User user) {
        int choice;
        do {
            System.out.println("-------------------------欢迎使用中国建设银行ATM自助服务-------------------------");
            System.out.println("1.存款  2.取款  3.转账  4.查询余额  5.修改密码  6.修改预留手机号  7.注销账户  0.退出");
            System.out.println("------------------------------------------------------------------------------");
            System.out.print("请输入操作编码：");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    this.deposit(user);
                    break;
                case 2:
                    this.withdraw(user);
                    break;
                case 3:
                    this.transfer(user);
                    break;
                case 4:
                    this.inquire(user);
                    break;
                case 5:
                    this.changePwd(user);
                    break;
                case 6:
                    this.changePhone(user);
                    break;
                case 7:
                    this.cancel(user);
                    this.welcomeMenu();
                    break;
                case 0:
                    System.out.println("谢谢使用！");
                    //结束该方法，执行该方法以下的语句
                    this.welcomeMenu();
                default:
                    System.out.println("输入有误！请重新输入！");
            }
        } while (true);
    }

    //取款
    public void withdraw(User user) {
        System.out.print("请输入取款金额：");
        double money = input.nextDouble();
        if (money <= user.getBalance()) {
            user.setBalance(user.getBalance() - money);
            System.out.println("取款成功！当前余额为：" + user.getBalance() + "元！");
        } else {
            System.out.println("余额不足！");
        }
    }

    //存款
    public void deposit(User user) {
        System.out.print("请输入存款金额：");
        double money = input.nextDouble();
        user.setBalance(user.getBalance() + money);
        System.out.println("存款成功！");
    }

    //转账
    public void transfer(User user) {
        System.out.print("请输入卡号：");
        int no = input.nextInt();
        User check = transferCheck(no);
        //对卡号校验
        if (check != null) {
            System.out.print("请输入转账金额：");
            Double money = input.nextDouble();
            if (user.getBalance() >= money) {
                user.setBalance(user.getBalance() - money);
                for (int i = 0; i < size; i++) {
                    if (users[i].getCardNo() == no) {
                        users[i].setBalance(users[i].getBalance() + money);
                    }
                }
                System.out.println("转账成功！");
            } else {
                System.out.println("余额不足！");
            }
        } else {
            System.out.println("输入的卡号有误！");
        }
    }

    //查询余额
    public void inquire(User user) {
        System.out.println("账户余额为：" + user.getBalance() + "元！");
    }

    //修改密码
    public void changePwd(User user) {
        String newPwd1;
        do {
            System.out.print("请输入8~16位新密码：");
            newPwd1 = input.next();
        } while (newPwd1.length() < 8 || newPwd1.length() > 16);
        System.out.print("请再次输入新密码：");
        String newPwd2 = input.next();
        if (newPwd1.equals(newPwd2)) {
            user.setPassWord(newPwd2);
            System.out.println("修改成功！");
        } else {
            System.out.println("两次密码输入不同！");
        }
    }

    //修改预留手机号
    public void changePhone(User user) {
        System.out.print("请输入正确的原预留手机号：");
        String oldPhone = input.next();
        if (oldPhone.equals(user.getPhone())) {
            String newPhone;
            do {
                System.out.print("请输入正确的新手机号：");
                newPhone = input.next();
            } while (newPhone.length() != 11);
            user.setPhone(newPhone);
            System.out.println("预留手机号修改成功！");
        } else {
            System.out.println("原预留手机号不正确！");
        }
    }

    //注销账户
    public void cancel(User user) {
        int index = size + 1;
        for (int i = 0; i < size; i++) {
//            size不需要非空判断
//            if (users[i] != null) {
            if (users[i] == user) {
                index = i;
                break;
            }
//            }
        }
        //移动该元素之后的每个元素
        for (int i = index; i < size - 1; i++) {
            users[i] = users[i + 1];
        }
        size--;
        System.out.println("注销成功！");
    }
}

