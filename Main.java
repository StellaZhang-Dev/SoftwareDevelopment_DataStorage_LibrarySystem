import java.util.Scanner;
import java.util.Random;

/**
 * This program focus on a library system to manage books and their lending status.
 *
 * Users can do something like this:
 * Add books with unique IDs and valid ISBNs;
 * Remove books that are not currently loaned;
 * Loan books and keep trach of loan details;
 * Return books and calculate costs;
 * Print a sorted list about all books;
 * Print a summary of all loans and revenue.
 *
 * @author Stella Zhang
 */
public class Main {

    //global constants and Scanner object
    private static final Scanner userInputScanner = new Scanner(System.in);
    private static final int MAX_BOOKS = 100;
    private static final int QUIT = -1;
    private static final int LOAN_FREE_DAYS = 10;
    private static final int DAILY_LATE_FEE = 15;
    private static final int DAYS_IN_MONTH = 30;
    private static final int DAYS_IN_YEAR = 360;
    private static final int MAX_TITLE_LENGTH = 5;
    private static final int MIN_BOOK_ID = 1000;
    private static final int MAX_BOOK_ID = 9000;


    public static void main(final String[] args) {
        String[][] books = new String[MAX_BOOKS][MAX_TITLE_LENGTH];
        String[][] loans = new String[MAX_BOOKS][MAX_TITLE_LENGTH];
        int bookCount = 0;
        int loanCount = 0;

        while (true) {
            printMenu();
            String choice = userInputScanner.nextLine().trim();
            switch (choice) {
                case "1":
                    bookCount = addBook(books, bookCount);
                    break;
                case "2":
                    bookCount = removeBook(books, loans, bookCount);
                    break;
                case "3":
                    loanCount = loanBook(books, loans, bookCount, loanCount);
                    break;
                case "4":
                    loanCount = returnBook(books, loans, bookCount, loanCount);
                    break;
                case "5":
                    printBookList (books, bookCount);
                    break;
                case "6":
                    printLoanSummary (loans, loanCount);
                    break;
                case "q":
                    System.out.println("Exiting LTU Library System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid menu item. Please try again.");
                   
            }
        }
    }

    /**
     * print the main menu for the LTU Library system
     */
    private static void printMenu() {
        System.out.println("------------------------------------");
        System.out.println("-----------# LTU Library-----------");
        System.out.println("------------------------------------");
        System.out.println("1. Add book");
        System.out.println("2. Remove book");
        System.out.println("3. Loan a book");
        System.out.println("4. Return a book");
        System.out.println("5. Print book list");
        System.out.println("6. Print lending summary");
        System.out.println("q. End program");
        System.out.println("> Enter your option: ");
    }

    /**
     * adds a book to the library system.
     *
     * @param books 
     * @param bookCount
     * @return the updated book count
     */
    private static int addBook(final String[][] books, int bookCount) {
        if (bookCount >= MAX_BOOKS) {
            System.out.println("Library capacity reached. Can not add more books.");
            return bookCount;
        }
        System.out.println("> Enter book title: ");
        String title = userInputScanner.nextLine().trim();

        System.out.println("> Enter ISBN-10 code: ");
        String isbn = userInputScanner.nextLine().trim();

        if (!isbn.matches("\\d{9}-\\d")) {
            System.out.println("Invalid ISBN format. Please use format: 123456789-0");
            return bookCount;
        }

        for (int i = 0; i < bookCount; i++) {
            if (books[i][1].equals(isbn)) {
                System.out.println("ISBN " + isbn + " already exists.\n");
                return bookCount;
            }
        }

        Random random = new Random();
        int id;
        boolean idExists;
        do {
            id = MIN_BOOK_ID + random.nextInt(MAX_BOOK_ID);
            idExists = false;
            for (int i = 0; i < bookCount; i++) {
                if (Integer.parseInt(books[i][0]) == id) {
                    idExists = true;
                    break;
                }
            }
        } while (idExists);

        books[bookCount][0] = String.valueOf(id);
        books[bookCount][1] = isbn;
        books[bookCount][2] = title;
        books[bookCount][3] = "Available";
        System.out.println("Book with title " + title + " was assigned ID " + id + " and added to the system.");
        return bookCount + 1;
    }

    /**
     * Removes a book from the library system
     *
     * @param books
     * @param loans
     * @param bookCount
     * @return the updated book count
     */
    private static int removeBook(final String[][] books, String[][] loans, int bookCount) {
        System.out.print("> Enter book ID number: ");
        String id = userInputScanner.nextLine().trim();

        for (int i = 0; i < bookCount; i++) {
            if (books[i][0].equals(id)) {
                if (!books[i][3].equals("Available")) {
                    System.out.println("Book with ID " + id + " is loaned out and needs to be returned before removal.");
                    return bookCount;
                }
                System.out.println("Book " + books[i][2] + " was removed from the system.");
                books[i] = books[bookCount - 1];
                return bookCount - 1;
            }
        }
        System.out.println("ID " + id + " Does not exist.\n");
        return bookCount;
    }

    /**
     * Loans a book
     *
     * @param books 
     * @param loans 
     * @param bookCount
     * @param loanCount
     * @return the updated book count
     */
    private static int loanBook(final String[][] books, String[][] loans, int bookCount, int loanCount) {
        System.out.println("> Enter book ID number: ");
        String id = userInputScanner.nextLine().trim();

        int bookIndex = -1;
        for (int i = 0; i < bookCount; i++) {
            if (books[i][0]. equals(id)) {
                bookIndex = i;
                break;
            }
        }

        if (!books[bookIndex][3].equals("Available")) {
            System.out.println("Book " + books[bookIndex][2] + " is already loaned");
            return loanCount;
        }

        System.out.print("> Enter lender's name: ");
        String lender = userInputScanner.nextLine().trim();

        System.out.print("> Enter start date of the loan (YYYY-MM-DD): ");
        String date = userInputScanner.nextLine().trim();

        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invaldi date format. Please use YYYY-MM-DD.");
            return loanCount;
        }

        books[bookIndex][3] = "Loaned";
        loans[loanCount][0] = id;
        loans[loanCount][1] = lender;
        loans[loanCount][2] = date;
        loans[loanCount][3] = "Ongoing";
        System.out.println("Book " + books[bookIndex][2] + " was loaned by " + lender + " on " + date + ".");
        return loanCount + 1;
    }

    /**
     * return a book
     *
     * @param books
     * @param loans 
     * @param bookCount
     * @param loanCount
     * @return the updated book count
     */
    private static int returnBook(final String[][] books, String[][] loans, int bookCount, int loanCount) {
        System.out.println("> Enter book ID number: ");
        String id = userInputScanner.nextLine().trim();

        int loanIndex = -1;
        for (int i = 0; i < loanCount; i++) {
            if (loans[i][0]. equals(id) && loans[i][3]. equals("Ongoing")) {
                loanIndex = i;
                break;
            }
        }

        if (loanIndex == -1) {
            System.out.println("ID " + id + " is not currently loaned.\n");
            return loanCount;
        }

        System.out.print("> Enter return date (YYYY-MM-DD): ");
        String returnDate = userInputScanner.nextLine().trim();

        if (!returnDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invaldi date format. Please use YYYY-MM-DD.");
            return loanCount;
        }

        int bookIndex = -1;
        for (int i = 0; i < bookCount; i++) {
            if (books[i][0]. equals(id)) {
                bookIndex = i;
                break;
            }
        }

        if (bookIndex == -1) {
            System.out.println("Book with ID " + id +" does not exist.");
            return loanCount;
        }

        String lender = loans[loanIndex][1];
        String startDate = loans[loanIndex][2];

        int duration = calculateDaysBetween(startDate, returnDate);
        int cost = Math.max(0, (duration - LOAN_FREE_DAYS) * DAILY_LATE_FEE);


        books[bookIndex][3] = "Available";
        loans[loanIndex][3] = returnDate;

        
        System.out.println("Lender's name: " + lender);
        System.out.println("Book title: " + books[bookIndex][2]);
        System.out.println("ISBN-10: " + books[bookIndex][1]);
        System.out.println("Period: " + startDate + " to " + returnDate);
        System.out.println("Duration: " + duration + " days");
        System.out.println("Cost: " + cost);
        return loanCount;

    }

    /**
     * Calculates the number of days
     *
     * @param startDate
     * @param endDate 
     * @return the number of days between startDate and endDate
     */
    private static int calculateDaysBetween(final String startDate, String endDate) {
        String[] start = startDate.split("-");
        String[] end = endDate.split("-");

        int startYear = Integer.parseInt(start[0]);
        int startMonth = Integer.parseInt(start[1]);
        int startDay = Integer.parseInt(start[2]);

        int endYear = Integer.parseInt(end[0]);
        int endMonth = Integer.parseInt(end[1]);
        int endDay = Integer.parseInt(end[2]);

        int startTotalDays = startYear * DAYS_IN_YEAR + startMonth * DAYS_IN_MONTH + startDay;
        int endTotalDays = endYear * DAYS_IN_YEAR + endMonth * DAYS_IN_MONTH + endDay;

        return endTotalDays - startTotalDays;
    }

    /**
     * Prints the list of books in the library
     *
     * @param books
     * @param bookCount
     */
    private static void printBookList(final String[][] books, int bookCount) {
        System.out.println("Book list LTU Library");
        System.out.println("ID    ISBN-10    Title    Status");
        for (int i = 0; i < bookCount; i++) {
            System.out.printf("%-6s %-14s %-22s %-10s%n", books[i][0], books[i][1], books[i][2], books[i][3]);
        }
    }

    /**
     * Prints the summary of all loans
     *
     * @param loans
     * @param loanCount
     */
    private static void printLoanSummary(final String[][] loans, int loanCount) {
        System.out.println("Loan summary LTU Library");
        System.out.println("ID    Lender    Start Date    Return Date Cost");

        int totalLoans = 0;
        int totalCost =0;

        for (int i = 0; i < loanCount; i++) {
            String id = loans[i][0];
            String lender = loans[i][1];
            String startDate = loans[i][2];
            String returnDate = loans[i][3];

            if (returnDate != null && !returnDate.equals("Ongoing")) {
                int duration = calculateDaysBetween(startDate, returnDate);
                int cost = Math.max(0, (duration - LOAN_FREE_DAYS) * DAILY_LATE_FEE);

                System.out.printf("%-6s %-13s %-12s %-12s %d%n", id, lender, startDate, returnDate, cost);

                totalLoans++;
                totalCost += cost;
            }

            System.out.println("------------------------------------");
            System.out.println("Number of loans: " + totalLoans);
            System.out.println("Total cost: " + totalCost);

        }
    }
}
