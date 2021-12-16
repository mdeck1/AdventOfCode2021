package com.malcolmdeck.adventofcode2021.levels.level16;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level16 {
    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level16\\level16data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            line = convertHexStringToBinaryString(line);
            Packet packet = parseAndReturnPacket(line);
            System.out.println("Answer: " + packet.getVersionSum());
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level16\\level16data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            line = convertHexStringToBinaryString(line);
            Packet packet = parseAndReturnPacket(line);
            System.out.println("Answer: " + packet.getValue().toString());
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static String convertHexStringToBinaryString(String hex) {
        StringBuilder builder = new StringBuilder(hex.length() * 4);
        for (int i = 0; i < hex.length(); ++i) {
            String binaryString =
                    Integer.toBinaryString(Integer.parseInt(hex.substring(i, i + 1), 16));
            while (binaryString.length() < 4) {
                binaryString = "0" + binaryString;
            }
            builder.append(binaryString);
        }
        return builder.toString();
    }

    private static abstract class Packet {
        int version;
        int type;
        int length;

        abstract int getVersionSum();
        
        abstract BigInteger getValue();
    }

    private static class LiteralPacket extends Packet {
        BigInteger value;

        LiteralPacket() {
            type = 4;
        }

        int getVersionSum() {
            return version;
        }
        
        BigInteger getValue() {
            return value;
        }

        static LiteralPacket parseFromString(String line) {
            LiteralPacket literalPacket = new LiteralPacket();
            literalPacket.version = Integer.parseInt(line.substring(0, 3), 2);
            int i = 6;
            StringBuilder builder = new StringBuilder();
            while (line.charAt(i) != '0') {
                builder.append(line.substring(i + 1, i + 5));
                i += 5;
            }
            // Last 5-bit to read;
            builder.append(line.substring(i + 1, i + 5));
            literalPacket.length = i + 5;
            literalPacket.value = new BigInteger(builder.toString(), 2);
            return literalPacket;
        }
    }

    private static class OperatorPacket extends Packet {
        int lengthTypeId;
        List<Packet> subPacketList;

        OperatorPacket() {
            this.subPacketList = new ArrayList<>();
        }

        int getVersionSum() {
            int versionSum = this.version;
            for (Packet packet : subPacketList) {
                versionSum += packet.getVersionSum();
            }
            return versionSum;
        }
        
        BigInteger getValue() {
            switch (type) {
                case 0:
                    BigInteger sum = BigInteger.ZERO;
                    for (Packet packet : subPacketList) {
                        sum = sum.add(packet.getValue());
                    }
                    return sum;
                case 1:
                    BigInteger product = BigInteger.ONE;
                    for (Packet packet : subPacketList) {
                        product = product.multiply(packet.getValue());
                    }
                    return product;
                case 2:
                    BigInteger current = subPacketList.get(0).getValue();
                    for (Packet packet : subPacketList) {
                        current = current.min(packet.getValue());
                    }
                    return current;
                case 3:
                    current = BigInteger.ZERO;
                    for (Packet packet : subPacketList) {
                        current = current.max(packet.getValue());
                    }
                    return current;
                case 5:
                    BigInteger a = subPacketList.get(0).getValue();
                    BigInteger b = subPacketList.get(1).getValue();
                    return a.compareTo(b) == 1 ? BigInteger.ONE : BigInteger.ZERO;
                case 6:
                    a = subPacketList.get(0).getValue();
                    b = subPacketList.get(1).getValue();
                    return a.compareTo(b) == -1 ? BigInteger.ONE : BigInteger.ZERO;
                case 7:
                    a = subPacketList.get(0).getValue();
                    b = subPacketList.get(1).getValue();
                    return a.compareTo(b) == 0 ? BigInteger.ONE : BigInteger.ZERO;
                default:
                    throw new RuntimeException("Unknown Operator Type: " + type);
            }
        }

        static OperatorPacket parseFromString(String line) {
            OperatorPacket operatorPacket = new OperatorPacket();
            operatorPacket.version = Integer.parseInt(line.substring(0, 3), 2);
            operatorPacket.type = Integer.parseInt(line.substring(3, 6), 2);
            operatorPacket.lengthTypeId = Integer.parseInt(line.substring(6, 7));
            if (operatorPacket.lengthTypeId == 0) {
                int lengthInBits = Integer.parseInt(line.substring(7, 22), 2);
                int currLengthParsed = 0;
                while (currLengthParsed < lengthInBits) {
                    Packet nextPacket = parseAndReturnPacket(line.substring(22 + currLengthParsed));
                    operatorPacket.subPacketList.add(nextPacket);
                    currLengthParsed += nextPacket.length;
                }
                operatorPacket.length = 22 + lengthInBits;
            } else {
                int numberOfPackets = Integer.parseInt(line.substring(7, 18), 2);
                int currOffset = 0;
                operatorPacket.length = 18;
                for (int i = 0; i < numberOfPackets; ++i) {
                    Packet nextPacket = parseAndReturnPacket(line.substring(18 + currOffset));
                    operatorPacket.subPacketList.add(nextPacket);
                    currOffset += nextPacket.length;
                    operatorPacket.length += nextPacket.length;
                }
            }
            return operatorPacket;
        }
    }

    private static Packet parseAndReturnPacket(String line) {
        if (Integer.parseInt(line.substring(3, 6), 2) == 4) {
            return LiteralPacket.parseFromString(line);
        } else {
            return OperatorPacket.parseFromString(line);
        }
    }
    
}
