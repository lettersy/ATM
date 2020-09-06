package Dao;

import java.util.Arrays;
import java.util.Scanner;

import entity.User;

//�й�������
public class Bank {
    Scanner input = new Scanner(System.in);

    //������ע����û�����
    User[] users = new User[3];

    int cardNo = 100000;//����
    int size = 3;//�������û��±�

    //��ʼ�������������һЩ������ʼʱ����Ҫִ�еĴ��룬��д���ʼ������initial�У�
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

    //��¼
    public void login() {
        System.out.print("�����뿨�ţ�");
        int no = input.nextInt();
        System.out.print("���������루8~16λ����");
        String pwd = input.next();
        User check = loginCheck(no, pwd);

        if (check != null) {
            this.showMenu(check);
        } else {
            System.out.println("�û���������������������룡");
            this.login();
        }
    }

    //����
    public void register() {
        System.out.print("������������");
        String userName = input.next();
        String identity;
        do {
            System.out.print("���������֤���룺");
            identity = input.next();
        } while (identity.length() != 18);
        String phone;
        do {
            System.out.print("������Ԥ���ֻ����룺");
            phone = input.next();
        } while (phone.length() != 11);
        String passWord;
        do {
            System.out.print("���������룺");
            passWord = input.next();
        } while (passWord.length()!=6);//����8~16λ
        double money;
        do {
            System.out.print("������Ԥ������ڵ���10Ԫ����");
            money = input.nextInt();
        } while (money < 10);


        User user = new User();
        user.setCardNo(cardNo);
        user.setUserName(userName);
        user.setIdentity(identity);
        user.setPhone(phone);
        user.setPassWord(passWord);
        user.setBalance(money);
        System.out.println("�����ɹ������Ŀ���Ϊ��" + cardNo);
        cardNo++;
        //���ö���洢��users������
        //�����ж�
        if (size > (users.length - 1)) {
            User[] newUsers = Arrays.copyOf(users, size + 1);
            users = newUsers;
            users[size] = user;
        }
        size++;
    }

    //��¼��֤
    public User loginCheck(int no, String pwd) {
        for (int i = 0; i < size; i++) {
            //��֤���š�����
            if (users[i] != null) {//�ǿ��ж�
                if ((no == users[i].getCardNo() && pwd.equals(users[i].getPassWord()))) {
                    return users[i];
                }
            }
        }
        return null;
    }

    //ת����֤
    public User transferCheck(int no) {
        for (int i = 0; i < users.length; i++) {
            //У�鿨��
            if (users[i] != null) {
                if (no == users[i].getCardNo()) {
                    return users[i];
                }
            }
        }
        return null;
    }

    //��ӭҳ��˵�
    public void welcomeMenu() {
        System.out.println("-------��ӭʹ���й���������ATM��������-------");
        System.out.println("---      1.��¼    2.����    0.�˳�     ---");
        System.out.println("------------------------------------------");
        System.out.print("������������룺");
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
                System.out.println("�����������������룡");
        }
    }

    //�˳�
    public void exit() {
        System.exit(0);
    }

    //��ʾ�˵�
    public void showMenu(User user) {
        int choice;
        do {
            System.out.println("-------------------------��ӭʹ���й���������ATM��������-------------------------");
            System.out.println("1.���  2.ȡ��  3.ת��  4.��ѯ���  5.�޸�����  6.�޸�Ԥ���ֻ���  7.ע���˻�  0.�˳�");
            System.out.println("------------------------------------------------------------------------------");
            System.out.print("������������룺");
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
                    System.out.println("ллʹ�ã�");
                    //�����÷�����ִ�и÷������µ����
                    this.welcomeMenu();
                default:
                    System.out.println("�����������������룡");
            }
        } while (true);
    }

    //ȡ��
    public void withdraw(User user) {
        System.out.print("������ȡ���");
        double money = input.nextDouble();
        if (money <= user.getBalance()) {
            user.setBalance(user.getBalance() - money);
            System.out.println("ȡ��ɹ�����ǰ���Ϊ��" + user.getBalance() + "Ԫ��");
        } else {
            System.out.println("���㣡");
        }
    }

    //���
    public void deposit(User user) {
        System.out.print("���������");
        double money = input.nextDouble();
        user.setBalance(user.getBalance() + money);
        System.out.println("���ɹ���");
    }

    //ת��
    public void transfer(User user) {
        System.out.print("�����뿨�ţ�");
        int no = input.nextInt();
        User check = transferCheck(no);
        //�Կ���У��
        if (check != null) {
            System.out.print("������ת�˽�");
            Double money = input.nextDouble();
            if (user.getBalance() >= money) {
                user.setBalance(user.getBalance() - money);
                for (int i = 0; i < size; i++) {
                    if (users[i].getCardNo() == no) {
                        users[i].setBalance(users[i].getBalance() + money);
                    }
                }
                System.out.println("ת�˳ɹ���");
            } else {
                System.out.println("���㣡");
            }
        } else {
            System.out.println("����Ŀ�������");
        }
    }

    //��ѯ���
    public void inquire(User user) {
        System.out.println("�˻����Ϊ��" + user.getBalance() + "Ԫ��");
    }

    //�޸�����
    public void changePwd(User user) {
        String newPwd1;
        do {
            System.out.print("������8~16λ�����룺");
            newPwd1 = input.next();
        } while (newPwd1.length() < 8 || newPwd1.length() > 16);
        System.out.print("���ٴ����������룺");
        String newPwd2 = input.next();
        if (newPwd1.equals(newPwd2)) {
            user.setPassWord(newPwd2);
            System.out.println("�޸ĳɹ���");
        } else {
            System.out.println("�����������벻ͬ��");
        }
    }

    //�޸�Ԥ���ֻ���
    public void changePhone(User user) {
        System.out.print("��������ȷ��ԭԤ���ֻ��ţ�");
        String oldPhone = input.next();
        if (oldPhone.equals(user.getPhone())) {
            String newPhone;
            do {
                System.out.print("��������ȷ�����ֻ��ţ�");
                newPhone = input.next();
            } while (newPhone.length() != 11);
            user.setPhone(newPhone);
            System.out.println("Ԥ���ֻ����޸ĳɹ���");
        } else {
            System.out.println("ԭԤ���ֻ��Ų���ȷ��");
        }
    }

    //ע���˻�
    public void cancel(User user) {
        int index = size + 1;
        for (int i = 0; i < size; i++) {
//            size����Ҫ�ǿ��ж�
//            if (users[i] != null) {
            if (users[i] == user) {
                index = i;
                break;
            }
//            }
        }
        //�ƶ���Ԫ��֮���ÿ��Ԫ��
        for (int i = index; i < size - 1; i++) {
            users[i] = users[i + 1];
        }
        size--;
        System.out.println("ע���ɹ���");
    }
}

