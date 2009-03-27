package cn.icybear.GrassMudHorse;

import java.math.BigInteger;
import java.util.HashMap;

public class GMHCodeSection {
    String fileName;
    OpCode[] codes;
    BigInteger[] args;
    HashMap<BigInteger, Integer> labels;
    Integer[] lineNums;
    Integer[] colNums;
}
