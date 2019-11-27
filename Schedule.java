import java.util.ArrayList;
import java.util.Collections;

class Schedule {
  private int runningTotal = 0;
  private int stuNo = 0;
  private int choicecol;
  private int studID;
  private int numConflicts=0;
  private int[][] sessions = {{ 15,  13, 207, 201, 216,   4},        //row 100  rows are time slots, columns are locations
                              {215,   2,  10,   8,  11,  18}, //row 200
                              {  9, 202,   7,   5,  12, 100}, //row 300
                              {209,   6,   3,   1,  14, 204}, //row 400
                              {315,  17, 100, 301,  16, 100}  //row 500
				//7loc, 5slots not all exact, as modified in later method
  };

  ArrayList<Integer> s1 = new ArrayList<Integer>();
  ArrayList<Integer> s201 = new ArrayList<Integer>();
  ArrayList<Integer> s301 = new ArrayList<Integer>();
  ArrayList<Integer> s2 = new ArrayList<Integer>();
  ArrayList<Integer> s202 = new ArrayList<Integer>();
  ArrayList<Integer> s3 = new ArrayList<Integer>();
  ArrayList<Integer> s4 = new ArrayList<Integer>();
  ArrayList<Integer> s5 = new ArrayList<Integer>();
  ArrayList<Integer> s6 = new ArrayList<Integer>();
  ArrayList<Integer> s7 = new ArrayList<Integer>();
  ArrayList<Integer> s207 = new ArrayList<Integer>();
  ArrayList<Integer> s8 = new ArrayList<Integer>();
  ArrayList<Integer> s9 = new ArrayList<Integer>();
  ArrayList<Integer> s209 = new ArrayList<Integer>();
  ArrayList<Integer> s10 = new ArrayList<Integer>();
  ArrayList<Integer> s11 = new ArrayList<Integer>();
  ArrayList<Integer> s12 = new ArrayList<Integer>();
  ArrayList<Integer> s13 = new ArrayList<Integer>();
//  ArrayList<Integer> s14 = new ArrayList<Integer>();
  ArrayList<Integer> s15 = new ArrayList<Integer>();
  ArrayList<Integer> s215 = new ArrayList<Integer>();
  ArrayList<Integer> s315 = new ArrayList<Integer>();
  ArrayList<Integer> s16 = new ArrayList<Integer>();
  ArrayList<Integer> s216 = new ArrayList<Integer>();
  ArrayList<Integer> s17 = new ArrayList<Integer>();
  ArrayList<Integer> s18 = new ArrayList<Integer>();
  ArrayList<Integer> s19 = new ArrayList<Integer>();
//s1.add(1);

  private int[][] capacity = new int[5][6];
  private int[][] rawsched = new int[219][74];

  private int[] passes = new int[19];   //18sess,6runs
  private int[][] stuchoices = {
          {1, 0, 1, 12, 3, 2, 9, 0, 0, 0, 0, 0, 0,}, {
          2, 0, 15, 1, 8, 6, 14, 0, 0, 0, 0, 0, 0,}, {
          3, 0, 15, 18, 6, 10, 2, 0, 0, 0, 0, 0, 0,}, {
          4, 0, 8, 10, 1, 15, 16, 0, 0, 0, 0, 0, 0,}, {
          5, 0, 15, 10, 1, 18, 16, 0, 0, 0, 0, 0, 0,}, {
          6, 0, 2, 5, 16, 3, 15, 0, 0, 0, 0, 0, 0,}, {
          7, 0, 2, 3, 9, 7, 16, 0, 0, 0, 0, 0, 0,}, {
          8, 0, 8, 1, 14, 12, 9, 0, 0, 0, 0, 0, 0,}, {
          9, 0, 5, 2, 11, 3, 15, 0, 0, 0, 0, 0, 0,}, {
          10, 0, 2, 15, 1, 16, 7, 0, 0, 0, 0, 0, 0,}, {
          11, 0, 6, 8, 16, 9, 2, 0, 0, 0, 0, 0, 0,}, {
          12, 0, 8, 2, 16, 7, 15, 0, 0, 0, 0, 0, 0,}, {
          13, 0, 1, 3, 2, 10, 11, 0, 0, 0, 0, 0, 0,}, {
          14, 0, 1, 9, 16, 7, 2, 0, 0, 0, 0, 0, 0,}, {
          15, 0, 7, 9, 5, 18, 17, 0, 0, 0, 0, 0, 0,}, {
          16, 0, 7, 15, 12, 5, 1, 0, 0, 0, 0, 0, 0,}, {
          17, 0, 1, 16, 15, 7, 17, 0, 0, 0, 0, 0, 0,}, {
          18, 0, 6, 15, 16, 2, 1, 0, 0, 0, 0, 0, 0,}, {
          19, 0, 13, 7, 16, 1, 2, 0, 0, 0, 0, 0, 0,}, {
          20, 0, 7, 1, 6, 15, 16, 0, 0, 0, 0, 0, 0,}, {
          21, 0, 6, 9, 1, 15, 2, 0, 0, 0, 0, 0, 0,}, {
          22, 0, 1, 15, 10, 18, 2, 0, 0, 0, 0, 0, 0,}, {
          23, 0, 7, 2, 13, 17, 14, 0, 0, 0, 0, 0, 0,}, {
          24, 0, 15, 1, 7, 9, 2, 0, 0, 0, 0, 0, 0,}, {
          25, 0, 9, 1, 10, 2, 6, 0, 0, 0, 0, 0, 0,}, {
          26, 0, 15, 1, 9, 7, 14, 0, 0, 0, 0, 0, 0,}, {
          27, 0, 11, 7, 1, 15, 16, 0, 0, 0, 0, 0, 0,}, {
          28, 0, 11, 3, 2, 7, 16, 0, 0, 0, 0, 0, 0,}, {
          29, 0, 16, 11, 2, 1, 3, 0, 0, 0, 0, 0, 0,}, {
          30, 0, 15, 6, 1, 17, 16, 0, 0, 0, 0, 0, 0,}, {
          31, 0, 9, 18, 2, 13, 6, 0, 0, 0, 0, 0, 0,}, {
          32, 0, 1, 18, 15, 16, 6, 0, 0, 0, 0, 0, 0,}, {
          33, 0, 3, 1, 10, 6, 16, 0, 0, 0, 0, 0, 0,}, {
          34, 0, 11, 13, 5, 3, 6, 0, 0, 0, 0, 0, 0,}, {
          35, 0, 15, 1, 7, 9, 16, 0, 0, 0, 0, 0, 0,}, {
          36, 0, 7, 1, 2, 6, 18, 0, 0, 0, 0, 0, 0,}, {
          37, 0, 15, 1, 9, 7, 17, 0, 0, 0, 0, 0, 0,}, {
          38, 0, 7, 2, 16, 15, 18, 0, 0, 0, 0, 0, 0,}, {
          39, 0, 11, 13, 5, 3, 6, 0, 0, 0, 0, 0, 0,}, {
          40, 0, 9, 13, 7, 1, 2, 0, 0, 0, 0, 0, 0,}, {
          41, 0, 2, 12, 6, 16, 15, 0, 0, 0, 0, 0, 0,}, {
          42, 0, 3, 10, 7, 2, 1, 0, 0, 0, 0, 0, 0,}, {
          43, 0, 2, 9, 1, 12, 15, 0, 0, 0, 0, 0, 0,}, {
          44, 0, 8, 2, 13, 14, 18, 0, 0, 0, 0, 0, 0,}, {
          45, 0, 15, 14, 11, 1, 16, 0, 0, 0, 0, 0, 0,}, {
          46, 0, 13, 14, 4, 11, 16, 0, 0, 0, 0, 0, 0,}, {
          47, 0, 1, 2, 16, 15, 9, 0, 0, 0, 0, 0, 0,}, {
          48, 0, 18, 16, 1, 8, 7, 0, 0, 0, 0, 0, 0,}, {
          49, 0, 2, 15, 3, 1, 18, 0, 0, 0, 0, 0, 0,}, {
          50, 0, 7, 9, 16, 6, 1, 0, 0, 0, 0, 0, 0,}, {
          51, 0, 15, 1, 7, 9, 2, 0, 0, 0, 0, 0, 0,}, {
          52, 0, 1, 3, 2, 16, 18, 0, 0, 0, 0, 0, 0,}, {
          53, 0, 8, 2, 16, 15, 9, 0, 0, 0, 0, 0, 0,}, {
          54, 0, 4, 13, 5, 3, 6, 0, 0, 0, 0, 0, 0,}, {
          55, 0, 11, 13, 5, 3, 6, 0, 0, 0, 0, 0, 0,}, {
          56, 0, 1, 3, 2, 9, 11, 0, 0, 0, 0, 0, 0,}, {
          57, 0, 8, 7, 1, 15, 14, 0, 0, 0, 0, 0, 0,}, {
          58, 0, 15, 1, 2, 6, 7, 0, 0, 0, 0, 0, 0,}, {
          59, 0, 9, 15, 13, 1, 16, 0, 0, 0, 0, 0, 0,}, {
          60, 0, 9, 16, 14, 8, 18, 0, 0, 0, 0, 0, 0,}, {
          61, 0, 7, 1, 16, 6, 15, 0, 0, 0, 0, 0, 0,}, {
          62, 0, 1, 16, 7, 8, 6, 0, 0, 0, 0, 0, 0,}, {
          63, 0, 2, 7, 3, 15, 1, 0, 0, 0, 0, 0, 0,}, {
          64, 0, 8, 14, 17, 18, 2, 0, 0, 0, 0, 0, 0,}, {
          65, 0, 3, 2, 7, 10, 4, 0, 0, 0, 0, 0, 0,}, {
          66, 0, 15, 1, 7, 9, 2, 0, 0, 0, 0, 0, 0,}, {
          67, 0, 15, 1, 9, 11, 6, 0, 0, 0, 0, 0, 0,}, {
          68, 0, 15, 5, 13, 16, 6, 0, 0, 0, 0, 0, 0,}, {
          69, 0, 8, 14, 2, 16, 6, 0, 0, 0, 0, 0, 0,}, {
          70, 0, 15, 1, 7, 9, 2, 0, 0, 0, 0, 0, 0,}, {
          71, 0, 1, 16, 17, 15, 13, 0, 0, 0, 0, 0, 0}, {
          72, 0, 15, 10, 1, 9, 2, 0, 0, 0, 0, 0, 0}, {
          73, 0, 4, 13, 5, 3, 6, 0, 0, 0, 0, 0, 0}, {
          74, 0, 15, 1, 7, 9, 2, 0, 0, 0, 0, 0, 0}};
//74,15,5,7,9,2,

  public String count() {
    //s1.add(1);
    for (int x=0;x<74; x++) {
      for (int y = 2; y < 7; y++) {
        // System.out.println(x + "," +y);
        // for()
        if(stuchoices[x][y]==3){
            if(stuchoices[x][10]==0 &&s3.size()<18) {  //if their 4th choice time slot wasn't already full
              s3.add((x + 1));        //add student to session
              stuchoices[x][10] = 3;
              stuchoices[x][y] = 99;  }
            else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==4){
            if(stuchoices[x][7]==0 &&s4.size()<18) {  //if their 4th choice time slot wasn't already full
              s4.add((x + 1));        //add student to session
              stuchoices[x][7] = 4;
              stuchoices[x][y] = 99;}
            else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==5){
            if(stuchoices[x][11]==0 &&s5.size()<18) {  //if their 4th choice time slot wasn't already full
              s5.add((x + 1));        //add student to session
              stuchoices[x][11]=5;
              stuchoices[x][y] = 99; }
            else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==6){
            if(stuchoices[x][8]==0 &&s6.size()<18) {  //if their 4th choice time slot wasn't already full
              s6.add((x + 1));        //add student to session
              stuchoices[x][8]=6;
              stuchoices[x][y] = 99; }
            else stuchoices[x][1]=(stuchoices[x][1])+1;}

         else if(stuchoices[x][y]==8) {
            if(stuchoices[x][8]==0 &&s8.size()<18) {  //if their 4th choice time slot wasn't already full
              s8.add((x + 1));        //add student to session
              stuchoices[x][8]=8;
              stuchoices[x][y] = 99; }
            else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==10) {
            if(stuchoices[x][8]==0 &&s10.size()<18) {  //if their 4th choice time slot wasn't already full
              s10.add((x + 1));        //add student to session
              stuchoices[x][8]=10;
              stuchoices[x][y] = 99; }
            else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==11) {   //could be a 9
            if(stuchoices[x][9]==0 &&s11.size()<18) {  //if their 4th choice time slot wasn't already full
              s11.add((x + 1));        //add student to session
              stuchoices[x][9]=11;
              stuchoices[x][y] = 99; }
            else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==12){
            if(stuchoices[x][10]==0 &&s12.size()<18) {  //if their 4th choice time slot wasn't already full
              s12.add((x + 1));        //add student to session
              stuchoices[x][10]=12;
              stuchoices[x][y] = 99; }
            else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==13) {
            if(stuchoices[x][7]==0 &&s13.size()<18) {  //if their 4th choice time slot wasn't already full
              s13.add((x + 1));        //add student to session
              stuchoices[x][7]=13;
              stuchoices[x][y] = 99; }
            else stuchoices[x][1]=(stuchoices[x][1])+1;}


        else if(stuchoices[x][y]==17) {
            if(stuchoices[x][8]==0 &&s17.size()<18) {  //if their 4th choice time slot wasn't already full
              s17.add((x + 1));        //add student to session
              stuchoices[x][8]=17;
              stuchoices[x][y] = 99; }
            else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==18) {
            if(stuchoices[x][10]==0 &&s18.size()<18) {  //if their 4th choice time slot wasn't already full
              s18.add((x + 1));        //add student to session
              stuchoices[x][10]=18;
              stuchoices[x][y] = 99; }
            else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==2) {
          if (stuchoices[x][11] == 0 && s2.size() < 18) {  //if their 4th choice time slot wasn't already full
            s2.add((x + 1));        //add student to session
            stuchoices[x][11] = 2;
            stuchoices[x][y] = 99;
          } else if (stuchoices[x][9] == 0 && s202.size() < 18) {  //if their 4th choice time slot wasn't already full
            s202.add((x + 1));        //add student to session
            stuchoices[x][9] = 202;
            stuchoices[x][y] = 99; }
          else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==7) {
          if(stuchoices[x][9]==0 &&s7.size()<18) {  //if their 4th choice time slot wasn't already full
            s7.add((x + 1));        //add student to session
            stuchoices[x][9]=7;
            stuchoices[x][y] = 99; }
          else if(stuchoices[x][7]==0 &&s207.size()<18) {  //if their 4th choice time slot wasn't already full
            s207.add((x + 1));        //add student to session
            stuchoices[x][7]=207;
            stuchoices[x][y] = 99; }
          else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==9) {
          if(stuchoices[x][9]==0 &&s9.size()<18) {  //if their 4th choice time slot wasn't already full
            s9.add((x + 1));        //add student to session
            stuchoices[x][9]=9;
            stuchoices[x][y] = 99; }
          else if(stuchoices[x][11]==0 &&s209.size()<18) {  //if their 4th choice time slot wasn't already full
            s209.add((x + 1));        //add student to session
            stuchoices[x][11]=209;
            stuchoices[x][y] = 99; }
          else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==16){
          if(stuchoices[x][11]==0 &&s16.size()<18) {  //if their 4th choice time slot wasn't already full
            s16.add((x + 1));        //add student to session
            stuchoices[x][11]=16;
            stuchoices[x][y] = 99; }
          else if(stuchoices[x][7]==0 &&s216.size()<18) {  //if their 4th choice time slot wasn't already full
            s216.add((x + 1));        //add student to session
            stuchoices[x][7]=216;
            stuchoices[x][y]=99;}
          else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if (stuchoices[x][y]==1) {           //modified from ***  passes[stuchoices[x][y] ]++;   ** to build arraylist
          if (stuchoices[x][9] == 0 && s1.size() < 18) {  //if their 4th choice time slot wasn't already full
            s1.add((x + 1));//add student to session
            stuchoices[x][9] = 1;
            stuchoices[x][y] = 99;
          } else if (stuchoices[x][7] == 0 && s201.size() < 18) {  //if their 4th choice time slot wasn't already full
            s201.add((x + 1));        //add student to session
            stuchoices[x][7] = 201;
            stuchoices[x][y] = 99;
          } else if (stuchoices[x][11] == 0 && s301.size() < 18) {  //if their 4th choice time slot wasn't already full
            s301.add((x + 1));        //add student to session
            stuchoices[x][11] = 301;
            stuchoices[x][y] = 99; }
          else ;}


        else if(stuchoices[x][y]==15) {
          if(stuchoices[x][7]==0 &&s15.size()<18) {  //if their 4th choice time slot wasn't already full
            s15.add((x + 1));        //add student to session
            stuchoices[x][7]=15;
            stuchoices[x][y] = 99; }
          else if(stuchoices[x][8]==0 &&s215.size()<18) {  //if their 4th choice time slot wasn't already full
            s215.add((x + 1));        //add student to session
            stuchoices[x][8]=215;
            stuchoices[x][y] = 99; }
          else  if(stuchoices[x][10]==0 &&s315.size()<18) {  //if their 4th choice time slot wasn't already full
            s315.add((x + 1));        //add student to session
            stuchoices[x][10]=315;
            stuchoices[x][y] = 99; }
          else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else if(stuchoices[x][y]==14){  //14 was cancelled, so place in 12 or 18?
          if(stuchoices[x][8]==0 &&s8.size()<18) {  //if their 4th choice time slot wasn't already full
            s8.add((x + 1));        //add student to session
            stuchoices[x][8]=8;
            stuchoices[x][y] = 99; }
          else if(stuchoices[x][10]==0 &&s18.size()<18) {  //if their 4th choice time slot wasn't already full
            s18.add((x + 1));        //add student to session
            stuchoices[x][10]=18;
            stuchoices[x][y] = 99; }
          else if(stuchoices[x][7]==0 &&s13.size()<18) {  //if their 4th choice time slot wasn't already full
            s12.add((x + 1));        //add student to session
            stuchoices[x][7]=13;
            stuchoices[x][y] = 99; }
            else stuchoices[x][1]=(stuchoices[x][1])+1;}

        else {
            s19.add(x + 1);
             }

      }
    } //close outer for loop
    return "Count Done";
  }

  public String printLists() {
    Collections.sort(s1);
    Collections.sort(s201);
    Collections.sort(s301);
    Collections.sort(s2);
    Collections.sort(s3);
    Collections.sort(s4);
    //Collections.sort(s204);  slotted but not scheduled - juniors placed in
    Collections.sort(s6);
    Collections.sort(s7);
    Collections.sort(s207);
    Collections.sort(s8);
    Collections.sort(s9);
    Collections.sort(s209);
    Collections.sort(s10);
    Collections.sort(s11);
    Collections.sort(s12);
    Collections.sort(s13);
    //Collections.sort(s14);
    Collections.sort(s15);
    Collections.sort(s215);
    Collections.sort(s315);
    Collections.sort(s16);
    Collections.sort(s216);
    Collections.sort(s17);
    Collections.sort(s18);
    Collections.sort(s19);
    System.out.println(":");
    System.out.println("List of students in first time slot: ");
    System.out.println("List of students in s1,1,015: " + s15);
    System.out.println("List of students in s1,2,013: " + s13);
    System.out.println("List of students in s1,3,207: " + s207);
    System.out.println("List of students in s1,4,201: " + s201);
    System.out.println("List of students in s1,5,216: " + s216);
    System.out.println("List of students in s1,6,004: " + s4);

    System.out.println(":");
    System.out.println("List of students in second slot: ");
    System.out.println("List of students in s2,1,215: " + s215);
    System.out.println("List of students in s2,2,006: " + s6);
    System.out.println("List of students in s2,3,010: " + s10);
    System.out.println("List of students in s2,4,008: " + s8);
    System.out.println("List of students in s2,5,017: " + s17);
    System.out.println(":");
    System.out.println("List of students in third slot: ");
    System.out.println("List of students in s3,1,009: " + s9);
    System.out.println("EMPTY");
    System.out.println("List of students in s3,3,007: " + s7);
    System.out.println("List of students in s3,4,001: " + s1);
    System.out.println("List of students in s3,5,011: " + s11);
    System.out.println("List of students in s3,2,202: " + s202);
    System.out.println(":");
    System.out.println("List of students in forth slot: ");
    System.out.println("List of students in s4,1,209: " + s209);
    System.out.println("EMPTY");
    System.out.println("List of students in s4,3,003: " + s3);
    System.out.println("EMPTY");
    System.out.println("List of students in s4,5,012: " + s12);
    System.out.println("List of students in s4,6,018: " + s18);
    System.out.println(":");
    System.out.println("List of students in 5th time slot");
    System.out.println("List of students in s5,1,315: " + s315);
    System.out.println("EMPTY");
    System.out.println("List of students in s5,3,05: " + s5);
    System.out.println("List of students in s5,4,301: " + s301);
    System.out.println("List of students in s5,5,016: " + s16);
    System.out.println("List of students in s2,2,002: " + s2);
    System.out.println(":");
    System.out.println("Unassigned students: " + s19);
    //System.out.println("Session counts");
    //for(int each:passes){
    // runningTotal+=each;
    //System.out.println(each);  //checks total choices (#kids * 5)
    //}
    return "printLists Done";
  }// ended early total selections: "+ runningTotal); }   // close method

public String studentSessions() {   //puts the schedules back in students printed array
    for (int abc:s1) {  //4,4, [10]
       studID = abc;
          //studID-1 lines up w array, [10] is 4th time slot, 44000 is 44*1000 where first 4 is time, 2nd 4 is loc
      if (stuchoices[studID-1][10]==0) {
        stuchoices[studID-1][10]=1; }//44000+(abc-100);
      else {
        stuchoices[studID-1][12]=1; //44000+(abc-100);  //[12] represents something else in preferred time 4 slot
    }}

  for (int abc:s201) {  //1,4 [7]
     studID = abc;
    if (stuchoices[studID-1][7]==0){
      stuchoices[studID-1][7]=201; }//14000+(abc-100);
    else{
      stuchoices[studID-1][12]=201; //14000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }}
  for (int abc:s301) {  //5,4 [11]
     studID = abc;
    if (stuchoices[studID-1][11]==0) {
      stuchoices[studID - 1][11] = 301; //54000+(abc-100);
    }else{
      stuchoices[studID-1][12]=301; //54000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }}

    for (int abc:s2) {  //2,2 [8]
     studID = abc;    //studID-1 lines up w array, [10] is 4th time slot, 44000 is 44*1000 where first 4 is time, 2nd 4 is loc
      if (stuchoices[studID-1][8]==0)
        stuchoices[studID-1][8]=2; //22000+(abc-100);
      else
        stuchoices[studID-1][12]=2; //22000+(abc-100);  //[12] represents something else in preferred time 4 slot
    }
  for (int abc:s3) {  //4,3, [10]
     studID = abc;
    if (stuchoices[studID-1][10]==0)
      stuchoices[studID-1][10]= 3; //43000+(abc-100);
    else
      stuchoices[studID-1][12]=3; //43000+(abc-100);  //[12] represents something else in preferred time 4 slot
    }
  for (int abc:s4) {  //1, 6 first time slot 7th in student array
     studID = abc;
    if (stuchoices[studID-1][7]==0)
      stuchoices[studID-1][7]=4; //16000+(abc-100);
    else
      stuchoices[studID-1][12]=4; //16000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s5) {
     studID = abc;
    if (stuchoices[studID-1][9]==0)
      stuchoices[studID-1][9]=5; //34000+(abc-100);
    else
      stuchoices[studID-1][12]=5; //34000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s6) {  //4,2 [10]
     studID = abc;
    if (stuchoices[studID-1][10]==0)
      stuchoices[studID-1][10]=6; //42000+(abc-100);
    else
      stuchoices[studID-1][12]=6; //42000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s7) {  //3,3 [9]
     studID = abc;
    if (stuchoices[studID-1][9]==0)
      stuchoices[studID-1][9]=7; //33000+(abc-100);
    else
      stuchoices[studID-1][12]=7; //33000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s207) {  //1,3 [7]
     studID = abc;
    if (stuchoices[studID-1][7]==0)
      stuchoices[studID-1][7]=207; //33000+(abc-100);
    else
      stuchoices[studID-1][12]=207; //  13000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s8) {  //2,4 [9]
     studID = abc;
    if (stuchoices[studID-1][8]==0)
      stuchoices[studID-1][8]=8; //24000+(abc-100);
    else
      stuchoices[studID-1][12]=8; //24000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s9) {  //3,1 [9]
     studID = abc;
    if (stuchoices[studID-1][9]==0)
      stuchoices[studID-1][9]=9; //31000+(abc-100);
    else
      stuchoices[studID-1][12]=9; //31000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s209) {  //4,1 [10]]
     studID = abc;
    if (stuchoices[studID-1][10]==0)
      stuchoices[studID-1][10]=209; //31000+(abc-100);
    else
      stuchoices[studID-1][12]=209; //41000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
//****************************************************************************
  for (int abc:s10) {  //2,3 [8]
     studID = abc;
    if (stuchoices[studID-1][8]==0)
      stuchoices[studID-1][8]=10; //23000+(abc-100);
    else
      stuchoices[studID-1][12]=10; //23000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s11) {  //2,5
     studID = abc;
    if (stuchoices[studID-1][8]==0)
      stuchoices[studID-1][8]=11; //25000+(abc-100);
    else
      stuchoices[studID-1][12]=11; //31000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s12) {  //3,5  [9]
     studID = abc;
    if (stuchoices[studID-1][9]==0)
      stuchoices[studID-1][9]=12;// 35000+(abc-100);
    else
      stuchoices[studID-1][12]=12; //35000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s13) {  //3,1 [9]
     studID = abc;
    if (stuchoices[studID-1][9]==0)
      stuchoices[studID-1][9]=13; //31000+(abc-100);
    else
      stuchoices[studID-1][12]=13; //31000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  /*
  * ***************************
  *  No session #14 due to presenter cancellation
  * for (int abc:s15) {  //3,1 [9]
  * int studID = abc;
  *  if (stuchoices[studID-1][9]==0)
  *    stuchoices[studID-1][9]=31000+(abc-100);
  *  else
  *    stuchoices[studID-1][12]=31000+(abc-100);  //[12] represents something else in preferred time 4 slot
  * }
  * ********************************
  */
  for (int abc:s15) {  //1,1 [7]
     studID = abc;
    if (stuchoices[studID-1][7]==0)
      stuchoices[studID-1][7]=15; //11000+(abc-100);
    else
      stuchoices[studID-1][12]=15; //11000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s215) {  //2,1 [8]
     studID = abc;
    if (stuchoices[studID-1][8]==0)
      stuchoices[studID-1][8]=215; //21000+(abc-100);
    else
      stuchoices[studID-1][12]=215; //21000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s315) {  //5,1 [11]
     studID = abc;
    if (stuchoices[studID-1][11]==0)
      stuchoices[studID-1][11]=315; //51000+(abc-100);
    else
      stuchoices[studID-1][12]=315; //51000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }

  for (int abc:s16) {  //5,5 [11]
     studID = abc;
    if (stuchoices[studID-1][11]==0)
      stuchoices[studID-1][11]=16; //55000+(abc-100);
    else
      stuchoices[studID-1][12]=16; //55000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }

  for (int abc:s216) {  //1,5 [7]
     studID = abc;
    if (stuchoices[studID-1][7]==0)
      stuchoices[studID-1][7]=216; //15000+(abc-100);
    else
      stuchoices[studID-1][12]=216; //15000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }

  for (int abc:s17) {  //5,2 [11]
     studID = abc;
    if (stuchoices[studID-1][11]==0)
      stuchoices[studID-1][11]=17; //52000+(abc-100);
    else
      stuchoices[studID-1][12]=17; //52000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }
  for (int abc:s18) {  //4,6  [10]
     studID = abc;
    if (stuchoices[studID-1][10]==0)
      stuchoices[studID-1][10]=18; //46000+(abc-100);
    else
      stuchoices[studID-1][12]=18; //46000+(abc-100);  //[12] represents something else in preferred time 4 slot
  }

//  *********************************************************************
  fillGaps();
  //System.out.println(printKids());
  return "Done students";  }

  public String printKids() {
    for (int ww=0;ww<stuchoices.length; ww++) {
      System.out.print((stuchoices[ww][0]) + ": ");
      for (int vv = 7; vv < 12; vv++) {    //7 to 13 if only thier assigned slots
        System.out.print(stuchoices[ww][vv] + ", ");
      }
      System.out.println("");
    }
    //stuchoices[conflicts][1]
    for (int conflicts=0;conflicts<74; conflicts++ ){
      numConflicts = numConflicts + stuchoices[conflicts][1];
    }
    System.out.println("Num Conflicts: " + numConflicts);

    return "done printing kids";
  }



public String fillGaps() {
    for (int stu =0; stu<74; stu++) {

        while (s13.size() < 18){
          if (stuchoices[stu][7] == 0)
          s13.add((stu + 1));
          stuchoices[stu][7] = 13;
        }
      while (s4.size() < 18){
        if (stuchoices[stu][7] == 0)
        s4.add((stu + 1));
        stuchoices[stu][7] = 4;
      }
    }   return "Done fill gaps method";
}


public String slotStudents() {
  while (s1.size()>18) {
    s201.add(s1.get(s1.size()-1));
    s1.remove((s1.size()-1));
  }
  Collections.sort(s201);
  while (s201.size()>18) {
    s301.add(s201.get(s201.size()-1));
    s201.remove((s201.size()-1));
  }
  Collections.sort(s301);

  //split sections of session 2
   while (s2.size()>18) {
    s202.add(s2.get(s2.size()-1));
    s2.remove((s2.size()-1));
  }    Collections.sort(s202);

  //split sections of session 7
  while (s7.size()>18) {
    s207.add(s7.get(s7.size()-1));
    s7.remove((s7.size()-1));
  }    Collections.sort(s207);

  //split sections of session 9
  while (s9.size()>18) {
    s209.add(s9.get(s9.size()-1));
    s9.remove((s9.size()-1));
  }    Collections.sort(s209);

  while (s16.size()>18) {
    s216.add(s16.get(s16.size()-1));
    s16.remove((s16.size()-1));
  }    Collections.sort(s216);

  while (s15.size()>18) {
    s215.add(s15.get(s15.size()-1));
    s15.remove((s15.size()-1));
  }  Collections.sort(s215);

  while (s215.size()>18) {
    s315.add(s215.get(s215.size()-1));
    s215.remove((s215.size()-1));
  }  Collections.sort(s315);

  return "Done slotting";
}

 public String populate() {
   System.out.println("LL");
   //choicecol=2;
   for (int b = 0; b<5;b++) {
     for (int k = 0; k < 5; k++) {  //for each row (time slot) in the schedule
       for (int l = 0; l < 6; l++) {
          while (capacity[k][l] < 18 && stuNo < 74) {     //stuno is counting the rows of student choice
           if (sessions[k][l] == stuchoices[stuNo][b + 2]) {   // the two (2) in this line represents students first choice
             stuchoices[stuNo][b + 7] = sessions[k][l];
             rawsched[(sessions[k][l])][(capacity[k][l])] = stuchoices[stuNo][0];
           // System.out.println("cap:" +capacity[k][l] + " stuNo: " + stuNo + ", k:"+k+" ,l:"+l+","+choicecol);
              if (capacity[k][l]==18 && sessions[k][l] == stuchoices[stuNo][b + 2] )  //if session of this slot is full
                stuchoices[stuNo][b + 5] = stuchoices[stuNo][b + 2] + 200;   //add 200 to the students choice to put in next time slot
             capacity[k][l] = capacity[k][l] + 1;
           }
             //moved from 122
            stuNo++;
          }
         stuNo = 0;
         //choicecol++;

       }
     } //close outer for loop
   } //close for loop for b iterator - runs against 5 choices for students and populates their choice

   int testCount=0;
   for(int z=0; z<capacity.length;z++){
     for (int w=0; w<capacity[0].length;w++){
       System.out.print(""+capacity[z][w]+",");
       testCount = testCount + capacity[z][w];
     }System.out.println(" ");
   }
   System.out.print("Count Scheduled: "+ testCount);
   System.out.println("Printing Rough Schedule");
   for(int z=0; z<rawsched.length;z++){
     System.out.print("Session: " + z + ": ");
     for (int w=0; w<rawsched[0].length;w++){
       System.out.print(""+rawsched[z][w]+",");
       testCount = testCount + rawsched[z][w];
     }System.out.println(" ");
   }
   return ("Done populate method");
 }




}
