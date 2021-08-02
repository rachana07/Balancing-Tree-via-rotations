package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {

        // creating Target tree
        Tree T = new Tree();

        //creating arbitrary trees
        Tree S = new Tree();
        Tree S1 = new Tree();
        Tree S2 = new Tree();
        Tree S3 = new Tree();

        int n = 1000;

        Integer[] values = new Integer[n];

        for (int i = 1; i <= n; i++) {
            values[i - 1] = i;
        }

        //building the target tree
        int mid = values[values.length / 2];
        T.root = new Node(mid);
        T.root = T.insertLevelOrder(values, T.root, 0);
        T.printInorder(T.root);
        List<Integer> T_list = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            T_list.add(values[i]);
        }
        T.inOrder(T.root, T_list);
        T.assignHeight(T.root);

        // building arbitrary trees
        List<Integer> S_list = new ArrayList<Integer>();
        List<Integer> S1_list = new ArrayList<Integer>();
        List<Integer> S2_list = new ArrayList<Integer>();
        List<Integer> S3_list = new ArrayList<Integer>();
        List<Integer> rot_list = new ArrayList<Integer>();


        S.root = new Node(mid);
        S1.root = new Node(mid);
        S2.root = new Node(mid);
        S3.root = new Node(mid);

        S.root = S.insertLevelOrder(values, S.root, 0);
        S1.root = S.insertLevelOrder(values, S1.root, 0);
        S2.root = S.insertLevelOrder(values, S2.root, 0);
        S3.root = S.insertLevelOrder(values, S3.root, 0);

        S.printInorder(S.root);
        S1.printInorder(S1.root);
        S2.printInorder(S2.root);
        S3.printInorder(S3.root);


        for (int i = 0; i < n; i++) {
            S_list.add(values[i]);
            S1_list.add(values[i]);
            S2_list.add(values[i]);
            S3_list.add(values[i]);
            rot_list.add(values[i]);
        }

        S.inOrder(S.root, S_list);
        S.assignHeight(S.root);

        S1.inOrder(S1.root, S1_list);
        S1.assignHeight(S1.root);

        S2.inOrder(S2.root, S2_list);
        S2.assignHeight(S2.root);

        S3.inOrder(S3.root, S3_list);
        S3.assignHeight(S3.root);


        //Applying rotations on S based on given conditions
        // Marking the edges as "do not rotate" by using boolean variable rotate as false with 0.01 probability
        List<Integer> randList = new ArrayList<Integer>();
        Random random = new Random();

        while (randList.size() < 1) {
            int rand = rot_list.get(random.nextInt(n));

            if (!randList.contains(rand)) {
                S.preOrder(S.root, rand, false);
                S1.preOrder(S1.root, rand, false);
                S2.preOrder(S2.root, rand, false);
                S3.preOrder(S3.root, rand, false);
            }
            randList.add(rand);
        }

        //Marking the remaining edges as "do not rotate" by using boolean variable rotate as false with 0.5 probability
        int remaining = rot_list.size() / 2;

        List<Integer> dummyList = new ArrayList<Integer>();

        while (dummyList.size() < remaining) {
            int rand = rot_list.get(random.nextInt(n));

            if (!randList.contains(rand) && !dummyList.contains(rand)) {
                S.preOrder(S.root, rand, false);
                S1.preOrder(S1.root, rand, false);
                S2.preOrder(S2.root, rand, false);
                S3.preOrder(S3.root, rand, false);

            }
            dummyList.add(rand);
        }

        // Applying rotations on arbitrary trees
        S.root = S.rotateFunction(S.root);
        S1.root = S1.rotateFunction(S1.root);
        S2.root = S2.rotateFunction(S2.root);
        S3.root = S3.rotateFunction(S3.root);

        HashMap<String, String> dummy_map = new HashMap<String, String>();
        HashMap<String, String> TMap1 = S.getMap(T.root, dummy_map);

        //A1 algorithm
        Tree.A1(T, S);


        //A2 algorithm

        HashMap<Integer, String> map1 = S1.assignBinary(S1.root, n, S1.root.height);

        S1.discloseBinary(map1, S1.root);
        S1.printBinaryTree(S1.root);


        S1.root = S1.rotateFunction(S1.root);

        S2.root = S2.rotateFunction(S2.root);

        S1.setIntervals(S1.root);

        HashMap<String, String> dummy_map2 = new HashMap<String, String>();

        HashMap<String, String> TMap = S1.getMap(T.root, dummy_map2);

        HashMap<String, Integer> Int_Map = new HashMap<String, Integer>();
        HashMap<String, Integer> root_mapperz = S.root_interval_mapping(T.root, Int_Map);

        HashMap<Integer, Node> TObjectMap = new HashMap<Integer, Node>();
        HashMap<Integer, Node> TObjectMapper = T.assignObject(TObjectMap, T.root);


        List<Integer> leftT = T.getLeftForeArm(T.root.left);
        List<Integer> rightT = T.getRightForeArm(T.root.right);

        T.printInorder(T.root);

        S1.ignoringSameTreeNodes(S1.root, TMap);


        double x = T.computeRoot(n, T.root.height + 1);

        S1.root = S1.findRoot(S1.root, (int) x, S1.root.height, S1);


        ArrayList<Node> noder = S1.intoLeftForearms(S1, S1.root.left);


        S1.root.left = null;
        S1.root.left = noder.get(0);
        noder.remove(0);

        S1.leftInorder(S1.root.left, noder);


        ArrayList<Node> noder1 = S1.intoRightForearms(S1, S1.root.right);


        int r = noder1.size();
        S1.root.right = null;
        S1.root.right = noder1.get(0);
        noder1.remove(0);

        S1.inorderAssignRight(S1.root.right, noder1);

        S1.AdjustHeight(S1.root);

        S1.assigningParentLeft(S1.root.left, S1.root);


        S1.listRotFalse(S1.root, leftT, rightT);


        Tree SS_Right1 = new Tree();
        Node S_Right1 = (Node) S1.root.clone();
        SS_Right1.root = S_Right1;

        SS_Right1.AdjustHeight(SS_Right1.root);

        SS_Right1.assigningParentLeft(SS_Right1.root.right, SS_Right1.root);

        int original_node = SS_Right1.root.data;


        S1.AL2(S1.root.left, S1, T.root.height + 1, n, leftT, T);

        S1.printInorder(S1.root);


        S1.assigningParentLeft(S1.root.left, S1.root);
        S1.printInorder(S1.root);

        SS_Right1.root.left = S1.root;

        SS_Right1.AR2(SS_Right1.root.right, SS_Right1, T.root.height + 1, n, r, rightT, T);
        SS_Right1.printInorder(SS_Right1.root);


        Tree fin = new Tree();
        fin.root = new Node(original_node);
        fin.root.left = S1.root;
        fin.root.right = SS_Right1.root.right;

        SS_Right1.printInorder(fin.root);

        System.out.println("Total Rotations for Algorithm A2: " + S1.rotations);


        Tree Q = T.ignoringSubTreesinA3(S3.root, TObjectMapper, root_mapperz, S3);

        Q.A2(T, S3, n, leftT, rightT, TMap1);


    }
}