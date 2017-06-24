import java.lang.*;
import java.util.*;
import java.awt.*;

class Moderate {
  public static boolean ticTacToe(char[][] board){
    int i = 0;
      while (i<3){
        if (checkDown(board, new Point(0,i),0,'x')){
          return true;
        }
        else{
          i++;
        }
      }
      return false;

  }
  public static boolean checkDown(char[][] board, Point loc, int count, char team){
      if (loc.getX() >= 3){
        return false;
      }
      char mark = board[(int)loc.getX()][(int)loc.getY()];
      int c = count;
      System.out.println(loc.getX()+","+loc.getY()+mark +team+" "+count);
      if (mark == team){
        if (count == 2){
          return true;
        }
        else {
          int newX = (int) loc.getX() + 1;
          Point newLoc = new Point(newX, (int)loc.getY());
          return checkDown(board, newLoc, c+1, team);
        }
      }
      else {
        return false;

      }
  }


  public static Point smallestDifference(int[] array1, int[] array2){
    // Brute Force
    // Iterate through each value in array1 of length n
    // compute difference from each value in array2 of length m
    // O(n*m)


    // Find the two numbers in the second array closest to the value in array1
    // 1) Sort the Arrays
    // 2) Iterate through array1
    // 3) Compare with values in array 2, by taking the midpoint
    // 3a) if the values are the same return that value
    // 3b) if the value in array 1 is smaller, take the first half of the array and save that value as nearest large value
    // 3c) if value in array 1 is larger, take the second half of the array and save that value as nearest small value

    Arrays.sort(array1);
    Arrays.sort(array2);

    Point closest = null;

    for (int i : array1){
      Point testValues = smallestDifferenceHelper(array2,i,0,array2.length-1,-1);
      if (closest == null){
        closest = testValues;
      }
      int diff1 = Math.abs((int) closest.getX() - (int) closest.getY());
      int diff2 = Math.abs((int) testValues.getX() - (int) testValues.getY());
      if (diff1>diff2){
        closest = testValues;
      }
    }
    return closest;
  }

  public static Point smallestDifferenceHelper(int[] array, int value, int start, int end, int nearest){
    int index = (start + end)/2;
    // if we haven't found the value, return the closest value
    if (end < start){
      if (nearest != -1){
        return new Point(value,nearest);
      }
    }

    // check if nearest so far
    if (nearest == -1){
      nearest = array[index];
    }
    else {
      int diff = Math.abs(array[index]-value);
      int diffN = Math.abs(nearest-value);
      if (diff < diffN){
        nearest = array[index];
      }
    }
    // if value == index return Point
    if (array[index] == value ){
      return new Point(value, index);
    }
    else if(array[index] > value){
      return smallestDifferenceHelper(array, value, start, index-1, nearest);
    }
    else{
      return smallestDifferenceHelper(array, value, index+1, end, nearest);
    }
  }
  //
  // Question: Determine if any 3 integers in an array sum to 0.
  // Note: The following solutions assumes that repetitions
  //(i.e. choosing the same array element more than once)
  // are *allowed*, so the array [-5,1,10] contains a zero sum
  //(-5-5+10) and so does [0] (0+0+0).
  // [4, 2, -1, 1, -5, 6, -4] = True




  // public static int mostPeople(ArrayList<int[]> peopleYears){
  //   int[] countYears = new int[(2017-1900)+1];
  //   for (int[] personYears : peopleYears){
  //     for (int i = personYears[0]; i <= personYears[1]; i++){
  //       countYears[2017-i]++;
  //     }
  //   }
  //   int maxIndex = 0;
  //   int maxCount = 0;
  //   for (int j = 0; j < countYears.length; j++){
  //     if (countYears[j] > maxCount){
  //       maxIndex = j;
  //       maxCount = countYears[j];
  //     }
  //   }
  //
  //   System.out.println("MaxCount: " + maxCount);
  //   return 2017-maxIndex;
  // }

  public static int mostPeople(int[][] peopleYears){
    int[] births = peopleYears[0];
    int[] deaths = peopleYears[1];
    int maxYear = 0;
    int maxCount = 0;
    int runningCount = 0;

    Arrays.sort(births);
    Arrays.sort(deaths);

    int j = 0;

    for (int i = 0; i < births.length; i++){

      // check if there has been any deaths since the last birthyear until this & including year
      while (deaths[j] < births[i]){
        runningCount--;
        j++;
      }
      // add one for a birth
      runningCount++;

      if (runningCount > maxCount){
        maxCount = runningCount;
        maxYear = births[i];
      }
    }

    return maxYear;

  }

//Given an array of integers, write an in-place function to
//bring all the non-zero elements to the left of the array keeping the original order.

  public static int[] bringFront(int[] array){
    // maintain index of last non-zero
    // iterate through the array
    // - if nonzero,
    // -  if index+1 != currentIndex
    // -    swap index+1 and non-zero; increment index & currentIndex
    // - else increment currentIndex
    int lastNonZero = -1;
    for (int i = 0; i < array.length; i++){
      if (array[i] != 0){
        lastNonZero++;
        if (lastNonZero < i){
          array[lastNonZero] = array[i];
          array[i] = 0;
        }
      }
    }

    return array;
  }


  public static int[] sumSwap(int[] integers1, int[] integers2){
    //Brute force:
    // iterate through array1 (n),
    // check if difference with each value in array2 (m) == the difference needed
    // O(n*m)

    // ASSUME for now array1 sum < array2 sum
    // 1) Sum each array (O(n + m))
    // 2) The difference in sums/2 is the diference between the two ints you need to swap
    int sum1 = 0;
    for (int i = 0; i < integers1.length; i ++){
      sum1+=integers1[i];
    }
    int sum2 = 0;
    for (int j = 0; j < integers2.length; j ++){
      sum2+=integers2[j];
    }


    int[] array1;
    int[] array2;
    if(sum1 == sum2){
      return new int[2];
    }
    else if (sum1 < sum2){
      array1 = integers1;
      array2 = integers2;
    } else {
      array1 = integers2;
      array2 = integers1;
    }


    int diff = (sum2-sum1)/2;
    System.out.println("diff needed: ("+ sum2 + "-"+ sum1 +")/2= " + diff);

    //Sort arrays (O(nlogn + mlogm))
    Arrays.sort(array1);
    Arrays.sort(array2);

    int x = 0;
    int y = array2.length - 1;

    // iterate through both arrays with two pointers trying to find if there is
    // two ints that match the diff (O(n))
    while(x < array1.length && y > -1){
      int a = array1[x];
      int b = array2[y];
      int test = b-a;
      System.out.println(test + "= " + b +" - "+ a);
      if (test == diff){
        int[] result = {a,b};
        return result;
      }
      else if(test > diff) {
        y--;
      }
      else{
        x++;
      }
    }

    return new int[2];

  }

 public static int[] unsortedPairSum(int[] array, int sumGoal){
   // Brute Force
   // iterate through all ints in array and check if the sums equal the sumGoal
   // O(n^2)

   // Put all ints into a hashTable
   // iterate through each int in array
   // - look to see if sumGoal - array[i] in hashmap
   // O(n)

   HashMap<Integer, Integer> countInt = new HashMap<Integer, Integer>();

   for (int i = 0; i < array.length; i ++){
     if (countInt.get(array[i]) == null){
       countInt.put(array[i],1);
     }
     else {
       int currentVal = countInt.get(array[i]);
       countInt.put(array[i],currentVal+1);

     }
   }

   for (int j = 0; j < array.length; j ++){
     int curInt = array[j];
     int neededInt = sumGoal - curInt;

     int test = 0;
     if (countInt.get(neededInt) != null){
       test = countInt.get(neededInt);
     }

     if ((curInt != neededInt && test >= 1)
          || test >= 2){
            int[] result = {array[j],neededInt};
            return result;
          }
    }

    return new int[2];
 }

 public static int maximumProfit(int[] stockPrices){
   int maxProfit = 0;
   int i = 0;
   int j = stockPrices.length-1;

   int minX = stockPrices[0];
   int maxY = stockPrices[stockPrices.length-1];

   while (i <= stockPrices.length && j >= 0 && i<j){
     int x = stockPrices[i];
     int y = stockPrices[j];

     System.out.println("("+x+","+y+")");

     if(x < minX){
       minX = x;
       System.out.println("minX: " + minX);
     }
     if (y > maxY){
       maxY = y;
       System.out.println("maxY: "+ maxY);
     }
     int currDiff = maxY - minX;
     if (currDiff > maxProfit){
       maxProfit = currDiff;
       System.out.println("max: " + maxProfit);
     }

     if (y > minX){
       i++;
     } else{
       j--;
     }
   }
   return maxProfit;
 }

  public static void main(String args[]){
  //   int[] test1 = {5,3,6,1};
  //   int[] test2 = {6,9,5,2,3};
  //
  //   for(int i : sumSwap(test1,test2)){
  //     System.out.println(i +" ");
  //   }
  //

  // int[] test1 = {1,2,3,4};
  //   for(int i : unsortedPairSum(test1,6)){
  //     System.out.println(i +" ");
  //   }


  int[] stocks = {25,30,48,15,25,45,10,25};
  System.out.println(maximumProfit(stocks));
  int[] stocks2 = {23,55,67,40,65,44,20};
  System.out.println(maximumProfit(stocks2));

  }
}
