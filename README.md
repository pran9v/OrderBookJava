# OrderBookJava
![image](https://github.com/user-attachments/assets/fa3af736-793d-4bb1-bab4-2dbe19c5eac5)


**OrderBookJava** is a terminal-based implementation of an electronic order book for trading systems, written in Java. It provides fast and efficient operations for adding, removing, and executing buy and sell orders with O(1) time complexity.

## Key Features
- **Types of Orders**:
  - Limit Order
  - Market Order
     
- **Order Management**:
  - Add new buy or sell orders.
  - Execute trades based on available orders.
  
- **Matching Logic**:
  - Automates the matching of compatible buy and sell orders based on price and quantity.
  - Ensures trades are executed efficiently and accurately.

- **Optimized Performance**:
  - Implements data structures to achieve O(1) time complexity for core operations.

## Project Structure

- **Core Classes**:
  - `OrderBook`: Manages the main operations for the order book, including adding, removing, and matching orders.
  - `Order`: Represents a single order with details like order type (buy/sell), price, and quantity.
  - `OrderBookApp`: Starting point of the program.

- **Flow**:
  1. Orders are added to the system.
  2. The matching logic identifies compatible buy and sell orders.
  3. Matched orders are executed and removed from the order book.

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 11 or higher.

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/pran9v/OrderBookJava.git
   cd OrderBookJava
   ```

2. Open the project in your preferred IDE.

3. Build the project:
   - For IntelliJ/Eclipse: Import the project as a Maven project and let the dependencies resolve.
   - Using the terminal:
     ```bash
     mvn clean install
     ```

4. Run the application:
   ```bash
   java -jar target/OrderBookJava.jar
   ```
   
### Usage

1. Add orders:
   - Provide order details via the terminal input.
2. View matched orders:
   - Orders matching the criteria are displayed and executed.
3. Monitor the order book:
   - Unmatched orders remain in the system for future execution.
