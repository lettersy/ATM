package Test;

import Dao.Bank;

public class TestBank {
    public static void main(String[] args) {
        Bank bank = new Bank();//创建了一家中国银行网点
        bank.welcomeMenu();
    }
}