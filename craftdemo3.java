package CRAFT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CRAFT 部分操作实现
 * MC PN （ATK ARC SB） 密钥调度
 */
public class craftdemo3 {
    public static void main(String[] args) throws IOException {
		/*int[][] templ,TK1 = new int[4][4],TK0 = new int[4][4];
		int[][] P = {{0,0,0,0},{1,0,0,0},{0,0,0,0},{0,0,0,0}};
		int[][] C = {{0,0,0,0},{0,0,1,0},{0,0,0,0},{0,0,0,1}};*/

/*		List list = BeforeRounds(P);
		for (Object o : list) {
			System.out.println(o);
		}
		List list1 = AfterRounds(C);
		for (Object o : list1) {
			System.out.println(o);
		}*/

        // DistinEnc(P);

        long startTime = System.currentTimeMillis();
        // System.out.println(startTime);
        Distin();
        long endTime = System.currentTimeMillis();
        System.out.println("总时长为：" + (endTime - startTime) + "ms");

		/*int[][] initEnc = {{0,0,0,0},{0,0,1,0},{0,0,0,0},{0,0,0,0}};
		List listBefore = BeforeRounds(initEnc);
		for (Object o : listBefore) {
			System.out.println(o);
		} // r = 5
		System.out.println("===========================================");
		System.out.println(ChangeDoulbe(DistinEnc(initEnc))); // r = 6
		System.out.println("===========================================");
		int[][] initDec = {{0,0,0,0},{0,0,1,0},{0,0,0,0},{0,0,0,0}};
		System.out.println("===========================================");
		System.out.println(ChangeDoulbe(DistinDec(initEnc))); // r = 5
		System.out.println("===========================================");
		// initDec = PN(MC(initDec));
		List listAfter = AfterRounds(initDec);
		for (Object o : listAfter) {
			System.out.println(o);
		} // r = 5*/
    }

    // PN操作
    public static int[][] PN(int[][] p) {
        int[][] c = new int[4][4];
        c[0][0] = p[3][3];
        c[0][1] = p[3][0];
        c[0][2] = p[3][1];
        c[0][3] = p[3][2];
        c[1][0] = p[2][2];
        c[1][1] = p[2][1];
        c[1][2] = p[2][0];
        c[1][3] = p[2][3];
        c[2][0] = p[1][2];
        c[2][1] = p[1][1];
        c[2][2] = p[1][0];
        c[2][3] = p[1][3];
        c[3][0] = p[0][1];
        c[3][1] = p[0][2];
        c[3][2] = p[0][3];
        c[3][3] = p[0][0];
        return c;
    }

    // MC操作
    public static int[][] MC(int[][] p) {
        int[][] c = new int[4][4];
        for (int j = 0; j < 4; j++) {
            if (j == 0) {
                for (int i = 0; i < 4; i++) {
                    if (p[0][i] == 1 || p[2][i] == 1 || p[3][i] == 1) c[j][i] = 1;
                }
            }
            if (j == 1) {
                for (int i = 0; i < 4; i++) {
                    if (p[1][i] == 1 || p[3][i] == 1) c[j][i] = 1;
                }
            }
            if (j == 2) {
                for (int i = 0; i < 4; i++) {
                    if (p[2][i] == 1) c[j][i] = 1;
                }
            }
            if (j == 3) {
                for (int i = 0; i < 4; i++) {
                    if (p[3][i] == 1) c[j][i] = 1;
                }
            }
        }
        return c;
    }

    // MC操作：存在灰色的情况，区分器部分
    public static int[][] MCGray(int[][] p) {
        int[][] c = new int[4][4];
        for (int j = 0; j < 4; j++) {
            if (j == 0) {
                for (int i = 0; i < 4; i++) {
                    int countB = 0, countG = 0;
                    if (p[0][i] == 1) {
                        countB++;  // 黑色
                    }
                    if (p[0][i] == 2) {
                        countG++;  // 灰色
                    }
                    if (p[2][i] == 1) {
                        countB++;  // 黑色
                    }
                    if (p[2][i] == 2) {
                        countG++;  // 灰色
                    }
                    if (p[3][i] == 1) {
                        countB++;  // 黑色
                    }
                    if (p[3][i] == 2) {
                        countG++;  // 灰色
                    }
                    if (countB == 1 && countG == 0)
                        c[j][i] = 1;
                    else if (countB > 1 || countG > 0)
                        c[j][i] = 2;  // 表灰色
                }
            }
            if (j == 1) {
                for (int i = 0; i < 4; i++) {
                    int countB = 0, countG = 0;
                    if (p[1][i] == 1) {
                        countB++;  // 黑色
                    }
                    if (p[1][i] == 2) {
                        countG++;  // 灰色
                    }
                    if (p[3][i] == 1) {
                        countB++;  // 黑色
                    }
                    if (p[3][i] == 2) {
                        countG++;  // 灰色
                    }
                    if (countB == 1 && countG == 0)
                        c[j][i] = 1;
                    else if (countB > 1 || countG > 0)
                        c[j][i] = 2;  // 表灰色
                }
            }
            if (j == 2) {
                for (int i = 0; i < 4; i++) {
                    if (p[2][i] == 1) c[j][i] = 1;
                    else if (p[2][i] == 2) c[j][i] = 2;
                }
            }
            if (j == 3) {
                for (int i = 0; i < 4; i++) {
                    if (p[3][i] == 1) c[j][i] = 1;
                    else if (p[3][i] == 2) c[j][i] = 2;
                }
            }
        }
        return c;
    }

    // 判断二维整数型数组中非零位的个数
    public static int Count(int[][] l) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (l[i][j] != 0) count = count + 1;
            }
        }
        return count;
    }

    // 输出二维整数型数组中第一个2的位置
    public static int positionOf2(int[][] l) {
        int count = 0, k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (l[i][j] == 2) {
                    count++;
                    k = i * 4 + j;
                }
            }
        }
        if (count == 1) {
            return k;
        } else {
            return -1;
        }
    }

    // 判断list.get()中非零元素的个数
    public static int CountString(String string) {
        int count = 0;
        int[][] c = new int[4][4];
        for (int i = 0; i < string.length(); i++) {
            c[i / 4][i % 4] = (int) string.charAt(i) - 48;
            if (c[i / 4][i % 4] != 0) count = count + 1;
        }
        return count;
    }

    // 将二维整数型数组转换为字符串 : 回显的，存在空格
    public static String ChangeDoulbe(int[][] arr) {
        String str = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 3 && i != 3) {
                    str = str + arr[i][j] + " ";
                } else {
                    str = str + arr[i][j];
                }
            }
        }
        return str;
    }

    // 将二维整数型数组转换为字符串 : 不回显的，连续的
    public static String ChangeDoulbe1(int[][] arr) {
        String str = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                str = str + arr[i][j];
            }
        }
        return str;
    }

    // 将一维整数型数组转换为字符串
    public static String ChangeSingle(int[] arr) {
        String str = "";
        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1) {
                str = str + arr[i] + " ";
            } else {
                str = str + arr[i];
            }
        }
        return str;
    }

    // 判断可以或不可以由密钥调度推出的位置
    /*
    ETK1 : 最后(最前)两轮中的一个
    ETK2 ：除了最后两轮中的(E)TK的其他值
     */
    public static int[] KeyScheJudgeETK(int[][] ETK1, int[][] ETK2, int judge) {
        int[] arr = new int[16]; // judge = 1：保存可以推出的位置
        int[] arrj = new int[16]; // judge = 0：保存不可以推出的位置
        int count = 0, countj = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (ETK1[i][j] != 0) {
                    if (ETK2[i][j] != 0) {
                        arr[count] = 4*i + j;
                        count++;
                    } else { // no
                        arrj[countj] = 4*i + j;
                        countj++;
                    }
                }
            }
        }
        int[] c = new int[count];  // 保存可以由密钥编排关系推导出的密钥值对应的位置
        for (int i = 0; i < count; i++) {
            c[i] = arr[i];
        }
        int[] cj = new int[countj];  // 保存可以由密钥编排关系推导出的密钥值对应的位置
        for (int i = 0; i < countj; i++) {
            cj[i] = arrj[i];
        }
        if (judge == 1) { // 返回可以由密钥调度推出的位置
            return c;
        } else {
            return cj; // 返回置不可以由密钥调度推出的位
        }
    }

    // 判断可以或不可以由密钥调度推出的位置
    public static int[] KeyScheJudge(int[][] TK, int[][] ETK, int judge) {
        int[] arr = new int[16]; // judge = 1：保存可以推出的位置
        int[] arrj = new int[16]; // judge = 0：保存不可以推出的位置
        int count = 0, countj = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (TK[i][j] != 0) {
                    if (i == 0) {
                        if (ETK[i][j] != 0 && ETK[i + 2][j] != 0 && ETK[i + 3][j] != 0) { // yes
                            arr[count] = j;
                            count++;
                        } else { // no
                            arrj[countj] = j;
                            countj++;
                        }
                    }
                    if (i == 1) {
                        if (ETK[i][j] != 0 && ETK[i + 2][j] != 0) {
                            arr[count] = 4 + j;
                            count++;
                        } else {
                            arrj[countj] = 4 + j;
                            countj++;
                        }
                    }
                    if (i == 2) {
                        if (ETK[i][j] != 0) {
                            arr[count] = 8 + j;
                            count++;
                        } else {
                            arrj[countj] = 8 + j;
                            countj++;
                        }
                    }
                    if (i == 3) {
                        if (ETK[i][j] != 0) {
                            arr[count] = 12 + j;
                            count++;
                        } else {
                            arrj[countj] = 12 + j;
                            countj++;
                        }
                    }
                }
            }
        }
        int[] c = new int[count];  // 保存可以由密钥编排关系推导出的密钥值对应的位置
        for (int i = 0; i < count; i++) {
            c[i] = arr[i];
        }
        int[] cj = new int[countj];  // 保存可以由密钥编排关系推导出的密钥值对应的位置
        for (int i = 0; i < countj; i++) {
            cj[i] = arrj[i];
        }
        if (judge == 1) { // 返回可以由密钥调度推出的位置
            return c;
        } else {
            return cj; // 返回不可以由密钥调度推出的位置
        }
    }

    // 判断两个二位矩阵的值是否一致
    public static boolean JudgeJuzhenequals(int[][] M1, int[][] M2) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (M1[i][j] != M2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // 判断第一个非零点的位置
    public static int JudgeFirstZeroPosition(int[][] P) {
        for (int i = 0; i < P.length; i++) {
            for (int j = 0; j < P.length; j++) {
                if (P[i][j] == 0)
                    return 4 * i + j;
            }
        }
        return -1; // -1表示零点位置
    }

    // 初始化一个二维数组
    public static int[][] InitArray(int[][] P) {
        for (int j = 0; j < P.length; j++) {
            for (int k = 0; k < P.length; k++) {
                P[j][k] = 0;
            }
        }
        return P;
    }

    // 将STring转化为int
    public static int[][] StringToInt(String string) {
        int[][] c = new int[4][4];
        for (int i = 0; i < string.length(); i++) {
            c[i / 4][i % 4] = (int) string.charAt(i) - 48;
        }
        return c;
    }


    // 将一维int数组转化为二维int数组
    public static int[][] arrOneToTwo(int[] arr) {
        int[][] c = new int[4][4];
        c = InitArray(c);
        for (int i = 0; i < arr.length; ++i) {
            c[arr[i] / 4][arr[i] % 4] = 1;
        }
        return c;
    }

    /**
     * 在区分器基础上前接和后接轮
     */
    // 2-1
    // 向前接的轮：每轮的密钥具体值保存在一个集合中
    public static List BeforeRounds0(int[][] P) {
        int[][] templ;
        List list = new ArrayList<>();
        // while(Count(P) <= 15){
        while(Count(P) != 15){ // 前接轮第一轮非零nibble的个数小于15,也是会超
                templ = PN(P);
                list.add(ChangeDoulbe1(templ));  // 将其前接轮的所有密钥值保存在List集合中
                P = MC(templ);
        }
        return list;
    }

    // 向后接的轮：每轮的密钥具体值保存在一个集合中
    public static List AfterRounds0(int[][] P) {
        int[][] templ;
        int k;
        List list = new ArrayList<>();
        while (Count(P) <= 15) {
            list.add(ChangeDoulbe1(P));  // 将其后接轮的所有密钥值保存在List集合中
            templ = MCGray(P);
            P = PN(templ);
            k = positionOf2(P);
            if (k >= 0) {
                P[k / 4][k % 4] = 0;
            }
        }
        return list;
    }

    // 1-2
    // 向前接的轮：每轮的密钥具体值保存在一个集合中
    public static List BeforeRounds(int[][] P) {
        int[][] templ;
        int k;
        List list = new ArrayList<>();
        while (Count(P) <= 15) {
            templ = PN(P);
            list.add(ChangeDoulbe1(templ));  // 将其前接轮的所有密钥值保存在List集合中
            P = MCGray(templ);
            k = positionOf2(P);
            if (k >= 0) {
                P[k / 4][k % 4] = 0;
            }
        }
        return list;
    }

    // 向后接的轮：每轮的密钥具体值保存在一个集合中
    public static List AfterRounds(int[][] P) {
        int[][] templ;
        List list = new ArrayList<>();
        while (Count(P) <= 15) {
            list.add(ChangeDoulbe1(P));  // 将其后接轮的所有密钥值保存在List集合中
            templ = MC(P);
            P = PN(templ);
        }
        return list;
    }

    /**
     * 找出最前面两轮或最后两轮的密钥值
     *
     * @param list : 存储的密钥值的List集合
     * @param bora ： 第一轮或第二轮的密钥值；倒数第一轮或倒数第二轮的密钥值；0：第一轮或最后一轮
     * @return 返回对应的密钥值
     */
    public static int[][] KeyValue(List list, int bora) {
        int[][] TK1 = new int[4][4], TK0 = new int[4][4];
        if (list.size() > 2) {
            List sublist = list.subList(list.size() - 2, list.size());
            boolean flag = true;
            for (Object o : sublist) {
                String string = (String) o;
                if (flag == true) {
                    TK1 = StringToInt(string);
                } else {
                    TK0 = StringToInt(string);
                }
                flag = false;
            }
        }
        if (bora == 0) {
            return TK0;
        } else {
            return TK1;
        }
    }

    /**
     * 原版区分器部分
     */
    // 1.加密
    public static int[][] DistinEnc(int[][] P) {
        int[][] templ;
        int count = 0;
        // MC --> ARC --> ATK --> PN --> SB
        while (Count(P) != 15) {
            //System.out.println(ChangeDoulbe(P));
            templ = MCGray(P);
            //System.out.println(ChangeDoulbe(templ));
            P = PN(templ);
            //System.out.println(ChangeDoulbe(P));
            //System.out.println("---------------------");
            count++;
        }
        //System.out.println(count);
        return P;
    }

    // 2.解密
    public static int[][] DistinDec(int[][] P) {
        int[][] templ;
        int count = 0;
        // SB --> PN --> ATK --> ARC --> MC
        while (Count(P) != 15) {
            // System.out.println(ChangeDoulbe(P));
            templ = PN(P);
            //System.out.println(ChangeDoulbe(templ));
            P = MCGray(templ);
            // System.out.println(ChangeDoulbe(P));
            // System.out.println("---------------------");
            count++;
        }
        //System.out.println(count);
        return P;
    }

    // 区分器的构造：确定其长度并且确保会有冲突
    public static void Distin() {
        int[][] initEnc = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                initDec = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                tem; // 初始化变量

        // initEnc中取一个1状态值扩散混淆至全部的情况
        for (int m = 0; m < 4; m++) {
            for (int i = 0; i < 4; i++) {
                int count = 0;
                initEnc[m][i] = 1; // 第m行第i列

                // MC --> ARC --> ATK --> PN --> SB  加密
                while (Count(initEnc) < 15) {  // 留一个空白的，制造矛盾点
                    tem = MCGray(initEnc);
                    initEnc = PN(tem);
                    count++;
                }
                // initDec中取一个1状态值扩散混淆至全部的情况
                for (int a = 0; a < 4; a++) {
                    for (int s = 0; s < 4; s++) {
                        int countD = 0;
                        initDec[a][s] = 1; // 第m行第i列
                        // SB --> PN --> ATK --> ARC --> MC  解密
                        while (Count(initDec) < 15) {
                            tem = PN(initDec);
                            initDec = MCGray(tem);
                            countD++;
                        }
                        // 判断是否有矛盾点
                        int positionEnc = JudgeFirstZeroPosition(initEnc)
                                , sum = 0, keyCount = 0, keySumCount = 0;
                        if ((count + countD) >= 12) { // 可以构造的最大轮的数量大于12轮的
                            if (positionEnc >= 0) { // 存在矛盾点，矛盾点位置为[positionEnc/4][positionEnc%4]
                                if (initDec[positionEnc / 4][positionEnc % 4] == 1) { // 向前解密(加密)一轮  2（1）进1（2）出
                                    int[][] Dec = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}, deC;
                                    int[][] Enc = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}, enC;
                                    int countEnc = count,
                                            countDec = countD;
                                    Enc[m][i] = 1; // 2进1出
                                    Dec[a][s] = 1;
                                    deC = Dec; // 1进2出
                                    enC = Enc;

                                    // 2进1出
                                    System.out.println("===================2进1出=====================");
                                    if (m == 1) { // 向前解密一轮得到2进1出的格式
                                        Enc = PN(Enc);
                                        Enc = MC(Enc);
                                        countEnc += 1;
                                    }

                                    List listBefore = BeforeRounds0(Enc);

                                    // 统计有多少个灰色的块
                                    List After = new ArrayList<>();
                                    int dEc[][] = Dec;
                                    // while (Count(dEc) <= 15) { // 加密至最后一轮
                                    while (Count(dEc) < 15) { // 加密至最后一轮
                                        After.add(ChangeDoulbe1(dEc));  // 将其后接轮的所有密钥值保存在List集合中
                                        dEc = MCGray(dEc);
                                        //dEc = MC(dEc);
                                        dEc = PN(dEc);
                                    }
                                    int count1 = 0;
                                    for (int j = 0; j < After.size() - 1; j++) { // 对每轮中的灰色块数量进行统计
                                        int D[][] = StringToInt( (String)After.get(j) );
                                        for (int k = 0; k < 4; k++) {
                                            for (int l = 0; l < 4; l++) {
                                                if (D[k][l] == 2) {
                                                    count1++;
                                                }
                                            }
                                        }
                                    }
                                    // 使用一个for循环将count1个灰色的块分别依次进行置零
                                    int count2 = 0;
                                    for (int j1 = 1; j1 <= count1; j1++) {
                                        int dEc1[][] = Dec;
                                        List listAfter = new ArrayList<>();
                                        // while (Count(dEc1) <= 15) {
                                        while (Count(dEc1) < 15) {
                                            for (int mc = 0; mc < 4; mc++) {
                                                for (int k = 0; k < 4; k++) {
                                                    if (dEc1[mc][k] == 2) {
                                                        count2++;
                                                        if (count2 == j1) {
                                                            dEc1[mc][k] = 0;
                                                        }
                                                    }
                                                }
                                            }
                                            listAfter.add(ChangeDoulbe1(dEc1));
                                            dEc1 = MCGray(dEc1);
                                            dEc1 = PN(dEc1);
                                        }
                                        // 每轮均验证
                                        VerRound21(countDec,countEnc,Enc,Dec,listAfter,listBefore);
                                        // VerRound21_1(countDec,countEnc,Enc,Dec,listAfter,listBefore);
                                    }
                                    // VerRound21(countDec,countEnc,Enc,Dec,After,listBefore);
                                    System.out.println("===================2进1出=====================");

                                    /*//TODO
                                    // 2进1出的还有问题，按照后接轮含2进行修改，但是结果不对*/

                                    // 1进2出
                                    System.out.println("===================1进2出=====================");
                                    countEnc = count;
                                    countDec = countD;
                                    if (a == 2) { // 向后加密一轮得到1进2出的格式
                                        deC = MC(deC);
                                        deC = PN(deC);
                                        countDec += 1;
                                    }
                                    List listAfter1 = AfterRounds(deC);

                                    // 统计有多少个灰色的块
                                    List Before = new ArrayList<>();
                                    int eNc[][] = enC;
                                    while (Count(eNc) < 15) { // 加密至最后一轮
                                        eNc = PN(eNc);
                                        Before.add(ChangeDoulbe1(eNc));  // 将其前接轮的所有密钥值保存在List集合中
                                        eNc = MCGray(eNc);
                                    }
                                    int count3 = 0;
                                    for (int j = 0; j < Before.size() - 1; j++) { // 对每轮中的灰色块数量进行统计
                                        int E[][] = StringToInt( (String)Before.get(j) );
                                        for (int k = 0; k < 4; k++) {
                                            for (int l = 0; l < 4; l++) {
                                                if (E[k][l] == 2) {
                                                    count3++;
                                                    //k1 = k * 4 + l;
                                                }
                                            }
                                        }
                                    }

                                    // 使用一个for循环将count1个灰色的块分别进行置零
                                    int count4 = 0;
                                    for (int j1 = 1; j1 <= count3; j1++) {
                                        int eNc1[][] = enC;
                                        List listBefore1 = new ArrayList<>();
                                        while (Count(eNc1) <= 15) {
                                            eNc1 = PN(eNc1);
                                            for (int mc = 0; mc < 4; mc++) {
                                                for (int k = 0; k < 4; k++) {
                                                    if (eNc1[mc][k] == 2) {
                                                        count4++;
                                                        if (count4 == j1) {
                                                            eNc1[mc][k] = 0;
                                                        }
                                                    }
                                                }
                                            }
                                            listBefore1.add(ChangeDoulbe1(eNc1));  // 将其前接轮的所有密钥值保存在List集合中
                                            eNc1 = MCGray(eNc1);
                                        }
                                        // 每轮均验证
                                        VerRound12(countDec,countEnc,enC,deC,listAfter1,listBefore1);
                                    }
                                    System.out.println("===================1进2出=====================");
                                }
                            }
                        }
                        // 重新置零
                        initDec = InitArray(initDec);
                    }
                }
                // 重新置零
                initEnc = InitArray(initEnc);
            }
        }
    }

    public static void VerRound21(int countDec,int countEnc, int[][] Enc, int[][] Dec, List listAfter, List listBefore) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        int sum = 0, flag = 0, keyCount = 0, keySumCount = 0;
        System.out.println("2进1出：" + ChangeDoulbe1(Enc) + " " + ChangeDoulbe1(Dec));
        sum += countDec + countEnc + listAfter.size() + listBefore.size();
        // System.out.println("countDec：" + countDec + "，countEnc：" + countEnc + "，listAfter.size()：" + listAfter.size() + "，listBefore.size()：" + listBefore.size();
        System.out.println("前接轮：" + listBefore.size() + "，前接轮（区分器）：" + countEnc + "，后接轮（区分器）：" + countDec + "，后接轮：" + listAfter.size() + "，总轮数：" + sum);

        System.out.println("后:");
        for(int k = 1;k <= listAfter.size(); k++) { // 从最后一轮开始打印
            System.out.println(listAfter.get(listAfter.size() - k));
        } // 最后一轮不用

        // 区分器后接轮密钥关系计算
        int lastNum = CountString((String) listAfter.get(listAfter.size() - 1));  // 最后一轮中ETK非零元素个数
        int secondToLastNum = CountString((String) listAfter.get(listAfter.size() - 2)); // 倒数第二轮中ETK非零元素个数
        keyCount = lastNum + secondToLastNum;
        keySumCount = lastNum + secondToLastNum;
        System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
        for (int j = listAfter.size() - 3; j >= 0; --j) { //最后一轮的轮密码不能用1111110111111111 ，因为使用了ETK
            if (flag == 0) {
                int last0[] = KeyScheJudgeETK(StringToInt((String) listAfter.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 1)), 0); // 推不出来的元素
                int last1[] = KeyScheJudgeETK(StringToInt((String) listAfter.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 1)), 1);
                keyCount += last0.length;
                keySumCount = keySumCount + last0.length + last1.length;
                // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                flag = 1;
            } else {
                int last0[] = KeyScheJudgeETK(StringToInt((String) listAfter.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 0); // 推不出来的元素
                int last1[] = KeyScheJudgeETK(StringToInt((String) listAfter.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 1);
                keyCount += last0.length;
                keySumCount = keySumCount + last0.length + last1.length;
                // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                flag = 0;
            }
        }

        // 区分器前接轮密钥关系计算，奇偶分开考虑
        if (sum % 2 == 0) { // 说明是偶数轮，最后一轮可以推倒第二轮
            flag = 0;
            for (int j = listBefore.size() - 1; j >= 0; --j) {
                if (flag == 0) {
                    int last0[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 1);
                    if (last0.length != 0) {
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter.get(listAfter.size() - 4)), 0);
                        int e = listAfter.size() - 4; // 记录后轮中的第二个推导ETK的位置
                        for (int k = e - 2; k >= 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listAfter.get(k)), 0);
                        }
                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    }
//                    keyCount += last0.length;
//                    keySumCount = keySumCount + last0.length + last1.length;
//                    System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    ++flag;
                } else if (flag == 1) {
                    int last0[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 1)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 1)), 1);
                    if (last0.length != 0) {
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter.get(listAfter.size() - 3)), 0);
                        int e = listAfter.size() - 3; // 记录后轮中的第二个推导ETK的位置
                        for (int k = e - 2; k >= 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listAfter.get(k)), 0);
                        }
                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    }
//                    keyCount += last0.length;
//                    keySumCount = keySumCount + last0.length + last1.length;
//                    System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    ++flag;
                } else if (flag % 2 == 0) { // 除第一轮外的奇数轮
                    int last0[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 1)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 1)), 1);
                    if (last0.length != 0) {
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter.get(listAfter.size() - 2)), 0);
                        for (int k = flag; k >= 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listBefore.get(listBefore.size() - 1 - k)), 0);
                        }
                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    }
                    ++flag;
                } else {
                    int last0[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 2)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 2)), 1);
                    if (last0.length != 0) {
                        // 放到ETK中验证
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter.get(listAfter.size() - 1)), 0);
                        // 放到ATK中验证
                        for (int k = flag; k >= 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listBefore.get(listBefore.size() - 1 - k)), 0);
                        }
                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    }
                    ++flag;
                }
            }
        } else { // 说明是奇数轮，最后一轮可以推倒第一轮
            flag = 0;
            for (int j = listBefore.size() - 1; j >= 0; --j) {
                if (flag == 0) { // 第一轮
                    int last0[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 1)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 1)), 1);
                    if (last0.length != 0) {
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter.get(listAfter.size() - 3)), 0);
                        int e = listAfter.size() - 3; // 记录后轮中的第二个推导ETK的位置
                        for (int k = e - 2; k >= 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listAfter.get(k)), 0);
                        }
                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    }
//                    keyCount += last0.length;
//                    keySumCount = keySumCount + last0.length + last1.length;
//                    System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    //System.out.println(listBefore.get(j));
                    ++flag;
                } else if (flag == 1) { // 第二轮
                    int last0[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 1);
                    if (last0.length != 0) {
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter.get(listAfter.size() - 4)), 0);
                        int e = listAfter.size() - 4; // 记录后轮中的第二个推导ETK的位置
                        for (int k = e - 2; k >= 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listAfter.get(k)), 0);
                        }
                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    }
//                    keyCount += last0.length;
//                    keySumCount = keySumCount + last0.length + last1.length;
//                    System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    //System.out.println(listBefore.get(j));
                    ++flag;
                } else if (flag % 2 == 0) { // 除第一轮外的奇数轮
                    int last0[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 1)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 1)), 1);

                    if (last0.length != 0) { // 如果
                        // 放到ETK中验证
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter.get(listAfter.size() - 1)), 0);
                        // 放到ATK中验证
                        for (int k = flag; k >= 0; k = k - 2) {
                            last00 = KeyScheJudge(arrOneToTwo(last00), StringToInt((String) listBefore.get(listBefore.size() - 1 - k)), 0);
                        }
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    }
                    ++flag;
                } else {
                    int last0[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 2)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 2)), 1);
                    if (last0.length != 0) {
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter.get(listAfter.size() - 2)), 0);

                        for (int k = flag; k >= 0; k = k - 2) {
                            last00 = KeyScheJudge(arrOneToTwo(last00), StringToInt((String) listBefore.get(listBefore.size() - 1 - k)), 0);
                        }

                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                        // System.out.println("keyCount:" + keyCount + ", keySumCount:" + keySumCount);
                    }
                    ++flag;
                }
            }
        }

        /*
        // 判断第一轮是否还可以减少一个猜测的密钥值（12-15位置上为2的项）
        String str = (String)listAfter.get(listAfter.size() - 1);
        str = "000000000000" + str.substring(12,16); // 得到第一轮密钥的后四位
        listAfter.add(str); // 将其添加到list中
        for (int j = 0; j < 4; j++) {
            int flag1 = 0; // 通过flag1来判断后面是否会用到前面的项
            if(str.substring(12 + j, 13 + j).equals("2")){ // 第一轮的12-15位依次进行判断(不能使用 ==)
                for (int k = listAfter.size() - 4; k >= 0; k = k - 2) {
                    String str1 = (String)listAfter.get(k);
                    str1 = str1.substring(12 + j, 13 + j);
                    if(str1.equals("0")){ // 和第一轮无关就是flag1 = 1
                        flag1++;  // 一般就比较两个，两个全为0 则表示均无关
                    }
                }
            }
            if(flag1 == 2){ // 均无关
                String str2 = (String)listAfter.get(listAfter.size() - 2);
                if(j != 3) {
                    listBefore.set(listAfter.size() - 2,(str2.substring(0, 12 + j) + "0" + str2.substring(13 + j, 16)));
                }else{
                    listBefore.set(listAfter.size() - 2,str2.substring(0, 12 + j) + "0");
                }
                keySumCount--;
                int last0[] = {};
                if((sum - 1) %2 == 0){ // 偶数轮
                    last0 = KeyScheJudge(StringToInt((String) listBefore.get(listBefore.size() - 2)), StringToInt((String) listAfter.get(listAfter.size() - 3)), 0);
                }else{
                    last0 = KeyScheJudge(StringToInt((String) listBefore.get(listBefore.size() - 2)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 0);
                }
                for (int k = 0; k < last0.length; k++) { // 判断该位置的密钥是否需要猜测
                    if(last0[k] == (12 + j)) { // 如果是需要猜测的话就将其总数减一
                        keyCount--;
                    }
                }

            }
        }*/

        System.out.println("前:");
        for(int k = listBefore.size() - 1;k >= 0; --k) { // 最后一轮不打印（新添加的）
            System.out.println(listBefore.get(k));
        }
        System.out.println("密钥关系后的密钥数：" + keyCount + "，总共的密钥数：" + keySumCount);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public static void VerRound21_1(int countDec,int countEnc, int[][] Enc, int[][] Dec, List listAfter, List listBefore) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        int sum = 0, flag = 0, keyCount = 0, keySumCount = 0;
        System.out.println("2进1出：" + ChangeDoulbe1(Enc) + " " + ChangeDoulbe1(Dec));
        sum += countDec + countEnc + listAfter.size() + listBefore.size();
        System.out.println("前接轮：" + listBefore.size() + "，前接轮（区分器）：" + countEnc + "，后接轮（区分器）：" + countDec + "，后接轮：" + (listAfter.size()-1) + "，总轮数：" + (sum-1));

        System.out.println("后:");
        for(int k = 2;k <= listAfter.size(); k++) { // 从倒数第二轮开始打印
            System.out.println(listAfter.get(listAfter.size() - k));
        } // 最后一轮不用

        // 区分器后接轮密钥关系计算
        int lastNum = CountString((String) listAfter.get(listAfter.size() - 1));  // 最后一轮中ETK非零元素个数
        int secondToLastNum = CountString((String) listAfter.get(listAfter.size() - 2)); // 倒数第二轮中ETK非零元素个数
        keyCount = lastNum + secondToLastNum;
        keySumCount = lastNum + secondToLastNum;
        for (int j = listAfter.size() - 4; j >= 0; --j) { //最后一轮的轮密码不能用1111110111111111 ，因为使用了ETK
            if (flag == 0) {
                int last0[] = KeyScheJudgeETK(StringToInt((String) listAfter.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 0); // 推不出来的元素
                int last1[] = KeyScheJudgeETK(StringToInt((String) listAfter.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 1);
                keyCount += last0.length;
                keySumCount += last1.length;
                flag = 1;
            } else {
                int last0[] = KeyScheJudgeETK(StringToInt((String) listAfter.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 3)), 0); // 推不出来的元素
                int last1[] = KeyScheJudgeETK(StringToInt((String) listAfter.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 3)), 1);
                keyCount += last0.length;
                keySumCount += last1.length;
                flag = 0;
            }
        }

        // 区分器前接轮密钥关系计算，奇偶分开考虑
        if ((sum-1) % 2 == 0) { // 说明是偶数轮，倒数第二轮可以推倒第二轮
            flag = 0;
            for (int j = listBefore.size() - 1; j >= 0; --j) {
                if (flag == 0) {
                    int last0[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 3)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 3)), 1);
                    // System.out.println(listBefore.get(j));
                    keyCount += last0.length;
                    keySumCount = keySumCount + last0.length + last1.length;
                    ++flag;
                } else if (flag == 1) {
                    int last0[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 1);
                    // System.out.println(listBefore.get(j));
                    keyCount += last0.length;
                    keySumCount = keySumCount + last0.length + last1.length;
                    ++flag;
                } else if (flag % 2 == 0) { // 除第一轮外的奇数轮
                    int last0[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 1)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 1)), 1);
                    if (last0.length != 0) {
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter.get(listAfter.size() - 3)), 0);
                        for (int k = flag; k >= 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listBefore.get(listBefore.size() - 1 - k)), 0);
                        }
                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    }
                    ++flag;
                } else {
                    int last0[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 2)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 2)), 1);
                    if (last0.length != 0) {
                        // 放到ETK中验证
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter.get(listAfter.size() - 2)), 0);
                        // 放到ATK中验证
                        for (int k = flag; k >= 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listBefore.get(listBefore.size() - 1 - k)), 0);
                        }
                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    }
                    ++flag;
                }
            }
        } else { // 说明是奇数轮，最后一轮可以推倒第一轮
            flag = 0;
            for (int j = listBefore.size() - 1; j >= 0; --j) {
                if (flag == 0) { // 第一轮
                    int last0[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 1);
                    keyCount += last0.length;
                    keySumCount = keySumCount + last0.length + last1.length;
                    //System.out.println(listBefore.get(j));
                    ++flag;
                } else if (flag == 1) { // 第二轮
                    int last0[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 3)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudge(StringToInt((String) listBefore.get(j)), StringToInt((String) listAfter.get(listAfter.size() - 3)), 1);
                    keyCount += last0.length;
                    keySumCount = keySumCount + last0.length + last1.length;
                    //System.out.println(listBefore.get(j));
                    ++flag;
                } else if (flag % 2 == 0) { // 除第一轮外的奇数轮
                    int last0[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 1)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 1)), 1);

                    if (last0.length != 0) { // 如果
                        // 放到ETK中验证
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter.get(listAfter.size() - 2)), 0);
                        // 放到ATK中验证
                        for (int k = flag; k >= 0; k = k - 2) {
                            last00 = KeyScheJudge(arrOneToTwo(last00), StringToInt((String) listBefore.get(listBefore.size() - 1 - k)), 0);
                        }
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    }
                    ++flag;
                } else {
                    int last0[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 2)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudgeETK(StringToInt((String) listBefore.get(j)), StringToInt((String) listBefore.get(listBefore.size() - 2)), 1);
                    if (last0.length != 0) {
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter.get(listAfter.size() - 3)), 0);

                        for (int k = flag; k >= 0; k = k - 2) {
                            last00 = KeyScheJudge(arrOneToTwo(last00), StringToInt((String) listBefore.get(listBefore.size() - 1 - k)), 0);
                        }

                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    }
                    ++flag;
                }
            }
        }

        /*
        // 判断第一轮是否还可以减少一个猜测的密钥值（12-15位置上为2的项）
        String str = (String)listAfter.get(listAfter.size() - 1);
        str = "000000000000" + str.substring(12,16); // 得到第一轮密钥的后四位
        listAfter.add(str); // 将其添加到list中
        for (int j = 0; j < 4; j++) {
            int flag1 = 0; // 通过flag1来判断后面是否会用到前面的项
            if(str.substring(12 + j, 13 + j).equals("2")){ // 第一轮的12-15位依次进行判断(不能使用 ==)
                for (int k = listAfter.size() - 4; k >= 0; k = k - 2) {
                    String str1 = (String)listAfter.get(k);
                    str1 = str1.substring(12 + j, 13 + j);
                    if(str1.equals("0")){ // 和第一轮无关就是flag1 = 1
                        flag1++;  // 一般就比较两个，两个全为0 则表示均无关
                    }
                }
            }
            if(flag1 == 2){ // 均无关
                String str2 = (String)listAfter.get(listAfter.size() - 2);
                if(j != 3) {
                    listBefore.set(listAfter.size() - 2,(str2.substring(0, 12 + j) + "0" + str2.substring(13 + j, 16)));
                }else{
                    listBefore.set(listAfter.size() - 2,str2.substring(0, 12 + j) + "0");
                }
                keySumCount--;
                int last0[] = {};
                if((sum - 1) %2 == 0){ // 偶数轮
                    last0 = KeyScheJudge(StringToInt((String) listBefore.get(listBefore.size() - 2)), StringToInt((String) listAfter.get(listAfter.size() - 3)), 0);
                }else{
                    last0 = KeyScheJudge(StringToInt((String) listBefore.get(listBefore.size() - 2)), StringToInt((String) listAfter.get(listAfter.size() - 2)), 0);
                }
                for (int k = 0; k < last0.length; k++) { // 判断该位置的密钥是否需要猜测
                    if(last0[k] == (12 + j)) { // 如果是需要猜测的话就将其总数减一
                        keyCount--;
                    }
                }

            }
        }*/

        System.out.println("前:");
        for(int k = listBefore.size() - 1;k >= 0; --k) { // 最后一轮不打印（新添加的）
            System.out.println(listBefore.get(k));
        }
        System.out.println("密钥关系后的密钥数：" + keyCount + "，总共的密钥数：" + keySumCount);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public static void VerRound12(int countDec,int countEnc, int[][] enC, int[][] deC, List listAfter1, List listBefore1) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        int sum = 0, flag = 0, keyCount = 0, keySumCount = 0;
        sum += countDec + countEnc + listAfter1.size() + listBefore1.size();
        System.out.println("1进2出：" + ChangeDoulbe1(enC) + " " + ChangeDoulbe1(deC));
        System.out.println("前接轮：" + listBefore1.size() + "，前接轮（区分器）：" + countEnc + "，后接轮（区分器）：" + countDec + "，后接轮：" + (listAfter1.size() - 1) + "，总轮数：" + (sum - 1));

        // 区分器后接轮密钥关系计算
        System.out.println("后:");
        for(int k = 2;k <= listAfter1.size(); k++) { // 最后一轮不打印
            System.out.println(listAfter1.get(listAfter1.size() - k));
        } // 最后一轮不用
        int lastNum1 = CountString((String) listAfter1.get(listAfter1.size() - 2));  // 最后一轮中ETK非零元素个数
        int secondToLastNum1 = CountString((String) listAfter1.get(listAfter1.size() - 3)); // 倒数第二轮中ETK非零元素个数
        keyCount = lastNum1 + secondToLastNum1;
        keySumCount = lastNum1 + secondToLastNum1;
        for (int j = listAfter1.size() - 4; j >= 0; --j) { // 最后一轮的不用，从倒数第四个开始推导
            if (flag == 0) {
                int last0[] = KeyScheJudgeETK(StringToInt((String) listAfter1.get(j)), StringToInt((String) listAfter1.get(listAfter1.size() - 2)), 0); // 推不出来的元素
                int last1[] = KeyScheJudgeETK(StringToInt((String) listAfter1.get(j)), StringToInt((String) listAfter1.get(listAfter1.size() - 2)), 1);
                keyCount += last0.length;
                keySumCount = keySumCount + last0.length + last1.length;
                flag = 1;
            } else {
                int last0[] = KeyScheJudgeETK(StringToInt((String) listAfter1.get(j)), StringToInt((String) listAfter1.get(listAfter1.size() - 3)), 0); // 推不出来的元素
                int last1[] = KeyScheJudgeETK(StringToInt((String) listAfter1.get(j)), StringToInt((String) listAfter1.get(listAfter1.size() - 3)), 1);
                keyCount += last0.length;
                keySumCount = keySumCount + last0.length + last1.length;
                flag = 0;
            }
        }

        // 区分器前接轮密钥关系计算，奇偶分开考虑 : （只考虑23轮, 6+7+6+4 = 23，sum = 24 -1）
        if ((sum - 1) % 2 == 0) { // 说明是偶数轮，最后一轮可以推倒第一轮
            flag = 0;
            for (int j = listBefore1.size() - 1; j >= 0; --j) {
                if (flag == 0) {// 第一轮
                    int last0[] = KeyScheJudge(StringToInt((String) listBefore1.get(j)), StringToInt((String) listAfter1.get(listAfter1.size() - 3)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudge(StringToInt((String) listBefore1.get(j)), StringToInt((String) listAfter1.get(listAfter1.size() - 3)), 1);
                                                /*System.out.println("前：");
                                                System.out.println(listBefore1.get(j));*/
                    if (last0.length != 0) {
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter1.get(listAfter1.size() - 3)), 0);
                        int e = listAfter1.size() - 3; // 记录后轮中的第二个推导ETK的位置
                        for (int k = e - 2; k >= 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listAfter1.get(k)), 0);
                        }
                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    }
//                    keyCount += last0.length;
//                    keySumCount = keySumCount + last0.length + last1.length;
                    ++flag;
                } else if (flag == 1) {
                    int last0[] = KeyScheJudge(StringToInt((String) listBefore1.get(j)), StringToInt((String) listAfter1.get(listAfter1.size() - 2)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudge(StringToInt((String) listBefore1.get(j)), StringToInt((String) listAfter1.get(listAfter1.size() - 2)), 1);
                    //System.out.println(listBefore1.get(j));
                    if (last0.length != 0) {
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter1.get(listAfter1.size() - 2)), 0);
                        int e = listAfter1.size() - 2; // 记录后轮中的第二个推导ETK的位置
                        for (int k = e - 2; k >= 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listAfter1.get(k)), 0);
                        }
                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    } else {
                        keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    }
//                    keyCount += last0.length;
//                    keySumCount = keySumCount + last0.length + last1.length;
                    ++flag;
                } else if (flag % 2 == 0) { // 除第一轮外的奇数轮
                    int last0[] = KeyScheJudgeETK(StringToInt((String) listBefore1.get(j)), StringToInt((String) listBefore1.get(listBefore1.size() - 1)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudgeETK(StringToInt((String) listBefore1.get(j)), StringToInt((String) listBefore1.get(listBefore1.size() - 1)), 1);
                    //System.out.println(listBefore1.get(j));
                    if (last0.length != 0) {
                        // ATK0/1推导不出来就交给ETK最后两轮中的其中一个进行推导
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter1.get(listAfter1.size() - 3)), 0);
                        // ATK0/1以及ETK最后两轮中的一个均推导不出来就寻找ATK2-flag中的进行推导
                        for (int k = flag - 2; k > 0; k = k - 2) { // k = flag - 2是为了不让出现自己推到自己这种情况出现
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listBefore1.get(listBefore1.size() - 1 - k)), 0);
                        }
                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    } else {
                        // keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    }
                    ++flag;
                } else {
                    int last0[] = KeyScheJudgeETK(StringToInt((String) listBefore1.get(j)), StringToInt((String) listBefore1.get(listBefore1.size() - 2)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudgeETK(StringToInt((String) listBefore1.get(j)), StringToInt((String) listBefore1.get(listBefore1.size() - 2)), 1);
                    // System.out.println(listBefore1.get(j));
                    if (last0.length != 0) {
                        // 放到ETK中验证
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter1.get(listAfter1.size() - 2)), 0);
                        // 放到ATK中验证
                        for (int k = flag - 2; k > 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listBefore1.get(listBefore1.size() - 1 - k)), 0);
                        }
                        keyCount += last00.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    } else {
                        // keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    }
                    ++flag;
                }
            }


        } else { // 说明是奇数轮，最后一轮可以推倒第一轮
            flag = 0;
            for (int j = listBefore1.size() - 1; j >= 0; --j) {
                if (flag == 0) { // 第一轮
                    int last0[] = KeyScheJudge(StringToInt((String) listBefore1.get(j)), StringToInt((String) listAfter1.get(listAfter1.size() - 2)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudge(StringToInt((String) listBefore1.get(j)), StringToInt((String) listAfter1.get(listAfter1.size() - 2)), 1);
                    keyCount += last0.length;
                    keySumCount = keySumCount + last0.length + last1.length;
                    //System.out.println("前：");
                    //System.out.println(listBefore1.get(j));
                    // System.out.println(keyCount);
                    ++flag;
                } else if (flag == 1) { // 第二轮
                    int last0[] = KeyScheJudge(StringToInt((String) listBefore1.get(j)), StringToInt((String) listAfter1.get(listAfter1.size() - 3)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudge(StringToInt((String) listBefore1.get(j)), StringToInt((String) listAfter1.get(listAfter1.size() - 3)), 1);
                    keyCount += last0.length;
                    keySumCount = keySumCount + last0.length + last1.length;
                    //System.out.println(listBefore1.get(j));
                    //System.out.println(keyCount);
                    ++flag;
                } else if (flag % 2 == 0) { // 除第一轮外的奇数轮
                    int last0[] = KeyScheJudgeETK(StringToInt((String) listBefore1.get(j)), StringToInt((String) listBefore1.get(listBefore1.size() - 1)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudgeETK(StringToInt((String) listBefore1.get(j)), StringToInt((String) listBefore1.get(listBefore1.size() - 1)), 1);
                    //System.out.println(listBefore1.get(j));
                    if (last0.length != 0) {
                        // 放到ETK中验证
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter1.get(listAfter1.size() - 2)), 0);
                        // 放到ATK中验证
                        for (int k = flag - 2; k > 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listBefore1.get(listBefore1.size() - 1 - k)), 0);
                        }
                        keyCount += last00.length;
                        //System.out.println(keyCount);
                        keySumCount = keySumCount + last0.length + last1.length;
                    } else {
                        //System.out.println(keyCount);
                        // keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    }
                    ++flag;
                } else {
                    int last0[] = KeyScheJudgeETK(StringToInt((String) listBefore1.get(j)), StringToInt((String) listBefore1.get(listBefore1.size() - 2)), 0); // 推不出来的元素
                    int last1[] = KeyScheJudgeETK(StringToInt((String) listBefore1.get(j)), StringToInt((String) listBefore1.get(listBefore1.size() - 2)), 1);
                    //System.out.println(listBefore1.get(j));
                    if (last0.length != 0) {
                        int last00[] = KeyScheJudge(arrOneToTwo(last0), StringToInt((String) listAfter1.get(listAfter1.size() - 3)), 0);
                        for (int k = flag - 2; k > 0; k = k - 2) {
                            last00 = KeyScheJudgeETK(arrOneToTwo(last00), StringToInt((String) listBefore1.get(listBefore1.size() - 1 - k)), 0);
                        }
                        keyCount += last00.length;
                        //System.out.println(keyCount);
                        keySumCount = keySumCount + last0.length + last1.length;
                    } else {
                        //System.out.println(keyCount);
                        // keyCount += last0.length;
                        keySumCount = keySumCount + last0.length + last1.length;
                    }
                    ++flag;
                }
            }
        }

        // 判断第一轮是否还可以减少一个猜测的密钥值（12-15位置上为2的项）
        String str2 = (String)listBefore1.get(listBefore1.size() - 1);
        str2 = "000000000000" + str2.substring(12,16); // 得到第一轮密钥的后四位
        listBefore1.add(str2); // 将其添加到list中
        for (int j = 0; j < 4; j++) {
            int flag1 = 0; // 通过flag1来判断后面是否会用到前面的项
            if(str2.substring(12 + j, 13 + j).equals("2")){ // 第一轮的12-15位依次进行判断(不能使用 ==)
                for (int k = listBefore1.size() - 4; k >= 0; k = k - 2) {
                    String str1 = (String)listBefore1.get(k);
                    str1 = str1.substring(12 + j, 13 + j);
                    if(str1.equals("0")){ // 和第一轮无关就是flag1 = 1
                        flag1++;  // 一般就比较两个，两个全为0 则表示均无关
                    }
                }
            }
            if(flag1 == 2){ // 均无关
                String str3 = (String)listBefore1.get(listBefore1.size() - 2);
                if(j != 3) {
                    listBefore1.set(listBefore1.size() - 2,(str3.substring(0, 12 + j) + "0" + str3.substring(13 + j, 16)));
                }else{
                    listBefore1.set(listBefore1.size() - 2,str3.substring(0, 12 + j) + "0");
                }
                keySumCount--;
                int last0[] = {};
                if((sum - 1) %2 == 0){ // 偶数轮
                    last0 = KeyScheJudge(StringToInt((String) listBefore1.get(listBefore1.size() - 2)), StringToInt((String) listAfter1.get(listAfter1.size() - 3)), 0);
                }else{
                    last0 = KeyScheJudge(StringToInt((String) listBefore1.get(listBefore1.size() - 2)), StringToInt((String) listAfter1.get(listAfter1.size() - 2)), 0);
                }
                //System.out.println(ChangeSingle(last0));
                for (int k = 0; k < last0.length; k++) { // 判断该位置的密钥是否需要猜测
                    if(last0[k] == (12 + j)) { // 如果是需要猜测的话就将其总数减一
                        keyCount--;
                    }
                }

            }
        }

        System.out.println("前:");
        for(int k = listBefore1.size() - 2;k >= 0; --k) { // 最后一轮不打印（新添加的）
            System.out.println(listBefore1.get(k));
        }

        System.out.println("密钥关系后的密钥数：" + keyCount + "，总共的密钥数：" + keySumCount);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}