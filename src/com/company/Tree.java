package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Tree implements Cloneable {

    // Implementing the function to print tree in 2D
    static int COUNT = 1000;
    int rotations = 0;
    Node root;
    Node dupe = null;

    // implementation of A1 algorithm
    static void A1(Tree T, Tree S) throws CloneNotSupportedException {

        int n = 1000;

        double rootOfT = T.computeRoot(n, T.root.height + 1);
        S.root = S.findRoot(S.root, (int) rootOfT, S.root.height, S);
        List<Integer> leftList = S.getLeftForeArm(S.root.left);
        List<Integer> rightList = S.getRightForeArm(S.root.right);
        ArrayList<Node> leftForeArm = S.intoLeftForearms(S, S.root.left);

        S.root.left = null;
        S.root.left = leftForeArm.get(0);
        leftForeArm.remove(0);

        S.leftInorder(S.root.left, leftForeArm);


        //Right Forearm
        ArrayList<Node> rightForeArm = S.intoRightForearms(S, S.root.right);

        int r = rightForeArm.size();
        S.root.right = null;
        S.root.right = rightForeArm.get(0);
        rightForeArm.remove(0);

        S.rightInorder(S.root.right, rightForeArm);

        S.AdjustHeight(S.root);

        S.assigningParentLeft(S.root.left, S.root);

        //Getting Skewed Right Tree and cloning arbitrary tree
        Tree skewedTree = new Tree();
        Node skewedNode = (Node) S.root.clone();
        skewedTree.root = skewedNode;

        skewedTree.AdjustHeight(skewedTree.root);

        skewedTree.assigningParentLeft(skewedTree.root.right, skewedTree.root);

        int original_node = skewedTree.root.data;

        S.AL(S.root.left, S, T.root.height + 1, n);
        S.printInorder(S.root);

        skewedTree.AR(skewedTree.root.right, skewedTree, T.root.height + 1, n, r);
        skewedTree.printInorder(skewedTree.root);

        Tree finalTree = new Tree();
        finalTree.root = new Node(original_node);
        finalTree.root.left = S.root;
        finalTree.root.right = skewedTree.root;

        skewedTree.printInorder(finalTree.root);

        System.out.println("Total Rotations for Algorithm A1: : " + (S.rotations + skewedTree.rotations));


    }

    static int X(int n) {
        int no = 0;
        Random rand = new Random();
        double r1 = (rand.nextDouble() * 0.1) + 1.1;
        int r2 = (int) (n * r1);
        return r2;
    }

    HashMap<Integer, Node> assignObject(HashMap<Integer, Node> map, Node root) {

        if (root != null) {
            assignObject(map, root.left);

            map.put(root.data, root);

            assignObject(map, root.right);
        }

        return map;

    }

    // Function to insert nodes in level order
    public Node insertLevelOrder(Integer[] arr, Node root,
                                 int i) {
        // Base case for recursion
        if (i < arr.length) {
            Node temp = new Node(arr[i]);
            root = temp;

            // insert left child
            root.left = insertLevelOrder(arr, root.left,
                    2 * i + 1);

            // insert right child
            root.right = insertLevelOrder(arr, root.right,
                    2 * i + 2);
        }
        return root;
    }

    int height(Node N) {
        if (N == null)
            return 0;

        return N.height;
    }

    // Function to print height of node
    public void assignHeight(Node root) {
        if (root != null) {
            assignHeight(root.left);
            assignHeight(root.right);

            if (root.left != null && root.right != null) {
                root.height = 1 + Math.max(height(root.left), height(root.right));
            }

        }
    }

    /* Given a binary tree, print its nodes in inorder*/
    void printInorder(Node node) {
        if (node == null)
            return;

        /* first recur on left child */
        printInorder(node.left);

        /* now recur on right child */
        printInorder(node.right);
    }

    //function to implement inOrder Traversal
    public void inOrder(Node node, List<Integer> list) {
        if (node != null && list.size() >= 0) {
            inOrder(node.left, list);

            node.data = list.get(0);
            list.remove(0);
            inOrder(node.right, list);
        }
    }

    //function to implement preOrder traversal
    void preOrder(Node node, int key, boolean rot) {

        if (node == null) {
            return;
        }

        if (node.data == key) {
            node.rotate = rot;
        }
        preOrder(node.left, key, rot);
        preOrder(node.right, key, rot);

    }

    //function to implement left and right rotations
    Node rotateFunction(Node node) {

        if (node == null)
            return node;


        node.left = rotateFunction(node.left);

        node.height = 1 + Math.max(height(node.left),
                height(node.right));


        if (node.rotate && node.left != null && node.height > 1) {
            return rightRotate(node);
        }

        if (node.rotate && node.right != null && node.height > 1) {
            return leftRotate(node);
        }

        node.right = rotateFunction(node.right);

        return node;
    }

    //Function to implement right rotation
    Node rightRotateArms(Node y, Node Parent) {

        if ((y.right == null && y.left == null) || (y.left != null && y.right == null) || (y.right != null && y.left == null)) {
            Node T2 = y.right;
            y.right = Parent;
            Parent.left = T2;
            return y;

        } else {
            Node T2 = y.right;
            y.right = Parent;
            Parent.left = T2;

            return y;
        }

    }

    //function to implement left rotation
    Node leftRotateArms(Node y, Node Parent) {
        if ((y.right == null && y.left == null) || (y.left != null && y.right == null) || (y.right != null && y.left == null)) {
            Node T2 = y.left;
            y.left = Parent;
            Parent.right = T2;
            return y;

        } else {
            Node T2 = y.left;
            y.left = Parent;
            Parent.right = T2;

            return y;
        }
    }

    //function to compute the root of the target tree T
    double computeRoot(int n, int h) {

        if ((Math.pow(2, h - 1)) <= n && n <= ((Math.pow(2, h - 1)) + (Math.pow(2, h - 2) - 2))) {
            return (n - Math.pow(2, h - 2) + 1);
        }

        if ((Math.pow(2, h - 1) + (Math.pow(2, h - 2) - 1)) <= n && n <= (Math.pow(2, h) - 1)) {
            return Math.pow(2, h - 1);
        }

        return h;
    }

    //function to find root in arbitrary tree S
    Node findRoot(Node root, int key, int rootHeight, Tree S) {

        if (root != null) {
            findRoot(root.left, key, rootHeight, S);
            if (root.data == key) {
                int rot = rootHeight - root.height;
                int j = 0;
                while (j < rot) {
                    S.root = S.rootRotate(S.root, true);
                    S.rotations = S.rotations + 1;
                    j++;
                }
            }
            findRoot(root.right, key, rootHeight, S);
        }

        return S.root;
    }

    //function to  rotate root towards left or right in left and right Forearms
    Node rootRotateArms(Node node, boolean left, Node Parent) {
        if (node == null)
            return node;

        node.height = 1 + Math.max(height(node.left),
                height(node.right));

        if (left) {
            return leftRotateArms(node, Parent);
        } else {
            return rightRotateArms(node, Parent);
        }

    }

    //function to get leftforearm of the tree
    List<Integer> getLeftForeArm(Node root) {
        List<Integer> list = new ArrayList<Integer>();
        while (root != null) {
            list.add(root.data);
            root = root.right;
        }
        return list;
    }

    //function to get rightforearm of the tree
    List<Integer> getRightForeArm(Node root) {
        List<Integer> list = new ArrayList<Integer>();
        while (root != null) {
            list.add(root.data);
            root = root.left;
        }
        return list;
    }

    //function to rotate elements present in leftForeArm
    ArrayList<Node> intoLeftForearms(Tree S, Node root) {
        ArrayList<Node> list = new ArrayList<Node>();
        S.root.left = root;
        Tree ex = new Tree();
        ex.root = new Node(0);

        while (root != null) {
            while (root.left != null && root.ignore == false) {
                root = rootRotateArms(root.left, false, root);
                S.rotations = S.rotations + 1;
            }
            list.add(root);
            root = root.right;
        }
        return list;
    }

    //function to rotate elements present in rightForeArm
    ArrayList<Node> intoRightForearms(Tree S, Node root) {
        ArrayList<Node> nodez = new ArrayList<Node>();
        S.root.right = root;
        Tree ex = new Tree();
        ex.root = new Node(0);

        while (root != null) {
            while (root.left != null && root.ignore == false) {
                root = rootRotateArms(root.left, false, root);
                S.rotations = S.rotations + 1;

            }
            nodez.add(root);
            root = root.right;
        }
        return nodez;
    }

    //function to implement inorder traversal in left
    void leftInorder(Node node, ArrayList<Node> list) {

        if (list.size() == 0) {
            return;
        } else {
            node.right = new Node(0);
            node.right = list.get(0);
            list.remove(0);
            leftInorder(node.right, list);
        }
    }

    void inorderAssignRight(Node root_s, ArrayList<Node> noder1) {
        if (noder1.size() == 0) {
            return;
        } else {
            root_s.right = new Node(0);
            root_s.right = noder1.get(0);
            noder1.remove(0);
            inorderAssignRight(root_s.right, noder1);
        }

    }

    void listRotFalse(Node root, List<Integer> list_a, List<Integer> list_b) {
        if (root != null) {

            listRotFalse(root.left, list_a, list_b);
            listRotFalse(root.right, list_a, list_b);

            if (list_a.contains(root.data) || list_b.contains(root.data)) {
                root.rotate = false;

            }
            root.height = 1 + Math.max(height(root.left), height(root.right));
        }
    }

    void AL2(Node root, Tree g, int h, int n, List<Integer> leftT, Tree t) throws CloneNotSupportedException {
        boolean flag = false;
        dupe = (Node) root.clone();
        int s = h;
        boolean rotate = false;
        boolean step2 = false;
        if (dupe != null) {
            //int match = Integer.parseInt(Integer.toBinaryString(root.data));

            if ((Math.pow(2, h - 1) <= n) && (n <= Math.pow(2, h - 1) + Math.pow(2, h - 2) - 2)) {
                flag = true;
                step2 = true;
                int k = n - (int) Math.pow(2, h - 1) + 1;

                for (int i = 1; i <= k; i++) {
                    rotate = false;

                    if (dupe.right != null) {

                        if (dupe.rotate == true) {

                            if (dupe.binary == 1 && !leftT.contains(dupe.data)) {
                                dupe.rotate = false;
                                g.root = rotatorLeft(dupe, dupe.parent, g);
                                rotate = true;
                            }
                        } else {

                            if (dupe.binary == 1 && !leftT.contains(dupe.data)) {
                                dupe.rotate = false;
                                g.root = rotatorLeft(dupe, dupe.parent, g);
                                rotate = true;
                            }
                        }
                        if (rotate) {
                            dupe = dupe.parent.right;
                        } else {
                            dupe = dupe.right;
                        }
                    }
                }
                s = h - 1;
            }

            int x;
            if (step2) {
                x = 10;
            } else {
                x = 1;
            }

            for (int j = s - 1; j >= 1; j--) {
                int k = (int) Math.pow(2, j) - 1;
                if (flag) {
                    dupe = (Node) g.root.clone();
                } else {
                    dupe = (Node) root.clone();
                    flag = true;
                }


                for (int i = 1; i < k; i++) {

                    rotate = false;
                    if (dupe != null) {
                        if (dupe.right != null) {


                            if (dupe.rotate == true) {
                                if (dupe.binary == x && !leftT.contains(dupe.data)) {
                                    dupe.rotate = false;
                                    g.root = rotatorLeft(dupe, dupe.parent, g);
                                    rotate = true;
                                } else {
                                    dupe.rotate = false;
                                }
                            } else {
                                if (dupe.binary == x && !leftT.contains(dupe.data)) {
                                    dupe.rotate = false;
                                    g.root = rotatorLeft(dupe, dupe.parent, g);
                                    rotate = true;
                                }
                            }
                            if (rotate) {
                                dupe = dupe.parent.right;
                            } else {
                                dupe = dupe.right;
                            }
                        }
                    }
                }
                x = x * 10;

                if (identical(g.root.left, t.root.left)) {
                    return;
                }
            }

        }

    }

    ArrayList<Integer> getLeaves(Node root, ArrayList<Integer> list) {

        if (root != null) {
            if (root.left == null && root.right == null) {
                list.add(root.data);
            } else {
                if (root.left == null && root.right != null) {
                    getLeaves(root.right, list);
                } else if (root.left != null && root.right == null) {
                    getLeaves(root.left, list);
                } else {
                    getLeaves(root.left, list);
                    getLeaves(root.right, list);
                }
            }
        }
        return list;
    }

    void AR2(Node root, Tree g, int h, int n, int r, List<Integer> rightT, Tree t) throws CloneNotSupportedException {
        boolean flag = false;
        dupe = (Node) root.clone();
        int s = h;
        boolean rotate = false;
        if (dupe != null) {

            if ((Math.pow(2, h - 1) + Math.pow(2, h - 2) <= n) && (n <= Math.pow(2, h) - 2)) {
                flag = true;
                int k = n - (int) Math.pow(2, h - 1) + 1;

                for (int i = 1; i <= (((r + 1) / 2) - 1); i++) {
                    if (dupe != null) {
                        if (dupe.right != null) {

                            if (dupe.rotate == true) {

                                if (dupe.binary == 1 && !rightT.contains(dupe.data)) {
                                    dupe.rotate = false;
                                    g.root = rotatorLeft(dupe, dupe.parent, g);
                                }
                            } else {
                                if (dupe.binary == 1 && !rightT.contains(dupe.data)) {
                                    dupe.rotate = false;
                                    g.root = rotatorLeft(dupe, dupe.parent, g);
                                }
                            }
                            if (rotate) {
                                dupe = dupe.parent.right;
                            } else {
                                dupe = dupe.right;
                            }
                        }
                    }
                    s = h - 1;
                }


                int x = 10;
                for (int j = s - 1; j >= 1; j--) {

                    if (flag) {
                        dupe = (Node) root.clone();
                    } else {
                        dupe = (Node) root.clone();
                        flag = true;
                    }
                    for (int i = 1; i < k - 1; i++) {
                        if (dupe != null) {

                            if (dupe.right != null) {

                                if (dupe.rotate == true) {
                                    if (dupe.binary == x && !rightT.contains(dupe.data)) {
                                        dupe.rotate = false;
                                        g.root = rotatorLeft(dupe, dupe.parent, g);
                                    }
                                } else {
                                    dupe.rotate = false;
                                }
                            } else {
                                if (dupe.binary == x && !rightT.contains(dupe.data)) {
                                    dupe.rotate = false;
                                    g.root = rotatorLeft(dupe, dupe.parent, g);
                                }
                            }
                            if (rotate) {
                                dupe = dupe.parent.right;
                            } else {
                                dupe = dupe.right;
                            }
                        }
                    }
                    x = x * 10;
                    if (identical(g.root.right, t.root.right)) {
                        return;
                    }
                }

            }

        }
    }

    //function to implement inorder traversal in right
    void rightInorder(Node root_s, ArrayList<Node> noder1) {
        if (noder1.size() == 0) {
            return;
        } else {
            root_s.right = new Node(0);
            root_s.right = noder1.get(0);
            noder1.remove(0);
            rightInorder(root_s.right, noder1);
        }

    }

    //function to rotate root towards left or right
    Node rootRotate(Node node, boolean left) {
        if (node == null)
            return node;

        node.height = 1 + Math.max(height(node.left),
                height(node.right));

        if (left) {
            return leftRotate(node);
        } else {
            return rightRotate(node);
        }

    }

    //function to rotate root towards left
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        //  Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    //function to rotate root towards right
    Node rightRotate(Node y) {

        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    //function to adjust heights
    public void AdjustHeight(Node root) {
        if (root != null) {
            AdjustHeight(root.left);
            AdjustHeight(root.right);
            root.rotate = true;
            root.height = 1 + Math.max(height(root.left), height(root.right));

        }
    }

    // implementation of algorithm AL

    void assigningParentLeft(Node root, Node head) {
        root.parent = head;
        head = root;
        if (root.right != null) {
            assigningParentLeft(root.right, head);
        } else {
            return;
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    void AL(Node root, Tree g, int h, int n) throws CloneNotSupportedException {
        boolean flag = false;
        dupe = (Node) root.clone();
        int s = h;
        if (dupe != null) {

            if ((Math.pow(2, h - 1) <= n) && (n <= Math.pow(2, h - 1) + Math.pow(2, h - 2) - 2)) {
                flag = true;
                int k = n - (int) Math.pow(2, h - 1) + 1;

                for (int i = 1; i <= k; i++) {

                    if (dupe.right != null) {

                        if (dupe.right.rotate == true) {
                            dupe.rotate = false;
                            g.root = rotatorLeft(dupe, dupe.parent, g);
                        }
                    } else {
                        dupe.rotate = false;
                        g.root = rotatorLeft(dupe, dupe.parent, g);
                    }
                    dupe = dupe.parent.right;
                }
                s = h - 1;
            }


            for (int j = s - 1; j >= 1; j--) {
                int k = (int) Math.pow(2, j) - 1;
                if (flag) {
                    dupe = (Node) g.root.clone();
                } else {
                    dupe = (Node) root.clone();
                    flag = true;
                }
                for (int i = 1; i < k; i++) {
                    if (dupe.right != null) {

                        if (dupe.right.rotate == true) {
                            dupe.rotate = false;
                            g.root = rotatorLeft(dupe, dupe.parent, g);
                        } else {
                            dupe.rotate = false;
                        }
                    } else {
                        dupe.rotate = false;
                        g.root = rotatorLeft(dupe, dupe.parent, g);
                    }
                    dupe = dupe.parent.right;
                }


            }

        }

    }

    //implementation of algorithm AR
    void AR(Node root, Tree g, int h, int n, int r) throws CloneNotSupportedException {
        boolean flag = false;
        dupe = (Node) root.clone();
        int s = h;
        if (dupe != null) {

            if ((Math.pow(2, h - 1) + Math.pow(2, h - 2) <= n) && (n <= Math.pow(2, h) - 2)) {
                flag = true;
                int k = n - (int) Math.pow(2, h - 1) + 1;

                for (int i = 1; i <= (((r + 1) / 2) - 1); i++) {

                    if (dupe.right != null) {

                        if (dupe.right.rotate == true) {
                            dupe.rotate = false;
                            g.root = rotatorLeft(dupe, dupe.parent, g);
                        }
                    } else {
                        dupe.rotate = false;
                        g.root = rotatorLeft(dupe, dupe.parent, g);
                    }
                    dupe = dupe.parent.right;
                }
                s = h - 1;
            }


            for (int j = s - 1; j >= 1; j--) {
                int k = (int) Math.pow(2, j) - 1;
                if (flag) {
                    dupe = (Node) g.root.clone();
                } else {
                    dupe = (Node) root.clone();
                    flag = true;
                }
                for (int i = 1; i < k - 1; i++) {
                    if (dupe != null) {

                        if (dupe.right != null) {

                            if (dupe.right.rotate == true) {
                                dupe.rotate = false;
                                g.root = rotatorLeft(dupe, dupe.parent, g);
                            } else {
                                dupe.rotate = false;
                            }
                        } else {
                            dupe.rotate = false;
                            g.root = rotatorLeft(dupe, dupe.parent, g);
                        }
                        dupe = dupe.parent.right;
                    }
                }

            }

        }

    }

    Node rotatorLeft(Node y, Node parent, Tree g) {
        if (y.parent.parent == null) {
            if (y.right.left == null) {
                y.right.left = y;

                y = y.right;
                y.left.right = null;
                y.left.parent = y;
                y.parent = parent;
                g.root = y;
                g.rotations = g.rotations + 1;
                return g.root;
            } else {
                Node tmp = y.right.left;
                y.right.left = y;
                y = y.right;
                y.left.right = tmp;
                y.left.parent = y;
                y.parent = parent;
                y.left.right.parent = y.left;
                g.root = y;
                g.rotations = g.rotations + 1;
                return g.root;

            }


        } else if (y.right == null) {

        } else {
            if (y.right.left == null) {

                Node tmp = y;
                y = y.right;
                parent.right = y;
                y.left = tmp;
                y.left.right = null;
                y.left.parent = y;
                y.parent = parent;
                g.rotations = g.rotations + 1;
                return g.root;

            } else {


                Node tmp = y.right.left;
                parent.right = y.right;
                y.right.left = y;
                y = y.right;
                y.left.right = tmp;
                y.left.parent = y;
                y.parent = parent;
                y.left.right.parent = y.left;
                g.rotations = g.rotations + 1;
                return g.root;
            }
        }
        return g.root;
    }

    Node rotatorRight(Node y, Node parent, Tree g) {
        if (y.parent.parent == null) {
            if (y.left.right == null) {
                y.left.right = y;

                y = y.left;
                y.right.left = null;
                y.right.parent = y;
                y.parent = parent;
                g.root = y;
                g.rotations = g.rotations + 1;

                return g.root;
            } else {
                Node tmp = y.left.right;
                y.left.right = y;
                y = y.left;
                y.right.left = tmp;
                y.right.parent = y;
                y.parent = parent;
                y.right.left.parent = y.right;
                g.root = y;
                g.rotations = g.rotations + 1;
                return g.root;

            }


        } else if (y.left == null) {

        } else {
            if (y.left.right == null) {

                Node tmp = y;
                y = y.left;
                parent.left = y;
                y.right = tmp;
                y.right.left = null;
                y.right.parent = y;
                y.parent = parent;
                g.rotations = g.rotations + 1;
                return g.root;

            } else {


                Node tmp = y.left.right;
                parent.left = y.left;
                y.left.right = y;
                y = y.left;
                y.right.left = tmp;
                y.right.parent = y;
                y.parent = parent;
                y.right.left.parent = y.right;
                g.rotations = g.rotations + 1;
                return g.root;
            }
        }
        return g.root;
    }

    HashMap<Integer, String> assigningBinaryTree(Node root, int n, int h) {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (int x = 1; x <= n; x++) {
            int level = Math.abs(getLevel(root, x));
            if (level != 0) {

                map.put(x, String.valueOf((int) Math.pow(2, Math.abs(getLevel(root, x) - 1 - h))));
            }
        }

        return map;
    }

    int getLevel(Node node, int data) {
        return getLevelUtil(node, data, 1);
    }

    int getLevelUtil(Node node, int data, int level) {
        if (node == null)
            return 0;

        if (node.data == data)
            return level;

        int downlevel = getLevelUtil(node.left, data, level + 1);
        if (downlevel != 0)
            return downlevel;

        downlevel = getLevelUtil(node.right, data, level + 1);
        return downlevel;
    }

    HashMap<Integer, String> assignBinary(Node root, int n, int h) {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (int x = 1; x <= n; x++) {
            int level = Math.abs(getLevel(root, x));
            if (level != 0) {

                map.put(x, String.valueOf((int) Math.pow(2, Math.abs(getLevel(root, x) - 1 - h))));
            }
        }

        return map;
    }

    void setIntervals(Node root) {
        if (root != null) {

            root.leftInt = getLeftMost(root);
            root.rightInt = getRightMost(root);
            setIntervals(root.left);
            setIntervals(root.right);
        }
    }

    int getLeftMost(Node root) {
        while (root.left != null) {
            root = root.left;
        }
        return root.data;
    }

    int getRightMost(Node root) {
        while (root.right != null) {
            root = root.right;
        }
        return root.data;
    }

    void ignoringSameTreeNodes(Node node, HashMap<String, String> map) {

        if (node != null) {
            ignoringSameTreeNodes(node.left, map);

            String c = node.leftInt + "-" + node.rightInt;
            if (map.containsKey(c)) {
                String current_post = "";
                current_post = getPostOrderString(node);
                if (map.get(c).equals(current_post) && node.left != null && node.right != null) {
                    node.ignore = true;
                    if (node.parent != null) {
                        node.parent.ignore = true;
                    }
                }
            }

            ignoringSameTreeNodes(node.right, map);

        }

    }

    HashMap<String, String> getMap(Node root, HashMap<String, String> map) {
        if (root != null) {
            getMap(root.left, map);

            String h = "";
            String x = getPostOrderString(root);
            String key = root.leftInt + "-" + root.rightInt;
            map.put(key, x);

            getMap(root.right, map);
        }
        return map;
    }

    void printBinaryTree(Node root) {
        if (root != null) {
            printBinaryTree(root.left);
            printBinaryTree(root.right);

        }
    }

    void discloseBinary(HashMap<Integer, String> map, Node root) {
        if (root != null) {
            discloseBinary(map, root.left);
            discloseBinary(map, root.right);
            int g = Integer.parseInt(map.get(root.data));
            root.binary = Double.parseDouble(Integer.toBinaryString(g));
        }
    }

    String getPostOrderString(Node node) {
        String returnString = "";
        if (node != null) {
            returnString += getPostOrderString(node.left);
            returnString += getPostOrderString(node.right);
            returnString += String.valueOf(node.data);
        }
        return returnString;

    }

    boolean identical(Node sRoot, Node tRoot) {

        String S = getPostOrderString(sRoot);
        String t = getPostOrderString(tRoot);

        return S.equals(t);
    }

    HashMap<String, Integer> root_interval_mapping(Node root, HashMap<String, Integer> map) {
        if (root != null) {
            root_interval_mapping(root.left, map);

            String key = root.leftInt + "-" + root.rightInt;
            map.put(key, root.data);

            root_interval_mapping(root.right, map);
        }
        return map;
    }


    Tree ignoringSubTreesinA3(Node node, HashMap<Integer, Node> object_mapper, HashMap<String, Integer> interval_mapper, Tree S) throws CloneNotSupportedException {

        if (node != null) {
            ignoringSubTreesinA3(node.left, object_mapper, interval_mapper, S);

            String c = node.leftInt + "-" + node.rightInt;

            String[] arr = c.split("-");
            if (!arr[0].equals(arr[1])) {
                if (interval_mapper.containsKey(c)) {

                    int t_val = interval_mapper.get(c);

                    int a = Integer.parseInt(arr[0]);
                    int b = Integer.parseInt(arr[1]);
                    int n_len = b - a + 1;

                    Integer[] arr1 = new Integer[n_len];
                    int y = 0;
                    for (int i = a; i <= b; i++) {
                        arr1[y] = i;
                        y++;
                    }


                    Tree dump = new Tree();
                    int mid = Integer.parseInt(arr[0]) + Integer.parseInt(arr[1]) / 2;
                    dump.root = new Node(mid);
                    dump.root = dump.insertLevelOrder(arr1, dump.root, 0);
                    dump.printInorder(dump.root);

                    List<Integer> deep_list = new ArrayList<Integer>();

                    for (int i = 0; i < n_len; i++) {
                        deep_list.add(arr1[i]);
                    }

                    dump.inOrder(dump.root, deep_list);


                    if (dump.root.data > node.parent.data) {
                        node.parent.right = dump.root;
                    } else {
                        node.parent.left = dump.root;
                    }

                }
            } else {

            }

            ignoringSubTreesinA3(node.right, object_mapper, interval_mapper, S);

        }
        return S;
    }


    void A2(Tree t2, Tree S, int n, List<Integer> leftT, List<Integer> rightT, HashMap<String, String> TMap1) throws CloneNotSupportedException {

        t2.printInorder(t2.root);

        S.ignoringSameTreeNodes(S.root, TMap1);

        double x = t2.computeRoot(n, t2.root.height + 1);


        S.root = S.findRoot(S.root, (int) x, S.root.height, S);


        int rootTop = S.rotations;


        ArrayList<Node> noder = S.intoLeftForearms(S, S.root.left);

        int intoLeftForearm = S.rotations - rootTop;


        S.root.left = null;
        S.root.left = noder.get(0);
        noder.remove(0);

        S.leftInorder(S.root.left, noder);


        ArrayList<Node> noder1 = S.intoRightForearms(S, S.root.right);


        int r = noder1.size();
        S.root.right = null;
        S.root.right = noder1.get(0);
        noder1.remove(0);

        S.inorderAssignRight(S.root.right, noder1);

        S.AdjustHeight(S.root);

        S.assigningParentLeft(S.root.left, S.root);

        S.listRotFalse(S.root, leftT, rightT);

        Tree SS_Right = new Tree();
        Node S_Right = (Node) S.root.clone();
        SS_Right.root = S_Right;

        SS_Right.AdjustHeight(SS_Right.root);

        SS_Right.assigningParentLeft(SS_Right.root.right, SS_Right.root);

        int original_node = SS_Right.root.data;

        S.AL2(S.root.left, S, t2.root.height + 1, n, leftT, t2);

        S.assigningParentLeft(S.root.left, S.root);
        S.printInorder(S.root);

        SS_Right.root.left = S.root;
        int performLeftRot = S.rotations - intoLeftForearm;


        SS_Right.AR2(SS_Right.root.right, SS_Right, t2.root.height + 1, n, r, rightT, t2);

        SS_Right.printInorder(SS_Right.root);

        Tree fin = new Tree();
        fin.root = new Node(original_node);
        fin.root.left = S.root;
        fin.root.right = SS_Right.root.right;

        SS_Right.printInorder(fin.root);

        System.out.println("Total Rotations for algorithm A3: " + (rootTop + 2 * (performLeftRot + intoLeftForearm)));

    }


}
