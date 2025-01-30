# 📚 LTU Library Management System

## **Project Overview**
This project is a **Library Management System** designed to manage books and their lending status.  
It was developed as a **time-limited 8-hour challenge**, where I implemented core functionalities necessary for an efficient book-tracking system.

## **🛠️ Features**
- ✅ **Add Books**: Assign unique IDs and store valid ISBN numbers.
- ✅ **Remove Books**: Remove books that are not currently loaned.
- ✅ **Loan Books**: Keep track of book loan details.
- ✅ **Return Books**: Calculate rental duration and late fees.
- ✅ **Print Sorted Book List**: Display all books in a structured format.
- ✅ **Print Loan Summary**: Show revenue and total loans.

## **🗃️ How to Use**
1. Clone the repository:
   ```sh
   git clone git@github.com:StellaZhang-Dev/SoftwareDevelopment_DataStorage_LibrarySystem.git
   cd SoftwareDevelopment_DataStorage_LibrarySystem
   ```
2. Compile the program:
   ```sh
   javac Main.java
   ```
3. Run the program:
   ```sh
   java Main
   ```
4. Follow the interactive menu options to manage books and loans.

## **📌 Code Structure**
The project follows a structured approach:
- `addBook()`: Adds books to the system with unique ID and valid ISBN.
- `removeBook()`: Removes available books from the system.
- `loanBook()`: Handles book loans and borrower details.
- `returnBook()`: Calculates loan duration, late fees, and updates records.
- `printBookList()`: Displays all available books in a sorted manner.
- `printLoanSummary()`: Prints a summary of all loans and revenue.

## **📚 License**
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
**👩‍💻 Developed by Stella Zhang**


