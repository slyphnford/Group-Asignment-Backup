/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chin Shan Hong
 */
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.Formatter;
public class NewClass{
    public static void main(String[] args) {
        Formatter f1 = new Formatter();
        int[] point = {1, 2, 3, 4, 5, 6};
        String[] name = {"john", "kang", "joe", "johan", "Aliggg", "Tehhhhh"};
        f1.format("%s%25s", "point", "name");
        System.out.println(f1);
        for(int i = 0; i < point.length; i++){
            Formatter f2 = new Formatter();
            f2.format("%d%29s", point[i], name[i]);
            System.out.println(f2);
        }
    }
}
