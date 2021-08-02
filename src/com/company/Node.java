package com.company;

public class Node implements Cloneable{

    int data;
   Node left, right,parent;
    int height = 0;
    boolean rotate = true;
    boolean ignore = false;
    double binary = 0;
    int leftInt;
    int rightInt;


    Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    protected Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}

