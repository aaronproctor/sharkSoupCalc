import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SharkSoupCalc {
    /**
     * This method gets the index of the smallest numbers
     *
     * @param arr
     * @param value
     * @return
     *
     * Source: https://stackoverflow.com/a/34173462
     */
    public static int getArrayIndex(int[] arr, int value) {


        int k = 0;
        for (int i = 0; i < arr.length; i++) {

            if (arr[i] == value) {
                k = i;
                break;
            }
        }
        return k;
    }



    /**
     * This method finds the smallest 2 numbers and their index
     *
     * @param soupIngredients
     * @return
     */
    private static int[] findSmallestNumbers(int[] soupIngredients) {
        int smallestItem = -1;
        int secondItem = -1;
        int smallest = Integer.MAX_VALUE;
        int secondSmallest = Integer.MAX_VALUE;

        for (int i = 0; i < soupIngredients.length - 2; i++) {
            if (smallest > soupIngredients[i]) {
                smallestItem = i;
                smallest = soupIngredients[i];
            }

        }

        for (int i = 0; i < soupIngredients.length - 2; i++) {
            if (i == smallestItem) ;     // Skip this value, don't want a repeat of smallest
            else if (secondSmallest > soupIngredients[i] && soupIngredients[i] >= smallest) {
                secondItem = i;
                secondSmallest = soupIngredients[i];
            }

        }

        // If the two smallest items are shells and oil, set oil to smallest and find next smallest
        if (smallestItem == 0 && secondItem == 1) {
            smallestItem = 1;
            smallest = secondSmallest;

            secondItem = 2;
            secondSmallest = soupIngredients[2];

            for (int i = 3; i < soupIngredients.length - 2; i++) {
                if (soupIngredients[i] < secondSmallest) {
                    secondItem = i;
                    secondSmallest = soupIngredients[i];
                }
            }
        }

        int[] smallestArray = {smallestItem, smallest, secondItem, secondSmallest};

        return smallestArray;

    }



    /**
     * This method calculates how many soups can be made with the current amount of ingredients
     *
     * @param soupIngredients
     * @param amountOfSoups
     * @return
     */
    private static int calculateSharkSoup(int[] soupIngredients, int amountOfSoups) {
        boolean outOfAnIngredient = false;

        while (outOfAnIngredient == false) {
            //If all ingredients are greater than 0
            if ((soupIngredients[0] > 0) && (soupIngredients[1] > 0) && (soupIngredients[2] > 0) &&
                    (soupIngredients[3] > 0) && (soupIngredients[4] > 0)) {
                for (int i = 0; i <= 4; i++) {
                    //Remove 1 from each item
                    soupIngredients[i] -= 1;
                }
                amountOfSoups++;
            }
            // Otherwise exit while loop
            else {
                outOfAnIngredient = true;
                break;
            }
        }

        return amountOfSoups;
    }



    /**
     * This method is for calculating the transmutation of oil from salt
     *
     * @param soupIngredients
     * @param smallestItemArray
     * @return
     */
    private static int[] calculateOilTransmutation(int[] soupIngredients, int[] smallestItemArray) {
        int saltAmount = soupIngredients[2];
        int oilAmount = soupIngredients[1];
        int energyAmount = soupIngredients[5];
        int transmuteAmount = 0;

        // While oil + 10 is less than the second smallest amount and oil + 30 is less than salt amount and energy
        // amount is greater than 10
        while ((oilAmount + 10 <= smallestItemArray[3]) && (oilAmount + 30 <= saltAmount) && (energyAmount >= 20)) {
            saltAmount -= 20;
            oilAmount += 10;
            energyAmount -= 20;
            transmuteAmount++;
        }

        soupIngredients[2] = saltAmount;
        soupIngredients[1] = oilAmount;
        soupIngredients[5] = energyAmount;
        soupIngredients[7] += transmuteAmount;
        return soupIngredients;
    }



    /**
     * This method is for calculating the transmutation of shells from oil
     *
     * @param soupIngredients
     * @param smallestItemArray
     * @return
     */
    private static int[] calculateShellsTransmutation(int[] soupIngredients, int[] smallestItemArray) {

        int shellAmount = soupIngredients[0];
        int oilAmount = soupIngredients[1];
        int energyAmount = soupIngredients[5];
        int transmuteAmount = 0;

        // While shell + 10 is less than the second smallest amount and shell+10 is less than oil amount and energy
        // amount is greater than 10
        while ((shellAmount + 10 <= smallestItemArray[3]) && (shellAmount + 10 <= oilAmount) && (energyAmount >= 10)) {
            oilAmount -= 10;
            shellAmount += 10;
            energyAmount -= 10;
            transmuteAmount++;
        }

        soupIngredients[0] = shellAmount;
        soupIngredients[1] = oilAmount;
        soupIngredients[5] = energyAmount;
        soupIngredients[6] += transmuteAmount;

        return soupIngredients;
    }



    public static void main(String[] args) {


        String[] soupIngredientsNames = {"Shiny tortle shell bowls", "Wobbegong Oil", "Alaea Sea Salt",
                "Bundles of Bamboo", "Sliced Mushrooms", "Ancestral Energy"};
        int[] soupIngredients = new int[8];
        int[] smallestItemArray = new int[4]; // Smallest Item, Amount, Second Smallest, Amount

        int sharkSoup;
        boolean areIngredientsEqual = false;

        Scanner s = new Scanner(System.in);

        // Gets ingredient amounts from user.
        for (int i = 0; i <= 5; i++) {
            System.out.printf("How many %s do you have? ", soupIngredientsNames[i]);
            try {
                //Checks if the scanner gets a character other than an integer. If so add to the array
                if (s.hasNextInt()) {
                    soupIngredients[i] = s.nextInt();
                }
                // Otherwise throw a InputMismatchException and allow the user to enter the amount again
                else {
                    soupIngredients[i] = 0;
                    s.next();
                    i = -1;
                    throw new InputMismatchException("Error, number must be a positive integer.");
                }
                //Checks if the scanner gets a positive integer. If so add to the array
                if (soupIngredients[i] < 0) {
                    // Otherwise throw a IllegalArgumentException and allow the user to enter the amount again
                    i = -1;
                    throw new IllegalArgumentException("Error, number must be a positive integer.");
                }
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        sharkSoup = calculateSharkSoup(soupIngredients, 0);

        while (!areIngredientsEqual) {
            smallestItemArray = findSmallestNumbers(soupIngredients);
            soupIngredients = calculateOilTransmutation(soupIngredients, smallestItemArray);
            sharkSoup = calculateSharkSoup(soupIngredients, sharkSoup);

            smallestItemArray = findSmallestNumbers(soupIngredients);
            soupIngredients = calculateShellsTransmutation(soupIngredients, smallestItemArray);
            sharkSoup = calculateSharkSoup(soupIngredients, sharkSoup);

            if (smallestItemArray[0] == 2 && soupIngredients[2] < 10) {
                break;
            } else if (smallestItemArray[0] == 1 && soupIngredients[2] < 30 && soupIngredients[0] < 10) {
                break;
            } else if (smallestItemArray[0] == 0 && soupIngredients[1] < 20) {
                break;
            } else if (smallestItemArray[0] == 3 || smallestItemArray[0] == 4) {
                break;
            }
        }
        System.out.printf("\n");
        if (soupIngredients[7] > 0) {
            System.out.printf("Transmute oil %s times using %s salt and %s energy\n", soupIngredients[7],
                    (soupIngredients[7] * 20), (soupIngredients[7] * 20));
        }
        if (soupIngredients[6] > 0) {
            System.out.printf("Transmute shells %s times using %s oil and %s energy\n", soupIngredients[6],
                    (soupIngredients[6] * 10), (soupIngredients[6] * 10));
        }

        System.out.printf("\nTotal amount of soups: %s", sharkSoup);
        System.out.printf("\n\nRemaining Items");
        for (int i = 0; i <= 5; i++) {
            System.out.printf("\n%s %s", soupIngredientsNames[i], +soupIngredients[i]);
        }
    }
}